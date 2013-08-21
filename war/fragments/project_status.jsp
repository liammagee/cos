<%@ page import="edu.rmit.sustainability.messaging.feedback.Suggestion" %>
<%@ page import="edu.rmit.sustainability.messaging.feedback.SimpleSuggestion" %>
<%@ page import="edu.rmit.sustainability.messaging.feedback.Feedback" %>
<%--
  Created by IntelliJ IDEA.
  User: E65691
  Date: 9/05/11
  Time: 2:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="project" scope="session" class="edu.rmit.sustainability.model.Project"/>




<script>
    $(document).ready(function() {
        $('#statusConductInitialAssessment').click(CoS.loadAssessments);
        $("#statusRankIssues").click(CoS.loadRankIssues);
        $('#statusAddIssues').click(CoS.loadIssues);
        $('#statusSetObjectives').click(CoS.loadIssues);
        $('#statusAddIndicators').click(CoS.loadIndicators);
        $('#statusDefineGeneralIssue').click(CoS.loadEditProject);
        $('#statusSetNormativeGoal').click(CoS.loadEditProject);
        $("#statusExamineImpacts").click(CoS.loadRelateIssues);
        $('#statusAssessAgainstDomain').click(CoS.loadAssessIssuesAgainstDomains);
        $('#statusCheckIssueCoverage').click(CoS.loadCheckIssueCoverage);
        $('#statusReportAgainstIndicators').click(CoS.loadReports);
        $('#statusMonitorResponse').click(CoS.notYetImplemented);
        $('#statusReviewModel').click(CoS.notYetImplemented);
    });


</script>


<h4 id="projectTitle">
    <% if (project != null && project.getProjectName() != null) { %>
        PROGRESS FOR <%= project.getProjectName().toUpperCase() %>
    <% } %>
</h4>


<div>

<ul>
<li class="menu-item" id="liCommit">
    Commit
    <ul>
        <li id="liAffirm">
            <a href="#" id="statusAffirm">Affirm</a>
        </li>
        <li id="liEstablish">
            <a href="#" id="statusEstablish">Establish</a>
        </li>
        <li id="liChoose">
            <a href="#" id="statusChoose">Choose</a>
        </li>
        <li id="liResource">
            <a href="#" id="statusResource">Resource</a>
        </li>
    </ul>
</li>
<li class="menu-item" id="liEngage">
    Engage

    <ul>
        <li id="liConsult">
            <a href="#" id="statusConsult">Consult</a>
        </li>
    </ul>
    <ul>
        <li id="liEntrust">
            <a href="#" id="statusEntrust">Entrust </a>
        </li>
    </ul>
    <ul>
        <li id="liEmpower">
            <a href="#" id="statusEmpower">Empower</a>
        </li>
    </ul>
    <ul>
        <li id="liAccord">
            <a href="#" id="statusAccord">Accord</a>
        </li>
    </ul>
</li>
<li class="menu-item" id="liAssess">
    Assess

    <ul>
        <li id="liIsHasInitialAssessmentBeenConducted">
            <a href="#" id="statusConductInitialAssessment">Conduct Initial Assessment</a>
        </li>
    </ul>
    <ul>
        <li id="liDetermine">
            <a href="#" id="statusDetermine">Determine</a>
        </li>
    </ul>
    <ul>
        <li id="liAnalyse">
            <a href="#" id="statusAnalyse">Analyse</a>
        </li>
    </ul>
    <ul>
        <li id="liResearch">
            <a href="#" id="statusResearch">Research</a>
        </li>
    </ul>
    <ul>
        <li id="liProject">
            <a href="#" id="statusProject">Project</a>
        </li>
    </ul>
