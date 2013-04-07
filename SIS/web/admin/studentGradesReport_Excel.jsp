<%@page import="sis.bean.StudentScoreCardVO"%>
<%@page import="java.util.List"%>

<%@ page contentType="application/vnd.ms-excel" %>

<%-- Set the content disposition header --%>
<%
    response.setHeader("Content-Disposition", "inline; filename=\"studentScoreReport.xls\"");
    List<StudentScoreCardVO> studentScoreCardVOs = (List<StudentScoreCardVO>) session.getAttribute("studentscorecardVOs");
%>
<table border="1">
    <tr>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Subject</th>
        <th>Final Score</th>
        <th>Grade Letter</th>
    </tr>
    <% for (int index = 0; index < studentScoreCardVOs.size(); index++) { 
       StudentScoreCardVO studentScoreCardVO = (StudentScoreCardVO) studentScoreCardVOs.get(index);
    %>
    <tr>
        <td><%=studentScoreCardVO.getFirstName()%></td>
        <td><%=studentScoreCardVO.getLastName()%></td>
        <td><%=studentScoreCardVO.getSubject()%></td>
        <td><%=studentScoreCardVO.getFinalscore()%></td>
        <td><%=studentScoreCardVO.getGradeletter()%></td>
    </tr>
    <% }%>
</table>