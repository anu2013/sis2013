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
@Table(name = "IEPPROGRESS")
@NamedQueries({
    @NamedQuery(name = "Iepprogress.findAll", query = "SELECT i FROM Iepprogress i")})
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
    @OneToMany(mappedBy = "iepprogressid")
    private List<Iepprogressresources> iepprogressresourcesList;
    @JoinColumn(name = "IEPGOALID", referencedColumnName = "IEPGOALID")
    @ManyToOne
    private Iepgoals iepgoalid;

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

    public List<Iepprogressresources> getIepprogressresourcesList() {
        return iepprogressresourcesList;
    }

    public void setIepprogressresourcesList(List<Iepprogressresources> iepprogressresourcesList) {
        this.iepprogressresourcesList = iepprogressresourcesList;
    }

    public Iepgoals getIepgoalid() {
        return iepgoalid;
    }

    public void setIepgoalid(Iepgoals iepgoalid) {
        this.iepgoalid = iepgoalid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iepprogressid != null ? iepprogressid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Iepprogress)) {
            return false;
        }
        Iepprogress other = (Iepprogress) object;
        if ((this.iepprogressid == null && other.iepprogressid != null) || (this.iepprogressid != null && !this.iepprogressid.equals(other.iepprogressid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sis.model.Iepprogress[ iepprogressid=" + iepprogressid + " ]";
    }
    
}
