<%@ page import="java.util.List" %>
<%@ page import="edu.rmit.sustainability.model.Domain" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="edu.rmit.sustainability.model.Subdomain" %>
<%--<script type="text/javascript" language="javascript" src="javascripts/assessment.js"></script>--%>

<script type="text/javascript" language="javascript">
$(document).ready(function() {
    drawAssessmentCircle();
    drawDomains();
    addHandler();
});

function addHandler() {
    var canvas = $("#assessmentCanvas")[0];
    var canvasElements = $("#assessmentCanvas");
    canvasElements.at
    canvasElements.click(function(e) {
        alert(e.pageX);
        alert(canvasElements.attr('offsetLeft'));
        var xPos = canvas.attr('offsetLeft');
        var yPos = canvas.attr('offsetTop');
        alert(e.pageX - xPos);
    });
}


function drawDomains() {
    var canvas = $("#assessmentCanvas")[0];
    var ctx = canvas.getContext('2d');
    ctx.fillStyle = "#000";
//    ctx.font = "20pt Calibri";
//    ctx.fillText('CIRCLES OF SUSTAINABILITY', 380, 550);

//    ctx.font = "10pt Calibri";

    <%
     List<Domain> domains = (ArrayList<Domain>)request.getAttribute("domains");
     int xPos = 0;
     int yPos = 0;
     int counter = 0;
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

    var segments = [
        [0, 0, 4],
        [0, 5, 3],
        [1, 3, 6],
        [1, 4, 8],
        [1, 6, 1],
        [2, 2, 5],
        [2, 3, 7],
        [3, 1, 9],
        [3, 3, 2]
    ];
    var colours = ["#FF2002", "#FF7A2D", "#FFA530", "#FFC919", "#FFFB2B", "#EDFF2D", "#D1FF47", "#96FF47", "#23FF3D"];
    var x = 400;
    var y = 300;
    var radius = 270;
    ctx.lineWidth = 2;

    // Circle and Area parameters
    var numCircles = 9;
    var maxArea = Math.pow(radius, 2) * Math.PI;



    // Draw segments lines

    for (var k = 0; k < segments.length; k++) {
        var segment = segments[k];
        var quadrant = segment[0];
        var sector = segment[1];
        var extent = segment[2];
        var colour = colours[extent - 1];
        var quadrantFactor = Math.PI;
        var directionFactor = 1;
        switch(quadrant) {
            case 0:
                quadrantFactor /= -2;
                break;
            case 1:
                quadrantFactor /= -2;
                directionFactor = -1;
                break;
            case 2:
                quadrantFactor /= 2;
                directionFactor = -1;
                break;
            case 3:
                quadrantFactor /= 2;
                break;
        }

        var newRadius = radius * extent / numCircles;
        if (useSameArea) {
            var newArea = maxArea * extent / numCircles;
            newRadius = Math.pow(newArea / Math.PI, 1/2);
        }
        var startArcX = x + Math.sin(quadrantFactor * sector  / 7) * directionFactor * newRadius;
        var startArcY = y + Math.cos(quadrantFactor * sector  / 7) * directionFactor * newRadius;
        var endArcX = x + Math.sin(quadrantFactor * (sector + 1) / 7) * directionFactor * newRadius;
        var endArcY = y + Math.cos(quadrantFactor * (sector + 1)  / 7) * directionFactor * newRadius;
        var startAngle = Math.PI / 14 * (quadrant * 7 + sector);
        var endAngle = startAngle + Math.PI / 14;
        ctx.beginPath();
        ctx.moveTo(x, y);
        ctx.arc(x, y, newRadius, startAngle, endAngle, false);
        ctx.closePath();
        ctx.fillStyle = colour;
        ctx.fill();
    }

    // Draw 28 - 4 segment lines
    ctx.beginPath();
    for (var i = 0; i < 4; i ++) {
        var quadrantFactor = Math.PI;
        var directionFactor = 1;
        switch(i) {
            case 0:
                quadrantFactor /= -2;
                break;
            case 1:
                quadrantFactor /= -2;
                directionFactor = -1;
                break;
            case 2:
                quadrantFactor /= 2;
                directionFactor = -1;
                break;
            case 3:
                quadrantFactor /= 2;
                break;
        }
        for (var j = 1; j < 7; j ++) {
            ctx.moveTo(x, y);
            ctx.lineTo(x + Math.sin(quadrantFactor * j  / 7) * directionFactor * radius, y + Math.cos(quadrantFactor * j  / 7) * directionFactor * radius);
        }
    }
    for (var i = numCircles; i > 0; i -= 1) {
        var newRadius = radius * i / numCircles;
        if (useSameArea) {
            var newArea = maxArea * i / numCircles;
            newRadius = Math.pow(newArea / Math.PI, 1/2);
        }
        ctx.moveTo(x + newRadius, y);
        ctx.arc(x, y, newRadius, 0, Math.PI * 2, false);
    }
//    for (var i = numCircles; i > 0; i -= 1) {
//        var newArea = maxArea * i / numCircles;
//        var newRadius = Math.pow(newArea / Math.PI, 1/2);
//        ctx.moveTo(x + newRadius, y);
//        ctx.arc(x, y, newRadius, 0, Math.PI * 2, false);
//    }
    ctx.closePath();
    ctx.strokeStyle = "#ccc";
    ctx.stroke();


    ctx.beginPath();
    ctx.moveTo(x, y);
    ctx.lineTo(x, y - 270);
    ctx.moveTo(x, y);
    ctx.lineTo(x, y + 270);
    ctx.moveTo(x, y);
    ctx.lineTo(x - 270, y);
    ctx.moveTo(x, y);
    ctx.lineTo(x + 270, y);
    ctx.closePath();
    ctx.strokeStyle = "#000";
    ctx.stroke();
}

</script>
<div>
    <canvas id="assessmentCanvas" width="800" height="600" ></canvas>
</div>
