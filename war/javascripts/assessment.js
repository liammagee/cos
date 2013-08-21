

var CoS = CoS || {};
CoS.Assessment = CoS.Assessment || {
    domains: ['Culture', 'Politics', 'Ecology', 'Economy'],
    economicSubdomains: [
        'Habitat and Land'
        , 'Emission and Waste'
        , 'Place and Abode'
        , 'Infrastructure and Constructions'
        , 'Flora and Fauna'
        , 'Materials and Energy'
        , 'Water and Air'
    ],
    ecologicalSubdomains: [
        'Technology and Fabrication'
        , 'Resourcing and Production'
        , 'Wealth and Allocation'
        , 'Consumption and Use'
        , 'Exchange and Transfer'
        , 'Regulation and Counting'
        , 'Labour and Welfare'
    ],
    politicalSubdomains: [
        'Dialogue and Reconciliation'
        , 'Ethics and Duty'
        , 'Organization and Governance'
        , 'Law and Justice'
        , 'Security and Conflict'
        , 'Representation and Negotiation'
        , 'Communication and Reconciliation'
    ],
    culturalSubdomains: [
        'Engagement and Affiliation'
        , 'Memory and Projection'
        , 'Health and Recreation'
        , 'Gender and Reproduction'
        , 'Enquiry and Learning'
        , 'Belief and Faith'
        , 'Symbolism and Creativity'
    ]
};

var domains;
var economicSubdomains, ecologicalSubdomains, politicalSubdomains, culturalSubdomains;
var currentDomain, currentSubdomain, currentDomainId, currentSubdomainId;



CoS.Assessment.drawSegment = function(ctx, quadrant, sector, extent) {
    var colours = ["#FF2002", "#FF7A2D", "#FFA530", "#FFC919", "#FFFB2B", "#EDFF2D", "#D1FF47", "#96FF47", "#23FF3D"];
    var x = 400, y = 300;
    var useSameArea = true;
    var radius = 270;
    var numCircles = 9;
    var maxArea = Math.pow(radius, 2) * Math.PI;
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
    ctx.arc(x, y, radius, startAngle, endAngle, false);
    ctx.closePath();
    ctx.fillStyle = '#fff';
    ctx.fill();

    ctx.beginPath();
    ctx.moveTo(x, y);
    ctx.arc(x, y, newRadius, startAngle, endAngle, false);
    ctx.closePath();
    ctx.fillStyle = colour;
    ctx.fill();

}

CoS.Assessment.drawSegmentLines = function(ctx) {
    var x = 400, y = 300;
    var useSameArea = true;
    var radius = 270;

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
    ctx.closePath();
    ctx.strokeStyle = "#ccc";
    ctx.stroke();
}

CoS.Assessment.drawCircles = function(ctx) {
    var x = 400, y = 300;
    var useSameArea = true;
    var radius = 270;
    var maxArea = Math.pow(radius, 2) * Math.PI;
    var numCircles = 9;

    for (var i = numCircles; i > 0; i -= 1) {
        var newRadius = radius * i / numCircles;
        if (useSameArea) {
            var newArea = maxArea * i / numCircles;
            newRadius = Math.pow(newArea / Math.PI, 1/2);
        }
        ctx.moveTo(x + newRadius, y);
        ctx.arc(x, y, newRadius, 0, Math.PI * 2, false);
    }
    ctx.closePath();
    ctx.strokeStyle = "#ccc";
    ctx.stroke();

}

CoS.Assessment.drawAxes = function(ctx) {
    var x = 400, y = 300;

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
