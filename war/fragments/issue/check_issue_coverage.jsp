<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="edu.rmit.sustainability.model.*" %>
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

<h3>Check Issue Coverage</h3>


<% if (project.getCriticalIssues() != null && project.getCriticalIssues().size() > 0) { %>
<table>
    <tr>
        <th>Issue</th>
        <th>Indicators</th>
    </tr>
    <% if (project.getCriticalIssues() != null) { %>
    <%
        for (Iterator<CriticalIssue> iterator = project.getCriticalIssues().iterator(); iterator.hasNext(); ) {
            CriticalIssue criticalIssue = iterator.next();
            int indicatorCount = criticalIssue.getIndicators().size();
            if (criticalIssue != null && criticalIssue.getRdfId() != null) {
    %>
    <tr>
        <td>
            <div>
                <%= criticalIssue.getName() %>
                <%
                    if (indicatorCount > 0) {
                 %>
                <img src="/images/icons/tick.png" />
                <%  }  else { %>
                <img src="/images/icons/cross.png" />
                <%  }   %>
            </div>
        </td>
        <td>
            <%
                if (criticalIssue.getIndicators() != null) {
                for (Indicator indicator : criticalIssue.getIndicators()) {
            %>
                <div>
                    <%= indicator.getName() %>
                </div>
            <%
                    }
                }
                if (indicatorCount == 0) {
            %>
                <div>No indicators have been selected for this issue yet.</div>
            <%  }   %>
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