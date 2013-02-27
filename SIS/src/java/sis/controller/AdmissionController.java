/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.controller;

import java.util.Calendar;
import java.util.Date;
import sis.model.Admission;
import java.util.List;
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
import javax.transaction.UserTransaction;
import sis.model.Schoolyearschedule;

/**
 *
 * @author Veekija
 */
@ManagedBean
public class AdmissionController {

    @PersistenceUnit(unitName = "SISPU")
    private EntityManagerFactory entityManagerFactory;
    @Resource
    private UserTransaction userTransaction;
    private List<Admission> admissions;
    @ManagedProperty(value = "#{admission}")
    private Admission admission;

    public String createAdmission() {
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            userTransaction.begin();
            Admission a = getAdmission();
            a.setCreateddate(new Date());
            entityManager.persist(a);
            userTransaction.commit();
            return "/admin/admissionConfirmation";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    public List<Admission> getAdmissions() {
        return admissions;
    }

    /**
     * @param admissions the admissions to set
     */
    public void setAdmissions(List<Admission> admissions) {
        this.admissions = admissions;
    }

    /**
     * @return the admission
     */
    public Admission getAdmission() {
        return admission;
    }

    /**
     * @param admission the admission to set
     */
    public void setAdmission(Admission admission) {
        this.admission = admission;
    }
}
