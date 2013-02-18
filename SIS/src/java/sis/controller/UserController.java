/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.controller;

import sis.model.Users;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

/**
 *
 * @author Anupama Karumudi Karunudi
 */
@ManagedBean(name="userController")
@SessionScoped
public class UserController implements Serializable {
    @PersistenceUnit(unitName="SISPU")
    private EntityManagerFactory entityManagerFactory;
    private String userName;
    private String password;
    private Users loggedInUser;
    
    public String login(){
        try{
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            String queryString = "select u from Users u  where u.userloginname = :username and u.password = :password";
            Query query = entityManager.createQuery(queryString);
            query.setParameter("username", userName);
            query.setParameter("password", password);

            try{
                loggedInUser = (Users)query.getSingleResult();
            }catch(Exception e) {
                e.printStackTrace();
            }
            if (loggedInUser == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Login failed. Invalid username/password, please try again"));
                userName = null;
                password = null;
            } else {
                //if(loggedInUser.isAdmin()){
                    return "admin/index?faces-redirect=true";
                //}else{
                //    return "teacher/index?faces-redirect=true";
                //}
            }
        }catch(Exception e){
            e.printStackTrace();
            return "error";
        }
        return null;
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index?faces-redirect=true";
    }

    public boolean isLoggedIn() {
        return (loggedInUser != null);
    }
    
    public boolean isAdmin() {
        return true;
        //return (loggedInUser != null && loggedInUser.getUserType() == 2);
    }
     
    /**
     * @return the user
     */
    public Users getUser() {
        return loggedInUser;
    }

    /**
     * @param user the user to set
     */
    public void setUser(Users user) {
        this.loggedInUser = user;
    }
    
      /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
