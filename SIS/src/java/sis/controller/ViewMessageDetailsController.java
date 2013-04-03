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
import javax.servlet.http.HttpServletRequest;
import sis.model.Conversations;
import sis.model.Recipients;

/**
 *
 * @author Anupama Karumudi
 */
@ManagedBean(name="viewMessageDetailsController")
@ViewScoped
public class ViewMessageDetailsController implements Serializable{
    @PersistenceUnit(unitName="SISPU")
    private EntityManagerFactory entityManagerFactory;
    
    @ManagedProperty("#{userController}")
    private UserController userController;
    
    private List<Conversations> conversationsList;
    private String subject;
    
    @PostConstruct
    public void init(){
        populateConversations();
    }

    private void populateConversations(){
        try{
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            Integer mid = Integer.parseInt(request.getParameter("mid"));
            
            Integer[] conIds = null;
            
            setConversationsList(null);

            EntityManager entityManager = entityManagerFactory.createEntityManager();
            String rcpRueryString = "select r from Recipients r where r.recipient.userid = :userid";
            String conQueryString = "select c from Conversations c where c.message.messageid = :mid";
            Query query = entityManager.createQuery(rcpRueryString);
            query.setParameter("userid", userController.getUser().getUserid());
            List<Recipients> records = query.getResultList();
            if(records != null && records.size() > 0){
                conIds = new Integer[records.size()];
                for(int i=0; i<records.size(); ++i) {
                    conIds[i] = records.get(i).getConversationId();
                }
            }
            if(null != conIds){
                conQueryString += " and (c.sentby.userid = :userid or c.conversationid IN :conids)";
            }else{
                conQueryString += " and c.sentby.userid = :userid";
            }
            conQueryString += " order by c.sentdate";
            
            query = entityManager.createQuery(conQueryString);
            query.setParameter("mid", mid);
            query.setParameter("userid", userController.getUser().getUserid());
            if(conIds != null){
                query.setParameter("conids", Arrays.asList(conIds));
            }
            
            List<Conversations> conList = query.getResultList();
            if(conList != null && conList.size() > 0){
                setConversationsList(conList);
                setSubject(conList.get(0).getMessage().getSubject());
            }else{
                setErrorMessage("There are no conversations found.");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public String replyMessage(Conversations con){
        return "composeMessage?faces-redirect=true&cid=" + con.getConversationid() + "&mid=" + con.getMessage().getMessageid();
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
    
     public String getSubject() {
        return subject;
    }

    public void setSubject(String val) {
        this.subject = val;
    }
    
    public UserController getUserController() {
        return userController;
    }

    public void setUserController(UserController controller) {
        this.userController = controller;
    }
}
