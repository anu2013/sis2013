<%@page import="sis.bean.TeacherScheduleVO"%>
<%@page import="java.util.List"%>

<%@ page contentType="application/vnd.ms-excel" %>

<%-- Set the content disposition header --%>
<%
    response.setHeader("Content-Disposition", "inline; filename=\"teacherScheduleReport.xls\"");
    List<TeacherScheduleVO> teacherScheduleVOs = (List<TeacherScheduleVO>) session.getAttribute("teacherScheduleVOs");
%>
<table border="1">
    <tr>
        <th>Name</th>
        <th>Schedule Type</th>
        <th>Period Name</th>
        <th>Start Time</th>
        <th>End Time</th>
        <th>Schedule Days</th>
    </tr>
    <% for (int index = 0; index < teacherScheduleVOs.size(); index++) { 
       TeacherScheduleVO teacherScheduleVO = (TeacherScheduleVO) teacherScheduleVOs.get(index);
    %>
    <tr>
        <td><%=teacherScheduleVO.getName()%></td>
        <td><%=teacherScheduleVO.getScheduleType()%></td>
        <td><%=teacherScheduleVO.getPeriodName()%></td>
        <td><%=teacherScheduleVO.getPeriodStart()%></td>
        <td><%=teacherScheduleVO.getPeriodEnd()%></td>
        <td><%=teacherScheduleVO.getScheduleDays()%></td>
    </tr>
    <% }%>
</table>