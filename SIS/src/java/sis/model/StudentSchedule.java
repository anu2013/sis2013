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
import javax.persistence.Transient;

/**
 *
 * @author Anupama Karumudi
 */
@ManagedBean
@Entity
@Table(name = "STUDENTSUBJECTSCHEDULE")
public class StudentSchedule implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "STUDENTSUBJECTSCHEDULEID")
    private Integer studentsubjectscheduleid;
    
    @JoinColumn(name = "SUBJECTSCHEDULEID", referencedColumnName = "SUBJECTSCHEDULEID")
    @ManyToOne(optional = false)
    private TeacherSchedule schedule;
    
    @JoinColumn(name = "STUDENTID", referencedColumnName = "STUDENTID")
    @ManyToOne(optional = false)
    private Student student;

    /* Current grade level */
    @Transient
    private Gradelevel gradeLevel;
    
    @Transient
    private Attendancetracking selectedDayAttendance;
    
    @Transient
    private Studentscorecard scoreCard;
   
    public StudentSchedule() {
    }

    public StudentSchedule(Integer studentsubjectscheduleid) {
        this.studentsubjectscheduleid = studentsubjectscheduleid;
    }

    public Integer getStudentsubjectscheduleid() {
        return studentsubjectscheduleid;
    }

    public void setStudentsubjectscheduleid(Integer studentsubjectscheduleid) {
        this.studentsubjectscheduleid = studentsubjectscheduleid;
    }

    public TeacherSchedule getSchedule() {
        return schedule;
    }

    public void setSchedule(TeacherSchedule schedule) {
        this.schedule = schedule;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
    
    public Gradelevel getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(Gradelevel gradeLevel) {
        this.gradeLevel = gradeLevel;
    }
    
    public Attendancetracking getSelectedDayAttendance() {
        return selectedDayAttendance;
    }

    public void setSelectedDayAttendance(Attendancetracking selectedDayAttendance) {
        this.selectedDayAttendance = selectedDayAttendance;
    }
            
    public Studentscorecard getScoreCard() {
        return scoreCard;
    }

    public void setScoreCard(Studentscorecard card) {
        this.scoreCard = card;
    }
}
