/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.model;

import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Anupama Karumudi
 */
@ManagedBean
@Entity
@Table(name = "CONVERSATIONS")
public class Conversations implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CONVERSATIONID")
    private Integer conversationid;
    
    @Lob
    @Column(name="CONVERSATIONTEXT") 
    private String conversationtext;
    
    @Column(name = "SENTDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sentdate;
    
    @JoinColumn(name = "SENTBY", referencedColumnName = "USERID")
    @ManyToOne
    private Users sentby;
    
    @JoinColumn(name = "MESSAGEID", referencedColumnName = "MESSAGEID")
    @ManyToOne
    private Messages message;
    
    @Column(name = "PARENTCONVERSATIONID")
    private Integer parentconversationid;

    public Conversations() {
    }

    public Conversations(Integer conversationid) {
        this.conversationid = conversationid;
    }

    public Integer getConversationid() {
        return conversationid;
    }

    public void setConversationid(Integer conversationid) {
        this.conversationid = conversationid;
    }

    public String getConversationtext() {
        return conversationtext;
    }

    public void setConversationtext(String conversationtext) {
        this.conversationtext = conversationtext;
    }

    public Date getSentdate() {
        return sentdate;
    }

    public void setSentdate(Date sentdate) {
        this.sentdate = sentdate;
    }

    public Users getSentby() {
        return sentby;
    }

    public void setSentby(Users sentby) {
        this.sentby = sentby;
    }

    public Messages getMessage() {
        return message;
    }

    public void setMessage(Messages value) {
        this.message = value;
    }

    public Integer getParentconversationid() {
        return parentconversationid;
    }

    public void setParentconversationid(Integer parentconversationid) {
        this.parentconversationid = parentconversationid;
    }
}
