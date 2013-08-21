<%@ page import="java.util.Iterator" %>
<%@ page import="edu.rmit.sustainability.model.Project" %>
<%@ page import="edu.rmit.sustainability.model.Subdomain" %>
<%@ page import="edu.rmit.sustainability.model.Domain" %>
<%@ page import="java.util.List" %>
<%@ page import="edu.rmit.sustainability.model.CriticalIssue" %>
<%--
  Created by IntelliJ IDEA.
  User: E65691
  Date: 9/05/11
  Time: 2:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="project" scope="session" class="edu.rmit.sustainability.model.Project"/>

<div id="internalNav">
    Back to: <a href="#" id="backToIssues">issues</a>
</div>

<h3>CHECK ISSUES AGAINST DOMAINS</h3>


<% if (project.getCriticalIssues() != null && project.getCriticalIssues().size() > 0) { %>
<table>
    <tr>
        <th>Issue</th>
        <th>Domains</th>
    </tr>
    <% if (project.getCriticalIssues() != null) { %>
    <%
        for (Iterator<CriticalIssue> iterator = project.getCriticalIssues().iterator(); iterator.hasNext(); ) {
            CriticalIssue criticalIssue = iterator.next();
            if (criticalIssue != null && criticalIssue.getRdfId() != null) {
    %>
    <tr>
        <td>
            <p><%= criticalIssue.getName() %>
            </p>
        </td>
        <td>
            <% if (criticalIssue.getSubdomains() != null) {
                for (Subdomain subdomain : criticalIssue.getSubdomains()) {
            %>
            <div><%= subdomain.getParentDomain().getName() %> </div>
            <%
                    }
                }
            %>
        </td>
    </tr>
    <% } %>
    <% } %>
    <% } %>
</table>
<% } %>



<script>
    $('#updateRanking').ajaxForm({ target: $('#dataEntryContainer') });

    $('#backToIssues').click(CoS.loadIssues);

    $("#rankIssues").click(function () {
        $('#dataEntryContainer').load("/criticalIssue", { action: "rank" });
        return false;
    });

</script>