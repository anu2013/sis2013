/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Anupama Karumudi
 */
@Entity
@Table(name = "SUBJECTSCHEDULE")
@NamedQueries({
    @NamedQuery(name = "Subjectschedule.findAll", query = "SELECT s FROM Subjectschedule s")})
public class Subjectschedule implements Serializable {
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subjectscheduleid")
    private List<Studentsubjectschedule> studentsubjectscheduleList;
    @JoinColumn(name = "PRIMARYTEACHERID", referencedColumnName = "TEACHERID")
    @ManyToOne
    private Teacher primaryteacherid;
    @JoinColumn(name = "SECONDARYTEACHERID", referencedColumnName = "TEACHERID")
    @ManyToOne
    private Teacher secondaryteacherid;
    @JoinColumn(name = "SUBJECTID", referencedColumnName = "SUBJECTID")
    @ManyToOne
    private Subject subjectid;
    @JoinColumn(name = "SCHOOLYEAR", referencedColumnName = "SCHOOLYEAR")
    @ManyToOne
    private Schoolyearschedule schoolyear;
    @JoinColumn(name = "PERIODID", referencedColumnName = "PERIODID")
    @ManyToOne
    private Period periodid;

    public Subjectschedule() {
    }

    public Subjectschedule(Integer subjectscheduleid) {
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

    public List<Studentsubjectschedule> getStudentsubjectscheduleList() {
        return studentsubjectscheduleList;
    }

    public void setStudentsubjectscheduleList(List<Studentsubjectschedule> studentsubjectscheduleList) {
        this.studentsubjectscheduleList = studentsubjectscheduleList;
    }

    public Teacher getPrimaryteacherid() {
        return primaryteacherid;
    }

    public void setPrimaryteacherid(Teacher primaryteacherid) {
        this.primaryteacherid = primaryteacherid;
    }

    public Teacher getSecondaryteacherid() {
        return secondaryteacherid;
    }

    public void setSecondaryteacherid(Teacher secondaryteacherid) {
        this.secondaryteacherid = secondaryteacherid;
    }

    public Subject getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(Subject subjectid) {
        this.subjectid = subjectid;
    }

    public Schoolyearschedule getSchoolyear() {
        return schoolyear;
    }

    public void setSchoolyear(Schoolyearschedule schoolyear) {
        this.schoolyear = schoolyear;
    }

    public Period getPeriodid() {
        return periodid;
    }

    public void setPeriodid(Period periodid) {
        this.periodid = periodid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (subjectscheduleid != null ? subjectscheduleid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Subjectschedule)) {
            return false;
        }
        Subjectschedule other = (Subjectschedule) object;
        if ((this.subjectscheduleid == null && other.subjectscheduleid != null) || (this.subjectscheduleid != null && !this.subjectscheduleid.equals(other.subjectscheduleid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sis.model.Subjectschedule[ subjectscheduleid=" + subjectscheduleid + " ]";
    }
    
}
