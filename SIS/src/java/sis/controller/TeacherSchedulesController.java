/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
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
@SessionScoped
public class TeacherSchedulesController implements Serializable {
    @PersistenceUnit(unitName="SISPU")
    private EntityManagerFactory entityManagerFactory;
    
    @ManagedProperty("#{userController}")
    private UserController userController;
    
    private List<TeacherSchedule> schedules = new ArrayList<TeacherSchedule>();
    private TeacherSchedule selectedSchedule = null;
    
    @PostConstruct
    public void init(){
        populateSchedules();
    }

    private void populateSchedules(){
        try{
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            String queryString = "select ts from TeacherSchedule ts where (ts.primaryteacher.teacherid = :uid or ts.secondaryteacher.teacherid = :uid) and ts.schoolyear.schoolyear = :sy";
            Query query = entityManager.createQuery(queryString);
            query.setParameter("uid", userController.getUser().getUserid());
            query.setParameter("sy", userController.getCurrentSchoolYear());
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
                setSelectedSchedule(schedule);
                return "attendance?faces-redirect=true&sid=" + schedule.getSubjectscheduleid();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

     /**
     * @return schedule by id
     */
    public TeacherSchedule getScheduleById(Integer id) {
        if(null != schedules){
             for(TeacherSchedule ts : schedules){
                if(ts.getSubjectscheduleid() == id){
                    return ts;
                }
             }
        }
        return null;
    }
    
     /**
     * @return the selected schedule
     */
    public TeacherSchedule getSelectedSchedule() {
        return selectedSchedule;
    }

    /**
     * @param schedule the selected schedule to set
     */
    public void setSelectedSchedule(TeacherSchedule schedule) {
        this.selectedSchedule = schedule;
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
