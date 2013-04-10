/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import sis.bean.StateReportVO;
import sis.model.Schoolyearschedule;
import sis.model.Studentgradelevel;

/**
 *
 * @author Veekija
 */
@ManagedBean
public class StateReportController {

    @PersistenceUnit(unitName = "SISPU")
    private EntityManagerFactory entityManagerFactory;
    private List<Schoolyearschedule> schoolyearschedules;
    private Integer selectedSchoolYear;
    private List<StateReportVO> stateReportVOs = null;
    private Integer mathsSubjectId = 7;
    private Integer readingSubjectId = 7;

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

        List<StateReportVO> stVOs = new ArrayList<StateReportVO>();
        List<StateReportVO> raceDeatils = retrieveRaceDetails();
        stVOs.addAll(raceDeatils);
        this.setStateReportVOs(stVOs);
        return null;
    }

    private List retrieveRaceDetails() {

        List<StateReportVO> stVOs = new ArrayList<StateReportVO>();
        StateReportVO stateReportVO = null;
        int totalNumberOfStudents = 0;
        int numberOfStudentsTested_Maths = 0;
        double percentageOfStudentsTested_Maths = 0.00;
        int numberOfStudentsScoredAtBasicLevel_Maths = 0;
        double percentageOfStudentsScoredAtBasicLevel_Maths = 0.00;
        int numberOfStudentsScoredAtProficentLevel_Maths = 0;
        double percentageOfStudentsScoredAtProficentLevel_Maths = 0.00;
        int numberOfStudentsScoredAtAdvanceLevel_Maths = 0;
        double percentageOfStudentsScoredAtAdvanceLevel_Maths = 0.00;
        int numberOfStudentsTested_Reading = 0;
        double percentageOfStudentsTested_Reading = 0.00;
        int numberOfStudentsScoredAtBasicLevel_Reading = 0;
        double percentageOfStudentsScoredAtBasicLevel_Reading = 0.00;
        int numberOfStudentsScoredAtProficentLevel_Reading = 0;
        double percentageOfStudentsScoredAtProficentLevel_Reading = 0.00;
        int numberOfStudentsScoredAtAdvanceLevel_Reading = 0;
        double percentageOfStudentsScoredAtAdvanceLevel_Reading = 0.00;

        List<String> raceList = new ArrayList<String>();
        raceList.add("American Indian or Alaska Native");
        raceList.add("Asian");
        raceList.add("Black or African American");
        raceList.add("Native Hawaiian and Other Pacific Islander");
        raceList.add("White");

        for (String race : raceList) {
            totalNumberOfStudents = retrieveTotalStudentsCount(race, "RACE");

            numberOfStudentsTested_Maths = retrieveTotalStudentsTestedCount(race, "RACE", mathsSubjectId);
            if (numberOfStudentsTested_Maths != 0) {
                percentageOfStudentsTested_Maths = ((double) numberOfStudentsTested_Maths / totalNumberOfStudents) * 100;
            } else {
                percentageOfStudentsTested_Maths = 0;
            }

            numberOfStudentsScoredAtBasicLevel_Maths = retrieveTotalStudentsScoredAtBasicLevel(race, "RACE", mathsSubjectId, "70", "79");
            if (numberOfStudentsScoredAtBasicLevel_Maths != 0) {
                percentageOfStudentsScoredAtBasicLevel_Maths = ((double) numberOfStudentsScoredAtBasicLevel_Maths / totalNumberOfStudents) * 100;
            } else {
                percentageOfStudentsScoredAtBasicLevel_Maths = 0;
            }

            numberOfStudentsScoredAtProficentLevel_Maths = retrieveTotalStudentsScoredAtBasicLevel(race, "RACE", mathsSubjectId, "80", "89");
            if (numberOfStudentsScoredAtProficentLevel_Maths != 0) {
                percentageOfStudentsScoredAtProficentLevel_Maths = ((double) numberOfStudentsScoredAtProficentLevel_Maths / totalNumberOfStudents) * 100;
            } else {
                percentageOfStudentsScoredAtProficentLevel_Maths = 0;
            }

            numberOfStudentsScoredAtAdvanceLevel_Maths = retrieveTotalStudentsScoredAtBasicLevel(race, "RACE", mathsSubjectId, "90", "99");
            if (numberOfStudentsScoredAtAdvanceLevel_Maths != 0) {
                percentageOfStudentsScoredAtAdvanceLevel_Maths = ((double) numberOfStudentsScoredAtAdvanceLevel_Maths / totalNumberOfStudents) * 100;
            } else {
                percentageOfStudentsScoredAtAdvanceLevel_Maths = 0;
            }

            numberOfStudentsTested_Reading = retrieveTotalStudentsTestedCount(race, "RACE", readingSubjectId);
            if (numberOfStudentsTested_Reading != 0) {
                percentageOfStudentsTested_Reading = ((double) numberOfStudentsTested_Reading / totalNumberOfStudents) * 100;
            } else {
                percentageOfStudentsTested_Reading = 0;
            }

            numberOfStudentsScoredAtBasicLevel_Reading = retrieveTotalStudentsScoredAtBasicLevel(race, "RACE", readingSubjectId, "70", "79");
            if (numberOfStudentsScoredAtBasicLevel_Reading != 0) {
                percentageOfStudentsScoredAtBasicLevel_Reading = ((double) numberOfStudentsScoredAtBasicLevel_Reading / totalNumberOfStudents) * 100;
            } else {
                percentageOfStudentsScoredAtBasicLevel_Reading = 0;
            }

            numberOfStudentsScoredAtProficentLevel_Reading = retrieveTotalStudentsScoredAtBasicLevel(race, "RACE", readingSubjectId, "80", "89");
            if (numberOfStudentsScoredAtProficentLevel_Reading != 0) {
                percentageOfStudentsScoredAtProficentLevel_Reading = ((double) numberOfStudentsScoredAtProficentLevel_Reading / totalNumberOfStudents) * 100;
            } else {
                percentageOfStudentsScoredAtProficentLevel_Reading = 0;
            }

            numberOfStudentsScoredAtAdvanceLevel_Reading = retrieveTotalStudentsScoredAtBasicLevel(race, "RACE", readingSubjectId, "90", "99");
            if (numberOfStudentsScoredAtAdvanceLevel_Reading != 0) {
                percentageOfStudentsScoredAtAdvanceLevel_Reading = ((double) numberOfStudentsScoredAtAdvanceLevel_Reading / totalNumberOfStudents) * 100;
            } else {
                percentageOfStudentsScoredAtAdvanceLevel_Reading = 0;
            }

            stateReportVO = new StateReportVO();
            stateReportVO.setSubGroupName(race);

            stateReportVO.setTotalNumberOfStudents_Maths(totalNumberOfStudents);
            stateReportVO.setNumberOfStudentsTested_Maths(numberOfStudentsTested_Maths);
            stateReportVO.setPercentageOfStudentsTested_Maths(percentageOfStudentsTested_Maths);
            stateReportVO.setPercentageOfStudentsScoredAtBasicLevel_Maths(percentageOfStudentsScoredAtBasicLevel_Maths);
            stateReportVO.setPercentageOfStudentsScoredAtProficentLevel_Maths(percentageOfStudentsScoredAtProficentLevel_Maths);
            stateReportVO.setPercentageOfStudentsScoredAtAdvanceLevel_Maths(percentageOfStudentsScoredAtAdvanceLevel_Maths);

            stateReportVO.setTotalNumberOfStudents_Reading(totalNumberOfStudents);
            stateReportVO.setNumberOfStudentsTested_Reading(numberOfStudentsTested_Reading);
            stateReportVO.setPercentageOfStudentsTested_Reading(percentageOfStudentsTested_Reading);
            stateReportVO.setPercentageOfStudentsScoredAtBasicLevel_Reading(percentageOfStudentsScoredAtBasicLevel_Reading);
            stateReportVO.setPercentageOfStudentsScoredAtProficentLevel_Reading(percentageOfStudentsScoredAtProficentLevel_Reading);
            stateReportVO.setPercentageOfStudentsScoredAtAdvanceLevel_Reading(percentageOfStudentsScoredAtAdvanceLevel_Reading);

            stVOs.add(stateReportVO);
        }
        return stVOs;
    }

    private int retrieveTotalStudentsCount(String argSubGroupName, String argSubGroupType) {
        int count = 0;
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String queryString = "";
        Query query = null;
        if ("RACE".equalsIgnoreCase(argSubGroupType)) {
            queryString = "select stglvl from Studentgradelevel stglvl where "
                    + "stglvl.schoolyear.schoolyear=:schoolyear and "
                    + "stglvl.student.race=:race";
            query = entityManager.createQuery(queryString);
            query.setParameter("schoolyear", this.selectedSchoolYear);
            query.setParameter("race", argSubGroupName);
        } else if ("ETHNICITY".equalsIgnoreCase(argSubGroupType)) {
            queryString = "select stglvl from Studentgradelevel stglvl where "
                    + "stglvl.schoolyear.schoolyear=:schoolyear and "
                    + "stglvl.student.ethnicity=:ethnicity";
            query = entityManager.createQuery(queryString);
            query.setParameter("schoolyear", this.selectedSchoolYear);
            query.setParameter("ethnicity", argSubGroupName);
        } else if ("GENDER".equalsIgnoreCase(argSubGroupType)) {
            queryString = "select stglvl from Studentgradelevel stglvl where "
                    + "stglvl.schoolyear.schoolyear=:schoolyear and "
                    + "stglvl.student.profile.gender=:gender";
            query = entityManager.createQuery(queryString);
            query.setParameter("schoolyear", this.selectedSchoolYear);
            query.setParameter("gender", argSubGroupName);
        } else if ("DISABILITY".equalsIgnoreCase(argSubGroupType)) {
            queryString = "select stglvl from Studentgradelevel stglvl where "
                    + "stglvl.schoolyear.schoolyear=:schoolyear and "
                    + "stglvl.student.disabilitysupportneeded=:disabilitysupportneeded";
            query = entityManager.createQuery(queryString);
            query.setParameter("schoolyear", this.selectedSchoolYear);
            query.setParameter("disabilitysupportneeded", argSubGroupName);
        }
        List<Studentgradelevel> stglvlList = (List<Studentgradelevel>) query.getResultList();
        if (!stglvlList.isEmpty()) {
            count = stglvlList.size();
        }
        return count;
    }

    private int retrieveTotalStudentsTestedCount(String argSubGroupName, String argSubGroupType, Integer argSubjectId) {
        int count = 0;
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String queryString = "";
        Query query = null;
        if ("RACE".equalsIgnoreCase(argSubGroupType)) {
            queryString = "select stscd from Studentscorecard stscd where "
                    + "stscd.schoolyear=:schoolyear and "
                    + "stscd.subjectid=:subjectid and "
                    + "stscd.studentId in "
                    + "(select st.studentid from Student st where st.race=:race)";
            query = entityManager.createQuery(queryString);
            query.setParameter("schoolyear", this.selectedSchoolYear);
            query.setParameter("subjectid", argSubjectId);
            query.setParameter("race", argSubGroupName);
        } else if ("ETHNICITY".equalsIgnoreCase(argSubGroupType)) {
            queryString = "select stscd from Studentscorecard stscd where "
                    + "stscd.schoolyear=:schoolyear and "
                    + "stscd.subjectid=:subjectid and "
                    + "stscd.studentId in "
                    + "(select st.studentid from Student st where st.ethnicity=:ethnicity)";
            query = entityManager.createQuery(queryString);
            query.setParameter("schoolyear", this.selectedSchoolYear);
            query.setParameter("subjectid", argSubjectId);
            query.setParameter("ethnicity", argSubGroupName);
        } else if ("GENDER".equalsIgnoreCase(argSubGroupType)) {
            queryString = "select stscd from Studentscorecard stscd where "
                    + "stscd.schoolyear=:schoolyear and "
                    + "stscd.subjectid=:subjectid and "
                    + "stscd.studentId in "
                    + "(select st.studentid from Student st "
                    + "where st.profile.gender=:gender)";
            query = entityManager.createQuery(queryString);
            query.setParameter("schoolyear", this.selectedSchoolYear);
            query.setParameter("subjectid", argSubjectId);
            query.setParameter("gender", argSubGroupName);
        } else if ("DISABILITY".equalsIgnoreCase(argSubGroupType)) {
            queryString = "select stscd from Studentscorecard stscd where "
                    + "stscd.schoolyear=:schoolyear and "
                    + "stscd.subjectid=:subjectid and "
                    + "stscd.studentId in "
                    + "(select st.studentid from Student st "
                    + "where st.disabilitysupportneeded=:disabilitysupportneeded)";
            query = entityManager.createQuery(queryString);
            query.setParameter("schoolyear", this.selectedSchoolYear);
            query.setParameter("subjectid", argSubjectId);
            query.setParameter("disabilitysupportneeded", argSubGroupName);
        }
        List<Studentgradelevel> stglvlList = (List<Studentgradelevel>) query.getResultList();
        if (!stglvlList.isEmpty()) {
            count = stglvlList.size();
        }
        return count;
    }

    private int retrieveTotalStudentsScoredAtBasicLevel(
            String argSubGroupName,
            String argSubGroupType,
            Integer argSubjectId, String argStartScore, String argEndScore) {
        int count = 0;
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String queryString = "";
        Query query = null;
        if ("RACE".equalsIgnoreCase(argSubGroupType)) {
            queryString = "select stscd from Studentscorecard stscd where "
                    + "stscd.schoolyear=:schoolyear and "
                    + "stscd.subjectid=:subjectid and "
                    + "stscd.finalscore between :startscore and :endscore and "
                    + "stscd.studentId in "
                    + "(select st.studentid from Student st where st.race=:race)";
            query = entityManager.createQuery(queryString);
            query.setParameter("schoolyear", this.selectedSchoolYear);
            query.setParameter("subjectid", argSubjectId);
            query.setParameter("startscore", argStartScore);
            query.setParameter("endscore", argEndScore);
            query.setParameter("race", argSubGroupName);
        } else if ("ETHNICITY".equalsIgnoreCase(argSubGroupType)) {
            queryString = "select stscd from Studentscorecard stscd where "
                    + "stscd.schoolyear=:schoolyear and "
                    + "stscd.subjectid=:subjectid and "
                    + "stscd.studentId in "
                    + "(select st.studentid from Student st where st.ethnicity=:ethnicity)";
            query = entityManager.createQuery(queryString);
            query.setParameter("schoolyear", this.selectedSchoolYear);
            query.setParameter("subjectid", argSubjectId);
            query.setParameter("ethnicity", argSubGroupName);
        } else if ("GENDER".equalsIgnoreCase(argSubGroupType)) {
            queryString = "select stscd from Studentscorecard stscd where "
                    + "stscd.schoolyear=:schoolyear and "
                    + "stscd.subjectid=:subjectid and "
                    + "stscd.studentId in "
                    + "(select st.studentid from Student st "
                    + "where st.profile.gender=:gender)";
            query = entityManager.createQuery(queryString);
            query.setParameter("schoolyear", this.selectedSchoolYear);
            query.setParameter("subjectid", argSubjectId);
            query.setParameter("gender", argSubGroupName);
        } else if ("DISABILITY".equalsIgnoreCase(argSubGroupType)) {
            queryString = "select stscd from Studentscorecard stscd where "
                    + "stscd.schoolyear=:schoolyear and "
                    + "stscd.subjectid=:subjectid and "
                    + "stscd.studentId in "
                    + "(select st.studentid from Student st "
                    + "where st.disabilitysupportneeded=:disabilitysupportneeded)";
            query = entityManager.createQuery(queryString);
            query.setParameter("schoolyear", this.selectedSchoolYear);
            query.setParameter("subjectid", argSubjectId);
            query.setParameter("disabilitysupportneeded", argSubGroupName);
        }
        List<Studentgradelevel> stglvlList = (List<Studentgradelevel>) query.getResultList();
        if (!stglvlList.isEmpty()) {
            count = stglvlList.size();
        }
        return count;
    }

//    private int retrieveTotalStudentsScoredAtProficientLevel(String argSubGroupName, String argSubGroupType, Integer argSubjectId) {
//        int count = 0;
//        return count;
//    }
//
//    private int retrieveTotalStudentsScoredAtAdvanceLevel(String argSubGroupName, String argSubGroupType, Integer argSubjectId) {
//        int count = 0;
//        return count;
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

    protected void setInfoMessage(String summary) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
    }

    /**
     * @return the stateReportVOs
     */
    public List<StateReportVO> getStateReportVOs() {
        return stateReportVOs;
    }

    /**
     * @param stateReportVOs the stateReportVOs to set
     */
    public void setStateReportVOs(List<StateReportVO> stateReportVOs) {
        this.stateReportVOs = stateReportVOs;
    }
}
