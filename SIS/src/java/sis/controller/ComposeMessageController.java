/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.controller;

import java.io.Serializable;
import java.util.Arrays;
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
import javax.transaction.UserTransaction;
import sis.model.Conversations;
import sis.model.Messages;
import sis.model.Recipients;
import sis.model.Users;

/**
 *
 * @author Anupama Karumudi
 */
@ManagedBean(name="composeMessageController")
@ViewScoped
public class ComposeMessageController implements Serializable{
    @PersistenceUnit(unitName="SISPU")
    private EntityManagerFactory entityManagerFactory;
    
    @Resource
    private UserTransaction userTransaction;
     
    @ManagedProperty("#{userController}")
    private UserController userController;
    
    private Messages newMessage = new Messages();
    private Conversations newConversation = new Conversations();
    private String toList = "";
    
    @PostConstruct
    public void init(){
    }
     
    public String sendMessage(){
        try{
            if(null != newMessage && null != newConversation){
                
                if(null == newMessage.getSubject() || newMessage.getSubject().trim().length() < 1){
                    setErrorMessage("Message title is required, please enter text in th message title field.");
                    return null;
                }
                
                if(newMessage.getSubject().trim().length() > 254){
                    setErrorMessage("Message title should not exceed more than 255 characters.");
                    return null;
                }
                
                if(null == newConversation.getConversationtext() || newConversation.getConversationtext().trim().length() < 1){
                    setErrorMessage("Message body is required, please enter text in th message body.");
                    return null;
                }
                
                if(null == toList || toList.trim().length() < 1){
                    setErrorMessage("Recipients required, please enter comma separated list of recipient user names.");
                    return null;
                }
                
                String[] toUserNames = toList.trim().replaceAll(" ", "").replaceAll(";", ",").split(",");
                if(null == toUserNames || toUserNames.length < 1){
                    setErrorMessage("Recipients field should contain only comma separated list of recipient user names.");
                    return null;
                }
                
                List<Users> userList = getUsersList(toUserNames);
                if(null == userList || userList.size() < 1){
                    setErrorMessage("Recipients not found, please make sure to enter valid recipient user names.");
                    return null;
                }
                                
                userTransaction.begin();
                EntityManager entityManager = entityManagerFactory.createEntityManager();
                try{
                    newMessage.setMessagecreatedby(userController.getUser().getUserid());
                    newMessage.setMessagecreateddate(new Date());
                    entityManager.persist(newMessage);
                    
                    newConversation.setMessage(newMessage);
                    newConversation.setSentby(userController.getUser());
                    newConversation.setSentdate(new Date());
                    entityManager.persist(newConversation);
                    entityManager.flush();
                    entityManager.refresh(newConversation);
                    
                    for(int i=0; i<userList.size(); ++i) {
                        Recipients r = new Recipients();
                        r.setConversationId(newConversation.getConversationid());
                        r.setRecipient(userList.get(i));
                        entityManager.persist(r);    
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
                userTransaction.commit();
                
                return "inbox?faces-redirect=true";
            }
        }catch(Exception e){
            e.printStackTrace();
            return "error";
        }
        return null;
    }
    
     private List<Users> getUsersList(String[] userNames){
        try{
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            String queryString = "select u from Users u where u.userloginname IN :unames";
            Query query = entityManager.createQuery(queryString);
            query.setParameter("unames", Arrays.asList(userNames)); 
            List<Users> records = query.getResultList();
            if(records != null && records.size() > 0){
                return records;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
   
    public String discardMessage(){
        return "inbox?faces-redirect=true";
    }
     
    protected void setInfoMessage(String summary) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
    }

    protected void setErrorMessage(String summary) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null));
    }
    
    public UserController getUserController() {
        return userController;
    }

    public void setUserController(UserController controller) {
        this.userController = controller;
    }
    
    public Messages getNewMessage(){
        return this.newMessage;
    }
    
    public void setNewMessage(Messages val){
        this.newMessage = val;
    }
    
    public Conversations getNewConversation(){
        return this.newConversation;
    }
    
    public void setNewConversation(Conversations val){
        this.newConversation = val;
    }
    
    public String getToList(){
        return this.toList;
    }
    
    public void setToList(String val){
        this.toList = val;
    }
}
