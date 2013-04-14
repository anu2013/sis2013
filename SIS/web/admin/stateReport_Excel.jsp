<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@page import="sis.bean.SubjectVO"%>
<%@page import="sis.bean.StateReportVO"%>
<%@page import="sis.bean.StudentScoreCardVO"%>
<%@page import="java.util.List"%>

<%@ page contentType="application/vnd.ms-excel" %>

<%-- Set the content disposition header --%>
<%
    response.setHeader("Content-Disposition", "inline; filename=\"stateReport.xls\"");
    List<StateReportVO> stateReportVOs = (List<StateReportVO>) session.getAttribute("stateReportVOs");
    List<SubjectVO> subjectVOs = (List<SubjectVO>) session.getAttribute("subjectVOs");
    Integer selectedCAPTReportYear = (Integer) session.getAttribute("selectedCAPTReportYear");
%>
<table width="98%" border="1">
    <tr>
        <td align="center" colspan="<%=(subjectVOs.size()*6)+1 %>"><b><%=selectedCAPTReportYear%> Connecticut Academic Performance Test (CAPT) Achievement Data</b></td>
    </tr>
    <tr>
        <th rowspan="2" style="background-color: #306192; color: white; font-size: 10px; font-style: normal;">CAPT</th>
        <c:forEach items="#{subjectVOs}" var="subjectVO">
            <th colspan="6" style="background-color: #306192; color: white; font-size: 10px; font-style: normal;">${subjectVO.subjectName}</th>
        </c:forEach>
    </tr>
    <tr>
        <c:forEach items="#{subjectVOs}" var="subjectVO">
            <th style="background-color: #306192; color: white; font-size: 10px; font-style: normal;">Total Students</th>
            <th style="background-color: #306192; color: white; font-size: 10px; font-style: normal;"># Students Tested</th>
            <th style="background-color: #306192; color: white; font-size: 10px; font-style: normal;">% Students Tested</th>
            <th style="background-color: #306192; color: white; font-size: 10px; font-style: normal;">% Basic</th>
            <th style="background-color: #306192; color: white; font-size: 10px; font-style: normal;">% Proficient</th>
            <th style="background-color: #306192; color: white; font-size: 10px; font-style: normal;">% Advanced</th>
        </c:forEach>
    </tr>
    <c:forEach items="#{stateReportVOs}" var="stateReportVO">
        <tr>
            <td style="font-size: 10px; font-style: normal;">${stateReportVO.subGroupName}</td>
            <c:forEach items="#{stateReportVO.studentMetricsVOs}" var="studentMetricsVO">
                <td align="center">${studentMetricsVO.totalNumberOfStudents}</td>
                <td align="center">${studentMetricsVO.numberOfStudentsTested}</td>
                <td align="center">${studentMetricsVO.percentageOfStudentsTested}</td>
                <td align="center">${studentMetricsVO.percentageOfStudentsScoredAtBasicLevel}</td>
                <td align="center">${studentMetricsVO.percentageOfStudentsScoredAtProficentLevel}</td>
                <td align="center">${studentMetricsVO.percentageOfStudentsScoredAtAdvanceLevel}</td>
            </c:forEach>
        </tr>
    </c:forEach>
</table>
