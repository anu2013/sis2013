/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.bean;

/**
 *
 * @author Veekija
 */
public class TeacherScheduleVO {
    private Integer teacherId;
    private String firstName;
    private String lastName;
    private String periodName;
    private String periodStart;
    private String periodEnd;
    private boolean primary;

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
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
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
     * @return the primary
     */
    public boolean isPrimary() {
        return primary;
    }

    /**
     * @param primary the primary to set
     */
    public void setPrimary(boolean primary) {
        this.primary = primary;
    }
    
    
}
