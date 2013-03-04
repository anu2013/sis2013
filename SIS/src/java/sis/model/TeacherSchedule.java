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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Anupama Karumudi
 */
@ManagedBean
@Entity
@Table(name = "SUBJECTSCHEDULE")
public class TeacherSchedule implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SUBJECTSCHEDULEID")
    private Integer subjectscheduleid;
    
    @Size(max = 100)
    @Column(name = "SCHEDULENAME")
    private String schedulename;
    
    @Size(max = 100)
    @Column(name = "SCHEDULEDAYS")
    private String scheduledays;

    @JoinColumn(name = "PRIMARYTEACHERID", referencedColumnName = "TEACHERID")
    @ManyToOne
    private Teacher primaryteacher;
    
    @JoinColumn(name = "SECONDARYTEACHERID", referencedColumnName = "TEACHERID")
    @ManyToOne
    private Teacher secondaryteacher;
    
    @JoinColumn(name = "SUBJECTID", referencedColumnName = "SUBJECTID")
    @ManyToOne
    private Subject subject;
    
    @JoinColumn(name = "SCHOOLYEAR", referencedColumnName = "SCHOOLYEAR")
    @ManyToOne
    private Schoolyearschedule schoolyear;
    
    @JoinColumn(name = "PERIODID", referencedColumnName = "PERIODID")
    @ManyToOne
    private Period period;
    
    public TeacherSchedule() {
    }

    public TeacherSchedule(Integer subjectscheduleid) {
        this.subjectscheduleid = subjectscheduleid;
    }

    public Integer getSubjectscheduleid() {
        return subjectscheduleid;
    }

    public void setSubjectscheduleid(Integer subjectscheduleid) {
        this.subjectscheduleid = subjectscheduleid;
    }

    public String getSchedulename() {
        return schedulename;
    }

    public void setSchedulename(String schedulename) {
        this.schedulename = schedulename;
    }

    public String getScheduledays() {
        return scheduledays;
    }

    public void setScheduledays(String scheduledays) {
        this.scheduledays = scheduledays;
    }

    public Teacher getPrimaryTeacher() {
        return primaryteacher;
    }

    public void setPrimaryTeacher(Teacher primaryteacher) {
        this.primaryteacher = primaryteacher;
    }

    public Teacher getSecondaryTeacher() {
        return secondaryteacher;
    }

    public void setSecondaryTeacher(Teacher secondaryteacher) {
        this.secondaryteacher = secondaryteacher;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Schoolyearschedule getSchoolyear() {
        return schoolyear;
    }

    public void setSchoolyear(Schoolyearschedule schoolyear) {
        this.schoolyear = schoolyear;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }
    
    public String getPrimaryTeacherName() {
        if(null != this.primaryteacher) {
            Userprofile profile = this.primaryteacher.getProfile();
            if(null != profile) {
                return profile.getFirstname() + " " + profile.getLastname();
            }
        }
        return null;
    }

    public String getSecondaryTeacherName() {
          if(null != this.secondaryteacher) {
            Userprofile profile = this.secondaryteacher.getProfile();
            if(null != profile) {
                return profile.getFirstname() + " " + profile.getLastname();
            }
        }
        return null;
    }
}
