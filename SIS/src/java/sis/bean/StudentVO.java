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


     
    
}
