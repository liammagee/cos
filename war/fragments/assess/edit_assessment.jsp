<%@ page import="edu.rmit.sustainability.model.Domain" %>
<%@ page import="edu.rmit.sustainability.model.Subdomain" %>
<%@ page import="edu.rmit.sustainability.model.assess.AssessmentValue" %>
<%@ page import="edu.rmit.sustainability.model.CriticalIssue" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.collections.list.TreeList" %>
<%--<script type="text/javascript" language="javascript" src="javascripts/assessment.js"></script>--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="project" scope="session" class="edu.rmit.sustainability.model.Project"/>
<jsp:useBean id="assessment" scope="session" class="edu.rmit.sustainability.model.assess.Assessment"/>

<h2>Sustainability Self-Assessment</h2>

<div>
    This tool allows you to quickly assess the sustainability of your current project.
</div>

<div>
    To conduct an assessment, click on the 'Create a new Self-Assessment' link below.

    Then click on each of the subdomain segments of the circle to indicate the approximate level of sustainability.
</div>

<div>
    Once the assessment is complete, click on the 'Save Assessment' button.
    You will be able to view and compare assessments at a later time.
</div>

<div>

    <a href="#" id="newAssessment">Create a new Self Assessment</a>
    <form id="updateAssessment" action="/assessment" method="post">

        <input name="action" value="update" type="hidden"/>

        <input type="checkbox" id="showDialog" name="showDialog"> Show dialog
        <div>
            <input type="submit" id="saveAssessmentButton" name="save" value="Save Assessment"/>
        </div>
    </form>
</div>


<jsp:include page="/fragments/assess/render_assessment.jsp" />

