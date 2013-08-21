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

<div id="internalNav">
    Back to: <a href="#" id="backToIssues">issues</a>
</div>


<h3>AHP Analysis</h3>


<div>
    <a href="ahp_analysis.jsp#" id="simpleRanking">Simple Ranking</a> | <a href="ahp_analysis.jsp#" id="criteriaRanking">Criteria-based Ranking</a>
</div>


<hr/>


<h4>AHP Analysis</h4>
<table>
    <tr>
        <th>Issue</th>
        <th>Weighting</th>
    </tr>
<%
    AHP ahp = project.getAhp();
    Map<CriticalIssue, Double> aggregatePrincipalEigenvector = ahp.getAggregatePrincipalEigenvector();
    for (Map.Entry<CriticalIssue, Double> entry : aggregatePrincipalEigenvector.entrySet()) {
        CriticalIssue criticalIssue = entry.getKey();
        Double weight = entry.getValue();
%>
    <tr>
        <td><%=  criticalIssue.getName() %></td>
        <td><%=  weight %></td>
    </tr>
<%
    }
%>
    <tr>
        <td><em>Consistency Index:</em></td>
        <td><%= project.getAhp().getAverageConsistencyIndex() %></td>
    </tr>
    <tr>
        <td><em>Consistency Ratio:</em></td>
        <td><%= project.getAhp().getAverageConsistencyRatio() %></td>
    </tr>
    <tr>
        <td><em>Consistent?</em></td>
        <td><%= project.getAhp().isGenerallyConsistent() %></td>
    </tr>
</table>



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
