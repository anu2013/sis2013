/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.controller;

import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import sis.model.Admission;
import sis.model.Admissionstep;
import sis.model.Admissionstepcomment;
import sis.model.Parent;
import sis.model.Previouseducation;
import sis.model.Role;

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
    @ManagedProperty(value = "#{previouseducation}")
    private Previouseducation previouseducation;
    @ManagedProperty(value = "#{parent}")
    private Parent parent;
    private List<Admissionstep> admissionsteps;
    private String comments;

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

    public void trackApplicationStatus() {
        try {
            EntityManager em = entityManagerFactory.createEntityManager();
            Admission ad = em.find(Admission.class, getAdmission().getAdmissionid());
            getAdmission().setStatus(ad.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String createAdmission(String argApplicationType) {
        String returnURL = "";
        try {
            UserController userController = (UserController) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userController");
            Users loggedInUser = userController.getUser();

            EntityManager entityManager = null;
            userTransaction.begin();
            entityManager = entityManagerFactory.createEntityManager();
            Admission ad = getAdmission();
            ad.setCreateddate(new Date());
            ad.setStatus("In-progress");
            ad.setApplicationtype(argApplicationType);
            ad.setCreatedby(loggedInUser.getUserid());
            entityManager.persist(ad);
            userTransaction.commit();

            userTransaction.begin();
            entityManager = entityManagerFactory.createEntityManager();
            Admissionstep admissionStep = new Admissionstep();
            admissionStep.setAdmissionid(ad);
            admissionStep.setAdmissionstepname("Initial Review");
            admissionStep.setCreateddate(new Date());
            admissionStep.setCreatedby(loggedInUser.getUserid());
            entityManager.persist(admissionStep);
            userTransaction.commit();
            //entityManager.refresh(admissionStep);

            userTransaction.begin();
            entityManager = entityManagerFactory.createEntityManager();
            Admissionstepcomment admissionStepComment = new Admissionstepcomment();
            admissionStepComment.setAdmissionid(ad);
            admissionStepComment.setAdmissionstepid(admissionStep);
            admissionStepComment.setComments("Application received.");
            admissionStepComment.setEntereddate(new Date());
            admissionStepComment.setEnteredby(loggedInUser.getUserid());
            entityManager.persist(admissionStepComment);
            userTransaction.commit();
            //entityManager.refresh(admissionStepComment);

            userTransaction.begin();
            entityManager = entityManagerFactory.createEntityManager();
            Users u = new Users();
            Role role = new Role();
            role.setRoleid(3);
            u.setRole(role);
            u.setCreatedby(loggedInUser.getUserid());
            u.setCreateddate(new Date());
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

            Previouseducation pe = getPreviouseducation();
            pe.setStudentid(st);
            userTransaction.begin();
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.persist(pe);
            userTransaction.commit();

            Parent pa = getParent();
            pa.setStudentid(st);
            userTransaction.begin();
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.persist(pa);
            userTransaction.commit();

            if (argApplicationType.equalsIgnoreCase("Online")) {
                returnURL = "admissionConfirmation";
            } else {
                retrieveAdmissions();
                returnURL = "/admin/admissionCRUD";
            }
            retrieveAdmissions();
            return returnURL;
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
            a.setGradelevelapplyingfor(this.admission.getGradelevelapplyingfor());
            a.setAdmissionseekingyear(this.admission.getAdmissionseekingyear());
            em.persist(a);

            Student s = em.find(Student.class, this.student.getStudentid());
            s.setRace(this.student.getRace());
            s.setEthnicity(this.student.getEthnicity());
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
        this.userprofile = up;

        queryString = "select pa from Parent pa  where pa.studentid.studentid = :studentid";
        query = entityManager.createQuery(queryString);
        query.setParameter("studentid", s.getStudentid());
        Parent pa = (Parent) query.getSingleResult();
        this.parent = pa;

        queryString = "select pe from Previouseducation pe  where pe.studentid.studentid = :studentid";
        query = entityManager.createQuery(queryString);
        query.setParameter("studentid", s.getStudentid());
        Previouseducation pe = (Previouseducation) query.getSingleResult();
        this.previouseducation = pe;

        return "/admin/admissionUpdate";
    }

    public String retrieveAdmissonSteps(Admission argAdmission) {
        this.admission = argAdmission;
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        String queryString = "select ast from Admissionstep ast  where ast.admissionid.admissionid = :admissionid";
//        Query query = entityManager.createQuery(queryString);
//        query.setParameter("admissionid", argAdmission.getAdmissionid());
//        List<Admissionstep> adSteps = (List<Admissionstep>) query.getResultList();
//        for (Admissionstep currentStep : adSteps) {
//            String queryString1 = "select astpcmt from Admissionstepcomment astpcmt  where astpcmt.admissionid.admissionid = :admissionid and astpcmt.admissionstepid.admissionstepid = :admissionstepid";
//            Query query1 = entityManager.createQuery(queryString1);
//            query1.setParameter("admissionid", argAdmission.getAdmissionid());
//            query1.setParameter("admissionstepid", currentStep.getAdmissionstepid());
//            List<Admissionstepcomment> adStepComments = (List<Admissionstepcomment>) query1.getResultList();
//            currentStep.setAdmissionstepcommentList(adStepComments);
//        }
//        this.setAdmissionsteps(adSteps);
        populateAdmissonStepsAndComments(argAdmission);
        return "/admin/admissionStepsCRUD";
    }

    private void populateAdmissonStepsAndComments(Admission argAdmission) {
        this.admission = argAdmission;
        Query stepCommentQuery = null;
        List<Admissionstepcomment> adStepComments = null;
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String stepQueryString = "select ast from Admissionstep ast  where ast.admissionid.admissionid = :admissionid";
        String stepCommentqueryString = "select astpcmt from Admissionstepcomment astpcmt  where astpcmt.admissionid.admissionid = :admissionid and astpcmt.admissionstepid.admissionstepid = :admissionstepid";
        Query stepQuery = entityManager.createQuery(stepQueryString);
        stepQuery.setParameter("admissionid", argAdmission.getAdmissionid());
        List<Admissionstep> adSteps = (List<Admissionstep>) stepQuery.getResultList();
        for (Admissionstep currentStep : adSteps) {
            stepCommentQuery = entityManager.createQuery(stepCommentqueryString);
            stepCommentQuery.setParameter("admissionid", argAdmission.getAdmissionid());
            stepCommentQuery.setParameter("admissionstepid", currentStep.getAdmissionstepid());
            adStepComments = (List<Admissionstepcomment>) stepCommentQuery.getResultList();
            currentStep.setAdmissionstepcommentList(adStepComments);
        }
        this.setAdmissionsteps(adSteps);
    }

    private Admissionstep retrieveTheLatestStep(Admission argAdmission) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String queryString = "select ast from Admissionstep ast where ast.admissionid.admissionid = :admissionid order by ast.admissionstepid";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("admissionid", argAdmission.getAdmissionid());
        List<Admissionstep> adSteps = (List<Admissionstep>) query.getResultList();
        Admissionstep theLatestStep = null;
        if (adSteps.size() != 0) {
            theLatestStep = adSteps.get(adSteps.size() - 1);
        }
        return theLatestStep;
    }

    public String updateComments(Admission argAdmission) {
        this.admission = argAdmission;
        try {
            UserController userController = (UserController) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userController");
            Users loggedInUser = userController.getUser();
            EntityManager entityManager = null;
            Admissionstep theLatestStep = retrieveTheLatestStep(argAdmission);
            userTransaction.begin();
            entityManager = entityManagerFactory.createEntityManager();
            Admissionstepcomment admissionStepComment = new Admissionstepcomment();
            admissionStepComment.setAdmissionid(argAdmission);
            admissionStepComment.setAdmissionstepid(theLatestStep);
            admissionStepComment.setComments(getComments());
            admissionStepComment.setEntereddate(new Date());
            admissionStepComment.setEnteredby(loggedInUser.getUserid());
            entityManager.persist(admissionStepComment);
            userTransaction.commit();
            populateAdmissonStepsAndComments(argAdmission);
            return "/admin/admissionStepsCRUD";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    public String requestForInterview(Admission argAdmission) {
        this.admission = argAdmission;
        try {
            UserController userController = (UserController) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userController");
            Users loggedInUser = userController.getUser();

            EntityManager entityManager = null;
            userTransaction.begin();
            entityManager = entityManagerFactory.createEntityManager();
            Admissionstep admissionStep = new Admissionstep();
            admissionStep.setAdmissionid(argAdmission);
            admissionStep.setAdmissionstepname("Interview Requested");
            admissionStep.setCreateddate(new Date());
            admissionStep.setCreatedby(loggedInUser.getUserid());
            entityManager.persist(admissionStep);
            userTransaction.commit();

            userTransaction.begin();
            entityManager = entityManagerFactory.createEntityManager();
            Admissionstepcomment admissionStepComment = new Admissionstepcomment();
            admissionStepComment.setAdmissionid(argAdmission);
            admissionStepComment.setAdmissionstepid(admissionStep);
            admissionStepComment.setComments(getComments());
            admissionStepComment.setEntereddate(new Date());
            admissionStepComment.setEnteredby(loggedInUser.getUserid());
            entityManager.persist(admissionStepComment);
            userTransaction.commit();
            populateAdmissonStepsAndComments(argAdmission);
            return "/admin/admissionStepsCRUD";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    public String approve(Admission argAdmission) {
        this.admission = argAdmission;
        try {
            UserController userController = (UserController) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userController");
            Users loggedInUser = userController.getUser();

            EntityManager entityManager = null;
            userTransaction.begin();
            entityManager = entityManagerFactory.createEntityManager();
            Admissionstep admissionStep = new Admissionstep();
            admissionStep.setAdmissionid(argAdmission);
            admissionStep.setAdmissionstepname("Approved");
            admissionStep.setCreateddate(new Date());
            admissionStep.setCreatedby(loggedInUser.getUserid());
            entityManager.persist(admissionStep);
            userTransaction.commit();

            userTransaction.begin();
            entityManager = entityManagerFactory.createEntityManager();
            Admissionstepcomment admissionStepComment = new Admissionstepcomment();
            admissionStepComment.setAdmissionid(argAdmission);
            admissionStepComment.setAdmissionstepid(admissionStep);
            admissionStepComment.setComments(getComments());
            admissionStepComment.setEntereddate(new Date());
            admissionStepComment.setEnteredby(loggedInUser.getUserid());
            entityManager.persist(admissionStepComment);
            userTransaction.commit();

            entityManager = entityManagerFactory.createEntityManager();
            userTransaction.begin();
            Admission a = entityManager.find(Admission.class, this.admission.getAdmissionid());
            a.setStatus("Granted");
            a.setEnddate(new Date());
            a.setLastupdateby(loggedInUser.getUserid());
            a.setLastupdateddate(new Date());
            entityManager.persist(a);
            userTransaction.commit();
            this.admission.setStatus(a.getStatus());

            populateAdmissonStepsAndComments(argAdmission);
            return "/admin/admissionStepsCRUD";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    public String reject(Admission argAdmission) {
        this.admission = argAdmission;
        try {
            UserController userController = (UserController) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userController");
            Users loggedInUser = userController.getUser();

            EntityManager entityManager = null;
            userTransaction.begin();
            entityManager = entityManagerFactory.createEntityManager();
            Admissionstep admissionStep = new Admissionstep();
            admissionStep.setAdmissionid(argAdmission);
            admissionStep.setAdmissionstepname("Rejected");
            admissionStep.setCreateddate(new Date());
            admissionStep.setCreatedby(loggedInUser.getUserid());
            entityManager.persist(admissionStep);
            userTransaction.commit();

            userTransaction.begin();
            entityManager = entityManagerFactory.createEntityManager();
            Admissionstepcomment admissionStepComment = new Admissionstepcomment();
            admissionStepComment.setAdmissionid(argAdmission);
            admissionStepComment.setAdmissionstepid(admissionStep);
            admissionStepComment.setComments(getComments());
            admissionStepComment.setEntereddate(new Date());
            admissionStepComment.setEnteredby(loggedInUser.getUserid());
            entityManager.persist(admissionStepComment);
            userTransaction.commit();

            entityManager = entityManagerFactory.createEntityManager();
            userTransaction.begin();
            Admission a = entityManager.find(Admission.class, this.admission.getAdmissionid());
            a.setStatus("Denied");
            a.setEnddate(new Date());
            a.setLastupdateby(loggedInUser.getUserid());
            a.setLastupdateddate(new Date());
            entityManager.persist(a);
            userTransaction.commit();
            this.admission.setStatus(a.getStatus());

            populateAdmissonStepsAndComments(argAdmission);
            return "/admin/admissionStepsCRUD";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    public boolean isDisabled(String argCommand, Admission argAdmission) {
        boolean result = false;
        Admissionstep theLatestStep = null;
        if ("Granted".equalsIgnoreCase(argAdmission.getStatus()) || "Denied".equalsIgnoreCase(argAdmission.getStatus())) {
            result = true;
        } else {
            theLatestStep = retrieveTheLatestStep(argAdmission);
            if (theLatestStep != null) {
                if ("Interview Requested".equalsIgnoreCase(theLatestStep.getAdmissionstepname())) {
                    if (argCommand.equalsIgnoreCase("interview")) {
                        result = true;
                    }
                }
            }
        }
        return result;
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
     * @return the admissionstps
     */
    public List<Admissionstep> getAdmissionsteps() {
        return admissionsteps;
    }

    /**
     * @param admissionstps the admissionstps to set
     */
    public void setAdmissionsteps(List<Admissionstep> admissionsteps) {
        this.admissionsteps = admissionsteps;
    }

    /**
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(String comments) {
        this.comments = comments;
    }
}
