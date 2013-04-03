/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.controller;

import java.util.LinkedHashMap;
import sis.model.TeacherSchedule;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import sis.model.Period;
import sis.model.Schoolyearschedule;
import sis.model.Subject;
import sis.model.Teacher;

/**
 *
 * @author Veekija
 */
@ManagedBean
public class TeacherScheduleCRUDController {

    @PersistenceUnit(unitName = "SISPU")
    private EntityManagerFactory entityManagerFactory;
    @Resource
    private UserTransaction userTransaction;
    private List<TeacherSchedule> teacherSchedules;
    @ManagedProperty(value = "#{teacherSchedule}")
    private TeacherSchedule teacherSchedule;
    private Integer schoolYear;
    private Integer periodId;
    private Integer subjectId;
    private Integer primaryTeacherId;
    private Integer secondaryTeacherId;
    private static Map<String, Object> scheduleDaysMap;
    private String[] scheduleDaysStringArray;

    static {
        scheduleDaysMap = new LinkedHashMap<String, Object>();
        scheduleDaysMap.put("Monday", "M"); //label, value
        scheduleDaysMap.put("Tuesday", "T");
        scheduleDaysMap.put("Wednesday", "W");
        scheduleDaysMap.put("Thursday", "TR");
        scheduleDaysMap.put("Friday", "F");
    }

    @PostConstruct
    public void init() {
        retrieveTeacherSchedules();
    }

