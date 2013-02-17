/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author Anupama Karumudi
 */
@Entity
@Table(name = "IEPPROGRESSRESOURCES")
@NamedQueries({
    @NamedQuery(name = "Iepprogressresources.findAll", query = "SELECT i FROM Iepprogressresources i")})
public class Iepprogressresources implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IEPPROGRESSRESOURCEID")
    private Integer iepprogressresourceid;
    @Size(max = 100)
    @Column(name = "RESOURCENAME")
    private String resourcename;
    @Size(max = 255)
    @Column(name = "RESOURCEURL")
    private String resourceurl;
    @Column(name = "UPLOADEDDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date uploadeddate;
    @JoinColumn(name = "IEPPROGRESSID", referencedColumnName = "IEPPROGRESSID")
    @ManyToOne
    private Iepprogress iepprogressid;

    public Iepprogressresources() {
    }

    public Iepprogressresources(Integer iepprogressresourceid) {
        this.iepprogressresourceid = iepprogressresourceid;
    }

    public Integer getIepprogressresourceid() {
        return iepprogressresourceid;
    }

    public void setIepprogressresourceid(Integer iepprogressresourceid) {
        this.iepprogressresourceid = iepprogressresourceid;
    }

    public String getResourcename() {
        return resourcename;
    }

    public void setResourcename(String resourcename) {
        this.resourcename = resourcename;
    }

    public String getResourceurl() {
        return resourceurl;
    }

    public void setResourceurl(String resourceurl) {
        this.resourceurl = resourceurl;
    }

    public Date getUploadeddate() {
        return uploadeddate;
    }

    public void setUploadeddate(Date uploadeddate) {
        this.uploadeddate = uploadeddate;
    }

    public Iepprogress getIepprogressid() {
        return iepprogressid;
    }

    public void setIepprogressid(Iepprogress iepprogressid) {
        this.iepprogressid = iepprogressid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iepprogressresourceid != null ? iepprogressresourceid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Iepprogressresources)) {
            return false;
        }
        Iepprogressresources other = (Iepprogressresources) object;
        if ((this.iepprogressresourceid == null && other.iepprogressresourceid != null) || (this.iepprogressresourceid != null && !this.iepprogressresourceid.equals(other.iepprogressresourceid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sis.model.Iepprogressresources[ iepprogressresourceid=" + iepprogressresourceid + " ]";
    }
    
}
