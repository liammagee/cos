/**
 * Created by IntelliJ IDEA.
 * User: E65691
 * Date: 9/05/11
 * Time: 2:03 PM
 * To change this template use File | Settings | File Templates.
 */

var pollingTimerId;
var pollingInterval = 5000000;


/**
 * CoS Namespace
 */
var CoS = CoS || {
    project: null,
    doProjectStatus: true,
    doNotifications: true,
    doSuggestions: true
};

$(document).ready(function() {
    CoS.addJQueryHooks();
    CoS.addGeneralListeners();
    CoS.startPolling();
});
$(document).ajaxComplete(function() {
    CoS.addJQueryHooks();
});
CoS.loadProjects = function () {
    $("#dataEntryContainer").html('<img src="/images/ajax-large-loader.gif"/>').load("/workingProject", { action: "list" });
    CoS.addJQueryHooks();
    return false;
};
CoS.loadAssessments = function() {
    $("#dataEntryContainer").html('<img src="/images/ajax-large-loader.gif"/>').load("/assessment", { action: "list" });
    CoS.addJQueryHooks();
    return false;
};
CoS.loadRankIssues = function () {
    $('#dataEntryContainer').load("/rankIssues", { action: "overview" });
    CoS.addJQueryHooks();
//    $.get("/rankIssues", { action: "overview" }, function(data) {
//                $('#dataEntryContainer').html(data);
//            }, "html");
    return false;
};
CoS.loadIssues = function() {
    $("#dataEntryContainer").html('<img src="/images/ajax-large-loader.gif"/>').load("/workingProject", { action: "open" });
    CoS.addJQueryHooks();
    return false;
};
CoS.loadIndicators = function() {
    $("#dataEntryContainer").html('<img src="/images/ajax-large-loader.gif"/>').load("/indicator", { action: "list" });
    CoS.addJQueryHooks();
    return false;
};
CoS.loadReports = function () {

    $("#dataEntryContainer").html('<img src="/images/ajax-large-loader.gif"/>').load("/report", { action: "view" });

    // To list multiple reports, use this:
//    $("#dataEntryContainer").html('<img src="/images/ajax-large-loader.gif"/>').load("/report", { action: "list" });
    CoS.addJQueryHooks();
    return false;
};
CoS.loadCollaborators = function () {
    $("#dataEntryContainer").html('<img src="/images/ajax-large-loader.gif"/>').load("/user", { action: "list" });
    CoS.addJQueryHooks();
    return false;
};
CoS.loadEditProject = function () {
    $('#dataEntryContainer').load("/workingProject", { action: "edit" });
    CoS.addJQueryHooks();
//    $.get("/workingProject", { action: "edit" }, function(data) {
//                $('#dataEntryContainer').html(data);
//            }, "html");
    return false;
};
CoS.loadRelateIssues = function () {
    $('#dataEntryContainer').load("/relateIssues", { action: "overview" });
    CoS.addJQueryHooks();
    return false;
};
CoS.loadAssessIssuesAgainstDomains = function () {
    $('#dataEntryContainer').load("/criticalIssue", { action: "assess" });
    CoS.addJQueryHooks();
    return false;
};
CoS.loadCheckIssueCoverage = function () {
    $('#dataEntryContainer').load("/criticalIssue", { action: "coverage" });
    CoS.addJQueryHooks();
    return false;
};
CoS.loadHelp = function() {
    $('#dataEntryContainer').load("/help", { action: "overview" });
    CoS.addJQueryHooks();
    return false;
};
CoS.notYetImplemented = function() {
    alert('Not yet implemented');
    return false;
};

CoS.addJQueryHooks = function () {
    $('button, input[type="submit"]').button();
    CoS.addHelpHooks();
}

CoS.addGeneralListeners = function () {
    $('#projectsLink').click(CoS.loadProjects);
    $('#assessLink').click(CoS.loadAssessments);
    $("#rankIssues").click(CoS.loadRankIssues);
    $('#issuesLink').click(CoS.loadIssues);
    $('#indicatorsLink').click(CoS.loadIndicators);
    $("#reportsLink").click(CoS.loadReports);
    $("#collaboratorsLink").click(CoS.loadCollaborators);
    $("#editProject").click(CoS.loadEditProject);
    $("#relateIssues").click(CoS.loadRelateIssues);
    $('#helpLink').click(CoS.loadHelp);
    $('#loginForm').ajaxForm({ target: $('#dataEntryContainer') });
}
CoS.addHelpHooks = function () {
    CoS.doInlineHelp($('#projectHelp'),
        "<p>Projects are the main organising structure in the <em>Circles of Sustainability</em> system." +
            "Each project should include a general issue (that motivates the project) and an normative goal (that stakeholders want addressed by the project).</div>"
    );
    CoS.doInlineHelp($('.criticalIssuesHelp'),
        "<div> \
        Issues are the key sustainability challenges, problems, opportunities or concerns raised by project stakeholders. \
        Each issue should be capable of being resolved by an associated <em>objective</em>. \
        </div>"
    );
}

CoS.doInlineHelp = function(selector, message) {
    selector.balloon({ contents: message, position: "right"  } );
}


CoS.startPolling = function() {
    CoS.doPolling();
    clearInterval(pollingTimerId);
    pollingTimerId = setInterval("CoS.doPolling()", pollingInterval);
    inPlay = true;
}

CoS.doPolling = function () {
    CoS.updateNotifications();
    CoS.updateSuggestions();
    CoS.updateProjectStatus();
}
CoS.updateNotifications = function() {
    if (this.doNotifications)
        $('#notifications').load("/notifications");
}
CoS.updateSuggestions = function() {
    if (this.doSuggestions)
        $('#suggestions').load("/suggestions");
}

