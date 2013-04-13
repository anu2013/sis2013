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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Anupama Karumudi
 */
@ManagedBean
@Entity
@Table(name = "SCHOOLYEARSCHEDULE")
@NamedQueries({
    @NamedQuery(name = "Schoolyearschedule.findAll", query = "SELECT s FROM Schoolyearschedule s")})
public class Schoolyearschedule implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SCHOOLYEAR")
    private Integer schoolyear;
    @Column(name = "STARTDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startdate;
    @Column(name = "ENDDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date enddate;
    @Column(name = "Active")
    private Short active;

    public Schoolyearschedule() {
    }

    public Schoolyearschedule(Integer schoolyear) {
        this.schoolyear = schoolyear;
    }

    public Integer getSchoolyear() {
        return schoolyear;
    }

    public void setSchoolyear(Integer schoolyear) {
        this.schoolyear = schoolyear;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (schoolyear != null ? schoolyear.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Schoolyearschedule)) {
            return false;
        }
        Schoolyearschedule other = (Schoolyearschedule) object;
        if ((this.schoolyear == null && other.schoolyear != null) || (this.schoolyear != null && !this.schoolyear.equals(other.schoolyear))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sis.model.Schoolyearschedule[ schoolyear=" + schoolyear + " ]";
    }

    /**
     * @return the active
     */
    public Short getActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(Short active) {
        this.active = active;
    }
    
}
