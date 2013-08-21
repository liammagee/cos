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

<jsp:include page="notice.jsp" />

<div style="text-align: center">
    <h2>YOUR PROJECTS</h2>

    <div>
        Projects are the way you organise your sustainability reports, issues and indicators.
        <a href="#" id="projectHelp">Find out more</a>.
    </div>

    <div>
        You can create a new project:
    </div>

    <div>
        <a href="#" id="newProject" class="buttonLink">Create a new project</a>
    </div>


    <div style="margin-top: 1.5em">
        ... or choose to work on an existing project:
    </div>


    <div>
        <% if (request.getAttribute("projects") != null) {
            Iterator i = ((List)request.getAttribute("projects")).iterator();
            while (i.hasNext()) {
                Project project = (Project)i.next();
        %>

        <div>
            <a href="#" class="editProjectLinks" id="<%= project.getRdfId().value() %>" class="buttonLink">
                <%= project.getProjectName() %>
            </a>
            (created by <%= project.getCreator().getUsername() %>)

            <a href="#" title="Delete this project" class="delProjectLinks" id="del_<%= project.getRdfId().value() %>" class="buttonLink"><img src="/images/icons/delete.png"/></a>

        </div>

        <% } %>
        <% } %>
    </div>
</div>



<script>
    $(document).ready(function() {
        $('#controlLinks').show();
        $('#welcomeLinks').hide();
    });

    $("#newProject").click(function () {
        $.get("/workingProject", { action: "new" }, function(data) {
//            CoS.refreshStatus();
            $('#dataEntryContainer').html(data);
            CoS.updateProjectStatus();
        }, "html");
        return false;
    });

    $(".editProjectLinks").click(function () {
        var projectID = $(this).attr('id');
        $.get("/workingProject", { action: "open", projectID: projectID }, function(data) {
//            CoS.refreshStatus();
            $('#dataEntryContainer').html(data);
            CoS.updateProjectStatus();
        }, "html");
        return false;
    });

    $(".delProjectLinks").click(function () {
        var projectID = $(this).attr('id');
        projectID = projectID.split('del_')[1];

        if (confirm("Are you sure you wish to delete this project?")) {
            $.get("/workingProject", { action: "delete", projectID: projectID }, function(data) {
                $('#dataEntryContainer').html(data);
            }, "html");
        }
        return false;
    });
</script>