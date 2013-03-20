/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.controller;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import sis.model.Gradelevel;
import java.util.List;
import java.util.Map;
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
import javax.transaction.UserTransaction;
import sis.model.Schoolyearschedule;
import sis.model.Student;

/**
 *
 * @author Veekija
 */
@ManagedBean
@ViewScoped
public class StudentEnrollmentController implements Serializable{

    @PersistenceUnit(unitName = "SISPU")
    private EntityManagerFactory entityManagerFactory;
    @Resource
    private UserTransaction userTransaction;
    private List<Gradelevel> gradelevels;
    private List<Schoolyearschedule> schoolyearschedules;
    private List<Student> students;
//    @ManagedProperty(value = "#{student}")
//    private Student student;
    private Integer selectedGradeLevelId;
    private Integer selectedSchoolYear;
    private Map<Integer, Boolean> selectedStudentIds = new HashMap<Integer, Boolean>();
    //private List<Student> selectedStudentsList;

    @PostConstruct
    public void init() {
        populateGradeLevels();
        populateSchoolYearschedules();
        populateStudents(0);
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
            String queryString = "select sys from Schoolyearschedule sys where sys.schoolyear >= :schoolyear";
            Query query = entityManager.createQuery(queryString);
            query.setParameter("schoolyear", year);
            this.setSchoolyearschedules((List<Schoolyearschedule>) query.getResultList());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateStudents(int argPa) {
        try {
            if (argPa == 0) {
                this.setStudents(null);
            } else {
                EntityManager entityManager = entityManagerFactory.createEntityManager();
                String queryString = "select s from Student s";
                Query query = entityManager.createQuery(queryString);
                this.setStudents((List<Student>) query.getResultList());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String retrieveStudents() {
        //System.out.println("Selected Grade Level == " + this.selectedGradeLevelId);
        //System.out.println("Selected School Year == " + this.selectedSchoolYear);
        if (this.selectedGradeLevelId == 0 || this.selectedSchoolYear == 0) {
            setInfoMessage("Please select valid School year and Grade level.");
            return null;
        }
        populateGradeLevels();
        populateSchoolYearschedules();
        populateStudents(1);
        return "/admin/studentEnrollment";
    }

    public String enrollStudents(List<Student> argStudents) {
        System.out.println("argStudents == " + argStudents.size());
        System.out.println("Selected Grade Level is == " + this.selectedGradeLevelId);
        System.out.println("Selected School Year == " + this.selectedSchoolYear);

        for (Student studentItem : students) {
            if (selectedStudentIds.get(studentItem.getStudentid()).booleanValue()) {
                System.out.println("Selected Student id is == " + studentItem.getStudentid());
                //selectedStudentsList.add(studentItem);
                selectedStudentIds.remove(studentItem.getStudentid());
            }
        }

        // Do your thing with the MyData items in List selectedDataList.

        return null;
        //return "/admin/studentUpdate_1?faces-redirect=true";
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
    public List<Student> getStudents() {
//        if (students == null) {
//            populateStudents();
//        }
        return students;
    }

    /**
     * @param students the students to set
     */
    public void setStudents(List<Student> students) {
        this.students = students;
    }

   

//   
//    /**
//     * @return the selectedStudentsList
//     */
//    public List<Student> getSelectedStudentsList() {
//        return selectedStudentsList;
//    }
//
//    /**
//     * @param selectedStudentsList the selectedStudentsList to set
//     */
//    public void setSelectedStudentsList(List<Student> selectedStudentsList) {
//        this.selectedStudentsList = selectedStudentsList;
//    }
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
     * @return the selectedStudentIds
     */
    public Map<Integer, Boolean> getSelectedStudentIds() {
        return selectedStudentIds;
    }

    /**
     * @param selectedStudentIds the selectedStudentIds to set
     */
    public void setSelectedStudentIds(Map<Integer, Boolean> selectedStudentIds) {
        this.selectedStudentIds = selectedStudentIds;
    }

    protected void setInfoMessage(String summary) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
    }
}
