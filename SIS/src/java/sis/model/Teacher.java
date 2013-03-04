/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.model;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Anupama Karumudi
 */
@ManagedBean
@Entity
@Table(name = "TEACHER")
public class Teacher implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "TEACHERID")
    private Integer teacherid;
    
    @Size(max = 255)
    @Column(name = "EDUCATIONALQUALIFICATION")
    private String educationalqualification;
    
    @Column(name = "YEARSOFEXPERIENCE")
    private Integer yearsofexperience;
    
    @Size(max = 255)
    @Column(name = "SPECIALIZATION")
    private String specialization;

    @JoinColumn(name = "TEACHERID", referencedColumnName = "USERID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Userprofile profile;
    
    public Teacher() {
    }

    public Teacher(Integer teacherid) {
        this.teacherid = teacherid;
    }

    public Integer getTeacherid() {
        return teacherid;
    }

    public void setTeacherid(Integer teacherid) {
        this.teacherid = teacherid;
    }

    public String getEducationalqualification() {
        return educationalqualification;
    }

    public void setEducationalqualification(String educationalqualification) {
        this.educationalqualification = educationalqualification;
    }

    public Integer getYearsofexperience() {
        return yearsofexperience;
    }

    public void setYearsofexperience(Integer yearsofexperience) {
        this.yearsofexperience = yearsofexperience;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public Userprofile getProfile() {
        return profile;
    }

    public void setProfile(Userprofile profile) {
        this.profile = profile;
    }    
}
