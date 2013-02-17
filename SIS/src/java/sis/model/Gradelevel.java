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
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Anupama Karumudi
 */
@ManagedBean
@Entity
@Table(name = "GRADELEVEL")
public class Gradelevel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "GRADELEVELID")
    private Integer gradelevelid;
    @Size(max = 255)
    @Column(name = "GRADELEVEL")
    private String gradelevel;
    @Size(max = 255)
    @Column(name = "DESCRIPTION")
    private String description;

    public Gradelevel() {
    }

    public Gradelevel(Integer gradelevelid) {
        this.gradelevelid = gradelevelid;
    }

    public Integer getGradelevelid() {
        return gradelevelid;
    }

    public void setGradelevelid(Integer gradelevelid) {
        this.gradelevelid = gradelevelid;
    }

    public String getGradelevel() {
        return gradelevel;
    }

    public void setGradelevel(String gradelevel) {
        this.gradelevel = gradelevel;
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
        hash += (gradelevelid != null ? gradelevelid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Gradelevel)) {
            return false;
        }
        Gradelevel other = (Gradelevel) object;
        if ((this.gradelevelid == null && other.gradelevelid != null) || (this.gradelevelid != null && !this.gradelevelid.equals(other.gradelevelid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sis.model.Gradelevel[ gradelevelid=" + gradelevelid + " ]";
    }
    
}
