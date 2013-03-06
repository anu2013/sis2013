/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.controller;

import com.sun.tools.xjc.reader.xmlschema.bindinfo.BIConversion;
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
import sis.model.Admission;
import sis.model.Role;
import sis.model.Schoolyearschedule;

import sis.model.Student;
import sis.model.Userprofile;
import sis.model.Users;

/**
 *
 * @author Veekija
 */
@ManagedBean
public class AdmissionCRUDController {

    @PersistenceUnit(unitName = "SISPU")
    private EntityManagerFactory entityManagerFactory;
    @Resource
    private UserTransaction userTransaction;
    private List<Admission> admissions;
    @ManagedProperty(value = "#{admission}")
    private Admission admission;
    @ManagedProperty(value = "#{student}")
    private Student student;
    @ManagedProperty(value = "#{userprofile}")
    private Userprofile userprofile;

    @PostConstruct
    public void init() {
        retrieveAdmissions();
    }

    private void retrieveAdmissions() {
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            String queryString = "select a from Admission a";
            Query query = entityManager.createQuery(queryString);
            this.setAdmissions((List<Admission>) query.getResultList());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String createAdmission() {
        try {
            EntityManager entityManager = null;
            userTransaction.begin();
            entityManager = entityManagerFactory.createEntityManager();
            Admission ad = getAdmission();
            ad.setCreateddate(new Date());
            ad.setStatus("In-progress");
            entityManager.persist(ad);
            userTransaction.commit();
            
            userTransaction.begin();
            entityManager = entityManagerFactory.createEntityManager();
            Users u = new Users();
            Role role = new Role();
            role.setRoleid(3);
            u.setRole(role);
            entityManager.persist(u);
            userTransaction.commit();

            Userprofile up = getUserprofile();
            up.setUserid(u.getUserid());
            userTransaction.begin();
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.persist(up);
            userTransaction.commit();

            Student st = getStudent();
            st.setStudentid(u.getUserid());
            st.setAdmissionid(ad.getAdmissionid());
            userTransaction.begin();
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.persist(st);
            userTransaction.commit();
            retrieveAdmissions();
            return "/admin/admissionCRUD";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    public String addAdmission() {
        return "/admin/admissionCreate";
    }

    public String updateAdmission() {
        try {

            EntityManager em = entityManagerFactory.createEntityManager();
            userTransaction.begin();
            Admission a = em.find(Admission.class, this.admission.getAdmissionid());
            a.setDescription(this.admission.getDescription());
            a.setApplicationtype(this.admission.getApplicationtype());
            a.setGradelevelapplyingfor(this.admission.getGradelevelapplyingfor());
            a.setAdmissionseekingyear(this.admission.getAdmissionseekingyear());
            em.persist(a);
            
            Student s = em.find(Student.class, this.student.getStudentid());
            s.setRace(this.student.getRace());
            em.persist(s);

            Userprofile up = em.find(Userprofile.class, this.student.getStudentid());
            up.setFirstname(this.userprofile.getFirstname());
            up.setLastname(this.userprofile.getLastname());
            up.setMiddlename(this.userprofile.getMiddlename());
            
            up.setCurrentaddress1(this.userprofile.getCurrentaddress1());
            up.setCurrentaddress2(this.userprofile.getCurrentaddress2());
            up.setCurrentcity(this.userprofile.getCurrentcity());
            up.setCurrentstate(this.userprofile.getCurrentstate());
            up.setCurrentzip(this.userprofile.getCurrentzip());
            up.setCurrentcountry(this.userprofile.getCurrentcountry());
            
            up.setMailingaddress1(this.userprofile.getMailingaddress1());
            up.setMailingaddress2(this.userprofile.getMailingaddress2());
            up.setMailingcity(this.userprofile.getMailingcity());
            up.setMailingstate(this.userprofile.getMailingstate());
            up.setMailingzip(this.userprofile.getMailingzip());
            up.setMailingcountry(this.userprofile.getMailingcountry());
            
            up.setEmail1(this.userprofile.getEmail1());
            up.setEmail2(this.userprofile.getEmail2());
            
            up.setHomephone(this.userprofile.getEmail2());
            up.setMobilephone(this.userprofile.getMobilephone());
            up.setHomephone(this.userprofile.getHomephone());
            
            em.persist(up);
            userTransaction.commit();
            retrieveAdmissions();
            return "/admin/admissionCRUD";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    public String deleteAdmission(Admission argAdmission) {
        try {
            EntityManager em = entityManagerFactory.createEntityManager();
            userTransaction.begin();
            Admission currentAdmission = em.find(Admission.class, argAdmission.getAdmissionid());
            em.remove(currentAdmission);
            userTransaction.commit();
            retrieveAdmissions();
            return "/admin/admissionCRUD";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    public String editAdmission(Admission argAdmission) {
        this.admission = argAdmission;

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String queryString = "select s from Student s  where s.admissionid = :admissionid";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("admissionid", argAdmission.getAdmissionid());
        Student s = (Student) query.getSingleResult();
        this.student = s;

        queryString = "select up from Userprofile up  where up.userid = :userid";
        query = entityManager.createQuery(queryString);
        query.setParameter("userid", s.getStudentid());
        Userprofile up = (Userprofile) query.getSingleResult();
        this.userprofile=up;
        return "/admin/admissionUpdate";
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

    /**
     * @return the studentinfo
     */
    public Student getStudent() {
        return student;
    }

    /**
     * @param studentinfo the studentinfo to set
     */
    public void setStudent(Student student) {
        this.student = student;
    }

    /**
     * @return the userprofile
     */
    public Userprofile getUserprofile() {
        return userprofile;
    }

    /**
     * @param userprofile the userprofile to set
     */
    public void setUserprofile(Userprofile userprofile) {
        this.userprofile = userprofile;
    }
}
