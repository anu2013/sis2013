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
@Table(name = "STUDENTSCORECARD")
@NamedQueries({
    @NamedQuery(name = "Studentscorecard.findAll", query = "SELECT s FROM Studentscorecard s")})
public class Studentscorecard implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "STUDENTSCORECARDID")
    private Integer studentscorecardid;
    @Size(max = 100)
    @Column(name = "FINALSCORE")
    private String finalscore;
    @Size(max = 255)
    @Column(name = "COMMENTS")
    private String comments;
    @Size(max = 100)
    @Column(name = "STATUS")
    private String status;
    @Size(max = 5)
    @Column(name = "GRADELETTER")
    private String gradeletter;
    @JoinColumn(name = "SUBJECTID", referencedColumnName = "SUBJECTID")
    @ManyToOne
    private Subject subjectid;
    @JoinColumn(name = "STUDENTID", referencedColumnName = "STUDENTID")
    @ManyToOne
    private Student studentid;
    @JoinColumn(name = "SCHOOLYEAR", referencedColumnName = "SCHOOLYEAR")
    @ManyToOne
    private Schoolyearschedule schoolyear;

    public Studentscorecard() {
    }

    public Studentscorecard(Integer studentscorecardid) {
        this.studentscorecardid = studentscorecardid;
    }

    public Integer getStudentscorecardid() {
        return studentscorecardid;
    }

    public void setStudentscorecardid(Integer studentscorecardid) {
        this.studentscorecardid = studentscorecardid;
    }

    public String getFinalscore() {
        return finalscore;
    }

    public void setFinalscore(String finalscore) {
        this.finalscore = finalscore;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGradeletter() {
        return gradeletter;
    }

    public void setGradeletter(String gradeletter) {
        this.gradeletter = gradeletter;
    }

    public Subject getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(Subject subjectid) {
        this.subjectid = subjectid;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (studentscorecardid != null ? studentscorecardid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Studentscorecard)) {
            return false;
        }
        Studentscorecard other = (Studentscorecard) object;
        if ((this.studentscorecardid == null && other.studentscorecardid != null) || (this.studentscorecardid != null && !this.studentscorecardid.equals(other.studentscorecardid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sis.model.Studentscorecard[ studentscorecardid=" + studentscorecardid + " ]";
    }
    
}
