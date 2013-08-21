<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="edu.rmit.sustainability.model.*" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="project" scope="session" class="edu.rmit.sustainability.model.Project"/>


<h2>SUSTAINABILITY REPORT TEMPLATE FOR: <%= project.getProjectName().toUpperCase() %></h2>

<div class="reportRow">
    <label class="reportLabel" for="projectName">Project name:</label>
    <span class="reportField" id="projectName"> <%= project.getProjectName() %></span>
</div>

<div class="reportRow">
    <label class="reportLabel" for="projectDescription">Project description:</label>
    <div class="reportField" id="projectDescription"> <%= project.getProjectDescription() %></div>
</div>

<div class="reportRow">
    <label class="reportLabel" for="projectName">General Issue:</label>
    <div class="reportField" id="generalIssue"> <%= project.getGeneralIssue() %></div>
</div>

<div class="reportRow">
    <label class="reportLabel" for="normativeGoal">Normative Goal:</label>
    <div class="reportField" id="normativeGoal"> <%= project.getNormativeGoal() %></div>
</div>


<h2>CURRENT ASSESSMENT</h2>

<div>
    <jsp:include page="/fragments/assess/render_assessment.jsp" />

        <script type="text/javascript" language="javascript">

            // DO THIS TO DRAW AN ASSESSMENT
            $(document).ready(function() {
                drawAssessmentCircle();
                drawDomains();
            });
        </script>
</div>


<h2>CRITICAL ISSUES FOR THIS PROJECT</h2>

<%
    if (project.getCriticalIssues() != null && project.getCriticalIssues().size() > 0) {
        for (Iterator<CriticalIssue> iterator = project.getCriticalIssues().iterator(); iterator.hasNext(); ) {
            CriticalIssue criticalIssue = iterator.next();
            if (criticalIssue != null && criticalIssue.getRdfId() != null) {
    %>

    <h3>CRITICAL ISSUE: <%= criticalIssue.getName() %></h3>

    <div>
        <label class="reportLabel" for="issueDescription<%= criticalIssue.getId() %>">Issue Description:</label>
        <div class="reportField" id="issueDescription<%= criticalIssue.getId() %>"> <%= criticalIssue.getDescription() %></div>
    </div>

    <div>
        <label class="reportLabel" for="issueDescription<%= criticalIssue.getId() %>">Statement of Objective:</label>
        <%
            String objective = "", directionStatement = "";
            if (criticalIssue.getAssociatedObjective() != null)
                objective = criticalIssue.getAssociatedObjective().getDescription();
            if (criticalIssue.getAssociatedObjective().getDesiredDirection() == 1)
                directionStatement = "The objective involves <em>increasing</em> the stock of one or more indicators.";
        %>
        <div class="reportField" id="objective<%= criticalIssue.getId() %>">
            <%= objective %>
        </div>
        <div class="reportField" id="objectiveDirection<%= criticalIssue.getId() %>">
            <%= directionStatement %>
        </div>
    </div>

    <div>
        <label class="reportLabel" for="issueSignificance<%= criticalIssue.getId() %>" title="Significance is scored on a scale of 1 (least significant) to 9 (most significant)">Perceived Significance of Issue:</label>

        <span class="reportField" id="issueSignificance<%= criticalIssue.getId() %>">
            <%= criticalIssue.getPerceivedSignificance() %>
        </span>
    </div>

    <div>
        <label class="reportLabel" for="issueSubdomains<%= criticalIssue.getId() %>" title="Sustainability areas or fields to which this issue relates">Related subdomains:</label>

        <div class="reportField" id="issueSubdomains<%= criticalIssue.getId() %>">
            <ul>
                <% for (Subdomain subdomain : criticalIssue.getSubdomains()) { %>
                    <li><%= subdomain.getParentDomain().getName() %>: <%= subdomain.getName() %></li>
                <% } %>
            </ul>
        </div>
    </div>


<% if (criticalIssue.getIndicators() != null && criticalIssue.getIndicators().size() > 0) { %>
    <h4>INDICATORS MEASURING THIS ISSUE</h4>

    <%
        for (Indicator indicator : criticalIssue.getIndicators() ) {
            if (indicator != null && indicator.getRdfId() != null) {
    %>

    <div>
        <label class="reportLabel" for="indicatorName<%= criticalIssue.getId() %>_<%= indicator.getId() %>">Indicator Name:</label>
        <span class="reportField" id="indicatorName<%= criticalIssue.getId() %>_<%= indicator.getId() %>"> <%= indicator.getName() %></span>
    </div>

    <div>
        <label class="reportLabel" for="indicatorCode<%= criticalIssue.getId() %>_<%= indicator.getId() %>">Code:</label>
        <span class="reportField" id="indicatorCode<%= criticalIssue.getId() %>_<%= indicator.getId() %>"> <%= indicator.getIdentifyingCode() %></span>
    </div>

    <%
        if (indicator.getTarget() != null) {
            Target target = indicator.getTarget();
            String value = target.getValue();
            // IGNORE FOR NOW
            int desiredDirection = target.getDesiredDirection();
    %>
    <div>
        <label class="reportLabel" for="indicatorTarget<%= criticalIssue.getId() %>_<%= indicator.getId() %>">Target:</label>
        <span class="reportField" id="indicatorTarget<%= criticalIssue.getId() %>_<%= indicator.getId() %>"> <%= value %></span>
    </div>
    <%
        }
    %>

    <div>
        <label class="reportLabel" for="indicatorUOM<%= criticalIssue.getId() %>_<%= indicator.getId() %>">Unit of Measure:</label>
        <span class="reportField" id="indicatorUOM<%= criticalIssue.getId() %>_<%= indicator.getId() %>"> <%= indicator.getUnitOfMeasure() %></span>
    </div>

    <div>
        <label class="reportLabel" for="indicatorDescription<%= criticalIssue.getId() %>_<%= indicator.getId() %>">Description:</label>
        <div class="reportField" id="indicatorDescription<%= criticalIssue.getId() %>_<%= indicator.getId() %>"> <%= indicator.getDescription() %></div>
    </div>

    <% if (indicator.getIndicatorSet() != null) { %>
    <div>
        <label class="reportLabel" for="indicatorSet<%= criticalIssue.getId() %>_<%= indicator.getId() %>">Indicator Set:</label>
        <div class="reportField" id="indicatorSet<%= criticalIssue.getId() %>_<%= indicator.getId() %>"> <%= indicator.getIndicatorSet().getName() %></div>
    </div>
    <% } %>


    <h4>INDICATOR SUBDOMAINS</h4>
    <div>
        <label class="reportLabel" for="indicatorSubdomains<%= criticalIssue.getId() %>_<%= indicator.getId() %>">Indicator Subdomains:</label>
        <div class="reportField" id="indicatorSubdomains<%= criticalIssue.getId() %>_<%= indicator.getId() %>">
            <ul>
                <% for (Subdomain subdomain : indicator.getSubdomains()) { %>
                <li><%= subdomain.getParentDomain().getName() %>: <%= subdomain.getName() %></li>
                <% } %>
            </ul>

        </div>
    </div>

        <%      } %>
        <%  } %>
    <% } %>

    <% } %>
    <% } %>
<% } %>

