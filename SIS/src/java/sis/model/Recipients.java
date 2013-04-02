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
@Table(name = "RECIPIENTS")
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

    @Column(name = "MESSAGEID")
    private Integer messageid;

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

    public Integer getMessageid() {
        return messageid;
    }

    public void setMessageid(Integer messageid) {
        this.messageid = messageid;
    }
}
