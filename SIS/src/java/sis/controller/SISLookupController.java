/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.controller;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Veekija
 */
@ManagedBean
public class SISLookupController {

    private List<String> timings;

    @PostConstruct
    public void init() {
        populateTimings();
    }

    private void populateTimings() {
        timings = new ArrayList<String>();
        timings.add("08:00 AM");
        timings.add("08:30 AM");
        timings.add("09:00 AM");
        timings.add("09:30 AM");
        timings.add("10:00 AM");
        timings.add("10:30 AM");
        timings.add("11:00 AM");
        timings.add("11:30 AM");
        timings.add("12:00 PM");
        timings.add("12:30 PM");
        timings.add("01:00 PM");
        timings.add("01:30 PM");
        timings.add("02:00 PM");
        timings.add("02:30 PM");
        timings.add("03:00 PM");
        timings.add("03:30 PM");
        timings.add("04:00 PM");
        timings.add("04:30 PM");
        timings.add("05:00 PM");
        setTimings(timings);
    }

    /**
     * @return the timings
     */
    public List<String> getTimings() {
        return timings;
    }

    /**
     * @param timings the timings to set
     */
    public void setTimings(List<String> timings) {
        this.timings = timings;
    }
    
    public void populatePeriodTimings(){
        populateTimings();
    }
}
