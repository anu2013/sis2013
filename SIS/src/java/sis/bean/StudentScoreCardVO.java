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
    private String gradeletter="";
    private String subject;

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
    public String getSubject() {
        return subject;
    }

    /**
     * @param comments the comments to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
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
