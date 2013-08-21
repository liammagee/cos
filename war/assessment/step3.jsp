<%@include file="getentry.jsp" %>
<html>
    <head>
		<link href="/stylesheets/index.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <%@include file="nav.jsp" %>
        <div>

            <h2>Step 3 - Rate the general awareness of the issue</h2>
<style type="text/css">
input#url, textarea#spec {
  font-family: courier;
  font-size: 12px;
  padding:3px;
  background-color:#EFEFEF;
  margin-left:0;
  overflow:auto;
}
</style>

<!--
<p>This page provides a way to test your chart URLs quickly and easily. Paste your
  URL into the URL text box, and edit the parameters in the Parameters box. The chart
  and both text boxes update as you type.</p>
<p><strong>Tips:</strong></p>
<ul>
  <li>Newlines with no indentation are equivalent to &quot;&amp;&quot; in the URL.</li>
  <li>Indented newlines are equivalent to &quot;|&quot; in the URL.</li>
  <li>Click &quot;Reformat Parameters&quot; to clean up the indentation and layout
    in the parameters box, for ease of editing.</li>
  <li>Autocomplete matches both parameter strings (<code>chxt</code>) and
    parameter friendly names (&quot;Chart titles&quot;) as you type.</li>
</ul>
-->
<p style="display: none"><strong>URL:</strong> (<a id="link" href="/apis/chart/docs/chart_playground.html"><em>Link
      to this page</em></a> )<br/>
  <input type="text" name="url" id="url" size="118" value=""
    onkeydown="convert()" onkeyup="convert()" />
</p>



<table class="columns" width="100%">
  <tr>
    <td width="35%" style="border:none; padding-left:0; padding-right: 10px; vertical-align:top;">
        <form name="assess" method="post" action="/selfassessment">
        <input type="hidden" name="step" value="4"/>
        <input type="hidden" name="id" value="<%= request.getAttribute("id") %>"/>
        <input type="hidden" name="q" value="<%= request.getAttribute("q") %>"/>
        <div>
        <%
        Object qtmp = request.getAttribute("q");
        if (qtmp == null)
            qtmp = "1";
        int q = Integer.parseInt(qtmp.toString());
        String question = "";
        switch (q) {
           case 4:
            question = "How well have responses to this issue been monitored across each domain?";
            break;
           case 3:
            question = "How appropriate have been the resources brought to bear on this issue in relation to each domain?<br/>";
            break;
           case 2:
            question = "How adequate have been the practical responses to this issue in relation to each domain?<br/>";
            break;
           case 1:
           default:
            question = "What is the depth of awareness of the issue in relation to each domain?";
            break;
        }
        %>
        <%
        %>
        <strong><%= question %></strong><br>
        <div>
        <%
        String []responses = null;
        int q_num = 1;
        if (request.getAttribute("q") != null)
            q_num = ((Integer)request.getAttribute("q")).intValue();
        switch(q_num) {
        case 1:
            responses = entry.getLevelOneQuestion1Responses();
            break;
        case 2:
            responses = entry.getLevelOneQuestion2Responses();
            break;
        case 3:
            responses = entry.getLevelOneQuestion3Responses();
            break;
        case 4:
            responses = entry.getLevelOneQuestion4Responses();
            break;
        }
        for (int i = 0; i < 4; i ++) {
            String domain = "";
            switch (i) {
                case 0:
                    domain = "Ecological";
                    break;
                case 1:
                    domain = "Economic";
                    break;
                case 2:
                    domain = "Political";
                    break;
                case 3:
                    domain = "Cultural";
                    break;
            }
            boolean []checkedOptions = {false, false, false, false, false};
            if (responses != null && responses.length > 0 && responses[i] != null) {
                int option = Integer.parseInt(responses[i]);
                checkedOptions[option - 1] = true;
            }
            else {
                checkedOptions[2] = true;
            }
            %>
            <div>
            <%= domain %>:<br/>
            <%
            for (int j = 0; j < 5; j ++) {
                String label = "";
                String checked = (checkedOptions[j] ? "checked=\"checked\"" : "");
                switch (j) {
                    case 0:
                        label = "unsatisfactory";
                        break;
                    case 1:
                        label = "minimal";
                        break;
                    case 2:
                        label = "satisfactory";
                        break;
                    case 3:
                        label = "good";
                        break;
                    case 4:
                        label = "excellent";
                        break;
                }
                %>
                    <input type="radio" id="d<%= i + 1 %>" name="d<%= i + 1 %>" value="<%= j + 1 %>" onclick="randomSegments();" <%= checked %>/><%= label %> 
                <%
            }
            %>
            </div>
            <%
        }
        %>
        </div>
        <input type="submit" value="Next question...">
        </form>
      <textarea name="spec" cols=50 rows=20 id="spec" onkeydown="update()" onkeyup="update()" style="display: none">
cht=pc
chl=Culture
    Politics
    Economy
    Ecology
chs=600x450
chd=t:50,50,50,50
    50,50,50,50
    50,50,50,50
    50,50,50,50
    50,50,50,50
chf=c,s,eeeeee|bg,s,EFEFEF
chco=00FF00
    FFFF00
    FF0000
    0000FF,33FF33
    FFFF33
    FF3333
    3333FF,66FF66
    FFFF66
    FF6666
    6666FF,FFFFFF
    FFFFFF
    FFFFFF
    FFFFFF,FFFFFF
    FFFFFF
    FFFFFF
    FFFFFF</textarea>
    <!--
      <div align="left">
        <button onclick="update(); convert()" title="Click to format the text in the parameters box nicely and unescape URL-safe characters.">Reformat
        Parameters</button>
      </div>
      -->
      </td valign="top">
    <td width="79%" style="vertical-align:top; padding-bottom:20px"><strong>Generated
        Chart:</strong><br>
      <img style="border:1px black solid;" src="#" id="chart" alt="Generated chart"/>
      <script type="text/javascript" src="/javascripts/googlecharts.js"/>
        <script>
            randomSegments();
        </script>

  </tr>
</table>
