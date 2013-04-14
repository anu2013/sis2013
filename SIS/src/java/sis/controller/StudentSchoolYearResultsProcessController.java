/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.controller;

import java.io.Serializable;
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
import sis.model.Schoolyearschedule;
import sis.model.Studentgradelevel;
import sis.model.Studentscorecard;

/**
 *
 * @author Veekija
 */
@ManagedBean
@ViewScoped
public class StudentSchoolYearResultsProcessController implements Serializable {

    @PersistenceUnit(unitName = "SISPU")
    private EntityManagerFactory entityManagerFactory;
    @Resource
    private UserTransaction userTransaction;
    private List<Schoolyearschedule> schoolyearschedules;
    private Integer selectedSchoolYear;
    private Integer selectedPassScore = 70;

    @PostConstruct
    public void init() {
        populateSchoolYearschedules();
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

    public String processStudentSchoolYearResults() {
        //Initial validation to check valid Grade Level and School Year is selected.
        if (this.selectedSchoolYear == 0) {
            setInfoMessage("Please select valid School year and Grade level.");
            return null;
        }
        System.out.println("Selected Pass scores is = " + selectedPassScore);
        updateStudentResults();
        setInfoMessage("Student results for the selected year have bean updated.");
        return null;
    }

    private void updateStudentResults() {
        List<Studentgradelevel> studentGradeLevels;
        List<Studentscorecard> studentScoreCards;
        EntityManager entityManager = null;
        String studentGradeLevelQueryString;
        Query studentGradeLevelQuery;
        String studentScoreCardQueryString;
        Query studentScoreCardQuery;
        String updateQueryString;
        Query updateQuery;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            studentGradeLevelQueryString = "select stgl from Studentgradelevel stgl where "
                    + "stgl.schoolyear.schoolyear = :schoolyear";
            studentGradeLevelQuery = entityManager.createQuery(studentGradeLevelQueryString);
            studentGradeLevelQuery.setParameter("schoolyear", this.selectedSchoolYear);
            studentGradeLevels = (List<Studentgradelevel>) studentGradeLevelQuery.getResultList();
            for (Studentgradelevel studentGradeLevel : studentGradeLevels) {
                studentScoreCardQueryString = "select stscd from Studentscorecard stscd where "
                        + "stscd.studentId=:studentId and "
                        + "stscd.schoolyear=:schoolyear";
                studentScoreCardQuery = entityManager.createQuery(studentScoreCardQueryString);
                studentScoreCardQuery.setParameter("studentId", studentGradeLevel.getStudent().getStudentid());
                studentScoreCardQuery.setParameter("schoolyear", this.selectedSchoolYear);
                studentScoreCards = (List<Studentscorecard>) studentScoreCardQuery.getResultList();
                int passedSubjectCount = 0;
                int totalSubjectsEnrolledCount = studentScoreCards.size();
                for (Studentscorecard studentscorecard : studentScoreCards) {
                    int finalScore = Integer.parseInt(studentscorecard.getFinalscore());
                    if (finalScore >= selectedPassScore.intValue()) {
                        passedSubjectCount = passedSubjectCount + 1;
                    }
                }
                if (totalSubjectsEnrolledCount != 0) {
                    String resultStatus = "";
                    if (passedSubjectCount == totalSubjectsEnrolledCount) {
                        resultStatus = "PASS";
                    } else {
                        resultStatus = "FAIL";
                    }
                    entityManager = entityManagerFactory.createEntityManager();
                    userTransaction.begin();
                    updateQueryString = "update Studentgradelevel stgl set stgl.status=:status where "
                            + "stgl.studentgradelevelid=:studentgradelevelid";
                    updateQuery = entityManager.createQuery(updateQueryString);
                    updateQuery.setParameter("status", resultStatus);
                    updateQuery.setParameter("studentgradelevelid", studentGradeLevel.getStudentgradelevelid());
                    updateQuery.executeUpdate();
                    userTransaction.commit();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateStudentGradeLevelStatus() {
    }

    protected void setInfoMessage(String summary) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
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
     * @return the selectedPassScore
     */
    public Integer getSelectedPassScore() {
        return selectedPassScore;
    }

    /**
     * @param selectedPassScore the selectedPassScore to set
     */
    public void setSelectedPassScore(Integer selectedPassScore) {
        this.selectedPassScore = selectedPassScore;
    }
}
