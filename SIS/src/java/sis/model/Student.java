/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@ManagedBean
@Entity
@Table(name = "STUDENT")
@NamedQueries({
    @NamedQuery(name = "Student.findAll", query = "SELECT s FROM Student s")})
public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "STUDENTID")
    private Integer studentid;
    @Column(name = "ADMISSIONID")
    private Integer admissionid;
    @Size(max = 100)
    @Column(name = "ADMISSIONSTATUS")
    private String admissionstatus;
    @Column(name = "STARTDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startdate;
    @Size(max = 50)
    @Column(name = "ETHNICITY")
    private String ethnicity;
    @Size(max = 50)
    @Column(name = "RACE")
    private String race;
    @Column(name = "HEALTHRECORDSRECEIVED")
    private Short healthrecordsreceived;
    @Column(name = "DISABILITYSUPPORTNEEDED")
    private Short disabilitysupportneeded;
    @Column(name = "IEPNEEDED")
    private Short iepneeded;
    @OneToMany(mappedBy = "studentid")
    private List<Parent> parentList;
    @OneToMany(mappedBy = "studentid")
    private List<Iepgoals> iepgoalsList;
    @OneToMany(mappedBy = "studentid")
    private List<Previouseducation> previouseducationList;
    @OneToMany(mappedBy = "studentid")
    private List<Studentgradelevel> studentgradelevelList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "studentid")
    private List<Studentsubjectschedule> studentsubjectscheduleList;
    @JoinColumn(name = "STUDENTID", referencedColumnName = "USERID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Users users;
    @OneToMany(mappedBy = "studentid")
    private List<Attendancetracking> attendancetrackingList;
    @OneToMany(mappedBy = "studentid")
    private List<Studentscorecard> studentscorecardList;

    @JoinColumn(name = "STUDENTID", referencedColumnName = "USERID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Userprofile userprofile;  
    
    public Student() {
    }

    public Student(Integer studentid) {
        this.studentid = studentid;
    }

    public Integer getStudentid() {
        return studentid;
    }

    public void setStudentid(Integer studentid) {
        this.studentid = studentid;
    }

    public Integer getAdmissionid() {
        return admissionid;
    }

    public void setAdmissionid(Integer admissionid) {
        this.admissionid = admissionid;
    }

    public String getAdmissionstatus() {
        return admissionstatus;
    }

    public void setAdmissionstatus(String admissionstatus) {
        this.admissionstatus = admissionstatus;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public String getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public Short getHealthrecordsreceived() {
        return healthrecordsreceived;
    }

    public void setHealthrecordsreceived(Short healthrecordsreceived) {
        this.healthrecordsreceived = healthrecordsreceived;
    }

    public Short getDisabilitysupportneeded() {
        return disabilitysupportneeded;
    }

    public void setDisabilitysupportneeded(Short disabilitysupportneeded) {
        this.disabilitysupportneeded = disabilitysupportneeded;
    }

    public Short getIepneeded() {
        return iepneeded;
    }

    public void setIepneeded(Short iepneeded) {
        this.iepneeded = iepneeded;
    }

    public List<Parent> getParentList() {
        return parentList;
    }

    public void setParentList(List<Parent> parentList) {
        this.parentList = parentList;
    }

    public List<Iepgoals> getIepgoalsList() {
        return iepgoalsList;
    }

    public void setIepgoalsList(List<Iepgoals> iepgoalsList) {
        this.iepgoalsList = iepgoalsList;
    }

    public List<Previouseducation> getPreviouseducationList() {
        return previouseducationList;
    }

    public void setPreviouseducationList(List<Previouseducation> previouseducationList) {
        this.previouseducationList = previouseducationList;
    }

    public List<Studentgradelevel> getStudentgradelevelList() {
        return studentgradelevelList;
    }

    public void setStudentgradelevelList(List<Studentgradelevel> studentgradelevelList) {
        this.studentgradelevelList = studentgradelevelList;
    }

    public List<Studentsubjectschedule> getStudentsubjectscheduleList() {
        return studentsubjectscheduleList;
    }

    public void setStudentsubjectscheduleList(List<Studentsubjectschedule> studentsubjectscheduleList) {
        this.studentsubjectscheduleList = studentsubjectscheduleList;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public List<Attendancetracking> getAttendancetrackingList() {
        return attendancetrackingList;
    }

    public void setAttendancetrackingList(List<Attendancetracking> attendancetrackingList) {
        this.attendancetrackingList = attendancetrackingList;
    }

    public List<Studentscorecard> getStudentscorecardList() {
        return studentscorecardList;
    }

    public void setStudentscorecardList(List<Studentscorecard> studentscorecardList) {
        this.studentscorecardList = studentscorecardList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (studentid != null ? studentid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Student)) {
            return false;
        }
        Student other = (Student) object;
        if ((this.studentid == null && other.studentid != null) || (this.studentid != null && !this.studentid.equals(other.studentid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sis.model.Student[ studentid=" + studentid + " ]";
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
