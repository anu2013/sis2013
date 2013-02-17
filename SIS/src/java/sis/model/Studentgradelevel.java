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
@Table(name = "STUDENTGRADELEVEL")
@NamedQueries({
    @NamedQuery(name = "Studentgradelevel.findAll", query = "SELECT s FROM Studentgradelevel s")})
public class Studentgradelevel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "STUDENTGRADELEVELID")
    private Integer studentgradelevelid;
    @Size(max = 100)
    @Column(name = "STATUS")
    private String status;
    @JoinColumn(name = "STUDENTID", referencedColumnName = "STUDENTID")
    @ManyToOne
    private Student studentid;
    @JoinColumn(name = "SCHOOLYEAR", referencedColumnName = "SCHOOLYEAR")
    @ManyToOne
    private Schoolyearschedule schoolyear;
    @JoinColumn(name = "GRADELEVELID", referencedColumnName = "GRADELEVELID")
    @ManyToOne
    private Gradelevel gradelevelid;

    public Studentgradelevel() {
    }

    public Studentgradelevel(Integer studentgradelevelid) {
        this.studentgradelevelid = studentgradelevelid;
    }

    public Integer getStudentgradelevelid() {
        return studentgradelevelid;
    }

    public void setStudentgradelevelid(Integer studentgradelevelid) {
        this.studentgradelevelid = studentgradelevelid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Student getStudentid() {
        return studentid;
    }

    public void setStudentid(Student studentid) {
        this.studentid = studentid;
    }

    public Schoolyearschedule getSchoolyear() {
        return schoolyear;
    }

    public void setSchoolyear(Schoolyearschedule schoolyear) {
        this.schoolyear = schoolyear;
    }

    public Gradelevel getGradelevelid() {
        return gradelevelid;
    }

    public void setGradelevelid(Gradelevel gradelevelid) {
        this.gradelevelid = gradelevelid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (studentgradelevelid != null ? studentgradelevelid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Studentgradelevel)) {
            return false;
        }
        Studentgradelevel other = (Studentgradelevel) object;
        if ((this.studentgradelevelid == null && other.studentgradelevelid != null) || (this.studentgradelevelid != null && !this.studentgradelevelid.equals(other.studentgradelevelid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sis.model.Studentgradelevel[ studentgradelevelid=" + studentgradelevelid + " ]";
    }
    
}
