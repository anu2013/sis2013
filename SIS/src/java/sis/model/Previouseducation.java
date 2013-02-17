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
@Table(name = "PREVIOUSEDUCATION")
@NamedQueries({
    @NamedQuery(name = "Previouseducation.findAll", query = "SELECT p FROM Previouseducation p")})
public class Previouseducation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PREVIOUSSCHOOLID")
    private Integer previousschoolid;
    @Size(max = 100)
    @Column(name = "PREVIOUSSCHOOLNAME")
    private String previousschoolname;
    @Size(max = 255)
    @Column(name = "PREVIOUSSCHOOLADDRESS1")
    private String previousschooladdress1;
    @Size(max = 255)
    @Column(name = "PREVIOUSSCHOOLADDRESS2")
    private String previousschooladdress2;
    @Size(max = 100)
    @Column(name = "PREVIOUSSCHOOLCITY")
    private String previousschoolcity;
    @Size(max = 20)
    @Column(name = "PREVIOUSSCHOOLZIP")
    private String previousschoolzip;
    @Size(max = 50)
    @Column(name = "PREVIOUSSCHOOLSTATE")
    private String previousschoolstate;
    @Size(max = 50)
    @Column(name = "PREVIOUSSCHOOLCOUNTRY")
    private String previousschoolcountry;
    @Size(max = 20)
    @Column(name = "LASTATTENDEDGRADELEVEL")
    private String lastattendedgradelevel;
    @JoinColumn(name = "STUDENTID", referencedColumnName = "STUDENTID")
    @ManyToOne
    private Student studentid;

    public Previouseducation() {
    }

    public Previouseducation(Integer previousschoolid) {
        this.previousschoolid = previousschoolid;
    }

    public Integer getPreviousschoolid() {
        return previousschoolid;
    }

    public void setPreviousschoolid(Integer previousschoolid) {
        this.previousschoolid = previousschoolid;
    }

    public String getPreviousschoolname() {
        return previousschoolname;
    }

    public void setPreviousschoolname(String previousschoolname) {
        this.previousschoolname = previousschoolname;
    }

    public String getPreviousschooladdress1() {
        return previousschooladdress1;
    }

    public void setPreviousschooladdress1(String previousschooladdress1) {
        this.previousschooladdress1 = previousschooladdress1;
    }

    public String getPreviousschooladdress2() {
        return previousschooladdress2;
    }

    public void setPreviousschooladdress2(String previousschooladdress2) {
        this.previousschooladdress2 = previousschooladdress2;
    }

    public String getPreviousschoolcity() {
        return previousschoolcity;
    }

    public void setPreviousschoolcity(String previousschoolcity) {
        this.previousschoolcity = previousschoolcity;
    }

    public String getPreviousschoolzip() {
        return previousschoolzip;
    }

    public void setPreviousschoolzip(String previousschoolzip) {
        this.previousschoolzip = previousschoolzip;
    }

    public String getPreviousschoolstate() {
        return previousschoolstate;
    }

    public void setPreviousschoolstate(String previousschoolstate) {
        this.previousschoolstate = previousschoolstate;
    }

    public String getPreviousschoolcountry() {
        return previousschoolcountry;
    }

    public void setPreviousschoolcountry(String previousschoolcountry) {
        this.previousschoolcountry = previousschoolcountry;
    }

    public String getLastattendedgradelevel() {
        return lastattendedgradelevel;
    }

    public void setLastattendedgradelevel(String lastattendedgradelevel) {
        this.lastattendedgradelevel = lastattendedgradelevel;
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
        hash += (previousschoolid != null ? previousschoolid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Previouseducation)) {
            return false;
        }
        Previouseducation other = (Previouseducation) object;
        if ((this.previousschoolid == null && other.previousschoolid != null) || (this.previousschoolid != null && !this.previousschoolid.equals(other.previousschoolid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sis.model.Previouseducation[ previousschoolid=" + previousschoolid + " ]";
    }
    
}
