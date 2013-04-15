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
import sis.model.Schoolyearschedule;
import sis.model.Student;
import sis.model.Gradelevel;
import sis.model.Studentgradelevel;

/**
 *
 * @author Veekija
 */
@ManagedBean
@ViewScoped
public class StudentEnrollmentController implements Serializable {

    @PersistenceUnit(unitName = "SISPU")
    private EntityManagerFactory entityManagerFactory;
    @Resource
    private UserTransaction userTransaction;
    private List<Gradelevel> gradelevels;
    private List<Schoolyearschedule> schoolyearschedules;
    private List<StudentVO> studentVOs;
    private List<StudentVO> modifyStudentVOs;
    private List<StudentVO> allEnrolledStudentVOs;
    private Integer selectedGradeLevelId;
    private Integer selectedSchoolYear;

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

    public String retrieveNewStudentsForEnrollment() {
        if (this.selectedGradeLevelId == 0 || this.selectedSchoolYear == 0) {
            setInfoMessage("Please select valid School year and Grade level.");
            return null;
        }
        populateNewStudents();
        if (this.getStudentVOs() == null) {
            setInfoMessage("There are no students available for enrollment.");
            //return null;
        }
        this.setModifyStudentVOs(null);
        this.setAllEnrolledStudentVOs(null);
        return null;
    }

    public String retrieveEnrolledStudentsForUpdate() {
        if (this.selectedGradeLevelId == 0 || this.selectedSchoolYear == 0) {
            setInfoMessage("Please select valid School year and Grade level.");
            return null;
        }
        populateEnrolledStudents();
        if (this.getModifyStudentVOs() == null) {
            setInfoMessage("There are no students available for enrollment updates.");
            //return null;
        }
        this.setStudentVOs(null);
        this.setAllEnrolledStudentVOs(null);
        return null;
    }

    public String retrieveAllEnrolledStudents() {
        if (this.selectedGradeLevelId == 0 || this.selectedSchoolYear == 0) {
            setInfoMessage("Please select valid School year and Grade level.");
            return null;
        }
        populateAllEnrolledStudents();
        if (this.getAllEnrolledStudentVOs() == null) {
            setInfoMessage("There are no students enrolled yet.");
            //return null;
        }
        this.setStudentVOs(null);
        this.setModifyStudentVOs(null);
        return null;
    }

    private void populateNewStudents() {
        List<StudentVO> studentVOs = null;
        this.setStudentVOs(studentVOs);
        List<Student> potentialNewStudents = null;

        //Retrieve newly admitted students 
        List<Student> newlyAdmittedStudents = retrieveNewlyAdmittedStudents();
        if (newlyAdmittedStudents != null) {
            if (!newlyAdmittedStudents.isEmpty()) {
                potentialNewStudents = new ArrayList<Student>(newlyAdmittedStudents);
            }
        }

        //Retrieve passed students 
        List<Student> passedStudents = retrievePassedStudents();
        if (passedStudents != null) {
            if (!passedStudents.isEmpty()) {
                if (potentialNewStudents == null) {
                    potentialNewStudents = new ArrayList<Student>(passedStudents);
                } else {
                    potentialNewStudents.addAll(passedStudents);
                }
            }
        }

        //Retrieve faileed students 
        List<Student> failedStudents = retrieveFailedStudents();
        if (failedStudents != null) {
            if (!failedStudents.isEmpty()) {
                if (potentialNewStudents == null) {
                    potentialNewStudents = new ArrayList<Student>(failedStudents);
                } else {
                    potentialNewStudents.addAll(failedStudents);
                }
            }
        }

        List<Student> actualNewStudents = retrieveActualNewStudents(potentialNewStudents);
        studentVOs = convertStudentToStudentVO(actualNewStudents);
        this.setStudentVOs(studentVOs);
    }

    private List<Student> retrieveNewlyAdmittedStudents() {
        List<Student> sts = null;
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            String queryString = "select st from Student st where st.admissionid in "
                    + "(select ad.admissionid from Admission ad where "
                    + "ad.gradelevelapplyingfor = :gradelevelapplyingfor and "
                    + "ad.admissionseekingyear = :admissionseekingyear and "
                    + "ad.status = :status)";
            Query query = entityManager.createQuery(queryString);
            query.setParameter("gradelevelapplyingfor", this.selectedGradeLevelId + "");
            query.setParameter("admissionseekingyear", this.selectedSchoolYear + "");
            query.setParameter("status", "Granted");
            sts = (List<Student>) query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sts;
    }