<script type="text/javascript" language="javascript">

    var currentDomain, currentSubdomain, currentDomainId, currentSubdomainId;

    $(document).ready(function() {

        $('#assessmentDialog').dialog({
            autoOpen: false,
            buttons: {
                "OK": function() {
                    var val = ($('#assessSlider').slider( "option", "value" ));
                    $(this).dialog('close');
                    updateSegment(val);
                },
                "Cancel": function() {
                    $(this).dialog('close');
                }
            }
        });
        $('#assessSlider').slider({ min: 1, max: 9, value: 5, step: 1 });

        drawAssessmentCircle();
        drawDomains();
        addHandler();
        $('#dataEntryContainer').tooltip({
            track: true,
            delay: 0,
            bodyHandler: function() { return currentSubdomain || ''; }
        })

    });

    function addHandler() {
        var canvas = $("#assessmentCanvas")[0];
        var canvasElements = $("#assessmentCanvas");
        canvas.addEventListener('click', function(e){
            var x, y;
            if (!e) var e = window.event;
            if (e.offsetX || e.offsetY) 	{
                x = e.offsetX;
                y = e.offsetY;
            }
            else if (e.pageX || e.pageY) 	{
                x = e.pageX;
                y = e.pageY;
            }
            else if (e.clientX || e.clientY) 	{
                x = e.clientX + document.body.scrollLeft
                        + document.documentElement.scrollLeft;
                y = e.clientY + document.body.scrollTop
                        + document.documentElement.scrollTop;
            }
            whichSegment(x, y);
        });
        canvas.addEventListener('mousemove', function(e){
            var x, y;
            if (!e) var e = window.event;
            if (e.offsetX || e.offsetY) 	{
                x = e.offsetX;
                y = e.offsetY;
            }
            else if (e.pageX || e.pageY) 	{
                x = e.pageX;
                y = e.pageY;
            }
            else if (e.clientX || e.clientY) 	{
                x = e.clientX + document.body.scrollLeft
                        + document.documentElement.scrollLeft;
                y = e.clientY + document.body.scrollTop
                        + document.documentElement.scrollTop;
            }
            showSubdomain(x, y);
        });
    }


    function whichSegment(x, y) {
        var canvas = $("#assessmentCanvas")[0];
        var centerX = 400;
        var centerY = 300;
        var radius = 270;
        var coordX = x - centerX;
        var coordY = y - centerY;
        var hypotenuse = Math.pow(Math.pow(coordX, 2) + Math.pow(coordY, 2), 0.5);
        if (hypotenuse < radius) {
            // Which quadrant?
            var quadrant = 0;
            if (coordX < 0 && coordY < 0) {
                quadrant = 2;
            }
            else if (coordY < 0) {
                quadrant = 3;
            }
            else if (coordX < 0) {
                quadrant = 1;
            }
            else  {
                quadrant = 0;
            }
            var angleInRadians = Math.atan(coordX / coordY);
            var angle = (angleInRadians * 2 / Math.PI) * 90;
            switch (quadrant) {
                case 0:
                    angle = (90 - angle);
                    break;
                case 1:
                    angle = (90 - angle);
                    break;
                case 2:
                    angle = 180 + (90 - angle);
                    break;
                case 3:
                    angle = 180 + (90 - angle);
                    break;
            }
            domainId = quadrant;
            currentDomain = CoS.Assessment.domains[domainId];
            subdomainId = Math.floor((angle / 360) * (4 * 7));
            if (subdomainId < 7)
                currentSubdomain = CoS.Assessment.culturalSubdomains[subdomainId];
            else if (subdomainId < 14)
                currentSubdomain = CoS.Assessment.politicalSubdomains[subdomainId - 7];
            else if (subdomainId < 21)
                currentSubdomain = CoS.Assessment.ecologicalSubdomains[subdomainId - 14];
            else
                currentSubdomain = CoS.Assessment.economicSubdomains[subdomainId - 21];

            if ($('#showDialog').is(':checked')) {
                showAssessmentDialog();
            }
            else {
                var extent = Math.floor((hypotenuse / radius) * 10);
                var useSameArea = true;
                var numCircles = 9;
                var maxArea = Math.pow(radius, 2) * Math.PI;
                if (useSameArea) {
                    var newArea = Math.pow(hypotenuse, 2) * Math.PI;
                    extent = Math.ceil(newArea / maxArea * 9);
                }
                updateSegment(extent);
            }
        }
    }

    function showSubdomain(x, y) {
        var canvas = $("#assessmentCanvas")[0];
        var centerX = 400;
        var centerY = 300;
        var radius = 270;
        var coordX = x - centerX;
        var coordY = y - centerY;
        var hypotenuse = Math.pow(Math.pow(coordX, 2) + Math.pow(coordY, 2), 0.5);
        if (hypotenuse < radius) {
            // Which quadrant?
            var quadrant = 0;
            if (coordX < 0 && coordY < 0) {
                quadrant = 2;
            }
            else if (coordY < 0) {
                quadrant = 3;
            }
            else if (coordX < 0) {
                quadrant = 1;
            }
            else  {
                quadrant = 0;
            }
            var angleInRadians = Math.atan(coordX / coordY);
            var angle = (angleInRadians * 2 / Math.PI) * 90;
            switch (quadrant) {
                case 0:
                    angle = (90 - angle);
                    break;
                case 1:
                    angle = (90 - angle);
                    break;
                case 2:
                    angle = 180 + (90 - angle);
                    break;
                case 3:
                    angle = 180 + (90 - angle);
                    break;
            }
            domainId = quadrant;
            currentDomain = CoS.Assessment.domains[domainId];
            subdomainId = Math.floor((angle / 360) * (4 * 7));
            if (subdomainId < 7)
                currentSubdomain = CoS.Assessment.culturalSubdomains[subdomainId];
            else if (subdomainId < 14)
                currentSubdomain = CoS.Assessment.politicalSubdomains[subdomainId - 7];
            else if (subdomainId < 21)
                currentSubdomain = CoS.Assessment.ecologicalSubdomains[subdomainId - 14];
            else
                currentSubdomain = CoS.Assessment.economicSubdomains[subdomainId - 21];

        }
        console.log(currentSubdomain)
//        $('#assessmentCanvas').tooltip({
//            track: true,
//            delay: 0,
//            showURL: false,
//            bodyHandler: function() {
//                return currentSubdomain;
//            }
//        })
    }

    function showAssessmentDialog() {
        $('#assessDomain').html(currentDomain);
        $('#assessSubdomain').html(currentSubdomain);
        $('#assessmentDialog').dialog('open');
    }

    function updateSegment(value) {
        saveAssessment(value);
//    drawSegment(domainId, subdomainId % 7, value);
//    drawSegmentLines();
//    drawCircles();
//    drawAxes();
    }

    function saveAssessment(value) {
        $.post("/assessment", {
                    action: "updateValue",
                    domainId: domainId,
                    subdomainId: subdomainId,
                    value: value
                },
                function(data) {
                    $('#dataEntryContainer').html(data);
                },
                "html");

    }



</script>


<div id="assessmentDialog" style="display: none;">
    <div>
        <label for="assessDomain">Domain</label>
        <span id="assessDomain"></span>
    </div>
    <div>
        <label for="assessSubdomain">Subdomain</label>
        <span id="assessSubdomain"></span>
    </div>

    <div>Rate how sustainable you think your project is against this subdomain, on a scale of 1 (least sustainable) to 9 (most sustainable):</div>
    <div id="assessSlider"></div>
</div>

<script>

    $("#newAssessment").click(function () {
        $.get("/assessment", { action: "new" }, function(data) {
            $('#dataEntryContainer').html(data);
        }, "html");
        return false;
    });

    $('#updateAssessment').ajaxForm({
        target: $('#dataEntryContainer')
    });

</script>