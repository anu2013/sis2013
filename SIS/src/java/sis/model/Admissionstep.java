/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "ADMISSIONSTEP")
@NamedQueries({
    @NamedQuery(name = "Admissionstep.findAll", query = "SELECT a FROM Admissionstep a")})
public class Admissionstep implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ADMISSIONSTEPID")
    private Integer admissionstepid;
    @Size(max = 255)
    @Column(name = "ADMISSIONSTEPNAME")
    private String admissionstepname;
    @Column(name = "CREATEDDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createddate;
    @Column(name = "ENDDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date enddate;
    @Column(name = "CREATEDBY")
    private Integer createdby;
    @Column(name = "LASTUPDATEDBY")
    private Integer lastupdatedby;
    @Column(name = "LASTUPDATEDDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastupdateddate;
    @JoinColumn(name = "ADMISSIONID", referencedColumnName = "ADMISSIONID")
    @ManyToOne(optional = false)
    private Admission admissionid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "admissionstepid")
    private List<Admissionstepcomment> admissionstepcommentList;

    public Admissionstep() {
    }

    public Admissionstep(Integer admissionstepid) {
        this.admissionstepid = admissionstepid;
    }

    public Integer getAdmissionstepid() {
        return admissionstepid;
    }

    public void setAdmissionstepid(Integer admissionstepid) {
        this.admissionstepid = admissionstepid;
    }

    public String getAdmissionstepname() {
        return admissionstepname;
    }

    public void setAdmissionstepname(String admissionstepname) {
        this.admissionstepname = admissionstepname;
    }

    public Date getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public Integer getCreatedby() {
        return createdby;
    }

    public void setCreatedby(Integer createdby) {
        this.createdby = createdby;
    }

    public Integer getLastupdatedby() {
        return lastupdatedby;
    }

    public void setLastupdatedby(Integer lastupdatedby) {
        this.lastupdatedby = lastupdatedby;
    }

    public Date getLastupdateddate() {
        return lastupdateddate;
    }

    public void setLastupdateddate(Date lastupdateddate) {
        this.lastupdateddate = lastupdateddate;
    }

    public Admission getAdmissionid() {
        return admissionid;
    }

    public void setAdmissionid(Admission admissionid) {
        this.admissionid = admissionid;
    }

    public List<Admissionstepcomment> getAdmissionstepcommentList() {
        return admissionstepcommentList;
    }

    public void setAdmissionstepcommentList(List<Admissionstepcomment> admissionstepcommentList) {
        this.admissionstepcommentList = admissionstepcommentList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (admissionstepid != null ? admissionstepid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Admissionstep)) {
            return false;
        }
        Admissionstep other = (Admissionstep) object;
        if ((this.admissionstepid == null && other.admissionstepid != null) || (this.admissionstepid != null && !this.admissionstepid.equals(other.admissionstepid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sis.model.Admissionstep[ admissionstepid=" + admissionstepid + " ]";
    }
    
}
