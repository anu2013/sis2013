/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
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
import javax.sql.DataSource;
import javax.transaction.UserTransaction;
import sis.bean.TeacherSubjectScheduleVO;
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
    @Resource(name = "jdbc/SISDB")
    private DataSource dataSource;
    @Resource
    private UserTransaction userTransaction;
    private List<TeacherSchedule> teacherSchedules;
    @ManagedProperty(value = "#{teacherSchedule}")
    private TeacherSchedule teacherSchedule;
    @ManagedProperty(value = "#{period}")
    private Period period;
    private Integer schoolYear;
    private Integer periodId;
    private Integer subjectId;
    private Integer primaryTeacherId;
    private Integer secondaryTeacherId;
    private static Map<String, Object> scheduleDaysMap;
    private String[] scheduleDaysStringArray;
    private List<TeacherSubjectScheduleVO> teacherSubjectScheduleVOs;

    static {
        scheduleDaysMap = new LinkedHashMap<String, Object>();
        scheduleDaysMap.put("Monday", "M");
        scheduleDaysMap.put("Tuesday", "T");
        scheduleDaysMap.put("Wednesday", "W");
        scheduleDaysMap.put("Thursday", "TH");
        scheduleDaysMap.put("Friday", "F");
    }

    @PostConstruct
    public void init() {
        retrieveTeacherSchedules();
    }

    private void retrieveTeacherSchedules() {
        TeacherSubjectScheduleVO teSSVO = null;
        List<TeacherSubjectScheduleVO> teSSVOs = null;
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement pstmt = connection.prepareStatement("select subjectscheduleId,"
                    + "schedulename,primaryteacherid,"
                    + "secondaryteacherid,periodid,subjectid,"
                    + "schoolyear,scheduledays from SISADMIN.SUBJECTSCHEDULE");
            ResultSet rst = pstmt.executeQuery();
            teSSVOs = new ArrayList<TeacherSubjectScheduleVO>();
            while (rst.next()) {
                teSSVO = new TeacherSubjectScheduleVO();
                teSSVO.setSubjectScheduleId(rst.getInt("subjectscheduleId"));
                teSSVO.setScheduleName(rst.getString("ScheduleName"));
                teSSVO.setScheduleDays(rst.getString("ScheduleDays"));
                teSSVO.setPrimaryTeacherId(rst.getInt("primaryTeacherId"));
                Teacher pT = em.find(Teacher.class, teSSVO.getPrimaryTeacherId());
                teSSVO.setPrimaryTeacherName(pT.getProfile().getFirstname() + " " + pT.getProfile().getFirstname());
                teSSVO.setSecondaryTeacherId(rst.getInt("secondaryTeacherId"));
                Teacher sT = em.find(Teacher.class, teSSVO.getSecondaryTeacherId());
                teSSVO.setSecondaryTeacherName(sT.getProfile().getFirstname() + " " + sT.getProfile().getFirstname());
                teSSVO.setPeriodId(rst.getInt("periodId"));
                Period p = em.find(Period.class, teSSVO.getPeriodId());
                teSSVO.setPeriodName(p.getDescription());
                teSSVO.setPeriodStart(p.getStarttime());
                teSSVO.setPeriodEnd(p.getEndtime());
                teSSVO.setSubjectId(rst.getInt("subjectId"));
                Subject subject = em.find(Subject.class, teSSVO.getSubjectId());
                teSSVO.setSubjectName(subject.getSubjectname());
                teSSVO.setSchoolYear(rst.getInt("schoolyear"));
                teSSVOs.add(teSSVO);
            }
            rst.close();
            pstmt.close();
            connection.close();
            this.setTeacherSubjectScheduleVOs(teSSVOs);
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
        for (String array1Element : argArray1) {
            if (matchFound == false) {
                for (String array2Element : argArray2) {
                    if (array1Element.equalsIgnoreCase(array2Element)) {
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
            //Validate the valid school year, period, subject, primary and secondry teachers are selected.
            if (this.schoolYear == 0) {
                setInfoMessage("Please select school year.");
                return null;
            }
            if (this.periodId == 0) {
                setInfoMessage("Please select period.");
                return null;
            }
            if (this.subjectId == 0) {
                setInfoMessage("Please select subject.");
                return null;
            }
            if (this.primaryTeacherId == 0) {
                setInfoMessage("Please select Primary Teacher.");
                return null;
            }
            if (this.secondaryTeacherId == 0) {
                setInfoMessage("Please select Secondary Teacher.");
                return null;
            }

            Period selectedPeriod = entityManager.find(Period.class, periodId);
            if (selectedPeriod != null) {
                if (selectedPeriod.getSchoolyear().getSchoolyear().intValue() != schoolYear.intValue()) {
                    setInfoMessage("The selected period code does not belong to the selected school year. "
                            + "Please select an appropriate period code or school year.");
                    return null;
                }
            }

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

            //Read selected scheduled days
            String scheduleDays = "";
            for (int index = 0; index < this.scheduleDaysStringArray.length; index++) {
                scheduleDays = scheduleDays + scheduleDaysStringArray[index] + ",";
            }
            scheduleDays = scheduleDays.substring(0, scheduleDays.length() - 1);

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
            entityManager.flush();
            userTransaction.commit();
            entityManager.refresh(tsch);
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
        List<TeacherSchedule> pTSchedules = null;
        List<TeacherSchedule> sTSchedules = null;
        EntityManager em = null;
        try {
            
            //Validate the valid school year, period, subject, primary and secondry teachers are selected.
            if (this.schoolYear == 0) {
                setInfoMessage("Please select school year.");
                return null;
            }
            if (this.periodId == 0) {
                setInfoMessage("Please select period.");
                return null;
            }
            if (this.subjectId == 0) {
                setInfoMessage("Please select subject.");
                return null;
            }
            if (this.primaryTeacherId == 0) {
                setInfoMessage("Please select Primary Teacher.");
                return null;
            }
            if (this.secondaryTeacherId == 0) {
                setInfoMessage("Please select Secondary Teacher.");
                return null;
            }
            em = entityManagerFactory.createEntityManager();
            Period selectedPeriod = em.find(Period.class, periodId);
            if (selectedPeriod != null) {
                if (selectedPeriod.getSchoolyear().getSchoolyear().intValue() != schoolYear.intValue()) {
                    setInfoMessage("The selected period code does not belong to the selected school year. "
                            + "Please select an appropriate period code or school year.");
                    return null;
                }
            }

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
            
            
            //Read selected scheduled days
            String scheduleDays = "";
            for (int index = 0; index < this.scheduleDaysStringArray.length; index++) {
                scheduleDays = scheduleDays + scheduleDaysStringArray[index] + ",";
            }
            scheduleDays = scheduleDays.substring(0, scheduleDays.length() - 1);


            em = entityManagerFactory.createEntityManager();
            userTransaction.begin();
            String updateQueryString = "update SISADMIN.SUBJECTSCHEDULE set "
                    + "schedulename=?, "
                    + "primaryteacherid=?, "
                    + "secondaryteacherid=?, "
                    + "periodid=?, "
                    + "subjectid=?, "
                    + "schoolyear=?, "
                    + "scheduledays=? where "
                    + "subjectscheduleid=?";
            Query updateQuery = em.createNativeQuery(updateQueryString);
            updateQuery.setParameter(1, this.teacherSchedule.getSchedulename());
            updateQuery.setParameter(2, this.primaryTeacherId);
            updateQuery.setParameter(3, this.secondaryTeacherId);
            updateQuery.setParameter(4, this.periodId);
            updateQuery.setParameter(5, this.subjectId);
            updateQuery.setParameter(6, this.schoolYear);
            updateQuery.setParameter(7, scheduleDays);
            updateQuery.setParameter(8, this.teacherSchedule.getSubjectscheduleid());
            int recordsAffected = updateQuery.executeUpdate();
            em.flush();
            userTransaction.commit();

            retrieveTeacherSchedules();
            return "/admin/teacherScheduleCRUD";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    public String editTeacherSchedule(TeacherSubjectScheduleVO argTeacherScheduleSubjectScheduleVO) {
        EntityManager em = entityManagerFactory.createEntityManager();
        TeacherSchedule currentTeacherSchedule = em.find(TeacherSchedule.class, argTeacherScheduleSubjectScheduleVO.getSubjectScheduleId());
        em.refresh(currentTeacherSchedule);
        this.teacherSchedule = currentTeacherSchedule;
        this.schoolYear = currentTeacherSchedule.getSchoolyear().getSchoolyear();
        this.periodId = currentTeacherSchedule.getPeriod().getPeriodid();
        this.primaryTeacherId = currentTeacherSchedule.getPrimaryTeacher().getTeacherid();
        this.secondaryTeacherId = currentTeacherSchedule.getSecondaryTeacher().getTeacherid();
        this.subjectId = currentTeacherSchedule.getSubject().getSubjectid();
        this.scheduleDaysStringArray = currentTeacherSchedule.getScheduledays().split(",");
        return "/admin/teacherScheduleUpdate";
    }

    public String deleteTeacherSchedule(TeacherSubjectScheduleVO argTeacherScheduleSubjectScheduleVO) {
        try {
            EntityManager em = entityManagerFactory.createEntityManager();
            userTransaction.begin();
            TeacherSchedule currentTeacherSchedule = em.find(TeacherSchedule.class, argTeacherScheduleSubjectScheduleVO.getSubjectScheduleId());
            em.remove(currentTeacherSchedule);
            userTransaction.commit();
            retrieveTeacherSchedules();
            return "/admin/teacherScheduleCRUD";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
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

    public List getPeriods() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        //String queryString = "select s from Period s where s.schoolyear.schoolyear=:schoolyear";
        String queryString = "select p from Period p where "
                + "p.schoolyear.schoolyear >= (select s.schoolyear from "
                + "Schoolyearschedule s where s.active = :active)";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("active", new Short("1"));
        return (List<Period>) query.getResultList();
    }

    /**
     * @return the period
     */
    public Period getPeriod() {
        return period;
    }

    /**
     * @param period the period to set
     */
    public void setPeriod(Period period) {
        this.period = period;
    }

    /**
     * @return the teacherSubjectScheduleVOs
     */
    public List<TeacherSubjectScheduleVO> getTeacherSubjectScheduleVOs() {
        return teacherSubjectScheduleVOs;
    }

    /**
     * @param teacherSubjectScheduleVOs the teacherSubjectScheduleVOs to set
     */
    public void setTeacherSubjectScheduleVOs(List<TeacherSubjectScheduleVO> teacherSubjectScheduleVOs) {
        this.teacherSubjectScheduleVOs = teacherSubjectScheduleVOs;
    }
}
