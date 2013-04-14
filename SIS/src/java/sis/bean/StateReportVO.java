/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.bean;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Veekija
 */
@ManagedBean
public class StateReportVO {
    private String subGroupName;
    private List<StudentMetricsVO> studentMetricsVOs;
    //private List<Integer> studentMetrics = new ArrayList<Integer>();
    
    
    
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
     * @return the studentMetrics
     */
//    public List<Integer> getStudentMetrics() {
//        return studentMetrics;
//    }
//
//    /**
//     * @param studentMetrics the studentMetrics to set
//     */
//    public void setStudentMetrics(List<Integer> studentMetrics) {
//        this.studentMetrics = studentMetrics;
//    }

    /**
     * @return the studentMetricsVOs
     */
    public List<StudentMetricsVO> getStudentMetricsVOs() {
        return studentMetricsVOs;
    }

    /**
     * @param studentMetricsVOs the studentMetricsVOs to set
     */
    public void setStudentMetricsVOs(List<StudentMetricsVO> studentMetricsVOs) {
        this.studentMetricsVOs = studentMetricsVOs;
    }
    
    
    
}
