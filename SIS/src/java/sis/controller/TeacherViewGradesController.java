/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
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
import sis.model.StudentSchedule;
import sis.model.Studentscorecard;
import sis.model.TeacherSchedule;

/**
 *
 * @author Anupama Karumudi
 */
@ManagedBean(name="teacherViewGradesController")
@ViewScoped
public class TeacherViewGradesController implements Serializable{
    @PersistenceUnit(unitName="SISPU")
    private EntityManagerFactory entityManagerFactory;
    
    @ManagedProperty("#{userController}")
    private UserController userController;
    
    @ManagedProperty("#{teacherSchedulesController}")
    private TeacherSchedulesController schedulesController;
    
    private List<StudentSchedule> studentSchedules = null;
    private transient List<List<String>> dataList;
    private transient HtmlPanelGroup dataTableGroup = null;
    
    @PostConstruct
    public void init(){
        populateGrades();
    }

    private void populateGrades(){
        try{
            dataTableGroup = null;
            setStudentSchedules(null);
            
            List<TeacherSchedule> schedules = schedulesController.getSchedules();
            if(null == schedules || schedules.size() < 1){
                setErrorMessage("There are no schedules found.");
                return;
            }
            
            Integer[] ssids = new Integer[schedules.size()];
            Integer[] subjectIds = new Integer[schedules.size()];
            for(int i=0; i<schedules.size(); ++i){
                ssids[i] = schedules.get(i).getSubjectscheduleid();
                subjectIds[i] = schedules.get(i).getSubject().getSubjectid();
            }
                
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            String queryString = "select ss from StudentSchedule ss where ss.schedule.subjectscheduleid IN :ssids";
            Query query = entityManager.createQuery(queryString);
            query.setParameter("ssids", Arrays.asList(ssids)); 
            List<StudentSchedule> records = query.getResultList();
            if(records != null && records.size() > 0){
                setStudentSchedules(records);
                
                Integer[] studentIds = new Integer[records.size()];
                for(int i=0; i<records.size(); ++i){
                    studentIds[i] = records.get(i).getStudent().getStudentid();
                }
                
                 // Get StudentScoreCard Record
                queryString = "select sc from Studentscorecard sc where sc.studentId IN :studentids and sc.subjectid IN :subjectids and sc.schoolyear = :schoolyear";
                query = entityManager.createQuery(queryString);
                query.setParameter("studentids",  Arrays.asList(studentIds)); 
                query.setParameter("subjectids",  Arrays.asList(subjectIds)); 
                query.setParameter("schoolyear", userController.getCurrentSchoolYear());
                List<Studentscorecard> scorecards = query.getResultList();
                    
                int prvStudent = 0;
                int colLength = schedules.size() + 1;
                dataList = new ArrayList<List<String>>();
                for(int i=0; i<studentSchedules.size(); ++i){
                    StudentSchedule ss = studentSchedules.get(i);
                    if(null != ss.getStudent()){
                        if(prvStudent != ss.getStudent().getStudentid()){
                            String[] rowData = new String[colLength];
                            for(int j=0; j<rowData.length; ++j){
                                rowData[j] = "";
                            }
                            rowData[0] =  ss.getStudent().getProfile().getFirstname() + " " + ss.getStudent().getProfile().getLastname();
                            dataList.add(Arrays.asList(rowData));
                            prvStudent = ss.getStudent().getStudentid();
                        }
                        List<String> row = dataList.get(dataList.size() - 1);
                        if(null != row && null != scorecards){
                            for(int k=0; k<schedules.size(); ++k){
                                TeacherSchedule ts = schedules.get(k);
                                for(int m=0; m<scorecards.size(); ++m){
                                    Studentscorecard score = scorecards.get(m);
                                    if(score.getStudentId() == ss.getStudent().getStudentid() && score.getSubjectid() == ts.getSubject().getSubjectid()){
                                        String val = score.getPercentage() + "% (" + score.getGradeletter() + ")";
                                        val = "<div class='"+ score.getCssClass() + "'><div class='bar score'>" + val + "</div</div>";
                                        row.set(k+1, val);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                populateDataTable();
            }else{
                setErrorMessage("There are no students enrolled.");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void populateDataTable() {
        HtmlDataTable dataTable = new HtmlDataTable();
        dataTable.setValueExpression("value", createValueExpression("#{teacherViewGradesController.dataList}", List.class));
        dataTable.setVar("dataItem");
        dataTable.setBorder(1);
        dataTable.setCellpadding("7");
        dataTable.setWidth("100%");
        dataTable.setRowClasses("graybg,whitebg");
        
        List<TeacherSchedule> schedules = schedulesController.getSchedules();
        for (int i = 0; i < dataList.get(0).size(); i++) {
            HtmlColumn column = new HtmlColumn();
            dataTable.getChildren().add(column);

            HtmlOutputText header = new HtmlOutputText();
            if(i == 0){
                header.setValue("Student");
            }else{
                header.setValue(schedules.get(i-1).getSubject().getSubjectname());
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
