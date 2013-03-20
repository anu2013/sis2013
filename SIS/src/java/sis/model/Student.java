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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Anupama Karumudi
 */
@ManagedBean
@Entity
@Table(name = "STUDENT")
public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "STUDENTID")
    private Integer studentid;
    
    @Column(name = "ADMISSIONID")
    private Integer admissionid;
    
    @Size(max = 100)
    @Column(name = "ADMISSIONSTATUS")
    private String admissionstatus;
    
    @Column(name = "STARTDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startdate;
    
    @Size(max = 50)
    @Column(name = "ETHNICITY")
    private String ethnicity;
    
    @Size(max = 50)
    @Column(name = "RACE")
    private String race;
    
    @Column(name = "HEALTHRECORDSRECEIVED")
    private Short healthrecordsreceived;
    
    @Column(name = "DISABILITYSUPPORTNEEDED")
    private Short disabilitysupportneeded;
    
    @Column(name = "IEPNEEDED")
    private Short iepneeded;
   
    @JoinColumn(name = "STUDENTID", referencedColumnName = "USERID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Userprofile profile;
    
    public Student() {
    }

    public Student(Integer studentid) {
        this.studentid = studentid;
    }

    public Integer getStudentid() {
        return studentid;
    }

    public void setStudentid(Integer studentid) {
        this.studentid = studentid;
    }

    public Integer getAdmissionid() {
        return admissionid;
    }

    public void setAdmissionid(Integer admissionid) {
        this.admissionid = admissionid;
    }

    public String getAdmissionstatus() {
        return admissionstatus;
    }

    public void setAdmissionstatus(String admissionstatus) {
        this.admissionstatus = admissionstatus;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public String getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public Short getHealthrecordsreceived() {
        return healthrecordsreceived;
    }

    public void setHealthrecordsreceived(Short healthrecordsreceived) {
        this.healthrecordsreceived = healthrecordsreceived;
    }

    public Short getDisabilitysupportneeded() {
        return disabilitysupportneeded;
    }

    public void setDisabilitysupportneeded(Short disabilitysupportneeded) {
        this.disabilitysupportneeded = disabilitysupportneeded;
    }

    public Short getIepneeded() {
        return iepneeded;
    }

    public void setIepneeded(Short iepneeded) {
        this.iepneeded = iepneeded;
    }

    public Userprofile getProfile() {
        return profile;
    }

    public void setProfile(Userprofile profile) {
        this.profile = profile;
    }  
}
