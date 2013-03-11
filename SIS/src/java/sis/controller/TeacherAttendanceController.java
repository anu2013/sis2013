/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.controller;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.UserTransaction;
import sis.model.Attendancetracking;
import sis.model.StudentSchedule;
import sis.model.Studentgradelevel;
import sis.model.TeacherSchedule;

/**
 *
 * @author Anupama Karumudi
 */
@ManagedBean(name="teacherAttendanceController")
@ViewScoped
public class TeacherAttendanceController implements Serializable{
    @PersistenceUnit(unitName="SISPU")
    private EntityManagerFactory entityManagerFactory;
    
    @Resource
    private UserTransaction userTransaction;
    
    @ManagedProperty("#{userController}")
    private UserController userController;
    
    @ManagedProperty("#{teacherSchedulesController}")
    private TeacherSchedulesController schedulesController;
    
    private List<StudentSchedule> studentSchedules;
    private Integer selectedScheduleId;
    private Date selectedDate;
    
    @PostConstruct
    public void init(){
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String sid = request.getParameter("sid");
        if(null != sid){
            selectedScheduleId = Integer.parseInt(sid);
        }
        if(null == selectedScheduleId)
            selectedScheduleId = 0;
        if(null == selectedDate)
            selectedDate = new Date();
        if(selectedScheduleId > 0) {
            populateAttendance();
        }
    }

