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
        StateReportVO stateReportVO = null;
        List<String> raceList = new ArrayList<String>();
        raceList.add("American Indian or Alaska Native");
        raceList.add("Asian");
        raceList.add("Black or African American");
        raceList.add("Native Hawaiian and Other Pacific Islander");
        raceList.add("White");

        for (String race : raceList) {
            stateReportVO = new StateReportVO();
            stateReportVO.setSubGroupName(race);
            stateReportVO.setTotalNumberOfStudents_Maths(retrieveTotalStudentsCount(race, "RACE"));
            stateReportVO.setNumberOfStudentsTested_Maths(50);
            double d = ((double) 50 / 60) * 100;
            stateReportVO.setPercentageOfStudentsTested_Maths(d);
            stateReportVO.setPercentageOfStudentsScoredAtAdvanceLevel_Maths(55.62);
            stateReportVO.setPercentageOfStudentsScoredAtBasicLevel_Maths(55.62);
            stateReportVO.setPercentageOfStudentsScoredAtProficentLevel_Maths(55.62);
            stateReportVO.setTotalNumberOfStudents_Reading(60);
            stateReportVO.setNumberOfStudentsTested_Reading(50);
            stateReportVO.setPercentageOfStudentsTested_Reading((50 / 60) * 100);
            stateReportVO.setPercentageOfStudentsScoredAtAdvanceLevel_Reading(55.62);
            stateReportVO.setPercentageOfStudentsScoredAtBasicLevel_Reading(55.62);
            stateReportVO.setPercentageOfStudentsScoredAtProficentLevel_Reading(55.62);
            stVOs.add(stateReportVO);
        }
        this.setStateReportVOs(stVOs);
        return null;
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

    private int retrieveTotalStudentsTestedCount() {
        int count = 0;
        return count;
    }

    private int retrieveTotalStudentsScoredAtBasicLevel() {
        int count = 0;
        return count;
    }

    private int retrieveTotalStudentsScoredAtProficientLevel() {
        int count = 0;
        return count;
    }

    private int retrieveTotalStudentsScoredAtAdvanceLevel() {
        int count = 0;
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
}
