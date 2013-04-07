/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.bean;

import javax.faces.bean.ManagedBean;

/**
 *
 * @author Veekija
 */
@ManagedBean
public class TeacherScheduleVO {
    private Integer teacherId;
    private Integer subjectScheduleId;
    private String name;
    private String scheduleName;
    private String periodName;
    private String periodStart;
    private String periodEnd;
    private String scheduleType;
    private String scheduleDays;
    private boolean selected;

    /**
     * @return the teacherId
     */
    public Integer getTeacherId() {
        return teacherId;
    }

    /**
     * @param teacherId the teacherId to set
     */
    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

   

    /**
     * @return the periodName
     */
    public String getPeriodName() {
        return periodName;
    }

    /**
     * @param periodName the periodName to set
     */
    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }

    /**
     * @return the periodStart
     */
    public String getPeriodStart() {
        return periodStart;
    }

    /**
     * @param periodStart the periodStart to set
     */
    public void setPeriodStart(String periodStart) {
        this.periodStart = periodStart;
    }

    /**
     * @return the periodEnd
     */
    public String getPeriodEnd() {
        return periodEnd;
    }

    /**
     * @param periodEnd the periodEnd to set
     */
    public void setPeriodEnd(String periodEnd) {
        this.periodEnd = periodEnd;
    }

    /**
     * @return the scheduleType
     */
    public String getScheduleType() {
        return scheduleType;
    }

    /**
     * @param scheduleType the scheduleType to set
     */
    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

    /**
     * @return the scheduleDays
     */
    public String getScheduleDays() {
        return scheduleDays;
    }

    /**
     * @param scheduleDays the scheduleDays to set
     */
    public void setScheduleDays(String scheduleDays) {
        this.scheduleDays = scheduleDays;
    }

    /**
     * @return the subjectScheduleId
     */
    public Integer getSubjectScheduleId() {
        return subjectScheduleId;
    }

    /**
     * @param subjectScheduleId the subjectScheduleId to set
     */
    public void setSubjectScheduleId(Integer subjectScheduleId) {
        this.subjectScheduleId = subjectScheduleId;
    }

    /**
     * @return the selected
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * @param selected the selected to set
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * @return the scheduleName
     */
    public String getScheduleName() {
        return scheduleName;
    }

    /**
     * @param scheduleName the scheduleName to set
     */
    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
    }

    
}
