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
public class StudentScoreCardVO {
    private Integer studentid;
    private String firstName;
    private String lastName;
    private String finalscore="";
    private String comments="";
    private String status="";
    private String gradeletter="";

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
     * @return the finalscore
     */
    public String getFinalscore() {
        return finalscore;
    }

    /**
     * @param finalscore the finalscore to set
     */
    public void setFinalscore(String finalscore) {
        this.finalscore = finalscore;
    }

    /**
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the gradeletter
     */
    public String getGradeletter() {
        return gradeletter;
    }

    /**
     * @param gradeletter the gradeletter to set
     */
    public void setGradeletter(String gradeletter) {
        this.gradeletter = gradeletter;
    }
    
    
    
}
