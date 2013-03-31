/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.bean;

import java.util.Date;
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
    
    
}
