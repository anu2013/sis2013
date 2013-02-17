/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author Anupama Karumudi
 */
@Entity
@Table(name = "MESSAGES")
@NamedQueries({
    @NamedQuery(name = "Messages.findAll", query = "SELECT m FROM Messages m")})
public class Messages implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "MESSAGEID")
    private Integer messageid;
    @Size(max = 255)
    @Column(name = "SUBJECT")
    private String subject;
    @Column(name = "MESSAGECREATEDDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date messagecreateddate;
    @OneToMany(mappedBy = "messageid")
    private List<Conversations> conversationsList;
    @OneToMany(mappedBy = "messageid")
    private List<Recipients> recipientsList;
    @JoinColumn(name = "MESSAGECREATEDBY", referencedColumnName = "USERID")
    @ManyToOne
    private Users messagecreatedby;

    public Messages() {
    }

    public Messages(Integer messageid) {
        this.messageid = messageid;
    }

    public Integer getMessageid() {
        return messageid;
    }

    public void setMessageid(Integer messageid) {
        this.messageid = messageid;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getMessagecreateddate() {
        return messagecreateddate;
    }

    public void setMessagecreateddate(Date messagecreateddate) {
        this.messagecreateddate = messagecreateddate;
    }

    public List<Conversations> getConversationsList() {
        return conversationsList;
    }

    public void setConversationsList(List<Conversations> conversationsList) {
        this.conversationsList = conversationsList;
    }

    public List<Recipients> getRecipientsList() {
        return recipientsList;
    }

    public void setRecipientsList(List<Recipients> recipientsList) {
        this.recipientsList = recipientsList;
    }

    public Users getMessagecreatedby() {
        return messagecreatedby;
    }

    public void setMessagecreatedby(Users messagecreatedby) {
        this.messagecreatedby = messagecreatedby;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (messageid != null ? messageid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Messages)) {
            return false;
        }
        Messages other = (Messages) object;
        if ((this.messageid == null && other.messageid != null) || (this.messageid != null && !this.messageid.equals(other.messageid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sis.model.Messages[ messageid=" + messageid + " ]";
    }
    
}
