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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name = "IEPPROGRESS")
public class Iepprogress implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IEPPROGRESSID")
    private Integer iepprogressid;
    
    @Size(max = 255)
    @Column(name = "PROGRESSDETAILS")
    private String progressdetails;
    
    @Size(max = 100)
    @Column(name = "LASTUPDATEDBY")
    private String lastupdatedby;
    
    @Column(name = "LASTUPDATEDDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastupdateddate;
    
    @Column(name = "IEPGOALID")
    private Integer iepgoalid;

    public Iepprogress() {
    }

    public Iepprogress(Integer iepprogressid) {
        this.iepprogressid = iepprogressid;
    }

    public Integer getIepprogressid() {
        return iepprogressid;
    }

    public void setIepprogressid(Integer iepprogressid) {
        this.iepprogressid = iepprogressid;
    }

    public String getProgressdetails() {
        return progressdetails;
    }

    public void setProgressdetails(String progressdetails) {
        this.progressdetails = progressdetails;
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

    public Integer getIepgoalid() {
        return iepgoalid;
    }

    public void setIepgoalid(Integer iepgoalid) {
        this.iepgoalid = iepgoalid;
    }
}
