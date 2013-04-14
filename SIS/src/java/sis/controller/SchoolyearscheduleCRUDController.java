/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.controller;

import java.util.Calendar;
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
    private List<Schoolyearschedule> allSchoolyearschedules;
    private List<Schoolyearschedule> activeSchoolyearschedules;
    @ManagedProperty(value = "#{schoolyearschedule}")
    private Schoolyearschedule schoolyearschedule;

    @PostConstruct
    public void init() {
        retrieveAllSchoolyearschedules();
        retrieveSchoolyearschedules();
        retrieveActiveSchoolyearschedules();
    }

    private void retrieveAllSchoolyearschedules() {
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            String queryString = "select s from Schoolyearschedule s "
                    + "order by s.schoolyear desc";
            Query query = entityManager.createQuery(queryString);
            this.setAllSchoolyearschedules((List<Schoolyearschedule>) query.getResultList());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void retrieveSchoolyearschedules() {
        try {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(cal.YEAR);
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            String queryString = "select sys from Schoolyearschedule sys "
                    + "where sys.schoolyear >= (select s.schoolyear from Schoolyearschedule s where "
                    + "s.active = :active) "
                    + "order by sys.schoolyear desc";
            Query query = entityManager.createQuery(queryString);
            query.setParameter("active", new Short("1"));
            this.setSchoolyearschedules((List<Schoolyearschedule>) query.getResultList());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void retrieveActiveSchoolyearschedules() {
        try {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(cal.YEAR);
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            String queryString = "select sys from Schoolyearschedule sys "
                    + "where sys.active = :active";
            Query query = entityManager.createQuery(queryString);
            query.setParameter("active", new Short("1"));
            this.setActiveSchoolyearschedules((List<Schoolyearschedule>) query.getResultList());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List retrieveSchoolyearscheduleByActiveStatus() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String queryString = "select sys from Schoolyearschedule sys "
                + "where sys.active = :active";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("active", new Short("1"));
        return query.getResultList();
    }

    private Schoolyearschedule retrieveSchoolyearschedule(Integer argSchoolYear) {
        EntityManager em = entityManagerFactory.createEntityManager();
        Schoolyearschedule s = em.find(Schoolyearschedule.class, argSchoolYear);
        return s;
    }

    public String createSchoolyearschedule() {
        Schoolyearschedule schoolyearschedule = retrieveSchoolyearschedule(getSchoolyearschedule().getSchoolyear());
        if (schoolyearschedule != null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("School year already exists, please try again"));
            return null;
        }
        if (getSchoolyearschedule().getActive() == 1) {
            if (!retrieveSchoolyearscheduleByActiveStatus().isEmpty()) {
                setInfoMessage("There is an active school year exists. If you want to add new active school year the deactivate existing active school year and create new.");
                return null;
            }
        }
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            userTransaction.begin();
            entityManager.persist(getSchoolyearschedule());
            userTransaction.commit();
            retrieveAllSchoolyearschedules();
            return "/admin/schoolyearscheduleCRUD";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    public String addSchoolyearschedule() {
        return "/admin/schoolyearscheduleCreate";
    }

    public String updateSchoolyearschedule() {
        if (this.schoolyearschedule.getActive() == 1) {
            Schoolyearschedule schoolyearschedule = retrieveSchoolyearschedule(this.schoolyearschedule.getSchoolyear());
            if (schoolyearschedule.getActive() == 0) {
                if (!retrieveSchoolyearscheduleByActiveStatus().isEmpty()) {
                    setInfoMessage("There is an active school year exists. If you want to add new active school year the deactivate existing active school year and create new.");
                    return null;
                }
            }
        }
        try {
            userTransaction.begin();
            EntityManager em = entityManagerFactory.createEntityManager();
            Schoolyearschedule s = em.find(Schoolyearschedule.class, this.schoolyearschedule.getSchoolyear());
            s.setStartdate(this.schoolyearschedule.getStartdate());
            s.setEnddate(this.schoolyearschedule.getEnddate());
            s.setActive(this.schoolyearschedule.getActive());
            em.persist(s);
            em.flush();
            userTransaction.commit();
            em.refresh(s);
            retrieveAllSchoolyearschedules();
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

            retrieveAllSchoolyearschedules();

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

    /**
     * @return the allSchoolyearschedules
     */
    public List<Schoolyearschedule> getAllSchoolyearschedules() {
        return allSchoolyearschedules;
    }

    /**
     * @param allSchoolyearschedules the allSchoolyearschedules to set
     */
    public void setAllSchoolyearschedules(List<Schoolyearschedule> allSchoolyearschedules) {
        this.allSchoolyearschedules = allSchoolyearschedules;
    }

    protected void setInfoMessage(String summary) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
    }

    /**
     * @return the activeSchoolyearschedules
     */
    public List<Schoolyearschedule> getActiveSchoolyearschedules() {
        return activeSchoolyearschedules;
    }

    /**
     * @param activeSchoolyearschedules the activeSchoolyearschedules to set
     */
    public void setActiveSchoolyearschedules(List<Schoolyearschedule> activeSchoolyearschedules) {
        this.activeSchoolyearschedules = activeSchoolyearschedules;
    }
    
    
}
