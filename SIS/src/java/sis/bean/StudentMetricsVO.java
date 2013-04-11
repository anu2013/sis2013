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
public class StudentMetricsVO {
    private int totalNumberOfStudents;
    private int numberOfStudentsTested;
    private double percentageOfStudentsTested;
    private double percentageOfStudentsScoredAtBasicLevel;
    private double percentageOfStudentsScoredAtProficentLevel;
    private double percentageOfStudentsScoredAtAdvanceLevel;

    /**
     * @return the totalNumberOfStudents
     */
    public int getTotalNumberOfStudents() {
        return totalNumberOfStudents;
    }

    /**
     * @param totalNumberOfStudents the totalNumberOfStudents to set
     */
    public void setTotalNumberOfStudents(int totalNumberOfStudents) {
        this.totalNumberOfStudents = totalNumberOfStudents;
    }

    /**
     * @return the numberOfStudentsTested
     */
    public int getNumberOfStudentsTested() {
        return numberOfStudentsTested;
    }

    /**
     * @param numberOfStudentsTested the numberOfStudentsTested to set
     */
    public void setNumberOfStudentsTested(int numberOfStudentsTested) {
        this.numberOfStudentsTested = numberOfStudentsTested;
    }

    /**
     * @return the percentageOfStudentsTested
     */
    public double getPercentageOfStudentsTested() {
        return percentageOfStudentsTested;
    }

    /**
     * @param percentageOfStudentsTested the percentageOfStudentsTested to set
     */
    public void setPercentageOfStudentsTested(double percentageOfStudentsTested) {
        this.percentageOfStudentsTested = percentageOfStudentsTested;
    }

    /**
     * @return the percentageOfStudentsScoredAtBasicLevel
     */
    public double getPercentageOfStudentsScoredAtBasicLevel() {
        return percentageOfStudentsScoredAtBasicLevel;
    }

    /**
     * @param percentageOfStudentsScoredAtBasicLevel the percentageOfStudentsScoredAtBasicLevel to set
     */
    public void setPercentageOfStudentsScoredAtBasicLevel(double percentageOfStudentsScoredAtBasicLevel) {
        this.percentageOfStudentsScoredAtBasicLevel = percentageOfStudentsScoredAtBasicLevel;
    }

    /**
     * @return the percentageOfStudentsScoredAtProficentLevel
     */
    public double getPercentageOfStudentsScoredAtProficentLevel() {
        return percentageOfStudentsScoredAtProficentLevel;
    }

    /**
     * @param percentageOfStudentsScoredAtProficentLevel the percentageOfStudentsScoredAtProficentLevel to set
     */
    public void setPercentageOfStudentsScoredAtProficentLevel(double percentageOfStudentsScoredAtProficentLevel) {
        this.percentageOfStudentsScoredAtProficentLevel = percentageOfStudentsScoredAtProficentLevel;
    }

    /**
     * @return the percentageOfStudentsScoredAtAdvanceLevel
     */
    public double getPercentageOfStudentsScoredAtAdvanceLevel() {
        return percentageOfStudentsScoredAtAdvanceLevel;
    }

    /**
     * @param percentageOfStudentsScoredAtAdvanceLevel the percentageOfStudentsScoredAtAdvanceLevel to set
     */
    public void setPercentageOfStudentsScoredAtAdvanceLevel(double percentageOfStudentsScoredAtAdvanceLevel) {
        this.percentageOfStudentsScoredAtAdvanceLevel = percentageOfStudentsScoredAtAdvanceLevel;
    }
    
    
}
