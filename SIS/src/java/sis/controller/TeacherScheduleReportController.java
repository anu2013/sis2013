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
import sis.model.Teacher;
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
    private String enteredFirstName;
    private String enteredLastName;
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
        List<TeacherScheduleVO> tsVOs = new ArrayList<TeacherScheduleVO>();
        TeacherScheduleVO pteacherScheduleVO = null;
        TeacherScheduleVO steacherScheduleVO = null;
        if (this.selectedSchoolYear == 0) {
            setInfoMessage("Please select school year.");
        } else {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            try {
                String sql = "select t from Teacher t where t.profile.firstname like :firstname and t.profile.lastname like :lastname";
                Query sqlQuery = entityManager.createQuery(sql);
                sqlQuery.setParameter("firstname", this.enteredFirstName+"%");
                sqlQuery.setParameter("lastname", this.enteredLastName+"%");
                List<Teacher> teachers = (List<Teacher>) sqlQuery.getResultList();
                if (teachers == null || teachers.size() == 0) {
                    setInfoMessage("There are no matching teachers exists.");
                } else {
                    for (Teacher teacher : teachers) {
                        //Identify primary teacher schedules
                        String primarySQL = "select tsch from TeacherSchedule tsch where "
                                + "tsch.schoolyear.schoolyear = :schoolyear and "
                                + "tsch.primaryteacher.teacherid = :teacherid";
                        Query primarySQLQuery = entityManager.createQuery(primarySQL);
                        primarySQLQuery.setParameter("schoolyear", this.selectedSchoolYear);
                        primarySQLQuery.setParameter("teacherid", teacher.getTeacherid());
                        List<TeacherSchedule> pTeacherSchedules = (List<TeacherSchedule>) primarySQLQuery.getResultList();
                        if (pTeacherSchedules == null || pTeacherSchedules.size() == 0) {
                        } else {
                            for (TeacherSchedule ptsch : pTeacherSchedules) {
                                pteacherScheduleVO = new TeacherScheduleVO();
                                pteacherScheduleVO.setTeacherId(teacher.getTeacherid());
                                pteacherScheduleVO.setName(ptsch.getPrimaryTeacherName());
                                pteacherScheduleVO.setPeriodName(ptsch.getPeriod().getDescription());
                                pteacherScheduleVO.setPeriodStart(ptsch.getPeriod().getStarttime());
                                pteacherScheduleVO.setPeriodEnd(ptsch.getPeriod().getEndtime());
                                pteacherScheduleVO.setScheduleType("Primary");
                                pteacherScheduleVO.setScheduleDays(ptsch.getScheduledays());
                                tsVOs.add(pteacherScheduleVO);
                            }
                        }
                        //Identify Secondary teacher schedules
                        String secondarySQL = "select tsch from TeacherSchedule tsch where "
                                + "tsch.schoolyear.schoolyear = :schoolyear and "
                                + "tsch.secondaryteacher.teacherid = :teacherid";
                        Query secondarySQLQuery = entityManager.createQuery(secondarySQL);
                        secondarySQLQuery.setParameter("schoolyear", this.selectedSchoolYear);
                        secondarySQLQuery.setParameter("teacherid", teacher.getTeacherid());
                        List<TeacherSchedule> sTeacherSchedules = (List<TeacherSchedule>) secondarySQLQuery.getResultList();
                        if (sTeacherSchedules == null || sTeacherSchedules.size() == 0) {
                        } else {
                            for (TeacherSchedule stsch : sTeacherSchedules) {
                                steacherScheduleVO = new TeacherScheduleVO();
                                steacherScheduleVO.setTeacherId(teacher.getTeacherid());
                                steacherScheduleVO.setName(stsch.getSecondaryTeacherName());
                                steacherScheduleVO.setPeriodName(stsch.getPeriod().getDescription());
                                steacherScheduleVO.setPeriodStart(stsch.getPeriod().getStarttime());
                                steacherScheduleVO.setPeriodEnd(stsch.getPeriod().getEndtime());
                                steacherScheduleVO.setScheduleType("Secondary");
                                steacherScheduleVO.setScheduleDays(stsch.getScheduledays());
                                tsVOs.add(steacherScheduleVO);
                            }
                        }
                    }

                    if (tsVOs.size() != 0) {
                        this.setTeacherScheduleVOs(tsVOs);
                        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("teacherScheduleVOs", tsVOs);
                    } else {
                        setInfoMessage("There are no matching teachers exists.");
                    }
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

    /**
     * @return the enteredFirstName
     */
    public String getEnteredFirstName() {
        return enteredFirstName;
    }

    /**
     * @param enteredFirstName the enteredFirstName to set
     */
    public void setEnteredFirstName(String enteredFirstName) {
        this.enteredFirstName = enteredFirstName;
    }

    /**
     * @return the enteredLastName
     */
    public String getEnteredLastName() {
        return enteredLastName;
    }

    /**
     * @param enteredLastName the enteredLastName to set
     */
    public void setEnteredLastName(String enteredLastName) {
        this.enteredLastName = enteredLastName;
    }
}
