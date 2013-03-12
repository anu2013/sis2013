/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.controller;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
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
import sis.model.StudentSchedule;
import sis.model.Studentgradelevel;
import sis.model.TeacherSchedule;

/**
 *
 * @author Anupama Karumudi
 */
@ManagedBean(name="teacherIepController")
@ViewScoped
public class TeacherIepController implements Serializable{
    @PersistenceUnit(unitName="SISPU")
    private EntityManagerFactory entityManagerFactory;
    
    @ManagedProperty("#{userController}")
    private UserController userController;
    
    @ManagedProperty("#{teacherSchedulesController}")
    private TeacherSchedulesController schedulesController;
    
    private List<StudentSchedule> studentSchedules;
    private Integer selectedScheduleId;
    
    @PostConstruct
    public void init(){
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String sid = request.getParameter("sid");
        if(null != sid){
            selectedScheduleId = Integer.parseInt(sid);
        }
        if(null == selectedScheduleId) selectedScheduleId = 0;
        
        if(selectedScheduleId < 1) {
            populateIepStudents();
        }
    }

    private void populateIepStudents(){
        try{
            setStudentSchedules(null);
       
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            String queryString = (selectedScheduleId > 0) ? "select ss from StudentSchedule ss where ss.schedule.subjectscheduleid = :ssid and ss.student.iepneeded = 1" : "select ss from StudentSchedule ss where ss.student.iepneeded = 1 and ss.schedule.subjectscheduleid in (select ts.subjectscheduleid from TeacherSchedule ts where (ts.primaryteacher.teacherid = :uid or ts.secondaryteacher.teacherid = :uid) and ts.schoolyear.schoolyear = :sy)";
            Query query = entityManager.createQuery(queryString);
            if(selectedScheduleId > 0){
                query.setParameter("ssid", getSelectedSchedule().getSubjectscheduleid());
            }else{
                query.setParameter("uid", userController.getUser().getUserid());
                query.setParameter("sy", userController.getCurrentSchoolYear());
            }

            List<StudentSchedule> records = query.getResultList();
            if(records != null && records.size() > 0){
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
                }
                setStudentSchedules(records);
            }else{
                setErrorMessage("There are no IEP students found.");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public String goButton(){
        populateIepStudents();
        return null;
    }
    
    public String goalsButton(StudentSchedule ss){
        return "iepGoals?faces-redirect=true&sid=" + ss.getStudent().getStudentid();
    }
    
    public String progressButton(StudentSchedule ss){
        return "iepProgress?faces-redirect=true&sid=" + ss.getStudent().getStudentid();
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
}