</li>
<li class="menu-item" id="liDefine">
    Define

    <ul>
        <li class="menu-item" id="liClarify">
            <a href="#" id="statusClarify">Clarify</a>
        </li>
        <li class="menu-item" id="liIdentify">
            <a href="#" id="statusIdentify">Identify</a>
        </li>
        <li class="menu-item" id="liRefine">
            <a href="#" id="statusRefine">Refine</a>
        </li>
        <li class="menu-item" id="liReview">
            <a href="#" id="statusReview">Review</a>
        </li>

        <li id="liIsProjectDescribed">
            <a href="#" id="statusDescribeYourProject">Describe Your Project</a>
        </li>
        <li id="liIsGeneralIssueDefined">
            <a href="#" id="statusDefineGeneralIssue">Set General Issue</a>
        </li>
        <li id="liIsNormativeGoalDefined">
            <a href="#" id="statusSetNormativeGoal">Set Normative Goal</a>
        </li>
        <li class="menu-item" id="liIsDoAtLeastFourIssuesExist">
            <a href="#" id="statusAddIssues">Add Issues</a>
        </li>
        <li class="menu-item" id="liIsAreIssuesRanked">
            <a href="#" id="statusRankIssues">Rank Issues</a>
        </li>
        <li class="menu-item" id="liIsAreAllDomainsCoveredByIssues">
            <a href="#" id="statusAssessAgainstDomain">Check Against Domains</a>
        </li>
        <li class="menu-item" id="liIsDoAtLeastFourIndicatorsExist">
            <a href="#" id="statusAddIndicators">Add Indicators</a>
        </li>
        <li class="menu-item" id="liIsHaveImpactsBetweenIssuesBeenAnalysed">
            <a href="#" id="statusExamineImpacts">Analyse Impacts </a>
        </li>
        <li class="menu-item" id="liIsAreAllIssuesMeasuredByAtLeastOneIndicator">
            <a href="#" id="statusCheckIssueCoverage">Check Against Issues</a>
        </li>
    </ul>
</li>
<li class="menu-item" id="liImplement">
    Implement
    <ul>
        <li class="menu-item" id="liAuthorise">
            <a href="#" id="statusAuthorise">Authorise</a>
        </li>
        <li class="menu-item" id="liEnable">
            <a href="#" id="statusEnable">Enable</a>
        </li>
        <li class="menu-item" id="liLiaise">
            <a href="#" id="statusLiaise">Liaise</a>
        </li>
        <li class="menu-item" id="liReview">
            <a href="#" id="statusReview">Review</a>
        </li>
    </ul>
</li>
<li class="menu-item" id="liMeasure">
    Measure

    <ul>
        <li class="menu-item" id="liMonitor">
            <a href="#" id="statusMonitor">Monitor</a>
        </li>
        <li class="menu-item" id="liDocument">
            <a href="#" id="statusDocument">Document</a>
        </li>
        <li class="menu-item" id="liReassess">
            <a href="#" id="statusReassess">Reassess</a>
        </li>
        <li class="menu-item" id="liEvaluate">
            <a href="#" id="statusEvaluate">Evaluate</a>
        </li>
    </ul>
</li>
<li class="menu-item" id="liCommunicate">
    Communicate

    <ul>
        <li class="menu-item" id="liTranslate">
            <a href="#" id="statusTranslate">Translate</a>
        </li>
        <li class="menu-item" id="liPublicise">
            <a href="#" id="statusPublicise">Publicise</a>
        </li>
        <li class="menu-item" id="liReport">
            <a href="#" id="statusReport">Report</a>
        </li>
        <li class="menu-item" id="liAdvise">
            <a href="#" id="statusAdvise">Advise</a>
        </li>

        <li class="menu-item" id="liIsReportCompiled">
            <a href="#" id="statusReportAgainstIndicators">Report against Indicators</a>
        </li>
        <li class="menu-item" id="liIsResponseDevelopedAndMonitored">
            <a href="#" id="statusMonitorResponse">Develop a Response</a>
        </li>
        <li class="menu-item" id="liIsModelReviewedAndAdapted">
            <a href="#" id="statusReviewModel">Review the Model</a>
        </li>
    </ul>
</li>
</ul>
</div>
