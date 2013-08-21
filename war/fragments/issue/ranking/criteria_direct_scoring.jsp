<%@ page import="java.util.Iterator" %>
<%@ page import="edu.rmit.sustainability.model.Project" %>
<%@ page import="java.util.List" %>
<%@ page import="edu.rmit.sustainability.model.CriticalIssue" %>
<%@ page import="java.util.Map" %>
<%@ page import="edu.rmit.sustainability.model.ahp.AHP" %>
<%@ page import="edu.rmit.sustainability.model.ahp.Criterion" %>
<%@ page import="java.util.TreeMap" %>
<%--
  Created by IntelliJ IDEA.
  User: E65691
  Date: 9/05/11
  Time: 2:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="project" scope="session" class="edu.rmit.sustainability.model.Project"/>

<h3>Criteria-based Issue Ranking</h3>

<div id="internalNav">
    Back to: <a href="#" id="backToIssues">issues</a>
</div>


<div>
    <a href="criteria_direct_scoring.jsp#" id="simpleRanking">Simple Ranking</a> | <a href="criteria_direct_scoring.jsp#" id="criteriaRanking">Criteria-based Ranking</a>
</div>


<hr/>

<h4>Direct Scoring of issues</h4>


<form id="updateRanking" action="/rankIssues" method="post">

    <input name="action" value="updateCriteriaRanking" type="hidden"/>

<table>
        <tr>
            <th>Issue</th>
            <th>Current Ranking</th>
                <%
                    for (Criterion criterion : project.getAhp().getCriteria()) {
                %>
                    <th><%=  criterion.getName() %></th>
                <%
                    }
                %>
        </tr>
    <%

        Map<Integer, String> significance = new TreeMap<Integer, String>();
        significance.put(1, "Least Significant");
        significance.put(2, "");
        significance.put(3, "");
        significance.put(4, "");
        significance.put(5, "Moderately Significant");
        significance.put(6, "");
        significance.put(7, "");
        significance.put(8, "");
        significance.put(9, "Most Significant");

        Map<Integer, List<CriticalIssue>> rankedIssues = (Map<Integer, List<CriticalIssue>>)request.getAttribute("rankedIssues");
        for (Map.Entry<Integer, List<CriticalIssue>> ranking : rankedIssues.entrySet()) {
            List<CriticalIssue> criticalIssues = ranking.getValue();
            Integer rank = ranking.getKey();
    %>
    <%
        for (CriticalIssue criticalIssue : criticalIssues) {
    %>
    <tr>
        <td><%= criticalIssue.getName() %></td>
        <td><%= rank %></td>
        <td>
            <select name="<%= criticalIssue.getId() %>">
                <%
                    for (Iterator<Map.Entry<Integer, String>> iterator = significance.entrySet().iterator(); iterator.hasNext(); ) {
                        String selected = "";
                        Map.Entry<Integer, String> next = (Map.Entry<Integer, String>) iterator.next();
                        Integer key = next.getKey();
                        if (key.equals(criticalIssue.getPerceivedSignificance()))
                            selected = "selected";
                        String value = next.getValue();
                        if (value.length() > 0)
                            value = " - " + value;
                %>
                    <option value="<%= key %>"  <%= selected %>><%= key %> <%= value %></option>
                <% } %>
            </select>
        </td>
    </tr>
    <%
        }
    %>

    <%
        }
    %>
</table>

    <p><input type="submit" value="Update Issues" /> </p>
</form>


<script>
    $('#backToIssues').click(CoS.loadIssues);
    $('#updateRanking').ajaxForm({ target: $('#dataEntryContainer') });

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

    $("#newCriterion").click(function () {
        $.get("/rankIssues", { action: "newCriterion" }, function(data) {
            $('#dataEntryContainer').html(data);
        }, "html");
        return false;
    });

    $("#viewCriteriaMatrix").click(function () {
        $.get("/rankIssues", { action: "viewCriteriaMatrix" }, function(data) {
            $('#dataEntryContainer').html(data);
        }, "html");
        return false;
    });

    $("#rebuildAHP").click(function () {
        $.get("/rankIssues", { action: "rebuildAHP" }, function(data) {
            $('#dataEntryContainer').html(data);
        }, "html");
        return false;
    });

    $(".criteriaLinks").click(function () {
        $.get("/rankIssues", { action: "editCriterion", criterionID: $(this).attr('id') }, function(data) {
            $('#dataEntryContainer').html(data);
        }, "html");
        return false;
    });

    $(".viewIssueMatrix").click(function () {
        var criterionID = $(this).attr('id');
        criterionID = criterionID.split("vim_")[1];
        $.get("/rankIssues", { action: "viewIssueMatrix", criterionID: criterionID }, function(data) {
            $('#dataEntryContainer').html(data);
        }, "html");
        return false;
    });


</script>
