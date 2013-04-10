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
public class StateReportVO {
    private String subGroupName;
    private int totalNumberOfStudents_Maths;
    private int numberOfStudentsTested_Maths;
    private double percentageOfStudentsTested_Maths;
    private double percentageOfStudentsScoredAtBasicLevel_Maths;
    private double percentageOfStudentsScoredAtProficentLevel_Maths;
    private double percentageOfStudentsScoredAtAdvanceLevel_Maths;
    private int totalNumberOfStudents_Reading;
    private int numberOfStudentsTested_Reading;
    private double percentageOfStudentsTested_Reading;
    private double percentageOfStudentsScoredAtBasicLevel_Reading;
    private double percentageOfStudentsScoredAtProficentLevel_Reading;
    private double percentageOfStudentsScoredAtAdvanceLevel_Reading;

    /**
     * @return the subGroupName
     */
    public String getSubGroupName() {
        return subGroupName;
    }

    /**
     * @param subGroupName the subGroupName to set
     */
    public void setSubGroupName(String subGroupName) {
        this.subGroupName = subGroupName;
    }

    /**
     * @return the totalNumberOfStudents_Maths
     */
    public int getTotalNumberOfStudents_Maths() {
        return totalNumberOfStudents_Maths;
    }

    /**
     * @param totalNumberOfStudents_Maths the totalNumberOfStudents_Maths to set
     */
    public void setTotalNumberOfStudents_Maths(int totalNumberOfStudents_Maths) {
        this.totalNumberOfStudents_Maths = totalNumberOfStudents_Maths;
    }

    /**
     * @return the numberOfStudentsTested_Maths
     */
    public int getNumberOfStudentsTested_Maths() {
        return numberOfStudentsTested_Maths;
    }

    /**
     * @param numberOfStudentsTested_Maths the numberOfStudentsTested_Maths to set
     */
    public void setNumberOfStudentsTested_Maths(int numberOfStudentsTested_Maths) {
        this.numberOfStudentsTested_Maths = numberOfStudentsTested_Maths;
    }

    /**
     * @return the percentageOfStudentsTested_Maths
     */
    public double getPercentageOfStudentsTested_Maths() {
        return percentageOfStudentsTested_Maths;
    }

    /**
     * @param percentageOfStudentsTested_Maths the percentageOfStudentsTested_Maths to set
     */
    public void setPercentageOfStudentsTested_Maths(double percentageOfStudentsTested_Maths) {
        this.percentageOfStudentsTested_Maths = percentageOfStudentsTested_Maths;
    }

    /**
     * @return the percentageOfStudentsScoredAtBasicLevel_Maths
     */
    public double getPercentageOfStudentsScoredAtBasicLevel_Maths() {
        return percentageOfStudentsScoredAtBasicLevel_Maths;
    }

    /**
     * @param percentageOfStudentsScoredAtBasicLevel_Maths the percentageOfStudentsScoredAtBasicLevel_Maths to set
     */
    public void setPercentageOfStudentsScoredAtBasicLevel_Maths(double percentageOfStudentsScoredAtBasicLevel_Maths) {
        this.percentageOfStudentsScoredAtBasicLevel_Maths = percentageOfStudentsScoredAtBasicLevel_Maths;
    }

    /**
     * @return the percentageOfStudentsScoredAtProficentLevel_Maths
     */
    public double getPercentageOfStudentsScoredAtProficentLevel_Maths() {
        return percentageOfStudentsScoredAtProficentLevel_Maths;
    }

    /**
     * @param percentageOfStudentsScoredAtProficentLevel_Maths the percentageOfStudentsScoredAtProficentLevel_Maths to set
     */
    public void setPercentageOfStudentsScoredAtProficentLevel_Maths(double percentageOfStudentsScoredAtProficentLevel_Maths) {
        this.percentageOfStudentsScoredAtProficentLevel_Maths = percentageOfStudentsScoredAtProficentLevel_Maths;
    }

    /**
     * @return the percentageOfStudentsScoredAtAdvanceLevel_Maths
     */
    public double getPercentageOfStudentsScoredAtAdvanceLevel_Maths() {
        return percentageOfStudentsScoredAtAdvanceLevel_Maths;
    }

