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

<h2>Assessing Sustainability</h2>
<p>
    Before defining issues and indicators for a sustainability project, it is often useful to
    conduct a preliminary assessment of sustainability.
</p>
<p>
    There are two tools for helping with this.

    The <em>Self Assessment</em> tool provides a quick way of
    estimating strengths and weaknesses against the domains and subdomains of the Circles of Sustainability.

    The <em>Community Questionnaire</em> offers a more comprehensive means for gathering feedback from
    members of the community affected or assisted by the project.
</p>
<p>
    Both tools provide an informal way of highlighting areas to focus on. This should help with
    the selection of the Critical Issues and Indicators as you progress further in defining the project.
</p>

<a href="#" id="newAssessment">Create a new Self Assessment</a>

<a href="#" id="newQuestionnaire">Administer Questionnaire</a>


<script>
    $(document).ready(function() {
        $('#controlLinks').show();
        $('#welcomeLinks').hide();
    });

    $("#newSelfAssessment").click(function () {
        $.get("/assessment", { action: "new" }, function(data) {
            $('#dataEntryContainer').html(data);
        }, "html");
        return false;
    });

    $("#newQuestionnaire").click(function () {
        $.get("/questionnaire", { action: "new" }, function(data) {
            $('#dataEntryContainer').html(data);
        }, "html");
        return false;
    });

</script>