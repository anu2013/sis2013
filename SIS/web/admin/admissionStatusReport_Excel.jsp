<%@page import="sis.model.Admission"%>
<%@page import="java.util.List"%>

<%@ page contentType="application/vnd.ms-excel" %>

<%-- Set the content disposition header --%>
<%
    response.setHeader("Content-Disposition", "inline; filename=\"admissionStatusReport.xls\"");
    List<Admission> admissions = (List<Admission>) session.getAttribute("admissions");
%>
<table border="1">
    <tr>
        <th>Description</th>
        <th>Application Type</th>
        <th>Status</th>
    </tr>
    <% for (int index = 0; index < admissions.size(); index++) { 
       Admission ad = (Admission) admissions.get(index);
    %>
    <tr>
        <td><%=ad.getDescription()%></td>
        <td><%=ad.getApplicationtype()%></td>
        <td><%=ad.getStatus()%></td>
    </tr>
    <% }%>
</table>