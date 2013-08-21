<%@ page import="java.util.Iterator" %>
<%@ page import="edu.rmit.sustainability.model.Project" %>
<%@ page import="java.util.List" %>
<%@ page import="edu.rmit.sustainability.model.assess.Assessment" %>
<%--
  Created by IntelliJ IDEA.
  User: E65691
  Date: 9/05/11
  Time: 2:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="project" scope="session" class="edu.rmit.sustainability.model.Project"/>


<h2>Assessing Sustainability</h2>
<p>
    Before defining issues and indicators for a sustainability project, it is often useful to
    conduct a preliminary assessment of sustainability.
</p>
<p>
    There are three tools for helping with this.

    <ul>
        <li>
            The <em>Self Assessment</em> tool provides a quick way of estimating strengths and weaknesses against the domains and subdomains of the Circles of Sustainability.

        </li>
        <li>
            The <em>Epistemic Evaluation</em> tool provides a quick way of estimating strengths and weaknesses against the domains and subdomains of the Circles of Sustainability.
        </li>
        <li>
            The <em>Community Questionnaire</em> offers a more comprehensive means for gathering feedback from
            members of the community affected or assisted by the project.
        </li>
    </ul>


</p>
<p>
    These tools provide an informal way of highlighting areas to focus on. This should help with
    the selection of the Critical Issues and Indicators as you progress further in defining the project.
</p>

<div>
    <a href="#" id="newAssessment" class="buttonLink">Create a new Assessment</a>
</div>

<div>

    <div>
        <% if (project.getAssessments() != null && project.getAssessments().size() > 0) { %>
            <h2>Previous Assessments</h2>
            <table>

            <% for (Assessment assessment : project.getAssessments()) { %>
                <tr>
                    <td>

                    <%
                        String assessmentID = null;
                        try {
                            assessmentID = assessment.getId();
                        }
                        catch (NullPointerException npe) {
                        }
                        if (assessmentID != null) {
                    %>
                        <a class="editAssessmentLinks" id="<%= assessment.getId() %>" href="#"><%= assessment.getCreatedAt() %></a>
                    <%
                        }
                        else {
                    %>
                    <%= assessment.getCreatedAt() %> [Can't edit this assessment]
                    <%
                        }
                    %>
                    </td>
                </tr>
            <% } %>
            </table>
        <% } %>
    </div>
</div>


<div>
    <a href="#" id="newEpistemicEvaluation"><!--Evaluate Knowledge about the Issues, Goals and Indicators of the Project--></a>
</div>

<div>
    <a href="#" id="newQuestionnaire"><!--Evaluate Community Opinion--></a>
</div>



<script>

    $("#newAssessment").click(function () {
        $.get("/assessment", { action: "new" }, function(data) {
            $('#dataEntryContainer').html(data);
        }, "html");
        return false;
    });

    $(".editAssessmentLinks").click(function () {
        var assessmentID = $(this).attr('id');
        $.get("/assessment", { action: "edit", assessmentID: assessmentID }, function(data) {
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