    private void retrieveTeacherSchedules() {
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            String queryString = "select ts from TeacherSchedule ts";
            Query query = entityManager.createQuery(queryString);
            this.setTeacherSchedules((List<TeacherSchedule>) query.getResultList());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<TeacherSchedule> retrievePrimaryTeacherSchedules(Integer argTeacherId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String queryString = "select ts from TeacherSchedule ts where "
                + "ts.primaryteacher.teacherid=:teacherid and "
                + "ts.schoolyear.schoolyear=:schoolyear and "
                + "ts.period.periodid=:periodid";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("teacherid", argTeacherId);
        query.setParameter("schoolyear", schoolYear);
        query.setParameter("periodid", periodId);
        return (List<TeacherSchedule>) query.getResultList();
    }

    private List<TeacherSchedule> retrieveSecondaryTeacherSchedules(Integer argTeacherId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String queryString = "select ts from TeacherSchedule ts where "
                + "ts.secondaryteacher.teacherid=:teacherid and "
                + "ts.schoolyear.schoolyear=:schoolyear and "
                + "ts.period.periodid=:periodid";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("teacherid", argTeacherId);
        query.setParameter("schoolyear", schoolYear);
        query.setParameter("periodid", periodId);
        return (List<TeacherSchedule>) query.getResultList();
    }

    private boolean isMatchFound(String[] argArray1, String[] argArray2) {
        boolean matchFound = false;
        for (String a11 : argArray1) {
            if (matchFound == false) {
                for (String b11 : argArray2) {
                    if (a11.equalsIgnoreCase(b11)) {
                        matchFound = true;
                    }
                }
            }
        }
        return matchFound;
    }

    public String createTeacherSchedule() {
        List<TeacherSchedule> pTSchedules = null;
        List<TeacherSchedule> sTSchedules = null;
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        
        try {
            //Read selected scheduled days
            String scheduleDays = "";
            for (int index = 0; index < this.scheduleDaysStringArray.length; index++) {
                scheduleDays = scheduleDays + scheduleDaysStringArray[index] + ",";
            }
            scheduleDays = scheduleDays.substring(0, scheduleDays.length() - 1);

            //If primary teacher and secondary teacher are same then display error message
            if (this.primaryTeacherId == this.secondaryTeacherId) {
                setInfoMessage("Primary teacher and Secondary teacher should not be the same for the period.");
                return null;
            }

            //If primary teacher is already assigned to a specific period/days then display a error message.
            pTSchedules = retrievePrimaryTeacherSchedules(this.primaryTeacherId);
            boolean primaryMatchFound = false;
            for (TeacherSchedule ts : pTSchedules) {
                primaryMatchFound = isMatchFound(ts.getScheduledays().split(","), this.scheduleDaysStringArray);
                if (primaryMatchFound) {
                    break;
                }
            }
            if (primaryMatchFound) {
                setInfoMessage("Primary teacher already assigned to the selected period. "
                        + "Please select different primary teacher.");
                return null;
            }

            //If secondary teacher is already assigned to a specific period/days then display a error message.
            sTSchedules = retrieveSecondaryTeacherSchedules(this.secondaryTeacherId);
            boolean secondaryMatchFound = false;
            for (TeacherSchedule ts : sTSchedules) {
                secondaryMatchFound = isMatchFound(ts.getScheduledays().split(","), this.scheduleDaysStringArray);
                if (secondaryMatchFound) {
                    break;
                }
            }
            if (secondaryMatchFound) {
                setInfoMessage("Secondary teacher already assigned to the selected period. "
                        + "Please select different secondary teacher.");
                return null;
            }

            //All validation passed and insert starts....
            userTransaction.begin();
            TeacherSchedule tsch = getTeacherSchedule();
            tsch.setScheduledays(scheduleDays);

            Period p = new Period();
            p.setPeriodid(periodId);
            tsch.setPeriod(p);

            Schoolyearschedule sy = new Schoolyearschedule();
            sy.setSchoolyear(schoolYear);
            tsch.setSchoolyear(sy);

            Subject s = new Subject();
            s.setSubjectid(subjectId);
            tsch.setSubject(s);

            Teacher pt = new Teacher();
            pt.setTeacherid(primaryTeacherId);
            tsch.setPrimaryTeacher(pt);

            Teacher st = new Teacher();
            st.setTeacherid(secondaryTeacherId);
            tsch.setSecondaryTeacher(st);

            entityManager.persist(tsch);
            userTransaction.commit();
            retrieveTeacherSchedules();
            return "/admin/teacherScheduleCRUD";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    public String addTeacherSchedule() {
        return "/admin/teacherScheduleCreate";
    }

    public String updateTeacherSchedule() {
        try {
            EntityManager em = entityManagerFactory.createEntityManager();
            userTransaction.begin();
            TeacherSchedule tsch = em.find(TeacherSchedule.class, this.teacherSchedule.getSubjectscheduleid());
            tsch.setSchedulename(this.teacherSchedule.getSchedulename());
            tsch.setScheduledays(this.teacherSchedule.getScheduledays());
            tsch.getPeriod().setPeriodid(this.periodId);
            em.persist(tsch);
            userTransaction.commit();
            retrieveTeacherSchedules();
            return "/admin/teacherScheduleCRUD";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    public String deleteTeacherSchedule(TeacherSchedule argTeacherSchedule) {
        try {
            EntityManager em = entityManagerFactory.createEntityManager();
            userTransaction.begin();
            TeacherSchedule currentTeacherSchedule = em.find(TeacherSchedule.class, argTeacherSchedule.getSubjectscheduleid());
            em.remove(currentTeacherSchedule);
            userTransaction.commit();
            retrieveTeacherSchedules();
            return "/admin/teacherScheduleCRUD";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    public String editTeacherSchedule(TeacherSchedule argTeacherSchedule) {
        this.teacherSchedule = argTeacherSchedule;
        this.schoolYear = argTeacherSchedule.getSchoolyear().getSchoolyear();
        this.periodId = argTeacherSchedule.getPeriod().getPeriodid();
        this.primaryTeacherId = argTeacherSchedule.getPrimaryTeacher().getTeacherid();
        this.secondaryTeacherId = argTeacherSchedule.getSecondaryTeacher().getTeacherid();
        this.subjectId = argTeacherSchedule.getSubject().getSubjectid();
        return "/admin/teacherScheduleUpdate";
    }

    /**
     * @return the teacherSchedules
     */
    public List<TeacherSchedule> getTeacherSchedules() {
        return teacherSchedules;
    }

    /**
     * @param teacherSchedules the teacherSchedules to set
     */
    public void setTeacherSchedules(List<TeacherSchedule> teacherSchedules) {
        this.teacherSchedules = teacherSchedules;
    }

    /**
     * @return the teacherSchedule
     */
    public TeacherSchedule getTeacherSchedule() {
        return teacherSchedule;
    }

    /**
     * @param teacherSchedule the teacherSchedule to set
     */
    public void setTeacherSchedule(TeacherSchedule teacherSchedule) {
        this.teacherSchedule = teacherSchedule;
    }

    /**
     * @return the schoolYear
     */
    public Integer getSchoolYear() {
        return schoolYear;
    }

    /**
     * @param schoolYear the schoolYear to set
     */
    public void setSchoolYear(Integer schoolYear) {
        this.schoolYear = schoolYear;
    }

    /**
     * @return the periodId
     */
    public Integer getPeriodId() {
        return periodId;
    }

    /**
     * @param periodId the periodId to set
     */
    public void setPeriodId(Integer periodId) {
        this.periodId = periodId;
    }

    /**
     * @return the subjectId
     */
    public Integer getSubjectId() {
        return subjectId;
    }

    /**
     * @param subjectId the subjectId to set
     */
    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    /**
     * @return the primaryTeacherId
     */
    public Integer getPrimaryTeacherId() {
        return primaryTeacherId;
    }

    /**
     * @param primaryTeacherId the primaryTeacherId to set
     */
    public void setPrimaryTeacherId(Integer primaryTeacherId) {
        this.primaryTeacherId = primaryTeacherId;
    }

    /**
     * @return the secondaryTeacherId
     */
    public Integer getSecondaryTeacherId() {
        return secondaryTeacherId;
    }

    /**
     * @param secondaryTeacherId the secondaryTeacherId to set
     */
    public void setSecondaryTeacherId(Integer secondaryTeacherId) {
        this.secondaryTeacherId = secondaryTeacherId;
    }

    public Map<String, Object> getScheduleDaysMap() {
        return scheduleDaysMap;
    }

    protected void setInfoMessage(String summary) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
    }

    /**
     * @return the scheduleDaysStringArray
     */
    public String[] getScheduleDaysStringArray() {
        return scheduleDaysStringArray;
    }

    /**
     * @param scheduleDaysStringArray the scheduleDaysStringArray to set
     */
    public void setScheduleDaysStringArray(String[] scheduleDaysStringArray) {
        this.scheduleDaysStringArray = scheduleDaysStringArray;
    }
}