CoS.refreshStatus = function() {
    CoS.project = {};
    CoS.project.projectProgress = {};
    CoS.setStatus();
};
CoS.setStatus = function() {
    $('#projectTitle').html('PROGRESS FOR ' + CoS.project.projectName);

    CoS.setStatusState('#liIsProjectDescribed', (CoS.project.projectProgress.isGeneralIssueDefined == 1) && (CoS.project.projectProgress.isNormativeGoalDefined == 1));
    CoS.setStatusState('#liIsGeneralIssueDefined', CoS.project.projectProgress.isGeneralIssueDefined == 1);
    CoS.setStatusState('#liIsNormativeGoalDefined', CoS.project.projectProgress.isNormativeGoalDefined == 1);
    CoS.setStatusState('#liIsHasInitialAssessmentBeenConducted', CoS.project.projectProgress.hasInitialAssessmentBeenConducted == 1);

    CoS.setStatusState('#liIsDoAtLeastFourIssuesExist', CoS.project.projectProgress.doAtLeastFourIssuesExist == 1);
    CoS.setStatusState('#liAreObjectivesSet', CoS.project.projectProgress.doAtLeastFourIssuesExist == 1);
    CoS.setStatusState('#liIsAreIssuesRanked', CoS.project.projectProgress.areIssuesRanked == 1);
    CoS.setStatusState('#liIsAreAllDomainsCoveredByIssues', CoS.project.projectProgress.areAllDomainsCoveredByIssues == 1);
    CoS.setStatusState('#liIsAreAllIssuesMeasuredByAtLeastOneIndicator', CoS.project.projectProgress.areAllIssuesMeasuredByAtLeastOneIndicator == 1);

    CoS.setStatusState('#liIsDoAtLeastFourIndicatorsExist', CoS.project.projectProgress.doAtLeastFourIndicatorsExist == 1);
    CoS.setStatusState('#liIsHaveImpactsBetweenIssuesBeenAnalysed', CoS.project.projectProgress.areAllIssuesMeasuredByAtLeastOneIndicator == 1);

    CoS.setStatusState('#liIsReportCompiled', CoS.project.projectProgress.isReportCompiled == 1);

    CoS.setStatusState('#liIsResponseDevelopedAndMonitored', CoS.project.projectProgress.isResponseDevelopedAndMonitored == 1);
    CoS.setStatusState('#liIsModelReviewedAndAdapted', CoS.project.projectProgress.isModelReviewedAndAdapted == 1);

    CoS.addStatusHint();
};
CoS.addStatusHint = function() {
    if (! CoS.project.projectProgress.isGeneralIssueDefined == 1) {
        CoS.setStatusBalloon('#liIsGeneralIssueDefined', 'Click here to define a general issue');
    }
    else if (! CoS.project.projectProgress.hasInitialAssessmentBeenConducted == 1) {
        CoS.setStatusBalloon('#liIsHasInitialAssessmentBeenConducted', 'Click here to conduct an assessment');
    }
    else if (! CoS.project.projectProgress.doAtLeastFourIssuesExist == 1) {
        CoS.setStatusBalloon('#liIsDoAtLeastFourIssuesExist', 'Click here to conduct an assessment');
    }
    else if (! CoS.project.projectProgress.areIssuesRanked == 1) {
        CoS.setStatusBalloon('#liIsAreIssuesRanked', 'Click here to conduct an assessment');
    }
    else if (! CoS.project.projectProgress.areAllDomainsCoveredByIssues == 1) {
        CoS.setStatusBalloon('#liIsAreAllDomainsCoveredByIssues', 'Click here to conduct an assessment');
    }
    else if (! CoS.project.projectProgress.doAtLeastFourIndicatorsExist == 1) {
        CoS.setStatusBalloon('#liIsDoAtLeastFourIndicatorsExist', 'Click here to conduct an assessment');
    }
    else if (! CoS.project.projectProgress.areAllIssuesMeasuredByAtLeastOneIndicator == 1) {
        CoS.setStatusBalloon('#liIsHaveImpactsBetweenIssuesBeenAnalysed', 'Click here to conduct an assessment');
    }
    else if (! CoS.project.projectProgress.areAllIssuesMeasuredByAtLeastOneIndicator == 1) {
        CoS.setStatusBalloon('#liIsAreAllIssuesMeasuredByAtLeastOneIndicator', 'Click here to conduct an assessment');
    }
    else if (! CoS.project.projectProgress.isReportCompiled == 1) {
        CoS.setStatusBalloon('#liIsReportCompiled', 'Click here to conduct an assessment');
    }
    else if (! CoS.project.projectProgress.isResponseDevelopedAndMonitored == 1) {
        CoS.setStatusBalloon('#liIsResponseDevelopedAndMonitored', 'Click here to conduct an assessment');
    }
    else if (! CoS.project.projectProgress.isModelReviewedAndAdapted == 1) {
        CoS.setStatusBalloon('#liIsModelReviewedAndAdapted', 'Click here to conduct an assessment');
    }
};

CoS.setStatusBalloon = function(item, message) {
    $(item).showBalloon({ contents: message, position: "right" })
        .mouseout(function(){ $(this).hideBalloon(); } )
        .click(function(){ $(this).hideBalloon(); } );
};

CoS.setStatusState = function(item, value) {
    var doneColor = ''
    if (value)
        $(item).css({'background-color': '#e4f99E'});
    else
        $(item).css({'background-color': '#f9d49e'});
};

CoS.updateProjectStatus = function() {
    if (this.doProjectStatus) {
        cosj = $.getJSON("/projectStatus");
        $.getJSON("/projectStatus", function(data) {
            if (data && data.project) {
                CoS.project = data.project;
                CoS.setStatus();
            }
        });

    }

}


