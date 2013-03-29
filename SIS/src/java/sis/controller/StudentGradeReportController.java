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
import sis.bean.StudentScoreCardVO;
import sis.model.Gradelevel;
import sis.model.Schoolyearschedule;
import sis.model.Student;
import sis.model.Studentscorecard;
import sis.model.TeacherSchedule;

/**
 *
 * @author Veekija
 */
@ManagedBean
public class StudentGradeReportController {
    
    @PersistenceUnit(unitName = "SISPU")
    private EntityManagerFactory entityManagerFactory;
    @Resource
    private UserTransaction userTransaction;
    private List<Gradelevel> gradelevels;
    private List<Schoolyearschedule> schoolyearschedules;
    private Integer selectedGradeLevelId;
    private Integer selectedSchoolYear;
    private List<StudentScoreCardVO> studentscorecardVOs = null;
    
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
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            String queryString = "select sys from Schoolyearschedule sys";
            Query query = entityManager.createQuery(queryString);
            this.setSchoolyearschedules((List<Schoolyearschedule>) query.getResultList());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String generateReport() {
        List<StudentScoreCardVO> scVOs = null;
        StudentScoreCardVO scVO = null;
        if (this.selectedGradeLevelId == 0 || this.selectedSchoolYear == 0) {
            setInfoMessage("Please select Grade Level and School year.");
        } else {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            String selectSQL = "select sc from Studentscorecard sc where sc.studentId in "
                    + "(select sgl.student.studentid from Studentgradelevel sgl where sgl.schoolyear.schoolyear=:schoolyear "
                    + "and sgl.gradelevel.gradelevelid = :gradelevelid)";
            try {
                Query selectQuery = entityManager.createQuery(selectSQL);
                selectQuery.setParameter("gradelevelid", this.selectedGradeLevelId);
                selectQuery.setParameter("schoolyear", this.selectedSchoolYear);
                List<Studentscorecard> scorecards = (List<Studentscorecard>) selectQuery.getResultList();
                
                if (scorecards == null || scorecards.size() == 0) {
                    setInfoMessage("There are no matching student score card exists.");
                } else {
                    scVOs = new ArrayList<StudentScoreCardVO>();
                    for (Studentscorecard scorecard : scorecards){
                        scVO = new StudentScoreCardVO();
                        Student st = entityManager.find(Student.class, scorecard.getStudentId());
                        scVO.setStudentid(scorecard.getStudentId());
                        scVO.setFirstName(st.getProfile().getFirstname());
                        scVO.setLastName(st.getProfile().getLastname());
                        scVO.setFinalscore(scorecard.getFinalscore());
                        scVO.setGradeletter(scorecard.getGradeletter());
                        scVO.setComments(scorecard.getComments());
                        scVO.setStatus(scorecard.getStatus());
                        scVOs.add(scVO);
                    }
                    this.setStudentscorecardVOs(scVOs);
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("studentscorecardVOs", scVOs);
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
    
    protected void setInfoMessage(String summary) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
    }

    /**
     * @return the studentscorecardVOs
     */
    public List<StudentScoreCardVO> getStudentscorecardVOs() {
        return studentscorecardVOs;
    }

    /**
     * @param studentscorecardVOs the studentscorecardVOs to set
     */
    public void setStudentscorecardVOs(List<StudentScoreCardVO> studentscorecardVOs) {
        this.studentscorecardVOs = studentscorecardVOs;
    }

   
    
}
