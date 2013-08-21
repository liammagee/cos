var CHARTPLAY_URL = '/apis/chart/docs/chart_playground.html';
var HOST = 'chart.apis.google.com';
var gChartImg = document.getElementById('chart');
var debugIframe = document.getElementById('debugFrame');
var gUrlBox = document.getElementById('url');
var gSpecBox = document.getElementById('spec');
var gLink = document.getElementById('link');

/*
 * Replaces the chart img src with the specified url.
 * If it includes the debugging parameter chof, it sends the url
 * to the iframe and makes it visible, to display the debugging message.
 */
function replaceImgUrl(url){
  gChartImg.src = url;
  debugIframe.src=url.replace(/&chof=\w*/,'')+'&chof=validate';
}

/*
 * Updates the img source URL, and then
 * Unescapes the URL string, reformats it, and puts formatted text into
 * the formatted text box.
 */
function convert() {
    var url = gUrlBox.value;
    replaceImgUrl(url);
    if (url) {
      gLink.href = CHARTPLAY_URL + '?url=' + encodeURIComponent(url);
    }
    var query;
    var match = url.match(/(.*?)\?(.*)/);
    if (match) {
      query = match[2];
    } else {
      query = url;
    }
    query = query.replace(/&[a-z]+=&/g, '&');
    query = query.replace(/%20/g, '+');
    query = query.replace(/%22/g, '"');
    query = query.replace(/%27/g, "'");
    query = query.replace(/%2C/g, ',');
    query = query.replace(/%3A/g, ':');
    query = query.replace(/%3C/g, '<');
    query = query.replace(/%3E/g, '>');
    query = query.replace(/%5C/g, '\\');
    query = query.replace(/%7C/g, '|');
    var spec = query.replace(/ *& */g, '\n');
    spec = spec.replace(/ *\| */g, '\n    ');
    gSpecBox.value = spec;
}

/*
 * Updates the img source to reflect the current
 * text in the parameters box. Called when user types
 * in spec box.
 */
function update() {
    var spec = gSpecBox.value;

    // Replace comments, newlines, spaces with
    // appropriate URL elements.
    spec = spec.replace(/#.*/g, '');
    spec = spec.replace(/\n\n+/g, '\n');
    spec = spec.replace(/^\s*/, '');
    spec = spec.replace(/\s*$/, '');
    spec = spec.replace(/\n +/g, '|');
    spec = spec.replace(/ /g, '');
    spec = spec.replace(/\n/g, '&');
    var url = 'http://' + window.HOST + '/chart?' + spec;
    gUrlBox.value = url;
    replaceImgUrl(url);
    if (url) {
        gLink.href = CHARTPLAY_URL + '?url=' + encodeURIComponent(url);
    }
}

function randomSegments(){
    var q1 = radioValue(document.assess.d4);
    var q2 = radioValue(document.assess.d3);
    var q3 = radioValue(document.assess.d2);
    var q4 = radioValue(document.assess.d1);
    var i = 0;
    var j = 0;
    var colours = "";
    for (;i < 5; i++) {
        if (i > 0)
            colours += ",";
        var c1 = "FFFFFF";
        var c2 = "FFFFFF";
        var c3 = "FFFFFF";
        var c4 = "FFFFFF";
        var offset = (51 * i).toString(16);
        if (offset == "0")
            offset = "00";
        if (q1 > i)
            c1 = offset + "FF" + offset;
        if (q2 > i)
            c2 = "FFFF" + offset;
        if (q3 > i)
            c3 = "FF" + offset + "" + offset;
        if (q4 > i)
            c4 = offset + "" + offset + "FF";
        colours += c1 + "|" + c2 + "|" + c3 + "|" + c4;
    }
    var url = 'http://chart.apis.google.com/chart?cht=pc&chl=Culture|Politics|Economy|Ecology&chs=600x450&chd=t:50,50,50,50|50,50,50,50|50,50,50,50|50,50,50,50|50,50,50,50&chf=c,s,eeeeee|bg,s,EFEFEF&chco=' + colours;
    gUrlBox.value = url;
    replaceImgUrl(url);
}

function radioValue(radio) {
    var val = 0;

    for( i = 0; i < radio.length; i++ )
    {
        if (radio[i].checked == true )
            return radio[i].value;
    }
    return "";
}


// Toggle functionality
$(document).ready(function(){

  $("button#toggleValidity").click(function () {
    $("iframe#debugFrame").toggle();
    var button = $(this);
    if(button.text() == "Show errors..."){
      button.text("Hide errors");
    } else {
      button.text("Show errors...");
    }
  });

  populateAutocomplete();
});