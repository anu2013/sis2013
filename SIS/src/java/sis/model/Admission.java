/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@ManagedBean
@Entity
@Table(name = "ADMISSION")
@NamedQueries({
    @NamedQuery(name = "Admission.findAll", query = "SELECT a FROM Admission a")})
public class Admission implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ADMISSIONID")
    private Integer admissionid;
    @Size(max = 255)
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "CREATEDDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createddate;
    @Column(name = "ENDDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date enddate;
    @Size(max = 20)
    @Column(name = "STATUS")
    private String status;
    @Size(max = 20)
    @Column(name = "APPLICATIONTYPE")
    private String applicationtype;
    @Size(max = 20)
    @Column(name = "GRADELEVELAPPLYINGFOR")
    private String gradelevelapplyingfor;
    @Size(max = 25)
    @Column(name = "TRACKINGNUMBER")
    private String trackingnumber;
    @Size(max = 20)
    @Column(name = "ADMISSIONSEEKINGYEAR")
    private String admissionseekingyear;
    @Column(name = "CREATEDBY")
    private Integer createdby;
    @Column(name = "LASTUPDATEBY")
    private Integer lastupdateby;
    @Column(name = "LASTUPDATEDDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastupdateddate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "admissionid")
    private List<Admissionstep> admissionstepList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "admissionid")
    private List<Admissionstepcomment> admissionstepcommentList;

    public Admission() {
    }

    public Admission(Integer admissionid) {
        this.admissionid = admissionid;
    }

    public Integer getAdmissionid() {
        return admissionid;
    }

    public void setAdmissionid(Integer admissionid) {
        this.admissionid = admissionid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApplicationtype() {
        return applicationtype;
    }

    public void setApplicationtype(String applicationtype) {
        this.applicationtype = applicationtype;
    }

    public String getGradelevelapplyingfor() {
        return gradelevelapplyingfor;
    }

    public void setGradelevelapplyingfor(String gradelevelapplyingfor) {
        this.gradelevelapplyingfor = gradelevelapplyingfor;
    }

    public String getTrackingnumber() {
        return trackingnumber;
    }

    public void setTrackingnumber(String trackingnumber) {
        this.trackingnumber = trackingnumber;
    }

    public String getAdmissionseekingyear() {
        return admissionseekingyear;
    }

    public void setAdmissionseekingyear(String admissionseekingyear) {
        this.admissionseekingyear = admissionseekingyear;
    }

    public Integer getCreatedby() {
        return createdby;
    }

    public void setCreatedby(Integer createdby) {
        this.createdby = createdby;
    }

    public Integer getLastupdateby() {
        return lastupdateby;
    }

    public void setLastupdateby(Integer lastupdateby) {
        this.lastupdateby = lastupdateby;
    }

    public Date getLastupdateddate() {
        return lastupdateddate;
    }

    public void setLastupdateddate(Date lastupdateddate) {
        this.lastupdateddate = lastupdateddate;
    }

    public List<Admissionstep> getAdmissionstepList() {
        return admissionstepList;
    }

    public void setAdmissionstepList(List<Admissionstep> admissionstepList) {
        this.admissionstepList = admissionstepList;
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
        hash += (admissionid != null ? admissionid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Admission)) {
            return false;
        }
        Admission other = (Admission) object;
        if ((this.admissionid == null && other.admissionid != null) || (this.admissionid != null && !this.admissionid.equals(other.admissionid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sis.model.Admission[ admissionid=" + admissionid + " ]";
    }
    
}
