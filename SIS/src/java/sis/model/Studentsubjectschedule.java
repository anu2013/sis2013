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

/**
 *
 * @author Anupama Karumudi
 */
@ManagedBean
@Entity
@Table(name = "STUDENTSUBJECTSCHEDULE")
public class Studentsubjectschedule implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "STUDENTSUBJECTSCHEDULEID")
    private Integer studentsubjectscheduleid;
    
    @JoinColumn(name = "SUBJECTSCHEDULEID", referencedColumnName = "SUBJECTSCHEDULEID")
    @ManyToOne(optional = false)
    private TeacherSchedule subjectscheduleid;
    
    @JoinColumn(name = "STUDENTID", referencedColumnName = "STUDENTID")
    @ManyToOne(optional = false)
    private Student student;

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

    public TeacherSchedule getSubjectscheduleid() {
        return subjectscheduleid;
    }

    public void setSubjectscheduleid(TeacherSchedule subjectscheduleid) {
        this.subjectscheduleid = subjectscheduleid;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
