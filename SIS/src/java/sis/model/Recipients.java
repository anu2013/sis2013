/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Anupama Karumudi
 */
@Entity
@Table(name = "RECIPIENTS")
@NamedQueries({
    @NamedQuery(name = "Recipients.findAll", query = "SELECT r FROM Recipients r")})
public class Recipients implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "RECIPIENTSROWID")
    private Integer recipientsrowid;
    @Column(name = "MESSAGEREADDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date messagereaddate;
    @JoinColumn(name = "RECIPIENTID", referencedColumnName = "USERID")
    @ManyToOne
    private Users recipientid;
    @JoinColumn(name = "MESSAGEID", referencedColumnName = "MESSAGEID")
    @ManyToOne
    private Messages messageid;

    public Recipients() {
    }

    public Recipients(Integer recipientsrowid) {
        this.recipientsrowid = recipientsrowid;
    }

    public Integer getRecipientsrowid() {
        return recipientsrowid;
    }

    public void setRecipientsrowid(Integer recipientsrowid) {
        this.recipientsrowid = recipientsrowid;
    }

    public Date getMessagereaddate() {
        return messagereaddate;
    }

    public void setMessagereaddate(Date messagereaddate) {
        this.messagereaddate = messagereaddate;
    }

    public Users getRecipientid() {
        return recipientid;
    }

    public void setRecipientid(Users recipientid) {
        this.recipientid = recipientid;
    }

    public Messages getMessageid() {
        return messageid;
    }

    public void setMessageid(Messages messageid) {
        this.messageid = messageid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (recipientsrowid != null ? recipientsrowid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Recipients)) {
            return false;
        }
        Recipients other = (Recipients) object;
        if ((this.recipientsrowid == null && other.recipientsrowid != null) || (this.recipientsrowid != null && !this.recipientsrowid.equals(other.recipientsrowid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sis.model.Recipients[ recipientsrowid=" + recipientsrowid + " ]";
    }
    
}
