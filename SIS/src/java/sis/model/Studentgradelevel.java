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
@Table(name = "STUDENTGRADELEVEL")
public class Studentgradelevel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "STUDENTGRADELEVELID")
    private Integer studentgradelevelid;
    
    @Size(max = 100)
    @Column(name = "STATUS")
    private String status;
    
    @JoinColumn(name = "STUDENTID", referencedColumnName = "STUDENTID")
    @ManyToOne
    private Student student;
    
    @JoinColumn(name = "SCHOOLYEAR", referencedColumnName = "SCHOOLYEAR")
    @ManyToOne
    private Schoolyearschedule schoolyear;
    
    @JoinColumn(name = "GRADELEVELID", referencedColumnName = "GRADELEVELID")
    @ManyToOne
    private Gradelevel gradelevel;

    public Studentgradelevel() {
    }

    public Studentgradelevel(Integer studentgradelevelid) {
        this.studentgradelevelid = studentgradelevelid;
    }

    public Integer getStudentgradelevelid() {
        return studentgradelevelid;
    }

    public void setStudentgradelevelid(Integer studentgradelevelid) {
        this.studentgradelevelid = studentgradelevelid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Schoolyearschedule getSchoolyear() {
        return schoolyear;
    }

    public void setSchoolyear(Schoolyearschedule schoolyear) {
        this.schoolyear = schoolyear;
    }

    public Gradelevel getGradelevel() {
        return gradelevel;
    }

    public void setGradelevel(Gradelevel gradelevel) {
        this.gradelevel = gradelevel;
    }
}
