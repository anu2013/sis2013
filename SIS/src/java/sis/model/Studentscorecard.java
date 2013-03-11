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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Anupama Karumudi
 */
@ManagedBean
@Entity
@Table(name = "STUDENTSCORECARD")
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
    
    @Column(name = "SUBJECTID")
    private Integer subjectid;
    
    @Column(name = "STUDENTID")
    private Integer studentId;
    
    @Column(name = "SCHOOLYEAR")
    private Integer schoolyear;

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

    public Integer getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(Integer subjectid) {
        this.subjectid = subjectid;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getSchoolyear() {
        return schoolyear;
    }

    public void setSchoolyear(Integer schoolyear) {
        this.schoolyear = schoolyear;
    }
}
