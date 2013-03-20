/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.UserTransaction;
import sis.model.Gradelevel;
import sis.model.Iepgoals;
import sis.model.Iepprogress;
import sis.model.Student;
import sis.model.Studentgradelevel;

/**
 *
 * @author Anupama Karumudi
 */
@ManagedBean(name="teacherIepProgressController")
@ViewScoped
public class TeacherIepProgressController implements Serializable{
    @PersistenceUnit(unitName="SISPU")
    private EntityManagerFactory entityManagerFactory;
    
    @Resource
    private UserTransaction userTransaction;
     
    @ManagedProperty("#{userController}")
    private UserController userController;
    
    @ManagedProperty("#{teacherSchedulesController}")
    private TeacherSchedulesController schedulesController;
    
    private Student student;
    
    private Gradelevel gradeLevel;
    
    private Iepgoals currentIepGoal = new Iepgoals();
    
    private Iepprogress currentIepProgress = new Iepprogress();
    
    private List<Iepprogress> iepProgressList;
    
    @PostConstruct
    public void init(){
        if(null == this.student)
            populateStudentAndGradeLevel();
        
        loadCurrentIepGoal();
        loadCurrentIepProgress();
    }

     private void populateStudentAndGradeLevel(){
        try{
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            Integer sid = Integer.parseInt(request.getParameter("sid"));

            EntityManager entityManager = entityManagerFactory.createEntityManager();
            String queryString = "select s from Student s where s.studentid = :sid";
            Query query = entityManager.createQuery(queryString);
            query.setParameter("sid", sid);
            query.setMaxResults(1);
            List<Student> records = query.getResultList();
            if(records != null && records.size() > 0){
                setStudent(records.get((0)));
                
                // Get Grade Level
                queryString = "select sgl from Studentgradelevel sgl where sgl.student.studentid = :studentid and sgl.schoolyear.schoolyear = :schoolyear";
                query = entityManager.createQuery(queryString);
                query.setParameter("studentid", student.getStudentid());
                query.setParameter("schoolyear", userController.getCurrentSchoolYear());
                query.setMaxResults(1);
                Studentgradelevel sgl = null;
                try{
                    sgl = (Studentgradelevel)query.getSingleResult();
                }catch(Exception e) {
                    e.printStackTrace();
                }
                if(null == sgl) sgl = new Studentgradelevel();

                setGradeLevel(sgl.getGradelevel());
            }else{
                setErrorMessage("Student record not found!");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
     
    private void populateIepprogressList(){
        try{
            setIepProgressList(null);

            EntityManager entityManager = entityManagerFactory.createEntityManager();
            String queryString = "select ip from Iepprogress ip where ip.iepgoalid = :gid";
            Query query = entityManager.createQuery(queryString);
            query.setParameter("gid", this.currentIepGoal.getIepgoalid());
            List<Iepprogress> records = query.getResultList();
            if(null != records && records.size() > 0) {
                setIepProgressList(records);
            }else{
                setErrorMessage("There are no records found! You can create one by clicking on the 'Add Progress' button.");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
     public String saveIepProgress(Iepprogress rec){
        try{
            if(null != rec){
                userTransaction.begin();
                EntityManager entityManager = entityManagerFactory.createEntityManager();
                try{
                    Iepprogress record = null;
                    if(null != rec.getIepprogressid())
                        record = entityManager.find(Iepprogress.class, rec.getIepprogressid());
                    if(null != record) {
                        record.setProgressdetails(rec.getProgressdetails());
                    }else{
                        record = rec;
                        record.setIepgoalid(this.currentIepGoal.getIepgoalid());
                    }
                    record.setLastupdateddate(new Date());
                    record.setLastupdatedby(userController.getProfile().getFirstname() + " " + userController.getProfile().getLastname());
                    entityManager.persist(record);
                }catch(Exception e){
                    e.printStackTrace();
                }
                userTransaction.commit();
                
                return "iepProgress?faces-redirect=true&sid=" + this.student.getStudentid() + "&gid=" + this.currentIepGoal.getIepgoalid();
            }
        }catch(Exception e){
            e.printStackTrace();
            return "error";
        }
        return null;
    }

    public String deleteIepProgress(Iepprogress rec) {
        try {
            if(null != rec){
                userTransaction.begin();
                EntityManager entityManager = entityManagerFactory.createEntityManager();
                Iepprogress record = entityManager.find(Iepprogress.class, rec.getIepprogressid());
                if(null != record){
                    entityManager.remove(record);
                }
                userTransaction.commit();

                return "iepProgress?faces-redirect=true&sid=" + this.student.getStudentid() + "&gid=" + this.currentIepGoal.getIepgoalid();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return null;
    }
    
    public String loadCurrentIepGoal(){
        try{
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            Integer gid = Integer.parseInt(request.getParameter("gid"));
            if(gid > 0) {
                EntityManager entityManager = entityManagerFactory.createEntityManager();
                Iepgoals record = entityManager.find(Iepgoals.class, gid);
                if(null != record) {
                    setCurrentIepGoal(record);
                    setErrorMessage(null);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    public String loadCurrentIepProgress(){
        try{
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            Integer pid = Integer.parseInt(request.getParameter("pid"));
            if(pid > 0) {
                EntityManager entityManager = entityManagerFactory.createEntityManager();
                Iepprogress record = entityManager.find(Iepprogress.class, pid);
                if(null != record) {
                    setCurrentIepProgress(record);
                    setErrorMessage(null);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    public String addIepProgress(){
        setCurrentIepProgress(new Iepprogress());
        setErrorMessage(null);
        return "iepProgressEditor?faces-redirect=true&sid=" + this.student.getStudentid() + "&gid=" + this.currentIepGoal.getIepgoalid();
    }
    
    public String editIepProgress(Iepprogress rec){
        return "iepProgressEditor?faces-redirect=true&sid=" + this.student.getStudentid() + "&gid=" + this.currentIepGoal.getIepgoalid() + "&pid=" + rec.getIepprogressid();
    }
    
    public String cancelIepEditor(){
        return "iepProgress?faces-redirect=true&sid=" + this.student.getStudentid() + "&gid=" + this.currentIepGoal.getIepgoalid();
    }
     
    protected void setInfoMessage(String summary) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
    }

    protected void setErrorMessage(String summary) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null));
    }
    
    public List<Iepprogress> getIepProgressList() {
        if(null == this.iepProgressList)
            populateIepprogressList();
        return iepProgressList;
    }

    public void setIepProgressList(List<Iepprogress> list) {
        this.iepProgressList = list;
    }
    
    public UserController getUserController() {
        return userController;
    }

    public void setUserController(UserController controller) {
        this.userController = controller;
    }
    
    public TeacherSchedulesController getSchedulesController() {
        return schedulesController;
    }

    public void setSchedulesController(TeacherSchedulesController controller) {
        this.schedulesController = controller;
    }
            
    public Iepprogress getCurrentIepProgress(){
        return this.currentIepProgress;
    }
    
    public void setCurrentIepProgress(Iepprogress rec){
        this.currentIepProgress = rec;
    }
    
    public Iepgoals getCurrentIepGoal(){
        return this.currentIepGoal;
    }
    
    public void setCurrentIepGoal(Iepgoals goal){
        this.currentIepGoal = goal;
    }
    
    public Student getStudent(){
        return this.student;
    }
    
    public void setStudent(Student student){
        this.student = student;
    }
    
    public Gradelevel getGradeLevel(){
        return this.gradeLevel;
    }
    
    public void setGradeLevel(Gradelevel grade){
        this.gradeLevel = grade;
    }
}
