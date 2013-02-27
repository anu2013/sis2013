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
import sis.model.Admission;
import sis.model.Schoolyearschedule;
import sis.model.Userprofile;
import sis.model.Users;

/**
 *
 * @author Veekija
 */
@ManagedBean
public class UserProfileController {

    @PersistenceUnit(unitName = "SISPU")
    private EntityManagerFactory entityManagerFactory;
    @Resource
    private UserTransaction userTransaction;
    private List<Userprofile> userprofiles;
    @ManagedProperty(value = "#{userprofile}")
    private Userprofile userprofile;
    
//    @PostConstruct
//    public void init() {
//        retrieveUserProfiles();
//    }
//
//    private void retrieveUserProfiles() {
//        try {
//            EntityManager entityManager = entityManagerFactory.createEntityManager();
//            String queryString = "select up from Userprofile up";
//            Query query = entityManager.createQuery(queryString);
//            this.setUserprofiles((List<Userprofile>) query.getResultList());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public String updateProfile() {
        try {
            EntityManager em = entityManagerFactory.createEntityManager();
            userTransaction.begin();
            Userprofile up = em.find(Userprofile.class, this.userprofile.getUserid());
            up.setFirstname(this.userprofile.getFirstname());
            em.persist(up);
            userTransaction.commit();
            UserController userController = (UserController) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userController");
            Users loggedInUser = userController.getUser();
            String returnURL = "";
            if (loggedInUser.getIsAdmin()) {
                returnURL = "/admin/updateUserProfileConfirmation";
            } else if (loggedInUser.getIsTeacher()) {
                returnURL = "/teacher/updateUserProfileConfirmation";
            } else if (loggedInUser.getIsStudent()) {
                returnURL = "/student/updateUserProfileConfirmation";
            }
            return returnURL;
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    public String editProfile() {
        UserController userController = (UserController) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userController");
        Users loggedInUser = userController.getUser();
        EntityManager em = entityManagerFactory.createEntityManager();
        Userprofile up = em.find(Userprofile.class, loggedInUser.getUserid());
        this.userprofile = up;
        String returnURL = "";
        if (loggedInUser.getIsAdmin()) {
            returnURL = "/admin/updateUserProfile";
        } else if (loggedInUser.getIsTeacher()) {
            returnURL = "/teacher/updateUserProfile";
        } else if (loggedInUser.getIsStudent()) {
            returnURL = "/student/updateUserProfile";
        }
        return returnURL;
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
     * @return the userprofiles
     */
    public List<Userprofile> getUserprofiles() {
        return userprofiles;
    }

    /**
     * @param userprofiles the userprofiles to set
     */
    public void setUserprofiles(List<Userprofile> userprofiles) {
        this.userprofiles = userprofiles;
    }
    
    
}
