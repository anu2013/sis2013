/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.model;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
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
import javax.validation.constraints.Size;

/**
 *
 * @author Anupama Karumudi
 */
@ManagedBean
@Entity
@Table(name = "PERIOD")
@NamedQueries({
    @NamedQuery(name = "Period.findAll", query = "SELECT p FROM Period p")})
public class Period implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PERIODID")
    private Integer periodid;
    @Size(max = 255)
    @Column(name = "DESCRIPTION")
    private String description;
    @Size(max = 255)
    @Column(name = "PERIODCODE")
    private String periodcode;
    @Column(name = "SORTORDER")
    private Integer sortorder;
    @Size(max = 255)
    @Column(name = "STARTTIME")
    private String starttime;
    @Size(max = 255)
    @Column(name = "ENDTIME")
    private String endtime;
    @JoinColumn(name = "SCHOOLYEAR", referencedColumnName = "SCHOOLYEAR")
    @ManyToOne
    private Schoolyearschedule schoolyear;
    @OneToMany(mappedBy = "periodid")
    private List<Subjectschedule> subjectscheduleList;

    public Period() {
    }

    public Period(Integer periodid) {
        this.periodid = periodid;
    }

    public Integer getPeriodid() {
        return periodid;
    }

    public void setPeriodid(Integer periodid) {
        this.periodid = periodid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPeriodcode() {
        return periodcode;
    }

    public void setPeriodcode(String periodcode) {
        this.periodcode = periodcode;
    }

    public Integer getSortorder() {
        return sortorder;
    }

    public void setSortorder(Integer sortorder) {
        this.sortorder = sortorder;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public Schoolyearschedule getSchoolyear() {
        return schoolyear;
    }

    public void setSchoolyear(Schoolyearschedule schoolyear) {
        this.schoolyear = schoolyear;
    }

    public List<Subjectschedule> getSubjectscheduleList() {
        return subjectscheduleList;
    }

    public void setSubjectscheduleList(List<Subjectschedule> subjectscheduleList) {
        this.subjectscheduleList = subjectscheduleList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (periodid != null ? periodid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Period)) {
            return false;
        }
        Period other = (Period) object;
        if ((this.periodid == null && other.periodid != null) || (this.periodid != null && !this.periodid.equals(other.periodid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sis.model.Period[ periodid=" + periodid + " ]";
    }
    
}
