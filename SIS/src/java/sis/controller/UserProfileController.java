/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;
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
    @ManagedProperty(value = "#{users}")
    private Users changepassworduser;
    
    public String updatePassword() {
        try {
            EntityManager em = entityManagerFactory.createEntityManager();
            userTransaction.begin();
            Users u = em.find(Users.class, this.changepassworduser.getUserid());
            u.setPassword(this.changepassworduser.getPassword());
            em.persist(u);
            userTransaction.commit();
            UserController userController = (UserController) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userController");
            Users loggedInUser = userController.getUser();
            String returnURL = "";
            if (loggedInUser.getIsAdmin()) {
                returnURL = "/admin/updateUserPasswordConfirmation";
            } else if (loggedInUser.getIsTeacher()) {
                returnURL = "/teacher/updateUserPasswordConfirmation";
            } else if (loggedInUser.getIsStudent()) {
                returnURL = "/student/updateUserPasswordConfirmation";
            }
            return returnURL;
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
    
    public String updateProfile() {
        try {
            EntityManager em = entityManagerFactory.createEntityManager();
            userTransaction.begin();
            Userprofile up = em.find(Userprofile.class, this.userprofile.getUserid());
            up.setFirstname(this.userprofile.getFirstname());
            up.setLastname(this.userprofile.getLastname());
            up.setMiddlename(this.userprofile.getMiddlename());
            up.setDateofbirth(this.userprofile.getDateofbirth());
            up.setGender(this.userprofile.getGender());
            up.setCurrentaddress1(this.userprofile.getCurrentaddress1());
            up.setCurrentaddress2(this.userprofile.getCurrentaddress2());
            up.setCurrentcity(this.userprofile.getCurrentcity());
            up.setCurrentstate(this.userprofile.getCurrentstate());
            up.setCurrentcountry(this.userprofile.getCurrentcountry());
            up.setCurrentzip(this.userprofile.getCurrentzip());
            up.setMailingaddress1(this.userprofile.getMailingaddress1());
            up.setMailingaddress2(this.userprofile.getMailingaddress2());
            up.setMailingcity(this.userprofile.getMailingcity());
            up.setMailingstate(this.userprofile.getMailingstate());
            up.setMailingzip(this.userprofile.getMailingzip());
            up.setMailingcountry(this.userprofile.getMailingcountry());
            up.setEmail1(this.userprofile.getEmail1());
            up.setEmail2(this.userprofile.getEmail2());
            up.setHomephone(this.userprofile.getHomephone());
            up.setMobilephone(this.userprofile.getMobilephone());
            up.setWorkphone(this.userprofile.getWorkphone());
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
    
    public String editPassword() {
        UserController userController = (UserController) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userController");
        Users loggedInUser = userController.getUser();
        EntityManager em = entityManagerFactory.createEntityManager();
        Users u = em.find(Users.class, loggedInUser.getUserid());
        this.changepassworduser = u;
        String returnURL = "";
        if (loggedInUser.getIsAdmin()) {
            returnURL = "/admin/updateUserPassword";
        } else if (loggedInUser.getIsTeacher()) {
            returnURL = "/teacher/updateUserPassword";
        } else if (loggedInUser.getIsStudent()) {
            returnURL = "/student/updateUserPassword";
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

    /**
     * @return the changepassworduser
     */
    public Users getChangepassworduser() {
        return changepassworduser;
    }

    /**
     * @param changepassworduser the changepassworduser to set
     */
    public void setChangepassworduser(Users changepassworduser) {
        this.changepassworduser = changepassworduser;
    }
    
    
}
