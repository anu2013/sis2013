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

/**
 *
 * @author Anupama Karumudi
 */
@Entity
@Table(name = "STUDENTSUBJECTSCHEDULE")
@NamedQueries({
    @NamedQuery(name = "Studentsubjectschedule.findAll", query = "SELECT s FROM Studentsubjectschedule s")})
public class Studentsubjectschedule implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "STUDENTSUBJECTSCHEDULEID")
    private Integer studentsubjectscheduleid;
    @JoinColumn(name = "SUBJECTSCHEDULEID", referencedColumnName = "SUBJECTSCHEDULEID")
    @ManyToOne(optional = false)
    private Subjectschedule subjectscheduleid;
    @JoinColumn(name = "STUDENTID", referencedColumnName = "STUDENTID")
    @ManyToOne(optional = false)
    private Student studentid;

    public Studentsubjectschedule() {
    }

    public Studentsubjectschedule(Integer studentsubjectscheduleid) {
        this.studentsubjectscheduleid = studentsubjectscheduleid;
    }

    public Integer getStudentsubjectscheduleid() {
        return studentsubjectscheduleid;
    }

    public void setStudentsubjectscheduleid(Integer studentsubjectscheduleid) {
        this.studentsubjectscheduleid = studentsubjectscheduleid;
    }

    public Subjectschedule getSubjectscheduleid() {
        return subjectscheduleid;
    }

    public void setSubjectscheduleid(Subjectschedule subjectscheduleid) {
        this.subjectscheduleid = subjectscheduleid;
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
        hash += (studentsubjectscheduleid != null ? studentsubjectscheduleid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Studentsubjectschedule)) {
            return false;
        }
        Studentsubjectschedule other = (Studentsubjectschedule) object;
        if ((this.studentsubjectscheduleid == null && other.studentsubjectscheduleid != null) || (this.studentsubjectscheduleid != null && !this.studentsubjectscheduleid.equals(other.studentsubjectscheduleid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sis.model.Studentsubjectschedule[ studentsubjectscheduleid=" + studentsubjectscheduleid + " ]";
    }
    
}
