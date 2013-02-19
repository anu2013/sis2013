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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "USERS")
public class Users implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "USERID")
    private Integer userid;
    @Size(max = 100)
    @Column(name = "USERLOGINNAME")
    private String userloginname;
    @Size(max = 100)
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "CREATEDDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createddate;
    @Column(name = "ENDDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date enddate;
    @Column(name = "ACTIVE")
    private Short active;
    @Column(name = "CREATEDBY")
    private Integer createdby;
    @Column(name = "LASTUPDATEDBY")
    private Integer lastupdatedby;
    @Column(name = "LASTUPDATEDDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastupdateddate;
    @JoinColumn(name = "ROLEID", referencedColumnName = "ROLEID")
    @ManyToOne
    private Role role;
    
    public Users() {
    }

    public Users(Integer userid) {
        this.userid = userid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUserloginname() {
        return userloginname;
    }

    public void setUserloginname(String userloginname) {
        this.userloginname = userloginname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public Short getActive() {
        return active;
    }

    public void setActive(Short active) {
        this.active = active;
    }

    public Integer getCreatedby() {
        return createdby;
    }

    public void setCreatedby(Integer createdby) {
        this.createdby = createdby;
    }

    public Integer getLastupdatedby() {
        return lastupdatedby;
    }

    public void setLastupdatedby(Integer lastupdatedby) {
        this.lastupdatedby = lastupdatedby;
    }

    public Date getLastupdateddate() {
        return lastupdateddate;
    }

    public void setLastupdateddate(Date lastupdateddate) {
        this.lastupdateddate = lastupdateddate;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    
    public Boolean getIsAdmin() {
        if(null != role){
            String roleName = role.getRolename();
            if(null != roleName && roleName.equals("admin"))
                return true;
        }
        return false;
    }
    
    public Boolean getIsTeacher() {
        if(null != role){
            String roleName = role.getRolename();
            if(null != roleName && roleName.equals("teacher"))
                return true;
        }
        return false;
    }
      
    public Boolean getIsStudent() {
        if(null != role){
            String roleName = role.getRolename();
            if(null != roleName && roleName.equals("student"))
                return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userid != null ? userid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.userid == null && other.userid != null) || (this.userid != null && !this.userid.equals(other.userid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sis.model.Users[ userid=" + userid + " ]";
    }
    
}
