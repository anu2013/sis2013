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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Anupama Karumudi
 */
@ManagedBean
@Entity
@Table(name = "ATTENDANCETRACKING")
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
    private String attendancetakenby;
    
    @Column(name = "SUBJECTSCHEDULEID")
    private Integer subjectScheduleId;
    
    @Column(name = "STUDENTID")
    private Integer studentId;

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

    public String getAttendancetakenby() {
        return attendancetakenby;
    }

    public void setAttendancetakenby(String attendancetakenby) {
        this.attendancetakenby = attendancetakenby;
    }

    public void setSubjectScheduleId(Integer subjectScheduleId) {
        this.subjectScheduleId = subjectScheduleId;
    }

    public Integer getSubjectScheduleId() {
        return subjectScheduleId;
    }
            
    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }
}
