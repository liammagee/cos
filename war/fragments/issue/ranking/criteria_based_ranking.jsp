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


<h3>Criteria-based Issue Ranking</h3>


<div>
    <a href="criteria_based_ranking.jsp#" id="simpleRanking">Simple Ranking</a> | <a href="criteria_based_ranking.jsp#" id="criteriaRanking">Criteria-based Ranking</a>
</div>


<hr/>

<div>
    <a href="criteria_based_ranking.jsp#" id="newCriterion">Add new criteria</a> | <a href="criteria_based_ranking.jsp#" id="rebuildAHP">Rebuild matrix</a>
    | <a href="criteria_based_ranking.jsp#" id="directScoring">Direct Scoring</a>| <a href="criteria_based_ranking.jsp#" id="ahpAnalysis">AHP Analysis</a>
</div>



<h4>Criteria</h4>
<table>
    <tr>
        <th>Criterion</th>
        <th>Importance</th>
        <th>Action</th>
        <th><a href="#" id="viewCriteriaMatrix">View Criteria Matrix</a></th>
    </tr>
<%
    AHP ahp = project.getAhp();
%>

<%
        for (Criterion criterion : ahp.getCriteria()) {
%>
    <tr>
        <td><%=  criterion.getName() %></td>
        <td><%=  criterion.getInitialWeight() %></td>
        <td>
            <a href="#" class="editCriteriaLinks" id="edit_<%= criterion.getId() %>"><img src="/images/icons/report_edit.png"/></a>  |
            <a href="#" class="deleteCriteriaLinks" id="del_<%= criterion.getId() %>"><img src="/images/icons/delete.png"/></a>
        </td>
        <td><a href="#" class="viewIssueMatrix" id="vim_<%= criterion.getId() %>">View Issue Matrix</a> </td>
    </tr>
<%
    }
%>
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

    $("#directScoring").click(function () {
        $.get("/rankIssues", { action: "directScoring" }, function(data) {
            $('#dataEntryContainer').html(data);
        }, "html");
        return false;
    });

    $("#ahpAnalysis").click(function () {
        $.get("/rankIssues", { action: "ahpAnalysis" }, function(data) {
            $('#dataEntryContainer').html(data);
        }, "html");
        return false;
    });

    $(".editCriteriaLinks").click(function () {
        var criterionID = $(this).attr('id');
        criterionID = criterionID.split("edit_")[1];
        $.get("/rankIssues", { action: "editCriterion", criterionID: criterionID }, function(data) {
            $('#dataEntryContainer').html(data);
        }, "html");
        return false;
    });

    $(".deleteCriteriaLinks").click(function () {
        var criterionID = $(this).attr('id');
        criterionID = criterionID.split("del_")[1];
        $.get("/rankIssues", { action: "deleteCriterion", criterionID: criterionID }, function(data) {
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
