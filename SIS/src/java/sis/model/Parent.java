/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.model;

import java.io.Serializable;
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
import javax.validation.constraints.Size;

/**
 *
 * @author Anupama Karumudi
 */
@Entity
@Table(name = "PARENT")
@NamedQueries({
    @NamedQuery(name = "Parent.findAll", query = "SELECT p FROM Parent p")})
public class Parent implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PARENTID")
    private Integer parentid;
    @Size(max = 255)
    @Column(name = "PARENTFIRSTNAME")
    private String parentfirstname;
    @Size(max = 255)
    @Column(name = "PARENTLASTNAME")
    private String parentlastname;
    @Size(max = 255)
    @Column(name = "PARENTEDUCATION")
    private String parenteducation;
    @Size(max = 255)
    @Column(name = "PARENTCONTACTADDRESS1")
    private String parentcontactaddress1;
    @Size(max = 255)
    @Column(name = "PARENTCONTACTADDRESS2")
    private String parentcontactaddress2;
    @Size(max = 255)
    @Column(name = "PARENTCONTACTCITY")
    private String parentcontactcity;
    @Size(max = 255)
    @Column(name = "PARENTCONTACTSTATE")
    private String parentcontactstate;
    @Size(max = 255)
    @Column(name = "PARENTCONTACTZIP")
    private String parentcontactzip;
    @Size(max = 255)
    @Column(name = "PARENTCONTACTCOUNTRY")
    private String parentcontactcountry;
    @Size(max = 255)
    @Column(name = "RELATIONSHIPWITHSTUDENT")
    private String relationshipwithstudent;
    @JoinColumn(name = "STUDENTID", referencedColumnName = "STUDENTID")
    @ManyToOne
    private Student studentid;

    public Parent() {
    }

    public Parent(Integer parentid) {
        this.parentid = parentid;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public String getParentfirstname() {
        return parentfirstname;
    }

    public void setParentfirstname(String parentfirstname) {
        this.parentfirstname = parentfirstname;
    }

    public String getParentlastname() {
        return parentlastname;
    }

    public void setParentlastname(String parentlastname) {
        this.parentlastname = parentlastname;
    }

    public String getParenteducation() {
        return parenteducation;
    }

    public void setParenteducation(String parenteducation) {
        this.parenteducation = parenteducation;
    }

    public String getParentcontactaddress1() {
        return parentcontactaddress1;
    }

    public void setParentcontactaddress1(String parentcontactaddress1) {
        this.parentcontactaddress1 = parentcontactaddress1;
    }

    public String getParentcontactaddress2() {
        return parentcontactaddress2;
    }

    public void setParentcontactaddress2(String parentcontactaddress2) {
        this.parentcontactaddress2 = parentcontactaddress2;
    }

    public String getParentcontactcity() {
        return parentcontactcity;
    }

    public void setParentcontactcity(String parentcontactcity) {
        this.parentcontactcity = parentcontactcity;
    }

    public String getParentcontactstate() {
        return parentcontactstate;
    }

    public void setParentcontactstate(String parentcontactstate) {
        this.parentcontactstate = parentcontactstate;
    }

    public String getParentcontactzip() {
        return parentcontactzip;
    }

    public void setParentcontactzip(String parentcontactzip) {
        this.parentcontactzip = parentcontactzip;
    }

    public String getParentcontactcountry() {
        return parentcontactcountry;
    }

    public void setParentcontactcountry(String parentcontactcountry) {
        this.parentcontactcountry = parentcontactcountry;
    }

    public String getRelationshipwithstudent() {
        return relationshipwithstudent;
    }

    public void setRelationshipwithstudent(String relationshipwithstudent) {
        this.relationshipwithstudent = relationshipwithstudent;
    }

    public Student getStudentid() {
        return studentid;
    }

    public void setStudentid(Student studentid) {
        this.studentid = studentid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (parentid != null ? parentid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Parent)) {
            return false;
        }
        Parent other = (Parent) object;
        if ((this.parentid == null && other.parentid != null) || (this.parentid != null && !this.parentid.equals(other.parentid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sis.model.Parent[ parentid=" + parentid + " ]";
    }
    
}