    private List<Student> retrievePassedStudents() {
        List<Student> sts = null;
        Gradelevel grd = retrirevePreviousGradeLevel();
        if (grd != null) {
            try {
                EntityManager entityManager = entityManagerFactory.createEntityManager();
                String queryString = "select st from Student st where st.studentid in "
                        + "(select stgl.student.studentid from Studentgradelevel stgl where "
                        + "stgl.status = :status and "
                        + "stgl.schoolyear.schoolyear = :schoolyear and "
                        + "stgl.gradelevel.gradelevelid = :gradelevelid)";
                Query query = entityManager.createQuery(queryString);
                query.setParameter("status", "PASS");
                query.setParameter("schoolyear", this.selectedSchoolYear - 1);
                query.setParameter("gradelevelid", grd.getGradelevelid());
                sts = (List<Student>) query.getResultList();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sts;
    }

    private List<Student> retrieveFailedStudents() {
        List<Student> sts = null;
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            String queryString = "select st from Student st where st.studentid in "
                    + "(select stgl.student.studentid from Studentgradelevel stgl where "
                    + "stgl.status = :status and "
                    + "stgl.schoolyear.schoolyear = :schoolyear and "
                    + "stgl.gradelevel.gradelevelid = :gradelevelid)";
            Query query = entityManager.createQuery(queryString);
            query.setParameter("status", "FAIL");
            query.setParameter("schoolyear", this.selectedSchoolYear - 1);
            query.setParameter("gradelevelid", this.selectedGradeLevelId);
            sts = (List<Student>) query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sts;
    }

    private Gradelevel retrirevePreviousGradeLevel() {
        Gradelevel grd = null;
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            Gradelevel glvl = entityManager.find(Gradelevel.class, this.selectedGradeLevelId);
            if (glvl.getSortorder() > 1) {
                String queryString = "select gl from Gradelevel gl where "
                        + "gl.sortorder = :sortorder";
                Query query = entityManager.createQuery(queryString);
                query.setParameter("sortorder", glvl.getSortorder() - 1);
                grd = (Gradelevel) query.getSingleResult();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return grd;
    }

    private List<Student> retrieveActualNewStudents(List<Student> argPotentialNewStudents) {
        List<Student> actualNewStudents = new ArrayList<Student>();
        if (argPotentialNewStudents != null) {
            if (!argPotentialNewStudents.isEmpty()) {
                for (Student st : argPotentialNewStudents) {
                    EntityManager entityManager = entityManagerFactory.createEntityManager();
                    String queryString = "select stgl from Studentgradelevel stgl where "
                            + "stgl.student.studentid = :studentid and "
                            + "stgl.schoolyear.schoolyear = :schoolyear and "
                            + "stgl.gradelevel.gradelevelid = :gradelevelid";
                    Query query = entityManager.createQuery(queryString);
                    query.setParameter("studentid", st.getStudentid());
                    query.setParameter("schoolyear", this.selectedSchoolYear);
                    query.setParameter("gradelevelid", this.selectedGradeLevelId);
                    List stglvls = (List<Studentgradelevel>) query.getResultList();
                    if (stglvls.isEmpty()) {
                        actualNewStudents.add(st);
                    }
                }
            }
        }
        return actualNewStudents;
    }

    private void populateEnrolledStudents() {
        List<StudentVO> modifySVOs = null;
        this.setModifyStudentVOs(modifySVOs);
        List<Studentgradelevel> stgrdlvls = null;
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            String queryString = "select stgl from Studentgradelevel stgl where "
                    + "stgl.schoolyear.schoolyear = :schoolyear and "
                    + "stgl.gradelevel.gradelevelid = :gradelevelid and "
                    + "stgl.gradelevel.status = :status";
            Query query = entityManager.createQuery(queryString);
            query.setParameter("schoolyear", this.selectedSchoolYear);
            query.setParameter("gradelevelid", this.selectedGradeLevelId);
            query.setParameter("status", null);
            stgrdlvls = (List<Studentgradelevel>) query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        modifySVOs = convertStudentGradeLevelToStudentVO(stgrdlvls);
        this.setModifyStudentVOs(modifySVOs);
    }

    private void populateAllEnrolledStudents() {
        List<StudentVO> allEnrolledSVOs = null;
        this.setAllEnrolledStudentVOs(allEnrolledSVOs);
        List<Studentgradelevel> stgrdlvls = null;
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            String queryString = "select stgl from Studentgradelevel stgl where "
                    + "stgl.schoolyear.schoolyear = :schoolyear and "
                    + "stgl.gradelevel.gradelevelid = :gradelevelid";
            Query query = entityManager.createQuery(queryString);
            query.setParameter("schoolyear", this.selectedSchoolYear);
            query.setParameter("gradelevelid", this.selectedGradeLevelId);
            stgrdlvls = (List<Studentgradelevel>) query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        allEnrolledSVOs = convertStudentGradeLevelToStudentVO(stgrdlvls);
        this.setAllEnrolledStudentVOs(allEnrolledSVOs);
    }

    private List<StudentVO> convertStudentGradeLevelToStudentVO(List<Studentgradelevel> argStudentGradeLevels) {
        List<StudentVO> studentVOs = null;
        StudentVO sVO = null;
        if (argStudentGradeLevels != null) {
            if (!argStudentGradeLevels.isEmpty()) {
                studentVOs = new ArrayList<StudentVO>();
                for (Studentgradelevel st : argStudentGradeLevels) {
                    sVO = new StudentVO();
                    sVO.setStudentid(st.getStudent().getStudentid());
                    sVO.setFirstName(st.getStudent().getProfile().getFirstname());
                    sVO.setLastName(st.getStudent().getProfile().getLastname());
                    sVO.setStudentgradelevelid(st.getStudentgradelevelid());
                    sVO.setSelected(true);
                    studentVOs.add(sVO);
                }
            }
        }
        return studentVOs;
    }

    private List<StudentVO> convertStudentToStudentVO(List<Student> argStudents) {
        List<StudentVO> studentVOs = null;
        StudentVO sVO = null;
        if (argStudents != null) {
            if (!argStudents.isEmpty()) {
                studentVOs = new ArrayList<StudentVO>();
                for (Student st : argStudents) {
                    sVO = new StudentVO();
                    sVO.setStudentid(st.getStudentid());
                    sVO.setFirstName(st.getProfile().getFirstname());
                    sVO.setLastName(st.getProfile().getLastname());
                    studentVOs.add(sVO);
                }
            }
        }
        return studentVOs;
    }

    public String enrollNewStudents(List<StudentVO> argStudents) {
        EntityManager entityManager = null;
        Studentgradelevel sglvl = null;
        for (StudentVO studentItem : argStudents) {
            if (studentItem.isSelected()) {
                try {
                    entityManager = entityManagerFactory.createEntityManager();
                    userTransaction.begin();
                    sglvl = new Studentgradelevel();
                    Gradelevel glvl = entityManager.find(Gradelevel.class, this.selectedGradeLevelId);
                    sglvl.setGradelevel(glvl);
                    Schoolyearschedule syc = entityManager.find(Schoolyearschedule.class, this.selectedSchoolYear);
                    sglvl.setSchoolyear(syc);
                    Student st = entityManager.find(Student.class, studentItem.getStudentid());
                    sglvl.setStudent(st);
                    entityManager.persist(sglvl);
                    userTransaction.commit();
                    studentItem.setSelected(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        setInfoMessage("Students have been successfully enrolled !");
        setStudentVOs(null);
        return null;
        //return "/admin/studentUpdate_1?faces-redirect=true";
    }

    public String updateEnrolledStudents(List<StudentVO> argStudents) {
        EntityManager entityManager = null;
        Studentgradelevel sglvl = null;
        for (StudentVO studentItem : argStudents) {
            try {
                if (!studentItem.isSelected()) {
                    userTransaction.begin();
                    entityManager = entityManagerFactory.createEntityManager();
                    sglvl = entityManager.find(Studentgradelevel.class, studentItem.getStudentgradelevelid());
                    entityManager.remove(sglvl);
                    userTransaction.commit();
                    //studentItem.setSelected(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        setInfoMessage("Students have been successfully enrolled !");
        setModifyStudentVOs(null);
        return null;
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
     * @return the students
     */
    public List<StudentVO> getStudentVOs() {
        return studentVOs;
    }

    /**
     * @param students the students to set
     */
    public void setStudentVOs(List<StudentVO> students) {
        this.studentVOs = students;
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
     * @return the modifyStudentVOs
     */
    public List<StudentVO> getModifyStudentVOs() {
        return modifyStudentVOs;
    }

    /**
     * @param modifyStudentVOs the modifyStudentVOs to set
     */
    public void setModifyStudentVOs(List<StudentVO> modifyStudentVOs) {
        this.modifyStudentVOs = modifyStudentVOs;
    }

    /**
     * @return the allEnrolledStudentVOs
     */
    public List<StudentVO> getAllEnrolledStudentVOs() {
        return allEnrolledStudentVOs;
    }

    /**
     * @param allEnrolledStudentVOs the allEnrolledStudentVOs to set
     */
    public void setAllEnrolledStudentVOs(List<StudentVO> allEnrolledStudentVOs) {
        this.allEnrolledStudentVOs = allEnrolledStudentVOs;
    }
}
