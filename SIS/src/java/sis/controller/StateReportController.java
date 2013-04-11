/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.controller;

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
import sis.bean.StudentMetricsVO;
import sis.bean.SubjectVO;
import sis.model.Schoolyearschedule;
import sis.model.Studentgradelevel;
import sis.model.Studentscorecard;

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
    private List<SubjectVO> subjectVOs = new ArrayList<SubjectVO>();
    private int basicLevelStartScore = 70;
    private int basicLevelEndScore = 79;
    private int proficientLevelStartScore = 80;
    private int proficientLevelEndScore = 89;
    private int advanceLevelStartScore = 90;
    private int advanceLevelEndScore = 100;

    @PostConstruct
    public void init() {
        populateSchoolYearschedules();
        populateSubjects();
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

    private void populateSubjects() {
        List<SubjectVO> subVOs = new ArrayList<SubjectVO>();

        SubjectVO subVO = new SubjectVO();
        subVO.setSubjectId(7);
        subVO.setSubjectName("Mathematics");
        subVOs.add(subVO);

        subVO = new SubjectVO();
        subVO.setSubjectId(7);
        subVO.setSubjectName("Reading");
        subVOs.add(subVO);

        this.setSubjectVOs(subVOs);
    }

    public String generateReport() {
        List<StateReportVO> stVOs = new ArrayList<StateReportVO>();

        List<StateReportVO> raceDeatils = retrieveRaceDetails();
        stVOs.addAll(raceDeatils);

        List<StateReportVO> ethinicityDeatils = retrieveEthinicityDetails();
        stVOs.addAll(ethinicityDeatils);

        List<StateReportVO> disabilitySupportNeededDetails = retrieveDisabilitySupportNeededDetails();
        stVOs.addAll(disabilitySupportNeededDetails);

        List<StateReportVO> genderDetails = retrieveGenderDetails();
        stVOs.addAll(genderDetails);

        this.setStateReportVOs(stVOs);
        return null;
    }

    private List retrieveRaceDetails() {
        List<StateReportVO> stVOs = null;
        List<String> raceList = new ArrayList<String>();
        raceList.add("American Indian or Alaska Native");
        raceList.add("Asian");
        raceList.add("Black or African American");
        raceList.add("Native Hawaiian and Other Pacific Islander");
        raceList.add("White");
        stVOs = populateStudentVOs(raceList, "RACE");
        return stVOs;
    }

    private List retrieveEthinicityDetails() {
        List<StateReportVO> stVOs = null;
        List<String> ethinicityList = new ArrayList<String>();
        ethinicityList.add("Hispanic or Latino");
        ethinicityList.add("Not Hispanic or Latino");
        stVOs = populateStudentVOs(ethinicityList, "ETHNICITY");
        return stVOs;
    }

    private List retrieveGenderDetails() {
        List<StateReportVO> stVOs = null;
        List<String> genderList = new ArrayList<String>();
        genderList.add("Male");
        genderList.add("Female");
        stVOs = populateStudentVOs(genderList, "GENDER");
        return stVOs;
    }

    private List retrieveDisabilitySupportNeededDetails() {
        List<StateReportVO> stVOs = null;
        List<String> disabilitySupportNeeded = new ArrayList<String>();
        disabilitySupportNeeded.add("1");
        stVOs = populateStudentVOs(disabilitySupportNeeded, "DISABILITY");
        return stVOs;
    }

    private List populateStudentVOs(List<String> argSubGroupList, String argSubGroupType) {
        List<StateReportVO> stVOs = null;
        List<StudentMetricsVO> studentMetricsVOs = null;
        StateReportVO stateReportVO = null;
        StudentMetricsVO studentMetricsVO = null;
        stVOs = new ArrayList<StateReportVO>();
        for (String subGroupName : argSubGroupList) {
            stateReportVO = new StateReportVO();
            if (subGroupName.equalsIgnoreCase("1")) {
                stateReportVO.setSubGroupName("Students with Disabilities");
            } else {
                stateReportVO.setSubGroupName(subGroupName);
            }
            studentMetricsVOs = new ArrayList<StudentMetricsVO>();
            for (SubjectVO subjectVO : getSubjectVOs()) {
                studentMetricsVO = populateStudentMetrics(subGroupName, argSubGroupType, subjectVO.getSubjectId());
                studentMetricsVOs.add(studentMetricsVO);
            }
            stateReportVO.setStudentMetricsVOs(studentMetricsVOs);
            stVOs.add(stateReportVO);
        }
        return stVOs;
    }

    private StudentMetricsVO populateStudentMetrics(String argSubGroupName,
            String argSubGroupType, Integer argSubjectId) {

        StudentMetricsVO studentMetricsVO = new StudentMetricsVO();
        int totalNumberOfStudents = 0;
        int numberOfStudentsTested = 0;
        double percentageOfStudentsTested = 0.00;
        int numberOfStudentsScoredAtBasicLevel = 0;
        double percentageOfStudentsScoredAtBasicLevel = 0.00;
        int numberOfStudentsScoredAtProficentLevel = 0;
        double percentageOfStudentsScoredAtProficentLevel = 0.00;
        int numberOfStudentsScoredAtAdvanceLevel = 0;
        double percentageOfStudentsScoredAtAdvanceLevel = 0.00;

        totalNumberOfStudents = retrieveTotalStudentsCount(argSubGroupName, argSubGroupType);

        numberOfStudentsTested = retrieveTotalStudentsTestedCount(argSubGroupName, argSubGroupType, argSubjectId);
        percentageOfStudentsTested = calculatePercentage(totalNumberOfStudents, numberOfStudentsTested);

        numberOfStudentsScoredAtBasicLevel = retrieveTotalStudentsScoredAtBasicLevel(argSubGroupName, argSubGroupType, argSubjectId, this.basicLevelStartScore, this.basicLevelEndScore);
        percentageOfStudentsScoredAtBasicLevel = calculatePercentage(totalNumberOfStudents, numberOfStudentsScoredAtBasicLevel);

        numberOfStudentsScoredAtProficentLevel = retrieveTotalStudentsScoredAtBasicLevel(argSubGroupName, argSubGroupType, argSubjectId, this.proficientLevelStartScore, this.proficientLevelEndScore);
        percentageOfStudentsScoredAtProficentLevel = calculatePercentage(totalNumberOfStudents, numberOfStudentsScoredAtProficentLevel);

        numberOfStudentsScoredAtAdvanceLevel = retrieveTotalStudentsScoredAtBasicLevel(argSubGroupName, argSubGroupType, argSubjectId, this.advanceLevelStartScore, this.advanceLevelEndScore);;
        percentageOfStudentsScoredAtAdvanceLevel = calculatePercentage(totalNumberOfStudents, numberOfStudentsScoredAtAdvanceLevel);

        studentMetricsVO.setTotalNumberOfStudents(totalNumberOfStudents);
        studentMetricsVO.setNumberOfStudentsTested(numberOfStudentsTested);
        studentMetricsVO.setPercentageOfStudentsTested(percentageOfStudentsTested);
        studentMetricsVO.setPercentageOfStudentsScoredAtBasicLevel(percentageOfStudentsScoredAtBasicLevel);
        studentMetricsVO.setPercentageOfStudentsScoredAtProficentLevel(percentageOfStudentsScoredAtProficentLevel);
        studentMetricsVO.setPercentageOfStudentsScoredAtAdvanceLevel(percentageOfStudentsScoredAtAdvanceLevel);
        return studentMetricsVO;
    }

    private double calculatePercentage(int argTotal, int argScored) {
        double percentage = 0;
        if (argScored != 0) {
            percentage = ((double) argScored / argTotal) * 100;
        }
        return percentage;
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
            query.setParameter("disabilitysupportneeded", new Short(argSubGroupName));
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
            query.setParameter("disabilitysupportneeded", new Short(argSubGroupName));
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
            Integer argSubjectId, int argStartScore, int argEndScore) {
        int count = 0;
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String queryString = "";
        Query query = null;
        if ("RACE".equalsIgnoreCase(argSubGroupType)) {
            queryString = "select stscd from Studentscorecard stscd where "
                    + "stscd.schoolyear=:schoolyear and "
                    + "stscd.subjectid=:subjectid and "
                    //        + "stscd.finalscore between :startscore and :endscore and "
                    + "stscd.studentId in "
                    + "(select st.studentid from Student st where st.race=:race)";
            query = entityManager.createQuery(queryString);
            query.setParameter("schoolyear", this.selectedSchoolYear);
            query.setParameter("subjectid", argSubjectId);
            //query.setParameter("startscore", argStartScore);
            //query.setParameter("endscore", argEndScore);
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
            query.setParameter("disabilitysupportneeded", new Short(argSubGroupName));
        }
        List<Studentscorecard> stntScoreCards = (List<Studentscorecard>) query.getResultList();
        if (!stntScoreCards.isEmpty()) {
            for (Studentscorecard studentscorecard : stntScoreCards) {
                int score = Integer.parseInt(studentscorecard.getFinalscore());
                if (score >= argStartScore && score <= argEndScore) {
                    count = count + 1;
                }
            }
        }
        return count;
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

    /**
     * @return the subjectList
     */
    public List<SubjectVO> getSubjectVOs() {
        return subjectVOs;
    }

    /**
     * @param subjectList the subjectList to set
     */
    public void setSubjectVOs(List<SubjectVO> subjectVOs) {
        this.subjectVOs = subjectVOs;
    }
}
