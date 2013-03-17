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
@Table(name = "SUBJECT")
@NamedQueries({
    @NamedQuery(name = "Subject.findAll", query = "SELECT s FROM Subject s")})
public class Subject implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SUBJECTID")
    private Integer subjectid;
    @Size(max = 100)
    @Column(name = "SUBJECTNAME")
    private String subjectname;
    @Size(max = 255)
    @Column(name = "DESCRIPTION")
    private String description;
   
    public Subject() {
    }

    public Subject(Integer subjectid) {
        this.subjectid = subjectid;
    }

    public Integer getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(Integer subjectid) {
        this.subjectid = subjectid;
    }

    public String getSubjectname() {
        return subjectname;
    }

    public void setSubjectname(String subjectname) {
        this.subjectname = subjectname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (subjectid != null ? subjectid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Subject)) {
            return false;
        }
        Subject other = (Subject) object;
        if ((this.subjectid == null && other.subjectid != null) || (this.subjectid != null && !this.subjectid.equals(other.subjectid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sis.model.Subject[ subjectid=" + subjectid + " ]";
    }
    
}
