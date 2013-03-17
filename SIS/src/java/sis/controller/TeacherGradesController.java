/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.controller;

import java.io.Serializable;
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
import sis.model.StudentSchedule;
import sis.model.Studentgradelevel;
import sis.model.Studentscorecard;
import sis.model.TeacherSchedule;

/**
 *
 * @author Anupama Karumudi
 */
@ManagedBean(name="teacherGradesController")
@ViewScoped
public class TeacherGradesController implements Serializable{
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
    
    @PostConstruct
    public void init(){
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String sid = request.getParameter("sid");
        if(null != sid){
            selectedScheduleId = Integer.parseInt(sid);
        }
        if(null != selectedScheduleId && selectedScheduleId > 0) {
            populateGrades();
        }
    }

    private void populateGrades(){
        try{
            setStudentSchedules(null);
            
            if(selectedScheduleId < 1){
                setErrorMessage("Please select a subject to start the grading.");
                return;
            }

            EntityManager entityManager = entityManagerFactory.createEntityManager();
            String queryString = "select ss from StudentSchedule ss where ss.schedule.subjectscheduleid = :ssid";
            Query query = entityManager.createQuery(queryString);
            query.setParameter("ssid", getSelectedSchedule().getSubjectscheduleid());
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
                    
                    // Get StudentScoreCard Record
                    queryString = "select sc from Studentscorecard sc where sc.studentId = :studentid and sc.subjectid = :subjectid and sc.schoolyear = :schoolyear";
                    query = entityManager.createQuery(queryString);
                    query.setParameter("studentid", ss.getStudent().getStudentid());
                    query.setParameter("subjectid", getSelectedSchedule().getSubject().getSubjectid());
                    query.setParameter("schoolyear", userController.getCurrentSchoolYear());
                    query.setMaxResults(1);
                    Studentscorecard sc = null;
                    try{
                         List recs = query.getResultList();
                         if(null != recs && recs.size() > 0){
                             sc = (Studentscorecard)recs.get(0);
                         }
                    }catch(Exception e) {
                        e.printStackTrace();
                    }
                    if(null == sc) {
                        sc = new Studentscorecard();
                        sc.setStudentId(ss.getStudent().getStudentid());
                        sc.setSubjectid(getSelectedSchedule().getSubject().getSubjectid());
                        sc.setSchoolyear(userController.getCurrentSchoolYear());
                    }
                    ss.setScoreCard(sc);
                }
                setStudentSchedules(records);
            }else{
                setErrorMessage("There are no students enrolled in this class.");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
     public String saveScoreCards(List<StudentSchedule> records){
        try{
            userTransaction.begin();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            if(null != records){
                for(StudentSchedule ss : records){
                    try{
                        Studentscorecard existingSc = null;
                        Studentscorecard sc = ss.getScoreCard();
                        if(null != sc.getStudentscorecardid() && sc.getStudentscorecardid() > 0)
                            existingSc = entityManager.find(Studentscorecard.class, sc.getStudentscorecardid());
                        if(null != existingSc) {
                            existingSc.setFinalscore(sc.getFinalscore());
                            existingSc.setGradeletter(sc.getGradeletter());
                            existingSc.setComments(sc.getComments());
                            existingSc.setSubjectid(ss.getSchedule().getSubject().getSubjectid());
                            entityManager.persist(existingSc);
                        }else{
                            sc.setSubjectid(ss.getSchedule().getSubject().getSubjectid());
                            entityManager.persist(sc);
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
            userTransaction.commit();
            setStudentSchedules(null);
            setInfoMessage("Your changes to the score cards have been successfully saved!");
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
     
    public String goButton(){
        populateGrades();
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
}
