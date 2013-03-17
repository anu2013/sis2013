/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.controller;

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
import sis.model.Parent;
import sis.model.Previouseducation;
import sis.model.Student;
import sis.model.Userprofile;

/**
 *
 * @author Veekija
 */
@ManagedBean
public class StudentCRUDController {

    @PersistenceUnit(unitName = "SISPU")
    private EntityManagerFactory entityManagerFactory;
    @Resource
    private UserTransaction userTransaction;
    private List<Student> students;
    @ManagedProperty(value = "#{student}")
    private Student student;
    @ManagedProperty(value = "#{userprofile}")
    private Userprofile userprofile;
    @ManagedProperty(value = "#{previouseducation}")
    private Previouseducation previouseducation;
    @ManagedProperty(value = "#{parent}")
    private Parent parent;

    @PostConstruct
    public void init() {
        retrieveStudents();
    }

    private void retrieveStudents() {
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            String queryString = "select a from Student a";
            Query query = entityManager.createQuery(queryString);
            this.setStudents((List<Student>) query.getResultList());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String updateStudent() {
        try {
            EntityManager em = entityManagerFactory.createEntityManager();
            userTransaction.begin();
            Student s = em.find(Student.class, this.student.getStudentid());
            s.setRace(this.student.getRace());
            s.setEthnicity(this.student.getEthnicity());
            s.setHealthrecordsreceived(this.student.getHealthrecordsreceived());
            s.setDisabilitysupportneeded(this.student.getDisabilitysupportneeded());
            s.setIepneeded(this.student.getIepneeded());
            em.persist(s);

            Userprofile up = em.find(Userprofile.class, this.student.getStudentid());
            up.setFirstname(this.userprofile.getFirstname());
            up.setLastname(this.userprofile.getLastname());
            up.setMiddlename(this.userprofile.getMiddlename());
            up.setGender(this.userprofile.getGender());
            up.setDateofbirth(this.userprofile.getDateofbirth());
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

            Parent pa = em.find(Parent.class, this.parent.getParentid());
            pa.setParentfirstname(this.parent.getParentfirstname());
            pa.setParentlastname(this.parent.getParentlastname());
            pa.setParenteducation(this.parent.getParenteducation());
            pa.setParentcontactaddress1(this.parent.getParentcontactaddress1());
            pa.setParentcontactaddress2(this.parent.getParentcontactaddress2());
            pa.setParentcontactcity(this.parent.getParentcontactcity());
            pa.setParentcontactstate(this.parent.getParentcontactstate());
            pa.setParentcontactzip(this.parent.getParentcontactzip());
            pa.setParentcontactcountry(this.parent.getParentcontactcountry());
            pa.setRelationshipwithstudent(this.parent.getRelationshipwithstudent());

            Previouseducation pe = em.find(Previouseducation.class, this.previouseducation.getPreviousschoolid());
            pe.setLastattendedgradelevel(this.previouseducation.getLastattendedgradelevel());
            pe.setPreviousschoolname(this.previouseducation.getPreviousschoolname());
            pe.setPreviousschooladdress1(this.previouseducation.getPreviousschooladdress1());
            pe.setPreviousschooladdress2(this.previouseducation.getPreviousschooladdress2());
            pe.setPreviousschoolcity(this.previouseducation.getPreviousschoolcity());
            pe.setPreviousschoolstate(this.previouseducation.getPreviousschoolstate());
            pe.setPreviousschoolzip(this.previouseducation.getPreviousschoolzip());
            pe.setPreviousschoolcountry(this.previouseducation.getPreviousschoolcountry());
            userTransaction.commit();
            return "/admin/studentCRUD";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

    }

    public String editStudent(Student argStudent) {
        
        this.student = argStudent;
        this.userprofile = argStudent.getUserprofile();

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String queryString = "select pa from Parent pa  where pa.studentid.studentid = :studentid";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("studentid", argStudent.getStudentid());
        Parent pa = null;
        try {
            int cont = query.getResultList().size();
            System.out.println("Count == " + cont);
            if (cont != 0) {
                pa = (Parent) query.getSingleResult();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.parent = pa;

        queryString = "select pe from Previouseducation pe  where pe.studentid.studentid = :studentid";
        query = entityManager.createQuery(queryString);
        query.setParameter("studentid", argStudent.getStudentid());
        Previouseducation pe = null;
        try {
            int cont = query.getResultList().size();
            System.out.println("Count == " + cont);
            if (cont != 0) {
                pe = (Previouseducation) query.getSingleResult();;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.previouseducation = pe;

        return "/admin/studentUpdate";


    }

    /**
     * @return the student
     */
    public Student getStudent() {
        return student;
    }

    /**
     * @param student the student to set
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

    /**
     * @return the previouseducation
     */
    public Previouseducation getPreviouseducation() {
        return previouseducation;
    }

    /**
     * @param previouseducation the previouseducation to set
     */
    public void setPreviouseducation(Previouseducation previouseducation) {
        this.previouseducation = previouseducation;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(Parent parent) {
        this.parent = parent;
    }

    /**
     * @return the parent
     */
    public Parent getParent() {
        return parent;
    }

    /**
     * @return the students
     */
    public List<Student> getStudents() {
        return students;
    }

    /**
     * @param students the students to set
     */
    public void setStudents(List<Student> students) {
        this.students = students;
    }
    
    
    
}
