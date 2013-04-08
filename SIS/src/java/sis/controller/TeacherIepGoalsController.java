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
import sis.model.Student;
import sis.model.Studentgradelevel;

/**
 *
 * @author Anupama Karumudi
 */
@ManagedBean(name="teacherIepGoalsController")
@ViewScoped
public class TeacherIepGoalsController implements Serializable{
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
    
    private List<Iepgoals> iepGoalsList;
    
    @PostConstruct
    public void init(){
        if(null == this.student)
            populateStudentAndGradeLevel();
        
        loadCurrentIepGoal();
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
     
    private void populateIepGoals(){
        try{
            setIepGoalsList(null);

            EntityManager entityManager = entityManagerFactory.createEntityManager();
            String queryString = "select ig from Iepgoals ig where ig.studentid = :sid and ig.schoolyear = :schoolyear order by ig.startdate";
            Query query = entityManager.createQuery(queryString);
            query.setParameter("sid", this.student.getStudentid());
            query.setParameter("schoolyear", userController.getCurrentSchoolYear());
            List<Iepgoals> records = query.getResultList();
            if(null != records && records.size() > 0) {
                setIepGoalsList(records);
            }else{
                setErrorMessage("There are no IEP golas found! You can create a new goal by clicking on the 'Add New Goal' button.");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
     public String saveIepGoal(Iepgoals goal){
        try{
            if(null != goal){
                userTransaction.begin();
                EntityManager entityManager = entityManagerFactory.createEntityManager();
                try{
                    Iepgoals record = null;
                    if(null != goal.getIepgoalid())
                        record = entityManager.find(Iepgoals.class, goal.getIepgoalid());
                    if(null != record) {
                        record.setGoaltitle(goal.getGoaltitle());
                        record.setGoaldescription(goal.getGoaldescription());
                        record.setStatus(goal.getStatus());
                        record.setStartdate(goal.getStartdate());
                        record.setEnddate(goal.getEnddate());
                    }else{
                        record = goal;
                        record.setStudentid(this.student.getStudentid());
                    }
                    record.setLastupdateddate(new Date());
                    record.setLastupdatedby(userController.getProfile().getFirstname() + " " + userController.getProfile().getLastname());
                    record.setSchoolyear(userController.getCurrentSchoolYear());
                    entityManager.persist(record);
                }catch(Exception e){
                    e.printStackTrace();
                }
                userTransaction.commit();
                
                populateIepGoals();
                return "iepGoals?faces-redirect=true&sid=" + this.student.getStudentid();
            }
        }catch(Exception e){
            e.printStackTrace();
            return "error";
        }
        return null;
    }

    public String deleteIepGoal(Iepgoals goal) {
        try {
            if(null != goal){
                userTransaction.begin();
                EntityManager entityManager = entityManagerFactory.createEntityManager();
                Iepgoals record = entityManager.find(Iepgoals.class, goal.getIepgoalid());
                if(null != record){
                    entityManager.remove(record);
                }
                userTransaction.commit();

                return "iepGoals?faces-redirect=true&sid=" + this.student.getStudentid();
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
            String strGid = request.getParameter("gid");
            if(null != strGid){
                Integer gid = Integer.parseInt(strGid);
                if(gid > 0) {
                    EntityManager entityManager = entityManagerFactory.createEntityManager();
                    Iepgoals record = entityManager.find(Iepgoals.class, gid);
                    if(null != record) {
                        setCurrentIepGoal(record);
                        setErrorMessage(null);
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    public String addIepGoal(){
        setCurrentIepGoal(new Iepgoals());
        setErrorMessage(null);
        return "iepGoalEditor?faces-redirect=true&sid=" + this.student.getStudentid();
    }
    
    public String editIepGoal(Iepgoals goal){
        return "iepGoalEditor?faces-redirect=true&sid=" + this.student.getStudentid() + "&gid=" + goal.getIepgoalid();
    }
    
    public String showIepGoalProgress(Iepgoals goal){
        return "iepProgress?faces-redirect=true&sid=" + this.student.getStudentid() + "&gid=" + goal.getIepgoalid();
    }
    
    public String cancelIepEditor(){
        return "iepGoals?faces-redirect=true&sid=" + this.student.getStudentid();
    }
     
    protected void setInfoMessage(String summary) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
    }

    protected void setErrorMessage(String summary) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null));
    }
    
    public List<Iepgoals> getIepGoalsList() {
        if(null == this.iepGoalsList)
            populateIepGoals();
        return iepGoalsList;
    }

    public void setIepGoalsList(List<Iepgoals> list) {
        this.iepGoalsList = list;
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
