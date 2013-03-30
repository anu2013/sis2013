/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.controller;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import sis.bean.TeacherScheduleVO;
import sis.model.Schoolyearschedule;
import sis.model.TeacherSchedule;

/**
 *
 * @author Veekija
 */
@ManagedBean
public class TeacherScheduleReportController {

    @PersistenceUnit(unitName = "SISPU")
    private EntityManagerFactory entityManagerFactory;
    @Resource
    private UserTransaction userTransaction;
    private List<Schoolyearschedule> schoolyearschedules;
    private Integer selectedSchoolYear;
    private List<TeacherScheduleVO> teacherScheduleVOs = null;

    @PostConstruct
    public void init() {
        populateSchoolYearschedules();
    }

    private void populateSchoolYearschedules() {
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            String queryString = "select sys from Schoolyearschedule sys";
            Query query = entityManager.createQuery(queryString);
            this.setSchoolyearschedules((List<Schoolyearschedule>) query.getResultList());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String generateReport() {
        List<TeacherScheduleVO> tsVOs = null;
        TeacherScheduleVO teacherScheduleVO = null;
        if (this.selectedSchoolYear == 0) {
            setInfoMessage("Please select Grade Level and School year.");
        } else {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            String selectSQL = "select tsch from TeacherSchedule tsch where tsch.schoolyear.schoolyear = :schoolyear";
            try {
                Query selectQuery = entityManager.createQuery(selectSQL);
                selectQuery.setParameter("schoolyear", this.selectedSchoolYear);
                List<TeacherSchedule> teschs = (List<TeacherSchedule>) selectQuery.getResultList();
                if (teschs == null || teschs.size() == 0) {
                    setInfoMessage("There are no matching teachers exists.");
                } else {
                    tsVOs = new ArrayList<TeacherScheduleVO>();
                    for (TeacherSchedule tsch : teschs){
                        teacherScheduleVO = new TeacherScheduleVO();
                        
                    }
                    this.setTeacherScheduleVOs(tsVOs);
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("teacherScheduleVOs", tsVOs);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * @return the schoolyearschedules
     */
    public List<Schoolyearschedule> getSchoolyearschedules() {
        return schoolyearschedules;
    }

    /**
     * @param schoolyearschedules the schoolyearschedules to set
     */
    public void setSchoolyearschedules(List<Schoolyearschedule> schoolyearschedules) {
        this.schoolyearschedules = schoolyearschedules;
    }

    /**
     * @return the selectedSchoolYear
     */
    public Integer getSelectedSchoolYear() {
        return selectedSchoolYear;
    }

    /**
     * @param selectedSchoolYear the selectedSchoolYear to set
     */
    public void setSelectedSchoolYear(Integer selectedSchoolYear) {
        this.selectedSchoolYear = selectedSchoolYear;
    }

    protected void setInfoMessage(String summary) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
    }

    /**
     * @return the teacherSchedules
     */
    public List<TeacherScheduleVO> getTeacherScheduleVOs() {
        return teacherScheduleVOs;
    }

    /**
     * @param teacherSchedules the teacherSchedules to set
     */
    public void setTeacherScheduleVOs(List<TeacherScheduleVO> teacherScheduleVOs) {
        this.teacherScheduleVOs = teacherScheduleVOs;
    }
}
