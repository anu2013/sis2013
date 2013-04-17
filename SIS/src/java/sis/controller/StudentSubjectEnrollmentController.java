/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import sis.bean.StudentVO;
import sis.bean.TeacherScheduleVO;
import sis.model.Schoolyearschedule;
import sis.model.Gradelevel;
import sis.model.Student;
import sis.model.StudentSchedule;
import sis.model.Studentgradelevel;
import sis.model.Studentsubjectschedule;
import sis.model.TeacherSchedule;

/**
 *
 * @author Veekija
 */
@ManagedBean
@ViewScoped
public class StudentSubjectEnrollmentController implements Serializable {

    @PersistenceUnit(unitName = "SISPU")
    private EntityManagerFactory entityManagerFactory;
    @Resource
    private UserTransaction userTransaction;
    private List<Gradelevel> gradelevels;
    private List<Schoolyearschedule> schoolyearschedules;
    private Integer selectedGradeLevelId;
    private Integer selectedSchoolYear;
    private List<StudentVO> studentsForSubjectEnrollment;
    private List<StudentVO> allStudentsAndSubjectShedules;

    @PostConstruct
    public void init() {
        populateGradeLevels();
        populateSchoolYearschedules();
    }

    private void populateGradeLevels() {
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            String queryString = "select gl from Gradelevel gl";
            Query query = entityManager.createQuery(queryString);
            this.setGradelevels((List<Gradelevel>) query.getResultList());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateSchoolYearschedules() {
        try {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(cal.YEAR);
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            String queryString = "select sys from Schoolyearschedule sys "
                    + "where sys.schoolyear >= (select s.schoolyear from Schoolyearschedule s where "
                    + "s.active = :active) "
                    + "order by sys.schoolyear asc";
            Query query = entityManager.createQuery(queryString);
            query.setParameter("active", new Short("1"));
            this.setSchoolyearschedules((List<Schoolyearschedule>) query.getResultList());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String retrieveStudentsForSubjectEnrollment() {
        this.setStudentsForSubjectEnrollment(null);
        this.setAllStudentsAndSubjectShedules(null);
        //Initial validation to check valid Grade Level and School Year is selected.
        if (this.selectedGradeLevelId == 0 || this.selectedSchoolYear == 0) {
            setInfoMessage("Please select valid School year and Grade level.");
            return null;
        }

        //Initial validation to check if valid schedules are created for the selected school year.
        List<TeacherSchedule> teacherScheduleList = retrieveSubjectSchedules();
        if (teacherScheduleList.isEmpty()) {
            setInfoMessage("There are no schedules available for the selected school year.");
            return null;
        }

        List<StudentVO> studentVOs = retrieveEnrolledStudentsAndSubjects();
        if (studentVOs.isEmpty()) {
            setInfoMessage("There are no students available for subject enrollment for the selected school year and gradelevel.");
            return null;
        }
        this.setStudentsForSubjectEnrollment(studentVOs);
        this.setAllStudentsAndSubjectShedules(null);
        return null;
    }

    public String retrieveAllEnrolledStudentsAndSubjects() {
        List<Studentgradelevel> studentGradeLevels = null;
        List<Studentsubjectschedule> studentsubjectschedules = null;
        List<StudentVO> studentVOs = new ArrayList<StudentVO>();
        StudentVO studentVO = null;
        EntityManager entityManager = null;

        this.setStudentsForSubjectEnrollment(null);
        this.setAllStudentsAndSubjectShedules(null);
        //Initial validation to check valid Grade Level and School Year is selected.
        if (this.selectedGradeLevelId == 0 || this.selectedSchoolYear == 0) {
            setInfoMessage("Please select valid School year and Grade level.");
            return null;
        }

        try {
            entityManager = entityManagerFactory.createEntityManager();
            String queryString = "select stgl from Studentgradelevel stgl where "
                    + "stgl.schoolyear.schoolyear = :schoolyear and "
                    + "stgl.gradelevel.gradelevelid = :gradelevelid "
                    + "order by stgl.student.profile.firstname asc";
            Query query = entityManager.createQuery(queryString);
            query.setParameter("schoolyear", this.selectedSchoolYear);
            query.setParameter("gradelevelid", this.selectedGradeLevelId);
            studentGradeLevels = (List<Studentgradelevel>) query.getResultList();
            for (Studentgradelevel studentgradelevel : studentGradeLevels) {
                studentVO = new StudentVO();
                studentVO.setStudentid(studentgradelevel.getStudent().getStudentid());
                studentVO.setFirstName(studentgradelevel.getStudent().getProfile().getFirstname());
                studentVO.setLastName(studentgradelevel.getStudent().getProfile().getLastname());
                studentsubjectschedules = retrieveStudentSchedulesByStudentId(studentgradelevel.getStudent().getStudentid());
                String mondaySchedule = "";
                String tuesdaySchedule = "";
                String wednesdaySchedule = "";
                String thursdaySchedule = "";
                String fridaySchedule = "";
                String scheduleName = "";
                String scheduleDays = "";
                for (Studentsubjectschedule studentsubjectschedule : studentsubjectschedules) {
                    scheduleDays = studentsubjectschedule.getSubjectscheduleid().getScheduledays();
                    scheduleName = studentsubjectschedule.getSubjectscheduleid().getPeriod().getStarttime() + "-"
                            + studentsubjectschedule.getSubjectscheduleid().getPeriod().getEndtime() + " "
                            + studentsubjectschedule.getSubjectscheduleid().getSchedulename();
                    if (scheduleDays.indexOf("M") != -1) {
                        mondaySchedule = mondaySchedule + scheduleName + "<br/>";
                    }
                    if (scheduleDays.indexOf("T") != -1) {
                        tuesdaySchedule = tuesdaySchedule + scheduleName + "<br/>";
                    }
                    if (scheduleDays.indexOf("W") != -1) {
                        wednesdaySchedule = wednesdaySchedule + scheduleName + "<br/>";
                    }
                    if (scheduleDays.indexOf("TH") != -1) {
                        thursdaySchedule = thursdaySchedule + scheduleName + "<br/>";
                    }
                    if (scheduleDays.indexOf("F") != -1) {
                        fridaySchedule = fridaySchedule + scheduleName + "<br/>";
                    }
                }
                studentVO.setMondaySchedule(mondaySchedule);
                studentVO.setTuesdaySchedule(tuesdaySchedule);
                studentVO.setWednesdaySchedule(wednesdaySchedule);
                studentVO.setThursdaySchedule(thursdaySchedule);
                studentVO.setFridaySchedule(fridaySchedule);
                if (!studentsubjectschedules.isEmpty()) {
                    studentVOs.add(studentVO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (studentVOs.isEmpty()) {
            setInfoMessage("There are no students enrolled to the subjects for the selected grade level and school year");
            return null;
        }
        this.setStudentsForSubjectEnrollment(null);
        this.setAllStudentsAndSubjectShedules(studentVOs);
        return null;
    }

    private List<Studentsubjectschedule> retrieveStudentSchedulesByStudentId(Integer argStudentId) {
        List<Studentsubjectschedule> studentsubjectschedules = null;
        EntityManager entityManager = null;
        entityManager = entityManagerFactory.createEntityManager();
        String queryString = "select sss from Studentsubjectschedule sss where "
                + "sss.student.studentid=:studentid and "
                + "sss.subjectscheduleid.schoolyear.schoolyear=:schoolyear "
                + "order by sss.subjectscheduleid.period.sortorder asc";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("studentid", argStudentId);
        query.setParameter("schoolyear", this.selectedSchoolYear);
        studentsubjectschedules = (List<Studentsubjectschedule>) query.getResultList();
        return studentsubjectschedules;
    }

    private List retrieveEnrolledStudentsAndSubjects() {
        List<Studentgradelevel> studentGradeLevels = null;
        List<StudentVO> studentVOs = new ArrayList<StudentVO>();
        List<TeacherScheduleVO> ts = null;
        StudentVO studentVO = null;
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            String queryString = "select stgl from Studentgradelevel stgl where "
                    + "stgl.schoolyear.schoolyear = :schoolyear and "
                    + "stgl.gradelevel.gradelevelid = :gradelevelid and "
                    + "stgl.status=:status "
                    + "order by stgl.student.profile.firstname asc";
            Query query = entityManager.createQuery(queryString);
            query.setParameter("schoolyear", this.selectedSchoolYear);
            query.setParameter("gradelevelid", this.selectedGradeLevelId);
            query.setParameter("status",null);
            studentGradeLevels = (List<Studentgradelevel>) query.getResultList();
            for (Studentgradelevel studentGradeLevel : studentGradeLevels) {
                studentVO = new StudentVO();
                studentVO.setStudentid(studentGradeLevel.getStudent().getStudentid());
                studentVO.setFirstName(studentGradeLevel.getStudent().getProfile().getFirstname());
                studentVO.setLastName(studentGradeLevel.getStudent().getProfile().getLastname());
                ts = retrieveSubjectSchedules(studentVO);
                studentVO.setSubjectSchedules(ts);
                studentVOs.add(studentVO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return studentVOs;
    }

    private List retrieveSubjectSchedules() {
        List<TeacherSchedule> teacherSchedules = null;
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String queryString = "select t from TeacherSchedule t where "
                + "t.schoolyear.schoolyear = :schoolyear ";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("schoolyear", this.selectedSchoolYear);
        teacherSchedules = (List<TeacherSchedule>) query.getResultList();
        return teacherSchedules;
    }

    private List retrieveSubjectSchedules(StudentVO argStudentVO) {
        List<TeacherSchedule> teacherSchedules = null;
        List<TeacherScheduleVO> teacherScheduleVOs = new ArrayList<TeacherScheduleVO>();
        List<StudentSchedule> studentsubjectschedules = null;
        TeacherScheduleVO teacherScheduleVO = null;
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            String queryString = "select t from TeacherSchedule t where "
                    + "t.schoolyear.schoolyear = :schoolyear ";
            Query query = entityManager.createQuery(queryString);
            query.setParameter("schoolyear", this.selectedSchoolYear);
            teacherSchedules = (List<TeacherSchedule>) query.getResultList();
            for (TeacherSchedule teacherSchedule : teacherSchedules) {
                teacherScheduleVO = new TeacherScheduleVO();
                teacherScheduleVO.setSubjectScheduleId(teacherSchedule.getSubjectscheduleid());
                teacherScheduleVO.setScheduleName(teacherSchedule.getSchedulename());
                teacherScheduleVO.setPeriodStart(teacherSchedule.getPeriod().getStarttime());
                teacherScheduleVO.setPeriodEnd(teacherSchedule.getPeriod().getEndtime());
                teacherScheduleVO.setScheduleDays(teacherSchedule.getScheduledays());
                studentsubjectschedules = retrieveEnrolledStudentSubjects(argStudentVO, teacherScheduleVO);
                if (studentsubjectschedules.isEmpty()) {
                    teacherScheduleVO.setSelected(false);
                } else {
                    teacherScheduleVO.setSelected(true);
                }
                teacherScheduleVOs.add(teacherScheduleVO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return teacherScheduleVOs;
    }

    public String enrollStudentsForSubjects(List<StudentVO> argStudentsForSubjectEnrollment) {
        List<Studentsubjectschedule> studentsubjectschedules = null;
        EntityManager entityManager = null;
        for (StudentVO studentVO : argStudentsForSubjectEnrollment) {
            for (TeacherScheduleVO teacherScheduleVO : studentVO.getSubjectSchedules()) {
                // check any subject schedule is selected in the screen
                if (teacherScheduleVO.isSelected()) {
                    studentsubjectschedules = retrieveEnrolledStudentSubjects(studentVO, teacherScheduleVO);
                    // Check if student has already enrolled to the subject. 
                    // If already enrolled then skip the record.
                    // If not enrolled then insert 
                    if (studentsubjectschedules.isEmpty()) {
                        //insert
                        insertStudentSubjectEnrollment(studentVO, teacherScheduleVO);
                    } else {
                        //No action needed.
                    }
                } else { // If any subject schedule is not selected in the screen
                    studentsubjectschedules = retrieveEnrolledStudentSubjects(studentVO, teacherScheduleVO);
                    // Check if student has already enrolled to the subject. 
                    // If already enrolled then delete the record.
                    // If not enrolled then skip 
                    if (!studentsubjectschedules.isEmpty()) {
                        //delete
                        deleteEnrolledStudentSubjects(studentVO, teacherScheduleVO);
                    } else {
                        //No action needed.
                    }
                }
            }
        }
        this.setStudentsForSubjectEnrollment(null);
        this.setSelectedGradeLevelId(0);
        this.setSelectedSchoolYear(0);
        setInfoMessage("Subject enrollments are updated successfully.");
        return null;
    }

    private List retrieveEnrolledStudentSubjects(StudentVO argStudentVO, TeacherScheduleVO argTeacherScheduleVO) {
        List<Studentsubjectschedule> studentsubjectschedules = null;
        EntityManager entityManager = null;
        entityManager = entityManagerFactory.createEntityManager();
        String queryString = "select sss from Studentsubjectschedule sss where "
                + "sss.subjectscheduleid.subjectscheduleid = :subjectscheduleid and "
                + "sss.student.studentid=:studentid";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("subjectscheduleid", argTeacherScheduleVO.getSubjectScheduleId());
        query.setParameter("studentid", argStudentVO.getStudentid());
        studentsubjectschedules = (List<Studentsubjectschedule>) query.getResultList();
        return studentsubjectschedules;
    }

    private void insertStudentSubjectEnrollment(StudentVO argStudentVO, TeacherScheduleVO argTeacherScheduleVO) {
        Studentsubjectschedule studentsubjectschedule = null;
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            userTransaction.begin();
            studentsubjectschedule = new Studentsubjectschedule();
            Student st = entityManager.find(Student.class, argStudentVO.getStudentid());
            studentsubjectschedule.setStudent(st);
            TeacherSchedule teacherSchedule = entityManager.find(TeacherSchedule.class, argTeacherScheduleVO.getSubjectScheduleId());
            studentsubjectschedule.setSubjectscheduleid(teacherSchedule);
            entityManager.persist(studentsubjectschedule);
            userTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int deleteEnrolledStudentSubjects(StudentVO argStudentVO, TeacherScheduleVO argTeacherScheduleVO) {
        int result = 0;
        List<Studentsubjectschedule> studentsubjectschedules = null;
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            String queryString = "delete from Studentsubjectschedule sss where "
                    + "sss.subjectscheduleid.subjectscheduleid = :subjectscheduleid and "
                    + "sss.student.studentid=:studentid";
            Query query = entityManager.createQuery(queryString);
            query.setParameter("subjectscheduleid", argTeacherScheduleVO.getSubjectScheduleId());
            query.setParameter("studentid", argStudentVO.getStudentid());
            userTransaction.begin();
            result = query.executeUpdate();
            userTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    protected void setInfoMessage(String summary) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
    }

    /**
     * @return the gradelevels
     */
    public List<Gradelevel> getGradelevels() {
        return gradelevels;
    }

    /**
     * @param gradelevels the gradelevels to set
     */
    public void setGradelevels(List<Gradelevel> gradelevels) {
        this.gradelevels = gradelevels;
    }

    /**
     * @return the gradeLevelId
     */
    public Integer getSelectedGradeLevelId() {
        return selectedGradeLevelId;
    }

    /**
     * @param gradeLevelId the gradeLevelId to set
     */
    public void setSelectedGradeLevelId(Integer selectedGradeLevelId) {
        this.selectedGradeLevelId = selectedGradeLevelId;
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

    /**
     * @return the studentsForSubjectEnrollment
     */
    public List<StudentVO> getStudentsForSubjectEnrollment() {
        return studentsForSubjectEnrollment;
    }

    /**
     * @param studentsForSubjectEnrollment the studentsForSubjectEnrollment to
     * set
     */
    public void setStudentsForSubjectEnrollment(List<StudentVO> studentsForSubjectEnrollment) {
        this.studentsForSubjectEnrollment = studentsForSubjectEnrollment;
    }

    /**
     * @return the allStudentsAndSubjectShedules
     */
    public List<StudentVO> getAllStudentsAndSubjectShedules() {
        return allStudentsAndSubjectShedules;
    }

    /**
     * @param allStudentsAndSubjectShedules the allStudentsAndSubjectShedules to
     * set
     */
    public void setAllStudentsAndSubjectShedules(List<StudentVO> allStudentsAndSubjectShedules) {
        this.allStudentsAndSubjectShedules = allStudentsAndSubjectShedules;
    }
}
