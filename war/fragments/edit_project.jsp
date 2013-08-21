<%@ page import="java.util.Iterator" %>
<%@ page import="edu.rmit.sustainability.model.Project" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: E65691
  Date: 9/05/11
  Time: 2:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="project" scope="session" class="edu.rmit.sustainability.model.Project"/>




<form id="updateProject" action="/workingProject" method="post">

    <input name="action" value="update" type="hidden"/>
    <div>
        <label for="projectName">Give your project a name:</label>
        <input type="text" id="projectName" name="projectName" value="<%= project.getProjectName() %>">
    </div>

    <div>
        <label for="generalIssue">What is the <em>general issue</em> this project addresses?</label>
        <textarea id="generalIssue" name="generalIssue" rows="5" cols="50"><%= project.getGeneralIssue() %></textarea>
    </div>

    <div>
        <label for="normativeGoal">What is the overriding <em>goal</em> of the project?</label>
        <textarea id="normativeGoal" name="normativeGoal" rows="5" cols="50"><%= project.getNormativeGoal() %></textarea>
    </div>

    <div>
        <label for="projectDescription">Describe your project in more detail.
            </label>
        <div>Consider, for example, the duration, place, key stakeholders and constraints of the project.</div>
        <textarea id="projectDescription" name="projectDescription" rows="5" cols="50"><%= project.getProjectDescription() %></textarea>

    </div>

    <p><input class="button" type="submit" value="Save Project" /> </p>
</form>
<script>
    $('#updateProject').ajaxForm({ target: $('#dataEntryContainer') });
</script>
