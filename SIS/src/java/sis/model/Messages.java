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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author Anupama Karumudi
 */
@ManagedBean
@Entity
@Table(name = "MESSAGES")
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
    
    @Column(name = "MESSAGECREATEDBY")
    private Integer messagecreatedby;

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

    public Integer getMessagecreatedby() {
        return messagecreatedby;
    }

    public void setMessagecreatedby(Integer messagecreatedby) {
        this.messagecreatedby = messagecreatedby;
    }
}