    private void populateAttendance(){
        try{
            setStudentSchedules(null);
            
            if(selectedScheduleId < 1){
                setErrorMessage("Please select a period to start the attendance.");
                return;
            }
            
            if(null == selectedDate){
                setErrorMessage("Please select a date to update the attendance.");
                return;
            }
            
            if(selectedDate.compareTo(new Date()) > 0){
                setErrorMessage("Please select a valid date. You can not take attendance for future dates.");
                return;
            }
            
            if(selectedDate.before(userController.getCurrentSchoolYearShedule().getStartdate()) ||  selectedDate.after(userController.getCurrentSchoolYearShedule().getEnddate())) {
                setErrorMessage("Please select a valid date. The selected date is not within the current school year's satrt and end date.");
                return;
            }
            
            Calendar cal = new GregorianCalendar();
            cal.setTime(selectedDate);
            int day = cal.get(Calendar.DAY_OF_WEEK);
            if(day == 0 || day == 6){
                setErrorMessage("Please select a valid date. The selected date is not a week day.");
                return;
            }

            String scheduleDays = getSelectedSchedule().getScheduledays();
            if(null == scheduleDays) scheduleDays = "";
            scheduleDays = scheduleDays.replace(" ", "");
            scheduleDays = scheduleDays.replaceAll("(?i)F", "5");
            scheduleDays = scheduleDays.replaceAll("(?i)TH", "4");
            scheduleDays = scheduleDays.replaceAll("(?i)W", "3");
            scheduleDays = scheduleDays.replaceAll("(?i)T", "2");
            scheduleDays = scheduleDays.replaceAll("(?i)M", "1");
            scheduleDays += ",";
            if(scheduleDays.indexOf((day + ",")) < 0){
                setErrorMessage("You don't have any scheule for the selected day and period.");
                return;
            }
                
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            String queryString = "select ss from StudentSchedule ss where ss.schedule.subjectscheduleid = :ssid";
            Query query = entityManager.createQuery(queryString);
            query.setParameter("ssid", getSelectedSchedule().getSubjectscheduleid());
            List<StudentSchedule> records = query.getResultList();
            if(records != null){
                for(StudentSchedule ss : records){
                    // Get Grade Level
                    queryString = "select sgl from Studentgradelevel sgl where sgl.student.studentid = :studentid and sgl.schoolyear.schoolyear = :schoolyear";
                    query = entityManager.createQuery(queryString);
                    query.setParameter("studentid", ss.getStudent().getStudentid());
                    query.setParameter("schoolyear", userController.getCurrentSchoolYear());
                    query.setMaxResults(1);
                    Studentgradelevel sgl = null;
                    try{
                        sgl = (Studentgradelevel)query.getSingleResult();
                    }catch(Exception e) {
                        e.printStackTrace();
                    }
                    if(null == sgl) sgl = new Studentgradelevel();
                    
                    ss.setGradeLevel(sgl.getGradelevel());
                    
                    // Get Attendance Record
                    queryString = "select at from Attendancetracking at where at.studentId = :studentid and at.subjectScheduleId = :subjectScheduleId and at.attendancedate = :attendancedate  and at.schoolyear = :schoolyear";
                    query = entityManager.createQuery(queryString);
                    query.setParameter("studentid", ss.getStudent().getStudentid());
                    query.setParameter("subjectScheduleId", getSelectedSchedule().getSubjectscheduleid());
                    query.setParameter("attendancedate", selectedDate);
                    query.setParameter("schoolyear", userController.getCurrentSchoolYear());
                    query.setMaxResults(1);
                    Attendancetracking at = null;
                    try{
                         List recs = query.getResultList();
                         if(null != recs && recs.size() > 0){
                             at = (Attendancetracking)recs.get(0);
                         }
                    }catch(Exception e) {
                        e.printStackTrace();
                    }
                    if(null == at) {
                        at = new Attendancetracking();
                        at.setStudentId(ss.getStudent().getStudentid());
                        at.setSubjectScheduleId(getSelectedSchedule().getSubjectscheduleid());
                        at.setAttendancedate(selectedDate);
                        at.setSchoolyear(userController.getCurrentSchoolYear());
                    }
                    at.setAttendancetakenby(userController.getProfile().getFirstname() + " " + userController.getProfile().getLastname());
                    ss.setSelectedDayAttendance(at);
                }
                setStudentSchedules(records);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
     public String saveAttendance(List<StudentSchedule> records){
        try{
            userTransaction.begin();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            if(null != records){
                for(StudentSchedule ss : records){
                    try{
                        Attendancetracking existingAt = null;
                        Attendancetracking at = ss.getSelectedDayAttendance();
                        if(null != at.getAttendanceid() && at.getAttendanceid() > 0)
                            existingAt = entityManager.find(Attendancetracking.class, at.getAttendanceid());
                        if(null != existingAt) {
                            existingAt.setAttendanceflag(at.getAttendanceflag());
                            existingAt.setAttendancetakenby(at.getAttendancetakenby());
                            entityManager.persist(existingAt);
                        }else{
                            entityManager.persist(at);
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
            userTransaction.commit();
            setStudentSchedules(null);
            setInfoMessage("Your changes to the attendance have been successfully saved!");
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
     
    public String goButton(){
        populateAttendance();
        return null;
    }
    
    protected void setInfoMessage(String summary) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
    }

    protected void setErrorMessage(String summary) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null));
    }
    
    public List<StudentSchedule> getStudentSchedules() {
        return studentSchedules;
    }

    public void setStudentSchedules(List<StudentSchedule> schedules) {
        this.studentSchedules = schedules;
    }
    
    public UserController getUserController() {
        return userController;
    }

    public void setUserController(UserController controller) {
        this.userController = controller;
    }
    
    public TeacherSchedulesController getSchedulesController() {
        return schedulesController;
    }

    public void setSchedulesController(TeacherSchedulesController controller) {
        this.schedulesController = controller;
    }
    
    public Integer getSelectedScheduleId() {
        return selectedScheduleId;
    }

    public void setSelectedScheduleId(Integer scheduleId) {
        this.selectedScheduleId = scheduleId;
    }
            
    public TeacherSchedule getSelectedSchedule() {
        return schedulesController.getScheduleById(selectedScheduleId);
    }

    public Date getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(Date date) {
        this.selectedDate = date;
    }
    
}
