/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author Anupama Karumudi
 */
@Entity
@Table(name = "IEPGOALS")
@NamedQueries({
    @NamedQuery(name = "Iepgoals.findAll", query = "SELECT i FROM Iepgoals i")})
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
    @JoinColumn(name = "STUDENTID", referencedColumnName = "STUDENTID")
    @ManyToOne
    private Student studentid;
    @OneToMany(mappedBy = "iepgoalid")
    private List<Iepprogress> iepprogressList;

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

    public Student getStudentid() {
        return studentid;
    }

    public void setStudentid(Student studentid) {
        this.studentid = studentid;
    }

    public List<Iepprogress> getIepprogressList() {
        return iepprogressList;
    }

    public void setIepprogressList(List<Iepprogress> iepprogressList) {
        this.iepprogressList = iepprogressList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iepgoalid != null ? iepgoalid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Iepgoals)) {
            return false;
        }
        Iepgoals other = (Iepgoals) object;
        if ((this.iepgoalid == null && other.iepgoalid != null) || (this.iepgoalid != null && !this.iepgoalid.equals(other.iepgoalid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sis.model.Iepgoals[ iepgoalid=" + iepgoalid + " ]";
    }
    
}
