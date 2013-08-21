<%@ page import="java.util.Iterator" %>
<%@ page import="edu.rmit.sustainability.model.Project" %>
<%@ page import="java.util.List" %>
<%@ page import="edu.rmit.sustainability.model.User" %>
<%--
  Created by IntelliJ IDEA.
  User: E65691
  Date: 9/05/11
  Time: 2:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="../notice.jsp" />
<jsp:useBean id="project" scope="session" class="edu.rmit.sustainability.model.Project"/>
<jsp:useBean id="user" scope="session" class="edu.rmit.sustainability.model.User"/>

<p>
    <a href="#" id="inviteCollaborator">Invite a new collaborator</a>
</p>

<br/>


<% if (!project.getCollaborators().isEmpty()) { %>

<% if (project.getCreator().equals(user)) { %>



<h4>Current collaborators</h4>
<table>
    <tr>
        <th>Collaborator name</th>
    </tr>
    <% for(User collaborator : project.getCollaborators()) { %>
    <tr>
        <td><%= collaborator.getUsername() %></td>
    </tr>
    <% } %>
</table>

<% } else { %>
    <div>Sorry - you are not the creator of this project, and cannot view or invite other collaborators.</div>
<% } %>

<% } else { %>
    <p>There are no collaborators for this project yet.</p>
<% } %>


<script>

    $("#inviteCollaborator").click(function () {
        $.get("/user", { action: "invite" }, function(data) {
            $('#dataEntryContainer').html(data);
        }, "html");
        return false;
    });

    $(".collabatorLinks").click(function () {
        $.get("/user", { action: "open", projectID: $(this).attr('id') }, function(data) {
            $('#dataEntryContainer').html(data);
        }, "html");
        return false;
    });
</script>