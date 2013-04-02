/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import sis.model.Conversations;
import sis.model.Recipients;

/**
 *
 * @author Anupama Karumudi
 */
@ManagedBean(name="inboxController")
@ViewScoped
public class InboxController implements Serializable{
    @PersistenceUnit(unitName="SISPU")
    private EntityManagerFactory entityManagerFactory;
    
    @ManagedProperty("#{userController}")
    private UserController userController;
    
    private List<Conversations> conversationsList;
    
    @PostConstruct
    public void init(){
        populateInbox();
    }

    private void populateInbox(){
        try{
            Integer[] conIds = null;
            
            setConversationsList(null);

            EntityManager entityManager = entityManagerFactory.createEntityManager();
            String rcpRueryString = "select r from Recipients r where r.recipient.userid = :userid";
            //String conQueryString = "select c from Conversations c where c.sentby.userid = :userid";
            String conQueryString = "select c from Conversations c where";
            Query query = entityManager.createQuery(rcpRueryString);
            query.setParameter("userid", userController.getUser().getUserid());
            List<Recipients> records = query.getResultList();
            if(records != null && records.size() > 0){
                conIds = new Integer[records.size()];
                for(int i=0; i<records.size(); ++i) {
                    conIds[i] = records.get(i).getConversationId();
                }
                //conQueryString += " or c.conversationid IN :conids";
                conQueryString += " c.conversationid IN :conids";
            }
            conQueryString += " order by c.sentdate desc";
            
            query = entityManager.createQuery(conQueryString);
            //query.setParameter("userid", userController.getUser().getUserid());
            if(conIds != null){
                query.setParameter("conids", Arrays.asList(conIds));    
                List<Conversations> uniqueConversations = new ArrayList<Conversations>();
                List<Conversations> allConversations = query.getResultList();
                if(allConversations != null && allConversations.size() > 0){
                    for(int i=0; i<allConversations.size(); ++i) {
                        Conversations con = allConversations.get(i);
                        if(!uniqueConversations.contains(con)){
                            uniqueConversations.add(con);
                        }
                    }
                    setConversationsList(uniqueConversations);
                }else{
                    setErrorMessage("There are no messages found.");
                }
            }else{
                setErrorMessage("There are no messages found.");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public String createNewMessage(){
        return "composeMessage?faces-redirect=true";
    }
    
    public String viewMessageDetails(Conversations con){
        return "viewMessageDetails?faces-redirect=true";
    }
    
    protected void setInfoMessage(String summary) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
    }

    protected void setErrorMessage(String summary) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null));
    }
    
    public List<Conversations> getConversationsList() {
        return conversationsList;
    }

    public void setConversationsList(List<Conversations> list) {
        this.conversationsList = list;
    }
    
    public UserController getUserController() {
        return userController;
    }

    public void setUserController(UserController controller) {
        this.userController = controller;
    }
}
