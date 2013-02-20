/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.controller;

import sis.model.Period;
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
public class PeriodCRUDController {

    @PersistenceUnit(unitName = "SISPU")
    private EntityManagerFactory entityManagerFactory;
    @Resource
    private UserTransaction userTransaction;
    private List<Period> periods;
    @ManagedProperty(value = "#{period}")
    private Period period;
    @ManagedProperty(value = "#{schoolyearschedule}")
    private Schoolyearschedule schoolyearschedule;

    @PostConstruct
    public void init() {
        retrievePeriods();
    }

    private void retrievePeriods() {
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            String queryString = "select s from Period s";
            Query query = entityManager.createQuery(queryString);
            this.setPeriods((List<Period>) query.getResultList());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String createPeriod() {
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            userTransaction.begin();
            Period p = getPeriod();
            p.setSchoolyear(getSchoolyearschedule());
            entityManager.persist(p);
            userTransaction.commit();
            retrievePeriods();
            return "/admin/periodCRUD";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    public String addPeriod() {
        return "/admin/periodCreate";
    }

    public String updatePeriod() {
        try {
            EntityManager em = entityManagerFactory.createEntityManager();
            userTransaction.begin();
            Period p = em.find(Period.class, this.period.getPeriodid());
            p.setDescription(this.period.getDescription());
            p.setPeriodcode(this.period.getPeriodcode());
            p.setSortorder(this.period.getSortorder());
            p.setStarttime(this.period.getStarttime());
            p.setEndtime(this.period.getEndtime());
            em.persist(p);
            userTransaction.commit();
            retrievePeriods();
            return "/admin/periodCRUD";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    public String deletePeriod(Period argPeriod) {
        try {
            EntityManager em = entityManagerFactory.createEntityManager();
            userTransaction.begin();
            Period currentPeriod = em.find(Period.class, argPeriod.getPeriodid());
            em.remove(currentPeriod);
            userTransaction.commit();
            retrievePeriods();
            return "/admin/periodCRUD";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    public String editPeriod(Period argPeriod) {
        this.period = argPeriod;
        return "/admin/periodUpdate";
    }

    /**
     * @return the periods
     */
    public List<Period> getPeriods() {
        return periods;
    }

    /**
     * @param periods the periods to set
     */
    public void setPeriods(List<Period> periods) {
        this.periods = periods;
    }

    /**
     * @return the period
     */
    public Period getPeriod() {
        return period;
    }

    /**
     * @param period the period to set
     */
    public void setPeriod(Period period) {
        this.period = period;
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
