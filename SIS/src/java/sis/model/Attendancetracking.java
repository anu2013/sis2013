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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Anupama Karumudi
 */
@Entity
@Table(name = "ATTENDANCETRACKING")
@NamedQueries({
    @NamedQuery(name = "Attendancetracking.findAll", query = "SELECT a FROM Attendancetracking a")})
public class Attendancetracking implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ATTENDANCEID")
    private Integer attendanceid;
    @Column(name = "SCHOOLYEAR")
    private Integer schoolyear;
    @Column(name = "ATTENDANCEDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date attendancedate;
    @Column(name = "ATTENDANCEFLAG")
    private Short attendanceflag;
    @Column(name = "ATTENDANCETAKENBY")
    private Integer attendancetakenby;
    @JoinColumn(name = "STUDENTID", referencedColumnName = "STUDENTID")
    @ManyToOne
    private Student studentid;

    public Attendancetracking() {
    }

    public Attendancetracking(Integer attendanceid) {
        this.attendanceid = attendanceid;
    }

    public Integer getAttendanceid() {
        return attendanceid;
    }

    public void setAttendanceid(Integer attendanceid) {
        this.attendanceid = attendanceid;
    }

    public Integer getSchoolyear() {
        return schoolyear;
    }

    public void setSchoolyear(Integer schoolyear) {
        this.schoolyear = schoolyear;
    }

    public Date getAttendancedate() {
        return attendancedate;
    }

    public void setAttendancedate(Date attendancedate) {
        this.attendancedate = attendancedate;
    }

    public Short getAttendanceflag() {
        return attendanceflag;
    }

    public void setAttendanceflag(Short attendanceflag) {
        this.attendanceflag = attendanceflag;
    }

    public Integer getAttendancetakenby() {
        return attendancetakenby;
    }

    public void setAttendancetakenby(Integer attendancetakenby) {
        this.attendancetakenby = attendancetakenby;
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
        hash += (attendanceid != null ? attendanceid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Attendancetracking)) {
            return false;
        }
        Attendancetracking other = (Attendancetracking) object;
        if ((this.attendanceid == null && other.attendanceid != null) || (this.attendanceid != null && !this.attendanceid.equals(other.attendanceid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sis.model.Attendancetracking[ attendanceid=" + attendanceid + " ]";
    }
    
}
