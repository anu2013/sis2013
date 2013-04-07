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
import javax.validation.constraints.Size;

/**
 *
 * @author Anupama Karumudi
 */
@ManagedBean
@Entity
@Table(name = "IEPGOALS")
public class Iepgoals implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IEPGOALID")
    private Integer iepgoalid;
    
    @Column(name = "SCHOOLYEAR")
    private Integer schoolyear;
    
    @Size(max = 255)
    @Column(name = "GOALTITLE")
    private String goaltitle;
    
    @Size(max = 255)
    @Column(name = "GOALDESCRIPTION")
    private String goaldescription;
    
    @Size(max = 255)
    @Column(name = "STATUS")
    private String status;
    
    @Column(name = "STARTDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startdate;
    
    @Column(name = "ENDDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date enddate;
    
    @Size(max = 100)
    @Column(name = "LASTUPDATEDBY")
    private String lastupdatedby;
    
    @Column(name = "LASTUPDATEDDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastupdateddate;
    
    @Column(name = "STUDENTID")
    private Integer studentid;
    
    public Iepgoals() {
    }

    public Iepgoals(Integer iepgoalid) {
        this.iepgoalid = iepgoalid;
    }

    public Integer getIepgoalid() {
        return iepgoalid;
    }

    public void setIepgoalid(Integer iepgoalid) {
        this.iepgoalid = iepgoalid;
    }

    public Integer getSchoolyear() {
        return schoolyear;
    }

    public void setSchoolyear(Integer schoolyear) {
        this.schoolyear = schoolyear;
    }

    public String getGoaltitle() {
        return goaltitle;
    }

    public void setGoaltitle(String goaltitle) {
        this.goaltitle = goaltitle;
    }

    public String getGoaldescription() {
        return goaldescription;
    }

    public void setGoaldescription(String goaldescription) {
        this.goaldescription = goaldescription;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public String getLastupdatedby() {
        return lastupdatedby;
    }

    public void setLastupdatedby(String lastupdatedby) {
        this.lastupdatedby = lastupdatedby;
    }

    public Date getLastupdateddate() {
        return lastupdateddate;
    }

    public void setLastupdateddate(Date lastupdateddate) {
        this.lastupdateddate = lastupdateddate;
    }

    public Integer getStudentid() {
        return studentid;
    }

    public void setStudentid(Integer studentid) {
        this.studentid = studentid;
    }
}
