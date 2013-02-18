/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.controller;

import sis.model.Subject;
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
public class SubjectCRUDController {

    @PersistenceUnit(unitName = "SISPU")
    private EntityManagerFactory entityManagerFactory;
    @Resource
    private UserTransaction userTransaction;
    private List<Subject> subjects;
    @ManagedProperty(value = "#{subject}")
    private Subject subject;

    @PostConstruct
    public void init() {
        retrieveSubjects();
    }

    private void retrieveSubjects() {
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            String queryString = "select s from Subject s";
            Query query = entityManager.createQuery(queryString);
            this.setSubjects((List<Subject>) query.getResultList());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String createSubject() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            userTransaction.begin();
            entityManager.persist(getSubject());
            userTransaction.commit();
            retrieveSubjects();
            return "/admin/subjectCRUD";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    public String addSubject() {
        return "/admin/subjectCreate";
    }

    public String updateSubject() {
        try {
            EntityManager em = entityManagerFactory.createEntityManager();
            userTransaction.begin();
            Subject s = em.find(Subject.class, this.subject.getSubjectid());
            s.setSubjectname(this.subject.getSubjectname());
            s.setDescription(this.subject.getDescription());
            em.persist(s);
            userTransaction.commit();
            retrieveSubjects();
            return "/admin/subjectCRUD";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    public String deleteSubject(Subject argSubject) {
        try {
            EntityManager em = entityManagerFactory.createEntityManager();
            userTransaction.begin();
            Subject currentSubject = em.find(Subject.class, argSubject.getSubjectid());
            em.remove(currentSubject);
            userTransaction.commit();
            retrieveSubjects();
            return "/admin/subjectCRUD";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    public String editSubject(Subject argSubject) {
        this.subject = argSubject;
        return "/admin/subjectUpdate";
    }

    /**
     * @return the subjects
     */
    public List<Subject> getSubjects() {
        return subjects;
    }

    /**
     * @param subjects the subjects to set
     */
    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    /**
     * @return the subject
     */
    public Subject getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
