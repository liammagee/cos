
<%@ page import="java.util.*" %>
<%@ page import="edu.rmit.sustainability.model.Domain" %>
<%@ page import="edu.rmit.sustainability.model.Subdomain" %>
<%@ page import="edu.rmit.sustainability.model.assess.AssessmentValue" %>
<%@ page import="edu.rmit.sustainability.model.CriticalIssue" %>
<%@ page import="org.apache.commons.collections.list.TreeList" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="project" scope="session" class="edu.rmit.sustainability.model.Project"/>
<jsp:useBean id="assessment" scope="session" class="edu.rmit.sustainability.model.assess.Assessment"/>

<script type="text/javascript" language="javascript">

    // DO THIS TO DRAW AN ASSESSMENT
//$(document).ready(function() {
//    drawAssessmentCircle();
//    drawDomains();
//});


function drawDomains() {
    var canvas = $("#assessmentCanvas")[0];
    var ctx = canvas.getContext('2d');
    ctx.fillStyle = "#000";
//    ctx.font = "20pt Calibri";
//    ctx.fillText('CIRCLES OF SUSTAINABILITY', 380, 550);

//    ctx.font = "10pt Calibri";

<%
 int xPos = 0;
 int yPos = 0;
 int counter = 0;

 List<Domain> domains = (ArrayList<Domain>)request.getAttribute("domains");
 for(Iterator<Domain> iterator = domains.iterator(); iterator.hasNext(); counter++) {
    Domain domain = iterator.next();

   switch(counter) {
    case 0:
    xPos = 0;
    yPos = 20;
    break;
    case 1:
    xPos = 660;
    yPos = 20;
    break;
    case 2:
    xPos = 0;
    yPos = 480;
    break;
    case 3:
    xPos = 660;
    yPos = 480;
    break;
   }
%>
    ctx.fillText('<%= domain.getName().toUpperCase() %>', <%= xPos %>, <%= yPos %>);
<%
   List<Subdomain> subdomains = domain.getSubdomains();
   for(Iterator<Subdomain> subdomainIterator = subdomains.iterator(); subdomainIterator.hasNext(); ) {
     Subdomain subdomain = subdomainIterator.next();
     yPos += 15;
%>
    ctx.fillText('<%= subdomain.getName() %>', <%= xPos %>, <%= yPos %>);
<%
   }
 }
%>
}

function drawAssessmentCircle() {
    var canvas = $("#assessmentCanvas")[0];
    var ctx = canvas.getContext('2d');

    var useSameArea = true;

    var colours = ["#FF2002", "#FF7A2D", "#FFA530", "#FFC919", "#FFFB2B", "#EDFF2D", "#D1FF47", "#96FF47", "#23FF3D"];
    var x = 400;
    var y = 300;
    var radius = 270;
    ctx.lineWidth = 2;

    // Circle and Area parameters
    var numCircles = 9;
    var maxArea = Math.pow(radius, 2) * Math.PI;

    // Draw segments lines

<%
int sector = 0;
int quadrant = 0;
Domain lastDomain = null;
List<AssessmentValue> assessmentValues = assessment.getValues();
Collections.sort(assessmentValues);

for(Iterator<AssessmentValue> iterator = assessmentValues.iterator(); iterator.hasNext(); counter++) {
AssessmentValue assessmentValue = iterator.next();
double extent = assessmentValue.getValue();
Subdomain subdomain = assessmentValue.getSubdomain();
Domain domain = subdomain.getParentDomain();
if (lastDomain == null) {
    // Do nothing
}
else if (! domain.equals(lastDomain)) {
    sector = 0;
    quadrant += 1;
}
else {
    sector += 1;
}
lastDomain = domain;
%>
        CoS.Assessment.drawSegment(ctx, <%= quadrant %>, <%= sector %>, <%= extent %>);
<%
};
%>

    // Draw 28 - 4 segment lines
    CoS.Assessment.drawSegmentLines(ctx);
    CoS.Assessment.drawCircles(ctx);
    CoS.Assessment.drawAxes(ctx);
}


</script>

<div>
    <canvas id="assessmentCanvas" width="800" height="600" ></canvas>
</div>