    /**
     * @param percentageOfStudentsScoredAtAdvanceLevel_Maths the percentageOfStudentsScoredAtAdvanceLevel_Maths to set
     */
    public void setPercentageOfStudentsScoredAtAdvanceLevel_Maths(double percentageOfStudentsScoredAtAdvanceLevel_Maths) {
        this.percentageOfStudentsScoredAtAdvanceLevel_Maths = percentageOfStudentsScoredAtAdvanceLevel_Maths;
    }

    /**
     * @return the totalNumberOfStudents_Reading
     */
    public int getTotalNumberOfStudents_Reading() {
        return totalNumberOfStudents_Reading;
    }

    /**
     * @param totalNumberOfStudents_Reading the totalNumberOfStudents_Reading to set
     */
    public void setTotalNumberOfStudents_Reading(int totalNumberOfStudents_Reading) {
        this.totalNumberOfStudents_Reading = totalNumberOfStudents_Reading;
    }

    /**
     * @return the numberOfStudentsTested_Reading
     */
    public int getNumberOfStudentsTested_Reading() {
        return numberOfStudentsTested_Reading;
    }

    /**
     * @param numberOfStudentsTested_Reading the numberOfStudentsTested_Reading to set
     */
    public void setNumberOfStudentsTested_Reading(int numberOfStudentsTested_Reading) {
        this.numberOfStudentsTested_Reading = numberOfStudentsTested_Reading;
    }

    /**
     * @return the percentageOfStudentsTested_Reading
     */
    public double getPercentageOfStudentsTested_Reading() {
        return percentageOfStudentsTested_Reading;
    }

    /**
     * @param percentageOfStudentsTested_Reading the percentageOfStudentsTested_Reading to set
     */
    public void setPercentageOfStudentsTested_Reading(double percentageOfStudentsTested_Reading) {
        this.percentageOfStudentsTested_Reading = percentageOfStudentsTested_Reading;
    }

    /**
     * @return the percentageOfStudentsScoredAtBasicLevel_Reading
     */
    public double getPercentageOfStudentsScoredAtBasicLevel_Reading() {
        return percentageOfStudentsScoredAtBasicLevel_Reading;
    }

    /**
     * @param percentageOfStudentsScoredAtBasicLevel_Reading the percentageOfStudentsScoredAtBasicLevel_Reading to set
     */
    public void setPercentageOfStudentsScoredAtBasicLevel_Reading(double percentageOfStudentsScoredAtBasicLevel_Reading) {
        this.percentageOfStudentsScoredAtBasicLevel_Reading = percentageOfStudentsScoredAtBasicLevel_Reading;
    }

    /**
     * @return the percentageOfStudentsScoredAtProficentLevel_Reading
     */
    public double getPercentageOfStudentsScoredAtProficentLevel_Reading() {
        return percentageOfStudentsScoredAtProficentLevel_Reading;
    }

    /**
     * @param percentageOfStudentsScoredAtProficentLevel_Reading the percentageOfStudentsScoredAtProficentLevel_Reading to set
     */
    public void setPercentageOfStudentsScoredAtProficentLevel_Reading(double percentageOfStudentsScoredAtProficentLevel_Reading) {
        this.percentageOfStudentsScoredAtProficentLevel_Reading = percentageOfStudentsScoredAtProficentLevel_Reading;
    }

    /**
     * @return the percentageOfStudentsScoredAtAdvanceLevel_Reading
     */
    public double getPercentageOfStudentsScoredAtAdvanceLevel_Reading() {
        return percentageOfStudentsScoredAtAdvanceLevel_Reading;
    }

    /**
     * @param percentageOfStudentsScoredAtAdvanceLevel_Reading the percentageOfStudentsScoredAtAdvanceLevel_Reading to set
     */
    public void setPercentageOfStudentsScoredAtAdvanceLevel_Reading(double percentageOfStudentsScoredAtAdvanceLevel_Reading) {
        this.percentageOfStudentsScoredAtAdvanceLevel_Reading = percentageOfStudentsScoredAtAdvanceLevel_Reading;
    }
    
    
    
}
