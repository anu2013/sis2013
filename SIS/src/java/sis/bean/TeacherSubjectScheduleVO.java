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
public class TeacherSubjectScheduleVO {
    private Integer subjectScheduleId;
    private String scheduleName;
    private Integer schoolYear;
    private Integer periodId;
    private String periodName;
    private String periodStart;
    private String periodEnd;
    private Integer primaryTeacherId;
    private String primaryTeacherName;
    private Integer secondaryTeacherId;
    private String secondaryTeacherName;
    private Integer subjectId;
    private String subjectName;
    private String scheduleDays;

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

    /**
     * @return the schoolYear
     */
    public Integer getSchoolYear() {
        return schoolYear;
    }

    /**
     * @param schoolYear the schoolYear to set
     */
    public void setSchoolYear(Integer schoolYear) {
        this.schoolYear = schoolYear;
    }

    /**
     * @return the periodId
     */
    public Integer getPeriodId() {
        return periodId;
    }

    /**
     * @param periodId the periodId to set
     */
    public void setPeriodId(Integer periodId) {
        this.periodId = periodId;
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
     * @return the primaryTeacherId
     */
    public Integer getPrimaryTeacherId() {
        return primaryTeacherId;
    }

    /**
     * @param primaryTeacherId the primaryTeacherId to set
     */
    public void setPrimaryTeacherId(Integer primaryTeacherId) {
        this.primaryTeacherId = primaryTeacherId;
    }

    /**
     * @return the primaryTeacherName
     */
    public String getPrimaryTeacherName() {
        return primaryTeacherName;
    }

    /**
     * @param primaryTeacherName the primaryTeacherName to set
     */
    public void setPrimaryTeacherName(String primaryTeacherName) {
        this.primaryTeacherName = primaryTeacherName;
    }

    /**
     * @return the secondaryTeacherId
     */
    public Integer getSecondaryTeacherId() {
        return secondaryTeacherId;
    }

    /**
     * @param secondaryTeacherId the secondaryTeacherId to set
     */
    public void setSecondaryTeacherId(Integer secondaryTeacherId) {
        this.secondaryTeacherId = secondaryTeacherId;
    }

    /**
     * @return the secondaryTeacherName
     */
    public String getSecondaryTeacherName() {
        return secondaryTeacherName;
    }

    /**
     * @param secondaryTeacherName the secondaryTeacherName to set
     */
    public void setSecondaryTeacherName(String secondaryTeacherName) {
        this.secondaryTeacherName = secondaryTeacherName;
    }

    /**
     * @return the subjectId
     */
    public Integer getSubjectId() {
        return subjectId;
    }

    /**
     * @param subjectId the subjectId to set
     */
    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    /**
     * @return the subjectName
     */
    public String getSubjectName() {
        return subjectName;
    }

    /**
     * @param subjectName the subjectName to set
     */
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
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
    
    
    
}
