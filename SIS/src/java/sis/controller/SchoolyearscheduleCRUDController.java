/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.controller;

import sis.model.Schoolyearschedule;
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

/**
 *
 * @author Veekija
 */
@ManagedBean
public class SchoolyearscheduleCRUDController {

    @PersistenceUnit(unitName = "SISPU")
    private EntityManagerFactory entityManagerFactory;
    @Resource
    private UserTransaction userTransaction;
    private List<Schoolyearschedule> schoolyearschedules;
    @ManagedProperty(value = "#{schoolyearschedule}")
    private Schoolyearschedule schoolyearschedule;

    @PostConstruct
    public void init() {
        retrieveSchoolyearschedules();
    }

    private void retrieveSchoolyearschedules() {
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            String queryString = "select s from Schoolyearschedule s";
            Query query = entityManager.createQuery(queryString);
            this.setSchoolyearschedules((List<Schoolyearschedule>) query.getResultList());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Schoolyearschedule retrieveSchoolyearschedule() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Schoolyearschedule schoolyearschedule = entityManager.find(Schoolyearschedule.class, getSchoolyearschedule().getSchoolyear());
        return schoolyearschedule;
    }

    public String createSchoolyearschedule() {

        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            Schoolyearschedule schoolyearschedule = retrieveSchoolyearschedule();
            if (schoolyearschedule == null) {

                userTransaction.begin();

                entityManager.persist(getSchoolyearschedule());

                userTransaction.commit();

                retrieveSchoolyearschedules();
                return "/admin/schoolyearscheduleCRUD";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("School year already exists, please try again"));
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    public String addSchoolyearschedule() {
        return "/admin/schoolyearscheduleCreate";
    }

    public String updateSchoolyearschedule() {
        try {
            EntityManager em = entityManagerFactory.createEntityManager();
            userTransaction.begin();
            Schoolyearschedule s = em.find(Schoolyearschedule.class, this.schoolyearschedule.getSchoolyear());
            s.setSchoolyear(this.schoolyearschedule.getSchoolyear());
            s.setStartdate(this.schoolyearschedule.getStartdate());
            s.setEnddate(this.schoolyearschedule.getEnddate());
            em.persist(s);
            userTransaction.commit();
            retrieveSchoolyearschedules();
            return "/admin/schoolyearscheduleCRUD";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    public String deleteSchoolyearschedule(Schoolyearschedule argSchoolyearschedule) {
        try {
            EntityManager em = entityManagerFactory.createEntityManager();
            userTransaction.begin();
            Schoolyearschedule currentSchoolyearschedule = em.find(Schoolyearschedule.class, argSchoolyearschedule.getSchoolyear());
            em.remove(currentSchoolyearschedule);
            userTransaction.commit();
            retrieveSchoolyearschedules();
            return "/admin/schoolyearscheduleCRUD";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    public String editSchoolyearschedule(Schoolyearschedule argSchoolyearschedule) {
        this.schoolyearschedule = argSchoolyearschedule;
        return "/admin/schoolyearscheduleUpdate";
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
     * @return the schoolyearschedule
     */
    public Schoolyearschedule getSchoolyearschedule() {
        return schoolyearschedule;
    }

    /**
     * @param schoolyearschedule the schoolyearschedule to set
     */
    public void setSchoolyearschedule(Schoolyearschedule schoolyearschedule) {
        this.schoolyearschedule = schoolyearschedule;
    }
}
