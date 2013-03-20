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
import sis.model.Admission;
import sis.model.Gradelevel;
import sis.model.Schoolyearschedule;
import sis.model.Student;

/**
 *
 * @author Veekija
 */
@ManagedBean
public class AdmissionStatusReportController {
    
    @PersistenceUnit(unitName = "SISPU")
    private EntityManagerFactory entityManagerFactory;
    @Resource
    private UserTransaction userTransaction;
    private List<Gradelevel> gradelevels;
    private List<Schoolyearschedule> schoolyearschedules;
    private Integer selectedGradeLevelId;
    private Integer selectedSchoolYear;
    private List<Admission> admissions = null;
    
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
        
        if (this.selectedGradeLevelId == 0 || this.selectedSchoolYear == 0) {
            setInfoMessage("Please select Grade Level and School year.");
        } else {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            String selectSQL = "select ad from Admission ad where ad.gradelevelapplyingfor = :gradelevelapplyingfor and ad.admissionseekingyear = :admissionseekingyear";
            //String selectSQL = "select ad from Admission ad";
            try {
                Query selectQuery = entityManager.createQuery(selectSQL);
                selectQuery.setParameter("gradelevelapplyingfor", this.selectedGradeLevelId + "");
                selectQuery.setParameter("admissionseekingyear", this.selectedSchoolYear + "");
                List<Admission> adm = (List<Admission>) selectQuery.getResultList();
                if (adm == null || adm.size() == 0) {
                    setInfoMessage("There are no matching admissions exist.");
                } else {
                    this.setAdmissions(adm);
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
     * @return the admissions
     */
    public List<Admission> getAdmissions() {
        return admissions;
    }

    /**
     * @param admissions the admissions to set
     */
    public void setAdmissions(List<Admission> admissions) {
        this.admissions = admissions;
    }
}
