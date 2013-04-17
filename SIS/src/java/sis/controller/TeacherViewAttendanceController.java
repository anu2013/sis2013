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
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
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
import javax.servlet.http.HttpServletRequest;
import sis.model.Attendancetracking;
import sis.model.StudentSchedule;
import sis.model.TeacherSchedule;

/**
 *
 * @author Anupama Karumudi
 */
@ManagedBean(name="teacherViewAttendanceController")
@ViewScoped
public class TeacherViewAttendanceController implements Serializable{
    @PersistenceUnit(unitName="SISPU")
    private EntityManagerFactory entityManagerFactory;
    
    @ManagedProperty("#{userController}")
    private UserController userController;
    
    @ManagedProperty("#{teacherSchedulesController}")
    private TeacherSchedulesController schedulesController;
    
    private List<StudentSchedule> studentSchedules = null;
    private Integer selectedScheduleId;
    private List<Attendancetracking> attendanceList = null;
    private transient List<List<String>> dataList;
    private transient HtmlPanelGroup dataTableGroup = null;
    
    @PostConstruct
    public void init(){
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String sid = request.getParameter("sid");
        if(null != sid){
            selectedScheduleId = Integer.parseInt(sid);
            if(null != selectedScheduleId && selectedScheduleId > 0) {
                populateAttendance();
            }
        }
    }

    private void populateAttendance(){
        try{
            dataTableGroup = null;
            setStudentSchedules(null);
            
            if(selectedScheduleId < 1){
                setErrorMessage("Please select a subject schedule to view the attendance.");
                return;
            }
            
            TeacherSchedule selSchedule = getSelectedSchedule();
                
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            String queryString = "select ss from StudentSchedule ss where ss.schedule.subjectscheduleid = :ssid";
            Query query = entityManager.createQuery(queryString);
            query.setParameter("ssid", selSchedule.getSubjectscheduleid());
            List<StudentSchedule> records = query.getResultList();
            if(records != null && records.size() > 0){
                setStudentSchedules(records);
                
                // get attendance list
                queryString = "select at from Attendancetracking at where at.subjectScheduleId = :subjectScheduleId and at.schoolyear = :schoolyear order by at.attendancedate asc";
                query = entityManager.createQuery(queryString);
                query.setParameter("subjectScheduleId", selSchedule.getSubjectscheduleid());
                query.setParameter("schoolyear", userController.getCurrentSchoolYear());
                attendanceList = query.getResultList();
                
                if(null != attendanceList && attendanceList.size() > 0){
                    Date prvDate = null;
                    int colLength = studentSchedules.size() + 1;
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
                                int studentIndex = -1;
                                for(int k=0; k<studentSchedules.size(); ++k){
                                    if(studentSchedules.get(k).getStudent().getStudentid() == attendance.getStudentId()){
                                        studentIndex = k;
                                        break;
                                    }
                                }
                                if(studentIndex >= 0){
                                    String val = "";
                                    Short flag = attendance.getAttendanceflag();
                                    if(null != flag){
                                        if( flag == 1)
                                                val = "<div class='present'></div>";
                                        else if( flag == 2)
                                                val = "<div class='absent'></div>";
                                    }
                                    row.set(studentIndex+1, val);
                                }
                            }
                        }
                    }
                    populateDataTable();
                }else{
                    setErrorMessage("There are no attendance records found for the selected subject.");
                }
            }else{
                setErrorMessage("There are no students enrolled in this class.");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void populateDataTable() {
        HtmlDataTable dataTable = new HtmlDataTable();
        dataTable.setValueExpression("value", createValueExpression("#{teacherViewAttendanceController.dataList}", List.class));
        dataTable.setVar("dataItem");
        dataTable.setBorder(1);
        dataTable.setCellpadding("7");
        dataTable.setWidth("100%");
        dataTable.setRowClasses("graybg,whitebg");

        for (int i = 0; i < dataList.get(0).size(); i++) {
            HtmlColumn column = new HtmlColumn();
            dataTable.getChildren().add(column);

            HtmlOutputText header = new HtmlOutputText();
            if(i == 0){
                header.setValue("Date");
            }else{
                header.setValue(studentSchedules.get(i-1).getStudent().getProfile().getFirstname() + " " + studentSchedules.get(i-1).getStudent().getProfile().getLastname());
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
     
    public String goButton() {
        try{
            if (null != selectedScheduleId && selectedScheduleId > 0) {
                return "viewAttendance?faces-redirect=true&sid=" + selectedScheduleId;
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Please select a subject schedule!"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    protected void setInfoMessage(String summary) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
    }

    protected void setErrorMessage(String summary) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null));
    }
    
    public List<StudentSchedule> getStudentSchedules() {
        return studentSchedules;
    }

    public void setStudentSchedules(List<StudentSchedule> schedules) {
        this.studentSchedules = schedules;
    }
    
    public UserController getUserController() {
        return userController;
    }

    public void setUserController(UserController controller) {
        this.userController = controller;
    }
    
    public TeacherSchedulesController getSchedulesController() {
        return schedulesController;
    }

    public void setSchedulesController(TeacherSchedulesController controller) {
        this.schedulesController = controller;
    }
    
    public Integer getSelectedScheduleId() {
        return selectedScheduleId;
    }

    public void setSelectedScheduleId(Integer scheduleId) {
        this.selectedScheduleId = scheduleId;
    }
            
    public TeacherSchedule getSelectedSchedule() {
        return schedulesController.getScheduleById(selectedScheduleId);
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
}
