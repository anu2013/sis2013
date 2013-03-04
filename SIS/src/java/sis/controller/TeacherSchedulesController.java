/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.controller;

import java.util.ArrayList;
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
import sis.model.TeacherSchedule;

/**
 *
 * @author Anupama Karumudi
 */
@ManagedBean(name="teacherSchedulesController")
public class TeacherSchedulesController {
    @PersistenceUnit(unitName="SISPU")
    private EntityManagerFactory entityManagerFactory;
    
    @ManagedProperty("#{userController}")
    private UserController userController;
    
    private List<TeacherSchedule> schedules = new ArrayList<TeacherSchedule>();
    
    @PostConstruct
    public void init(){
        populateSchedules();
    }

    private void populateSchedules(){
        try{
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            String queryString = "select ts from TeacherSchedule ts where ts.primaryteacher.teacherid = :uid or ts.secondaryteacher.teacherid = :uid";
            Query query = entityManager.createQuery(queryString);
            query.setParameter("uid", userController.getUser().getUserid());
            List<TeacherSchedule> records = query.getResultList();
            if(records != null){
                setSchedules(records);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
     public String takeAttendance(TeacherSchedule schedule){
        try{
            if (null == schedule) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Schedule not found!"));
            } else {
                return "takeAttendance?faces-redirect=true&sid=" + schedule.getSubjectscheduleid();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return the schedules
     */
    public List<TeacherSchedule> getSchedules() {
        return schedules;
    }

    /**
     * @param schedules the schedules to set
     */
    public void setSchedules(List<TeacherSchedule> schedules) {
        this.schedules = schedules;
    }
    
     /**
     * @return the userController
     */
    public UserController getUserController() {
        return userController;
    }

    /**
     * @param controller the userController to set
     */
    public void setUserController(UserController controller) {
        this.userController = controller;
    }
}