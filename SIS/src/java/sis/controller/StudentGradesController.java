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
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import sis.model.StudentSchedule;
import sis.model.Studentgradelevel;
import sis.model.Studentscorecard;

/**
 *
 * @author Anupama Karumudi
 */
@ManagedBean(name="studentGradesController")
public class StudentGradesController implements Serializable {
    @PersistenceUnit(unitName="SISPU")
    private EntityManagerFactory entityManagerFactory;
    
    @ManagedProperty("#{userController}")
    private UserController userController;
    
    private List<StudentSchedule> schedules;
    
    @PostConstruct
    public void init(){
        populateSchedules();
    }

    private void populateSchedules(){
        try{
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            String queryString = "select ss from StudentSchedule ss where ss.student.studentid = :studentid and ss.schedule.schoolyear.schoolyear = :schoolyear order by ss.schedule.period.sortorder asc";
            Query query = entityManager.createQuery(queryString);
            query.setParameter("studentid", userController.getUser().getUserid());
            query.setParameter("schoolyear", userController.getCurrentSchoolYear());
            List<StudentSchedule> records = query.getResultList();
            if(records != null && records.size() > 0){
                 for(StudentSchedule ss : records){
                    // Get StudentScoreCard Record
                    queryString = "select sc from Studentscorecard sc where sc.studentId = :studentid and sc.subjectid = :subjectid and sc.schoolyear = :schoolyear";
                    query = entityManager.createQuery(queryString);
                    query.setParameter("studentid", ss.getStudent().getStudentid());
                    query.setParameter("subjectid", ss.getSchedule().getSubject().getSubjectid());
                    query.setParameter("schoolyear", userController.getCurrentSchoolYear());
                    query.setMaxResults(1);
                    try{
                         List recs = query.getResultList();
                         if(null != recs && recs.size() > 0){
                             Studentscorecard sc = (Studentscorecard)recs.get(0);
                             ss.setScoreCard(sc);
                         }
                    }catch(Exception e) {
                        e.printStackTrace();
                    }
                }
                setSchedules(records);
            }else{
                setErrorMessage("There are no schedules found. You did not enroll for classes.");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    protected void setInfoMessage(String summary) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
    }

    protected void setErrorMessage(String summary) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null));
    }
    
    public List<StudentSchedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<StudentSchedule> schedules) {
        this.schedules = schedules;
    }
    
    public UserController getUserController() {
        return userController;
    }

    public void setUserController(UserController controller) {
        this.userController = controller;
    }
}
