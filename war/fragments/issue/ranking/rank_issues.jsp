<%@ page import="java.util.Iterator" %>
<%@ page import="edu.rmit.sustainability.model.Project" %>
<%@ page import="java.util.List" %>
<%@ page import="edu.rmit.sustainability.model.CriticalIssue" %>
<%@ page import="java.util.Map" %>
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


<h3>Ranking Issues</h3>


<div>
    <p>
        It is often helpful to <em>rank</em> issues, in order to understand which issues are more <em>critical</em> than others.
        There are two approaches you might consider:
    </p>


    <div>
        <a href="#" id="simpleRanking">Simple Ranking</a> | <a href="#" id="criteriaRanking">Criteria-based Ranking</a>
    </div>


</div>

<table>
        <tr>
            <th>Issue</th>
            <th>Current Ranking</th>
        </tr>
    <%
        Map<Integer, List<CriticalIssue>> rankedIssues = (Map<Integer, List<CriticalIssue>>)request.getAttribute("rankedIssues");
        for (Map.Entry<Integer, List<CriticalIssue>> ranking : rankedIssues.entrySet()) {
            List<CriticalIssue> criticalIssues = ranking.getValue();
            Integer rank = ranking.getKey();
    %>
        <tr>
            <td>
                <%
                    for (CriticalIssue criticalIssue : criticalIssues) {
                %>
                    <p><%= criticalIssue.getName() %></p>
                <%
                    }
                %>
            </td>
            <td><%= rank %></td>
        </tr>

    <%
        }
    %>
</table>



<script>
    $('#backToIssues').click(CoS.loadIssues);

    $("#simpleRanking").click(function () {
        $.get("/rankIssues", { action: "simpleRanking" }, function(data) {
            $('#dataEntryContainer').html(data);
        }, "html");
        return false;
    });


    $("#criteriaRanking").click(function () {
        $.get("/rankIssues", { action: "criteriaRanking" }, function(data) {
            $('#dataEntryContainer').html(data);
        }, "html");
        return false;
    });


</script>