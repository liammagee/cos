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


<% if (request.getAttribute("users") != null) {

    Iterator i = ((List)request.getAttribute("users")).iterator();
    while (i.hasNext()) {
        User otherUser = (User)i.next();
%>
    <div>
        Invite <a href="javascript:alert('Not yet implemented!')"> <%= otherUser.getUsername() %></a>.
    </div>

<% } %>
<% } %>



<h4>Existing collaborators</h4>
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