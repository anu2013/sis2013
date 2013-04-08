/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.bean;

import java.util.List;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Veekija
 */
@ManagedBean
public class StudentVO { 
    private Integer studentid;
    private Integer studentgradelevelid;
    private String firstName;
    private String lastName;
    private boolean selected;
    private List<TeacherScheduleVO> subjectSchedules;
    private String mondaySchedule;
    private String tuesdaySchedule;
    private String wednesdaySchedule;
    private String thursdaySchedule;
    private String fridaySchedule;

    /**
     * @return the studentid
     */
    public Integer getStudentid() {
        return studentid;
    }

    /**
     * @param studentid the studentid to set
     */
    public void setStudentid(Integer studentid) {
        this.studentid = studentid;
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
     * @return the studentgradelevelid
     */
    public Integer getStudentgradelevelid() {
        return studentgradelevelid;
    }

    /**
     * @param studentgradelevelid the studentgradelevelid to set
     */
    public void setStudentgradelevelid(Integer studentgradelevelid) {
        this.studentgradelevelid = studentgradelevelid;
    }

    /**
     * @return the subjectSchedules
     */
    public List<TeacherScheduleVO> getSubjectSchedules() {
        return subjectSchedules;
    }

    /**
     * @param subjectSchedules the subjectSchedules to set
     */
    public void setSubjectSchedules(List<TeacherScheduleVO> subjectSchedules) {
        this.subjectSchedules = subjectSchedules;
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
     * @return the mondaySchedule
     */
    public String getMondaySchedule() {
        return mondaySchedule;
    }

    /**
     * @param mondaySchedule the mondaySchedule to set
     */
    public void setMondaySchedule(String mondaySchedule) {
        this.mondaySchedule = mondaySchedule;
    }

    /**
     * @return the tuesdaySchedule
     */
    public String getTuesdaySchedule() {
        return tuesdaySchedule;
    }

    /**
     * @param tuesdaySchedule the tuesdaySchedule to set
     */
    public void setTuesdaySchedule(String tuesdaySchedule) {
        this.tuesdaySchedule = tuesdaySchedule;
    }

    /**
     * @return the wednesdaySchedule
     */
    public String getWednesdaySchedule() {
        return wednesdaySchedule;
    }

    /**
     * @param wednesdaySchedule the wednesdaySchedule to set
     */
    public void setWednesdaySchedule(String wednesdaySchedule) {
        this.wednesdaySchedule = wednesdaySchedule;
    }

    /**
     * @return the thursdaySchedule
     */
    public String getThursdaySchedule() {
        return thursdaySchedule;
    }

    /**
     * @param thursdaySchedule the thursdaySchedule to set
     */
    public void setThursdaySchedule(String thursdaySchedule) {
        this.thursdaySchedule = thursdaySchedule;
    }

    /**
     * @return the fridaySchedule
     */
    public String getFridaySchedule() {
        return fridaySchedule;
    }

    /**
     * @param fridaySchedule the fridaySchedule to set
     */
    public void setFridaySchedule(String fridaySchedule) {
        this.fridaySchedule = fridaySchedule;
    }


     
    
}
