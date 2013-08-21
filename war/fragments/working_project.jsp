<%@ page import="java.util.Iterator" %>
<%@ page import="edu.rmit.sustainability.model.Project" %>
<%@ page import="java.util.List" %>
<%@ page import="edu.rmit.sustainability.model.CriticalIssue" %>
<%@ page import="edu.rmit.sustainability.model.Subdomain" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="project" scope="session" class="edu.rmit.sustainability.model.Project"/>

<h2>WORKING PROJECT: <%= project.getProjectName().toUpperCase() %>
</h2>

<div>
    <%= project.getProjectDescription() %>
</div>

<div>
    This project aims to address the following <strong>General Issue</strong>:
    <%= project.getGeneralIssue() %>.
</div>

<div>
    The project also has an overall <strong>Normative Goal</strong> of:
    <%= project.getNormativeGoal() %>.
</div>


<div>
    <a href="#" id="editProject">Update these project details</a>.
</div>

<hr>

<h2>
    PROJECT CRITICAL ISSUES <img class="criticalIssuesHelp" src="/images/icons/help.png"/>
</h2>


<h3>What are the <strong class="criticalIssuesHelp">Critical Issues</strong> for this project?</h3>

<% if (project.getCriticalIssues() != null && project.getCriticalIssues().size() > 0) { %>
<table>
    <tr>
        <th>Issue</th>
        <th>Stakeholder</th>
        <th>Significance</th>
        <th>Subdomains</th>
        <th>Actions</th>
    </tr>
    <% if (project.getCriticalIssues() != null) { %>
    <%
        for (Iterator<CriticalIssue> iterator = project.getCriticalIssues().iterator(); iterator.hasNext(); ) {
            CriticalIssue criticalIssue = iterator.next();
            if (criticalIssue != null && criticalIssue.getRdfId() != null) {
    %>
    <tr>
        <td>
            <p>
                <a href="#" class="editIssueLinks" id="<%= criticalIssue.getId() %>">
                    <%= criticalIssue.getName() %>
                </a>
            </p>
        </td>
        <td>
            <p>
                <% if (criticalIssue.getCreator() != null) { %>
                <%= criticalIssue.getCreator().getUsername()  %>
                <% } %>
            </p>
        </td>
        <td>
            <p>
                <%= criticalIssue.getPerceivedSignificance() %>
            </p>
        </td>
        <td>
            <% if (criticalIssue.getSubdomains() != null) {
                for (Subdomain subdomain : criticalIssue.getSubdomains()) {
            %>
            <div><%= subdomain.getName() %>
            </div>
            <%
                    }
                }
            %>
        </td>
        <td>
            <a href="#" class="delIssueLinks" id="del_<%= criticalIssue.getId() %>">Delete this issue</a>
            </a>
        </td>
    </tr>
    <% } %>
    <% } %>
    <% } %>
</table>


<% } %>
<div>
    <a href="#" id="addIssue" class="buttonLink">Create a new Critical Issue</a>
</div>

<h3>ADDITIONAL FEATURES</h3>
<div>
    It can be useful to rank issues in order of priority, and to show relationships (such as dependencies) between
    issues.
    <p>
        Follow these links to <a href="#" id="rankIssues">rank</a> or <a href="#" id="relateIssues">create relationships between</a> critical issues.
    </p>
</div>


<script>
    $("#editProject").click(function () {
        $.get("/workingProject", { action:"edit" }, function (data) {
            $('#dataEntryContainer').html(data);
        }, "html");
        return false;
    });

    $("#addIssue").click(function () {
        $.get("/criticalIssue", { action:"new" }, function (data) {
            $('#dataEntryContainer').html(data);
        }, "html");
        return false;
    });

    $(".editIssueLinks").click(function () {
        var issueID = $(this).attr('id');
        $.get("/criticalIssue", { action:"edit", issueID:issueID }, function (data) {
            $('#dataEntryContainer').html(data);
        }, "html");
        return false;
    });


    $(".delIssueLinks").click(function () {
        var issueID = $(this).attr('id');
        issueID = issueID.split('del_')[1];

        if (confirm("Are you sure you wish to delete this critical issue?")) {
            $.get("/criticalIssue", { action:"delete", issueID:issueID }, function (data) {
                $('#dataEntryContainer').html(data);
            }, "html");
        }
        return false;
    });

    $("#rankIssues").click(function () {
        $.get("/rankIssues", { action:"overview" }, function (data) {
            $('#dataEntryContainer').html(data);
        }, "html");
        return false;
    });

    $("#relateIssues").click(function () {
        $.get("/relateIssues", { action:"overview" }, function (data) {
            $('#dataEntryContainer').html(data);
        }, "html");
        return false;
    });

</script>