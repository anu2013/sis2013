/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.model;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@NamedQueries({
    @NamedQuery(name = "Teacher.findAll", query = "SELECT t FROM Teacher t")})
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
    private Userprofile userprofile;  
    @JoinColumn(name = "TEACHERID", referencedColumnName = "USERID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Users users;
    @OneToMany(mappedBy = "teacherid")
    private List<Previousworkhistory> previousworkhistoryList;
    @OneToMany(mappedBy = "primaryteacherid")
    private List<Subjectschedule> subjectscheduleList;
    @OneToMany(mappedBy = "secondaryteacherid")
    private List<Subjectschedule> subjectscheduleList1;

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

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public List<Previousworkhistory> getPreviousworkhistoryList() {
        return previousworkhistoryList;
    }

    public void setPreviousworkhistoryList(List<Previousworkhistory> previousworkhistoryList) {
        this.previousworkhistoryList = previousworkhistoryList;
    }

    public List<Subjectschedule> getSubjectscheduleList() {
        return subjectscheduleList;
    }

    public void setSubjectscheduleList(List<Subjectschedule> subjectscheduleList) {
        this.subjectscheduleList = subjectscheduleList;
    }

    public List<Subjectschedule> getSubjectscheduleList1() {
        return subjectscheduleList1;
    }

    public void setSubjectscheduleList1(List<Subjectschedule> subjectscheduleList1) {
        this.subjectscheduleList1 = subjectscheduleList1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (teacherid != null ? teacherid.hashCode() : 0);
        return hash;
    }

    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Teacher)) {
            return false;
        }
        Teacher other = (Teacher) object;
        if ((this.teacherid == null && other.teacherid != null) || (this.teacherid != null && !this.teacherid.equals(other.teacherid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sis.model.Teacher[ teacherid=" + teacherid + " ]";
    }

    /**
     * @return the userprofile
     */
    public Userprofile getUserprofile() {
        return userprofile;
    }

    /**
     * @param userprofile the userprofile to set
     */
    public void setUserprofile(Userprofile userprofile) {
        this.userprofile = userprofile;
    }
    
}
