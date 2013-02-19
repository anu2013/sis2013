/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.controller;

import sis.model.Gradelevel;
import java.util.List; 
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
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
public class GradelevelCRUDController {

    @PersistenceUnit(unitName = "SISPU")
    private EntityManagerFactory entityManagerFactory;
    @Resource
    private UserTransaction userTransaction;
    private List<Gradelevel> gradelevels;
    @ManagedProperty(value = "#{gradelevel}")
    private Gradelevel gradelevel;

    @PostConstruct
    public void init() {
        retrieveGradelevels();
    }

    private void retrieveGradelevels() {
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            String queryString = "select s from Gradelevel s";
            Query query = entityManager.createQuery(queryString);
            this.setGradelevels((List<Gradelevel>) query.getResultList());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String createGradelevel() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            userTransaction.begin();
            entityManager.persist(getGradelevel());
            userTransaction.commit();
            retrieveGradelevels();
            return "/admin/gradelevelCRUD";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    public String addGradelevel() {
        return "/admin/gradelevelCreate";
    }

    public String updateGradelevel() {
        try {
            EntityManager em = entityManagerFactory.createEntityManager();
            userTransaction.begin();
            Gradelevel s = em.find(Gradelevel.class, this.gradelevel.getGradelevelid());
            s.setGradelevel(this.gradelevel.getGradelevel());
            s.setDescription(this.gradelevel.getDescription());
            em.persist(s);
            userTransaction.commit();
            retrieveGradelevels();
            return "/admin/gradelevelCRUD";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    public String deleteGradelevel(Gradelevel argGradelevel) {
        try {
            EntityManager em = entityManagerFactory.createEntityManager();
            userTransaction.begin();
            Gradelevel currentGradelevel = em.find(Gradelevel.class, argGradelevel.getGradelevelid());
            em.remove(currentGradelevel);
            userTransaction.commit();
            retrieveGradelevels();
            return "/admin/gradelevelCRUD";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    public String editGradelevel(Gradelevel argGradelevel) {
        this.gradelevel = argGradelevel;
        return "/admin/gradelevelUpdate";
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
     * @return the gradelevel
     */
    public Gradelevel getGradelevel() {
        return gradelevel;
    }

    /**
     * @param gradelevel the gradelevel to set
     */
    public void setGradelevel(Gradelevel gradelevel) {
        this.gradelevel = gradelevel;
    }
}