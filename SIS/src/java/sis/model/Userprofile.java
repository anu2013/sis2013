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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Anupama Karumudi
 */
@Entity
@Table(name = "USERPROFILE")
@NamedQueries({
    @NamedQuery(name = "Userprofile.findAll", query = "SELECT u FROM Userprofile u")})
public class Userprofile implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "USERID")
    private Integer userid;
    @Size(max = 50)
    @Column(name = "FIRSTNAME")
    private String firstname;
    @Size(max = 50)
    @Column(name = "LASTNAME")
    private String lastname;
    @Size(max = 255)
    @Column(name = "MIDDLENAME")
    private String middlename;
    @Column(name = "DATEOFBIRTH")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateofbirth;
    @Size(max = 15)
    @Column(name = "GENDER")
    private String gender;
    @Size(max = 255)
    @Column(name = "CURRENTADDRESS1")
    private String currentaddress1;
    @Size(max = 255)
    @Column(name = "CURRENTADDRESS2")
    private String currentaddress2;
    @Size(max = 50)
    @Column(name = "CURRENTCITY")
    private String currentcity;
    @Size(max = 20)
    @Column(name = "CURRENTZIP")
    private String currentzip;
    @Size(max = 50)
    @Column(name = "CURRENTSTATE")
    private String currentstate;
    @Size(max = 50)
    @Column(name = "CURRENTCOUNTRY")
    private String currentcountry;
    @Size(max = 255)
    @Column(name = "MAILINGADDRESS1")
    private String mailingaddress1;
    @Size(max = 255)
    @Column(name = "MAILINGADDRESS2")
    private String mailingaddress2;
    @Size(max = 50)
    @Column(name = "MAILINGCITY")
    private String mailingcity;
    @Size(max = 20)
    @Column(name = "MAILINGZIP")
    private String mailingzip;
    @Size(max = 50)
    @Column(name = "MAILINGSTATE")
    private String mailingstate;
    @Size(max = 50)
    @Column(name = "MAILINGCOUNTRY")
    private String mailingcountry;
    @Size(max = 120)
    @Column(name = "EMAIL1")
    private String email1;
    @Size(max = 120)
    @Column(name = "EMAIL2")
    private String email2;
    @Size(max = 15)
    @Column(name = "HOMEPHONE")
    private String homephone;
    @Size(max = 15)
    @Column(name = "MOBILEPHONE")
    private String mobilephone;
    @Size(max = 15)
    @Column(name = "WORKPHONE")
    private String workphone;
    @JoinColumn(name = "USERID", referencedColumnName = "USERID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Users users;

    public Userprofile() {
    }

    public Userprofile(Integer userid) {
        this.userid = userid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public Date getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(Date dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCurrentaddress1() {
        return currentaddress1;
    }

    public void setCurrentaddress1(String currentaddress1) {
        this.currentaddress1 = currentaddress1;
    }

    public String getCurrentaddress2() {
        return currentaddress2;
    }

    public void setCurrentaddress2(String currentaddress2) {
        this.currentaddress2 = currentaddress2;
    }

    public String getCurrentcity() {
        return currentcity;
    }

    public void setCurrentcity(String currentcity) {
        this.currentcity = currentcity;
    }

    public String getCurrentzip() {
        return currentzip;
    }

    public void setCurrentzip(String currentzip) {
        this.currentzip = currentzip;
    }

    public String getCurrentstate() {
        return currentstate;
    }

    public void setCurrentstate(String currentstate) {
        this.currentstate = currentstate;
    }

    public String getCurrentcountry() {
        return currentcountry;
    }

    public void setCurrentcountry(String currentcountry) {
        this.currentcountry = currentcountry;
    }

    public String getMailingaddress1() {
        return mailingaddress1;
    }

    public void setMailingaddress1(String mailingaddress1) {
        this.mailingaddress1 = mailingaddress1;
    }

    public String getMailingaddress2() {
        return mailingaddress2;
    }

    public void setMailingaddress2(String mailingaddress2) {
        this.mailingaddress2 = mailingaddress2;
    }

    public String getMailingcity() {
        return mailingcity;
    }

    public void setMailingcity(String mailingcity) {
        this.mailingcity = mailingcity;
    }

    public String getMailingzip() {
        return mailingzip;
    }

    public void setMailingzip(String mailingzip) {
        this.mailingzip = mailingzip;
    }

    public String getMailingstate() {
        return mailingstate;
    }

    public void setMailingstate(String mailingstate) {
        this.mailingstate = mailingstate;
    }

    public String getMailingcountry() {
        return mailingcountry;
    }

    public void setMailingcountry(String mailingcountry) {
        this.mailingcountry = mailingcountry;
    }

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getHomephone() {
        return homephone;
    }

    public void setHomephone(String homephone) {
        this.homephone = homephone;
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public String getWorkphone() {
        return workphone;
    }

    public void setWorkphone(String workphone) {
        this.workphone = workphone;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userid != null ? userid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Userprofile)) {
            return false;
        }
        Userprofile other = (Userprofile) object;
        if ((this.userid == null && other.userid != null) || (this.userid != null && !this.userid.equals(other.userid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sis.model.Userprofile[ userid=" + userid + " ]";
    }
    
}
