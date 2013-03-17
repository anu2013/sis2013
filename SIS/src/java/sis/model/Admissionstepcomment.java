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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "ADMISSIONSTEPCOMMENT")
@NamedQueries({
    @NamedQuery(name = "Admissionstepcomment.findAll", query = "SELECT a FROM Admissionstepcomment a")})
public class Admissionstepcomment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ADMISSIONSTEPCOMMENTID")
    private Integer admissionstepcommentid;
    @Size(max = 255)
    @Column(name = "COMMENTS")
    private String comments;
    @Column(name = "ENTEREDDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date entereddate;
    @Column(name = "ENTEREDBY")
    private Integer enteredby;
    @JoinColumn(name = "ADMISSIONSTEPID", referencedColumnName = "ADMISSIONSTEPID")
    @ManyToOne(optional = false)
    private Admissionstep admissionstepid;
    @JoinColumn(name = "ADMISSIONID", referencedColumnName = "ADMISSIONID")
    @ManyToOne(optional = false)
    private Admission admissionid;

    public Admissionstepcomment() {
    }

    public Admissionstepcomment(Integer admissionstepcommentid) {
        this.admissionstepcommentid = admissionstepcommentid;
    }

    public Integer getAdmissionstepcommentid() {
        return admissionstepcommentid;
    }

    public void setAdmissionstepcommentid(Integer admissionstepcommentid) {
        this.admissionstepcommentid = admissionstepcommentid;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getEntereddate() {
        return entereddate;
    }

    public void setEntereddate(Date entereddate) {
        this.entereddate = entereddate;
    }

    public Integer getEnteredby() {
        return enteredby;
    }

    public void setEnteredby(Integer enteredby) {
        this.enteredby = enteredby;
    }

    public Admissionstep getAdmissionstepid() {
        return admissionstepid;
    }

    public void setAdmissionstepid(Admissionstep admissionstepid) {
        this.admissionstepid = admissionstepid;
    }

    public Admission getAdmissionid() {
        return admissionid;
    }

    public void setAdmissionid(Admission admissionid) {
        this.admissionid = admissionid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (admissionstepcommentid != null ? admissionstepcommentid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Admissionstepcomment)) {
            return false;
        }
        Admissionstepcomment other = (Admissionstepcomment) object;
        if ((this.admissionstepcommentid == null && other.admissionstepcommentid != null) || (this.admissionstepcommentid != null && !this.admissionstepcommentid.equals(other.admissionstepcommentid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sis.model.Admissionstepcomment[ admissionstepcommentid=" + admissionstepcommentid + " ]";
    }
    
}
