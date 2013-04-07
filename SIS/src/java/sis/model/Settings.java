/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.model;

import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author Veekija
 */
@ManagedBean
@Entity
@Table(name = "SETTINGS")
public class Settings {

    private static long serialVersionUID = 1L;

    /**
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * @param aSerialVersionUID the serialVersionUID to set
     */
    public static void setSerialVersionUID(long aSerialVersionUID) {
        serialVersionUID = aSerialVersionUID;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SETTINGID")
    private Integer settingId;

    @Size(max = 100)
    @Column(name = "SETTINGNAME")
    private String settingName;
    
    @Lob
    @Column(name = "SETTINGVALUE")
    private String SettingValue;
    
    @Column(name = "LASTUPDATEDDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdatedDate;

    /**
     * @return the settingId
     */
    public Integer getSettingId() {
        return settingId;
    }

    /**
     * @param settingId the settingId to set
     */
    public void setSettingId(Integer settingId) {
        this.settingId = settingId;
    }

    /**
     * @return the settingName
     */
    public String getSettingName() {
        return settingName;
    }

    /**
     * @param settingName the settingName to set
     */
    public void setSettingName(String settingName) {
        this.settingName = settingName;
    }

    /**
     * @return the SettingValue
     */
    public String getSettingValue() {
        return SettingValue;
    }

    /**
     * @param SettingValue the SettingValue to set
     */
    public void setSettingValue(String SettingValue) {
        this.SettingValue = SettingValue;
    }

    /**
     * @return the lastUpdatedDate
     */
    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    /**
     * @param lastUpdatedDate the lastUpdatedDate to set
     */
    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }
    
    
}
