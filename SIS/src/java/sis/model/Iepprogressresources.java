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
@Table(name = "IEPPROGRESSRESOURCES")
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
    
    @Column(name = "IEPPROGRESSID")
    private Integer iepprogressid;

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

    public Integer getIepprogressid() {
        return iepprogressid;
    }

    public void setIepprogressid(Integer iepprogressid) {
        this.iepprogressid = iepprogressid;
    }
}
