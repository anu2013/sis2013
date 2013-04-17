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

    private List retrievePeriodsBySortOrderAndYear(Integer argSchoolyear, Integer argSortOrder) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String queryString = "select p from Period p where p.schoolyear.schoolyear=:schoolyear and "
                + "p.sortorder=:sortorder";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("schoolyear", argSchoolyear);
        query.setParameter("sortorder", argSortOrder);
        return query.getResultList();
    }

    public String createPeriod() {
        if (!retrievePeriodsBySortOrderAndYear(getSchoolyearschedule().getSchoolyear(), getPeriod().getSortorder()).isEmpty()) {
            setInfoMessage("The selected period sort order already exists for the selected school year. Please select different sort order.");
            return null;
        }
        String startTime = getPeriod().getStarttime();
        String endTime = getPeriod().getEndtime();
        int startIndex = retrievieTimingIndex(startTime);
        int endIndex = retrievieTimingIndex(endTime);
        if (startIndex >= endIndex) {
            setInfoMessage("Start Timings should be less than End timings. Please select different start and end timings.");
            return null;
        }
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

    private int retrievieTimingIndex(String argTiming) {
        SISLookupController sis = new SISLookupController();
        sis.populatePeriodTimings();
        List<String> t = sis.getTimings();
        return t.indexOf(argTiming);
    }

    public String addPeriod() {
        return "/admin/periodCreate";
    }

    public String updatePeriod() {
        EntityManager em = entityManagerFactory.createEntityManager();
        Period pd = em.find(Period.class, this.period.getPeriodid());
        if (pd.getSortorder() != this.period.getSortorder()) {
            if (!retrievePeriodsBySortOrderAndYear(pd.getSchoolyear().getSchoolyear(), this.period.getSortorder()).isEmpty()) {
                setInfoMessage("The selected period sort order already exists for the selected school year. Please select different sort order.");
                return null;
            }
        }
        String startTime = this.period.getStarttime();
        String endTime = this.period.getEndtime();
        int startIndex = retrievieTimingIndex(startTime);
        int endIndex = retrievieTimingIndex(endTime);
        if (startIndex >= endIndex) {
            setInfoMessage("Start Timings should be less than End timings. Please select different start and end timings.");
            return null;
        }
        try {
            em = entityManagerFactory.createEntityManager();
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

    protected void setInfoMessage(String summary) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
    }
}
