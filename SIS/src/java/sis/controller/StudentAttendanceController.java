/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.controller;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.el.ValueExpression;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlColumn;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import sis.model.Attendancetracking;
import sis.model.StudentSchedule;

/**
 *
 * @author Anupama Karumudi
 */
@ManagedBean(name="studentAttendanceController")
@ViewScoped
public class StudentAttendanceController implements Serializable {
    @PersistenceUnit(unitName="SISPU")
    private EntityManagerFactory entityManagerFactory;
    
    @ManagedProperty("#{userController}")
    private UserController userController;
    
    private List<StudentSchedule> schedules = null;
    private List<Attendancetracking> attendanceList = null;

    private List<List<String>> dataList;
    private HtmlPanelGroup dataTableGroup;

    private Date fromDate;
    private Date toDate;
    
    @PostConstruct
    public void init(){
        if(null == toDate)
            toDate = new Date();
        if(null == fromDate){
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -1);
            fromDate = calendar.getTime();
        }
        populateAttendance();
    }

    private void populateAttendance(){
        try{
            dataTableGroup = null;
            
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            String queryString = "select ss from StudentSchedule ss where ss.student.studentid = :studentid and ss.schedule.schoolyear.schoolyear = :schoolyear order by ss.schedule.subject.subjectname asc";
            Query query = entityManager.createQuery(queryString);
            query.setParameter("studentid", userController.getUser().getUserid());
            query.setParameter("schoolyear", userController.getCurrentSchoolYear());
            schedules = query.getResultList();
            if(schedules != null && schedules.size() > 0){
                queryString = "select at from Attendancetracking at where at.studentId = :studentid and at.schoolyear = :schoolyear and at.attendancedate between :fromDate and :toDate order by at.attendancedate asc";
                query = entityManager.createQuery(queryString);
                query.setParameter("studentid", userController.getUser().getUserid());
                query.setParameter("schoolyear", userController.getCurrentSchoolYear());
                query.setParameter("fromDate", fromDate);
                query.setParameter("toDate", toDate);
                attendanceList = query.getResultList();
                
                if(null != attendanceList && attendanceList.size() > 0){
                    Date prvDate = null;
                    int colLength = schedules.size() + 1;
                    dataList = new ArrayList<List<String>>();
                    DateFormat df = new SimpleDateFormat("EEE, MMM dd, yyyy");
                    for(int i=0; i<attendanceList.size(); ++i){
                        Attendancetracking attendance = attendanceList.get(i);
                        if(null != attendance.getAttendancedate()){
                            if(null == prvDate || prvDate.compareTo(attendance.getAttendancedate()) != 0){
                                String[] rowData = new String[colLength];
                                for(int j=0; j<rowData.length; ++j){
                                    rowData[j] = "";
                                }
                                rowData[0] =  df.format(attendance.getAttendancedate());
                                dataList.add(Arrays.asList(rowData));
                                prvDate = attendance.getAttendancedate();
                            }
                            List<String> row = dataList.get(dataList.size() - 1);
                            if(null != row){
                                int subjectIndex = -1;
                                for(int k=0; k<schedules.size(); ++k){
                                    if(schedules.get(k).getSchedule().getSubjectscheduleid() == attendance.getSubjectScheduleId()){
                                        subjectIndex = k;
                                        break;
                                    }
                                }
                                if(subjectIndex >= 0){
                                    String val = "";
                                    Short flag = attendance.getAttendanceflag();
                                    if(null != flag){
                                        if( flag == 1)
                                                val = "<div class='present'></div>";
                                        else if( flag == 2)
                                                val = "<div class='absent'></div>";
                                    }
                                    row.set(subjectIndex+1, val);
                                }
                            }
                        }
                    }
                    populateDataTable();
                }else{
                    setErrorMessage("There are no attendance records found for the selected data range.");
                }
            }else{
                setErrorMessage("There are no schedules found. You did not enroll for classes.");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void populateDataTable() {
        HtmlDataTable dataTable = new HtmlDataTable();
        dataTable.setValueExpression("value", createValueExpression("#{studentAttendanceController.dataList}", List.class));
        dataTable.setVar("dataItem");
        dataTable.setBorder(1);
        dataTable.setCellpadding("7");
        dataTable.setWidth("900");
        dataTable.setRowClasses("graybg,whitebg");

        for (int i = 0; i < dataList.get(0).size(); i++) {
            HtmlColumn column = new HtmlColumn();
            dataTable.getChildren().add(column);

            HtmlOutputText header = new HtmlOutputText();
            if(i == 0){
                header.setValue("Date");
            }else{
                header.setValue(schedules.get(i-1).getSchedule().getSubject().getSubjectname());
            }
            column.setHeader(header);

            HtmlOutputText output = new HtmlOutputText();
            output.setValueExpression("value",
                createValueExpression("#{dataItem[" + i + "]}", String.class));
            output.setEscape(false);
            column.getChildren().add(output);
        }

        dataTableGroup = new HtmlPanelGroup();
        dataTableGroup.getChildren().add(dataTable);
    }

    private ValueExpression createValueExpression(String valueExpression, Class<?> valueType) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        return facesContext.getApplication().getExpressionFactory().createValueExpression(facesContext.getELContext(), valueExpression, valueType);
    }
   
    public String goButton(){
        populateAttendance();
        return null;
    }
    
    protected void setInfoMessage(String summary) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
    }

    protected void setErrorMessage(String summary) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null));
    }

    public UserController getUserController() {
        return userController;
    }

    public void setUserController(UserController controller) {
        this.userController = controller;
    }
	
    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date date) {
        this.fromDate = date;
    }
	
    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date date) {
        this.toDate = date;
    }

    public List<List<String>> getDataList() {
        return dataList;
    }

    public HtmlPanelGroup getDataTableGroup() {
        return dataTableGroup;
    }

    public void setDataTableGroup(HtmlPanelGroup group) {
        this.dataTableGroup = group;
    }
    
    public List<StudentSchedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<StudentSchedule> schedules) {
        this.schedules = schedules;
    }
    
}
