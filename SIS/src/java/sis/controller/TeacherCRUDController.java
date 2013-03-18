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
import sis.model.Previousworkhistory;
import sis.model.Role;
import sis.model.Teacher;
import sis.model.Userprofile;
import sis.model.Users;

/**
 *
 * @author Veekija
 */
@ManagedBean
public class TeacherCRUDController {

    @PersistenceUnit(unitName = "SISPU")
    private EntityManagerFactory entityManagerFactory;
    @Resource
    private UserTransaction userTransaction;
    private List<Teacher> teachers;
    @ManagedProperty(value = "#{teacher}")
    private Teacher teacher;
    @ManagedProperty(value = "#{userprofile}")
    private Userprofile userprofile;
    @ManagedProperty(value = "#{previousworkhistory}")
    private Previousworkhistory previousworkhistory;

    @PostConstruct
    public void init() {
        retrieveTeachers();
    }

    private void retrieveTeachers() {
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            String queryString = "select t from Teacher t";
            Query query = entityManager.createQuery(queryString);
            this.setTeachers((List<Teacher>) query.getResultList());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String createTeacher() {
        EntityManager entityManager = null;
        try {
            userTransaction.begin();
            entityManager = entityManagerFactory.createEntityManager();
            Users u = new Users();
            Role role = new Role();
            role.setRoleid(2);
            u.setRole(role);
            entityManager.persist(u);
            userTransaction.commit();
            entityManager.refresh(u);

            userTransaction.begin();
            Userprofile up = getUserprofile();
            up.setUserid(u.getUserid());
//            up.getUsers().setUserloginname(u.getUserid()+"");
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.persist(up);
            userTransaction.commit();

            userTransaction.begin();
            Teacher t = getTeacher();
            t.setTeacherid(up.getUserid());
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.persist(t);
            userTransaction.commit();
            entityManager.refresh(t);

            userTransaction.begin();
            Previousworkhistory pwh = getPreviousworkhistory();
            pwh.setTeacherid(t);
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.persist(pwh);
            userTransaction.commit();
            
            
//            System.out.println("updateUserLogin 111=="+u.getUserid());
//            Users updateUserLogin = entityManager.find(Users.class, u.getUserid());
//            updateUserLogin.setUserloginname(up.getLastname() + "_" + up.getFirstname().substring(0, 1) + "_" + u.getUserid());
//            updateUserLogin.setPassword("password");
//            userTransaction.begin();
//            entityManager = entityManagerFactory.createEntityManager();
//            entityManager.persist(updateUserLogin);
//            userTransaction.commit();

            retrieveTeachers();
            return "/admin/teacherCRUD";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    public String addTeacher() {
        return "/admin/teacherCreate";
    }

    public String updateTeacher() {
        try {
            EntityManager em = entityManagerFactory.createEntityManager();

            userTransaction.begin();

            Teacher t = em.find(Teacher.class, this.teacher.getTeacherid());
            t.setEducationalqualification(this.teacher.getEducationalqualification());
            t.setSpecialization(this.teacher.getSpecialization());
            t.setYearsofexperience(this.teacher.getYearsofexperience());
            em.persist(t);

            Userprofile up = em.find(Userprofile.class, this.teacher.getTeacherid());
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

            Previousworkhistory pwh = em.find(Previousworkhistory.class, this.previousworkhistory.getPreviousworkid());
            pwh.setPreviousschoolname(this.previousworkhistory.getPreviousschoolname());
            pwh.setPreviousschooladdress1(this.previousworkhistory.getPreviousschooladdress1());
            pwh.setPreviousschooladdress2(this.previousworkhistory.getPreviousschooladdress2());
            pwh.setPreviousschoolcity(this.previousworkhistory.getPreviousschoolcity());
            pwh.setPreviousschoolstate(this.previousworkhistory.getPreviousschoolstate());
            pwh.setPreviousschoolzip(this.previousworkhistory.getPreviousschoolzip());
            pwh.setPreviousschoolcountry(this.previousworkhistory.getPreviousschoolcountry());
            em.persist(pwh);
            
            userTransaction.commit();
            retrieveTeachers();
            return "/admin/teacherCRUD";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    public String deleteTeacher(Teacher argTeacher) {
        try {
            EntityManager em = entityManagerFactory.createEntityManager();
            userTransaction.begin();
            Teacher currentTeacher = em.find(Teacher.class, argTeacher.getTeacherid());
            em.remove(currentTeacher);
            userTransaction.commit();
            retrieveTeachers();
            return "/admin/teacherCRUD";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    public String editTeacher(Teacher argTeacher) {
        this.teacher = argTeacher;
        this.userprofile = argTeacher.getProfile();

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String queryString = "select pwh from Previousworkhistory pwh  where pwh.teacherid.teacherid = :teacherid";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("teacherid", argTeacher.getTeacherid());
        Previousworkhistory pwh = null;
        try {
            int cont = query.getResultList().size();
            if (cont != 0) {
                pwh = (Previousworkhistory) query.getSingleResult();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.previousworkhistory = pwh;

        return "/admin/teacherUpdate";
    }

    /**
     * @return the teachers
     */
    public List<Teacher> getTeachers() {
        return teachers;
    }

    /**
     * @param teachers the teachers to set
     */
    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    /**
     * @return the teacher
     */
    public Teacher getTeacher() {
        return teacher;
    }

    /**
     * @param teacher the teacher to set
     */
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
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
     * @return the previousworkhistory
     */
    public Previousworkhistory getPreviousworkhistory() {
        return previousworkhistory;
    }

    /**
     * @param previousworkhistory the previousworkhistory to set
     */
    public void setPreviousworkhistory(Previousworkhistory previousworkhistory) {
        this.previousworkhistory = previousworkhistory;
    }
}
