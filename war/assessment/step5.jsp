<%@ page import="edu.rmit.sustainability.client.*" %>
 <%@ page import="java.text.SimpleDateFormat" %>
<%@include file="getentry.jsp" %>
<%
int d1 = 0;
int d2 = 0;
int d3 = 0;
int d4 = 0;
String colours = "";

%>
<html>
    <head>
		<link href="/stylesheets/index.css" rel="stylesheet" type="text/css" />
        <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    </head>
    <body>
        <%@include file="nav.jsp" %>
        <div>

        <h2>Self-Assessment Report</h2>

            <%
            if (entry != null) {
                String DATE_FORMAT = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            %>
            <div><em>Name of assessing organisation:</em><br/> - <%= entry.organisationName %></div>
            <div><em>Date of assessment:</em><br/> - <%= sdf.format(entry.entryDate) %></div>
            <div>
                <h3>Background considerations</h3>
                <div>
                    
                <em>
                What kinds of things indicate that a city is sustainable?<br/>
                </em>
                 - <%= entry.backgroundConsiderations[0] %>
                </div>
                <br/>
                <div>
                <em>
                What kinds of things (when missing or present) indicate that a city is unsustainable?<br/>
                </em>
                 - <%= entry.backgroundConsiderations[1] %>
                </div>
                <br/>
                <div>
                <em>
                Who benefits and who loses in the current situation and how might this be changed?<br/>
                </em>
                 - <%= entry.backgroundConsiderations[2] %>
                </div>
                <br/>
                <div>
                <em>
                What does it mean, in relation to current norms, to negotiate these matters?<br/>
                </em>
                 - <%= entry.backgroundConsiderations[3] %>
                </div>
            </div>
            <div>
                <h3>Q1: What is the depth of awareness of the issue in relation to each domain?</h3>
                <div>
                <table>
                    <tr>
                    <td>
                        <em>
                        Ecological:
                        </em>
                    </td>
                    <td>
                        <%= entry.levelOneQuestion1Responses[0] %> out of 5.
                    </td>
                    </tr>
                    <tr>
                    <td>
                        <em>
                        Economic:
                        </em>
                    </td>
                    <td>
                        <%= entry.levelOneQuestion1Responses[1] %> out of 5.
                    </td>
                    </tr>
                    <tr>
                    <td>
                        <em>
                        Political:
                        </em>
                    </td>
                    <td>
                        <%= entry.levelOneQuestion1Responses[2] %> out of 5.
                    </td>
                    </tr>
                    <tr>
                    <td>
                        <em>
                        Cultural:
                        </em>
                    </td>
                    <td>
                        <%= entry.levelOneQuestion1Responses[3] %> out of 5.
                    </td>
                    </tr>
                </table>
                </div>
                <%
                d1 = Integer.parseInt(entry.levelOneQuestion1Responses[3]);
                d2 = Integer.parseInt(entry.levelOneQuestion1Responses[2]);
                d3 = Integer.parseInt(entry.levelOneQuestion1Responses[1]);
                d4 = Integer.parseInt(entry.levelOneQuestion1Responses[0]);
                colours = "";

                for (int j = 0; j < 5; j ++) {
                    if (j > 0)
                        colours += ",";
                    String c1 = "FFFFFF";
                    String c2 = "FFFFFF";
                    String c3 = "FFFFFF";
                    String c4 = "FFFFFF";
                    String offset = Integer.toHexString(51 * j);
                    if (offset.equals("0"))
                        offset = "00";
                    if (d1 > j)
                        c1 = offset + "FF" + offset;
                    if (d2 > j)
                        c2 = "FFFF" + offset;
                    if (d3 > j)
                        c3 = "FF" + offset + "" + offset;
                    if (d4 > j)
                        c4 = offset + "" + offset + "FF";
                    colours += c1 + "|" + c2 + "|" + c3 + "|" + c4;
                }
                %>
                <div><strong>Graphical summary:</strong></div>

                <img src="http://chart.apis.google.com/chart?cht=pc&chl=Culture|Politics|Economy|Ecology&chs=600x400&chd=t:50,50,50,50|50,50,50,50|50,50,50,50|50,50,50,50|50,50,50,50&chco=<%= colours %>"/>
            </div>
            <div>
                <h3>Q2: How adequate have been the practical responses to this issue in relation to each domain?</h3>
                <div>
                <table>
                    <tr>
                    <td>
                        <em>
                        Ecological:
                        </em>
                    </td>
                    <td>
                        <%= entry.levelOneQuestion2Responses[0] %> out of 5.
                    </td>
                    </tr>
                    <tr>
                    <td>
                        <em>
                        Economic:
                        </em>
                    </td>
                    <td>
                        <%= entry.levelOneQuestion2Responses[1] %> out of 5.
                    </td>
                    </tr>
                    <tr>
                    <td>
                        <em>
                        Political:
                        </em>
                    </td>
                    <td>
                        <%= entry.levelOneQuestion2Responses[2] %> out of 5.
                    </td>
                    </tr>
                    <tr>
                    <td>
                        <em>
                        Cultural:
                        </em>
                    </td>
                    <td>
                        <%= entry.levelOneQuestion2Responses[3] %> out of 5.
                    </td>
                    </tr>
                </table>
                </div>
                <%
                d1 = Integer.parseInt(entry.levelOneQuestion2Responses[3]);
                d2 = Integer.parseInt(entry.levelOneQuestion2Responses[2]);
                d3 = Integer.parseInt(entry.levelOneQuestion2Responses[1]);
                d4 = Integer.parseInt(entry.levelOneQuestion2Responses[0]);
                colours = "";

                for (int j = 0; j < 5; j ++) {
                    if (j > 0)
                        colours += ",";
                    String c1 = "FFFFFF";
                    String c2 = "FFFFFF";
                    String c3 = "FFFFFF";
                    String c4 = "FFFFFF";
                    String offset = Integer.toHexString(51 * j);
                    if (offset.equals("0"))
                        offset = "00";
                    if (d1 > j)
                        c1 = offset + "FF" + offset;
                    if (d2 > j)
                        c2 = "FFFF" + offset;
                    if (d3 > j)
                        c3 = "FF" + offset + "" + offset;
                    if (d4 > j)
                        c4 = offset + "" + offset + "FF";
                    colours += c1 + "|" + c2 + "|" + c3 + "|" + c4;
                }
                %>
                <img src="http://chart.apis.google.com/chart?cht=pc&chl=Culture|Politics|Economy|Ecology&chs=600x400&chd=t:50,50,50,50|50,50,50,50|50,50,50,50|50,50,50,50|50,50,50,50&chco=<%= colours %>"/>
            </div>
            <div>
                <h3>Q3: How appropriate have been the resources brought to bear on this issue in relation to each domain?</h3>
                <div>
                <table>
                    <tr>
                    <td>
                        <em>
                        Ecological:
                        </em>
                    </td>
                    <td>
                        <%= entry.levelOneQuestion3Responses[0] %> out of 5.
                    </td>
                    </tr>
                    <tr>
                    <td>
                        <em>
                        Economic:
                        </em>
                    </td>
                    <td>
                        <%= entry.levelOneQuestion3Responses[1] %> out of 5.
                    </td>
                    </tr>
                    <tr>
                    <td>
                        <em>
                        Political:
                        </em>
                    </td>
                    <td>
                        <%= entry.levelOneQuestion3Responses[2] %> out of 5.
                    </td>
                    </tr>
                    <tr>
                    <td>
                        <em>
                        Cultural:
                        </em>
                    </td>
                    <td>
                        <%= entry.levelOneQuestion3Responses[3] %> out of 5.
                    </td>
                    </tr>
                </table>
                </div>
                <%
                d1 = Integer.parseInt(entry.levelOneQuestion3Responses[3]);
                d2 = Integer.parseInt(entry.levelOneQuestion3Responses[2]);
                d3 = Integer.parseInt(entry.levelOneQuestion3Responses[1]);
                d4 = Integer.parseInt(entry.levelOneQuestion3Responses[0]);
                colours = "";

                for (int j = 0; j < 5; j ++) {
                    if (j > 0)
                        colours += ",";
                    String c1 = "FFFFFF";
                    String c2 = "FFFFFF";
                    String c3 = "FFFFFF";
                    String c4 = "FFFFFF";
                    String offset = Integer.toHexString(51 * j);
                    if (offset.equals("0"))
                        offset = "00";
                    if (d1 > j)
                        c1 = offset + "FF" + offset;
                    if (d2 > j)
                        c2 = "FFFF" + offset;
                    if (d3 > j)
                        c3 = "FF" + offset + "" + offset;
                    if (d4 > j)
                        c4 = offset + "" + offset + "FF";
                    colours += c1 + "|" + c2 + "|" + c3 + "|" + c4;
                }
                %>
                <img src="http://chart.apis.google.com/chart?cht=pc&chl=Culture|Politics|Economy|Ecology&chs=600x400&chd=t:50,50,50,50|50,50,50,50|50,50,50,50|50,50,50,50|50,50,50,50&chco=<%= colours %>"/>
            </div>
            <div>
                <h3>Q4: How well have responses to this issue been monitored across each domain?</h3>
                <div>
                <table>
                    <tr>
                    <td>
                        <em>
                        Ecological:
                        </em>
                    </td>
                    <td>
                        <%= entry.levelOneQuestion4Responses[0] %> out of 5.
                    </td>
                    </tr>
                    <tr>
                    <td>
                        <em>
                        Economic:
                        </em>
                    </td>
                    <td>
                        <%= entry.levelOneQuestion4Responses[1] %> out of 5.
                    </td>
                    </tr>
                    <tr>
                    <td>
                        <em>
                        Political:
                        </em>
                    </td>
                    <td>
                        <%= entry.levelOneQuestion4Responses[2] %> out of 5.
                    </td>
                    </tr>
                    <tr>
                    <td>
                        <em>
                        Cultural:
                        </em>
                    </td>
                    <td>
                        <%= entry.levelOneQuestion4Responses[3] %> out of 5.
                    </td>
                    </tr>
                </table>
                </div>
                <%
                d1 = Integer.parseInt(entry.levelOneQuestion4Responses[3]);
                d2 = Integer.parseInt(entry.levelOneQuestion4Responses[2]);
                d3 = Integer.parseInt(entry.levelOneQuestion4Responses[1]);
                d4 = Integer.parseInt(entry.levelOneQuestion4Responses[0]);
                colours = "";

                for (int j = 0; j < 5; j ++) {
                    if (j > 0)
                        colours += ",";
                    String c1 = "FFFFFF";
                    String c2 = "FFFFFF";
                    String c3 = "FFFFFF";
                    String c4 = "FFFFFF";
                    String offset = Integer.toHexString(51 * j);
                    if (offset.equals("0"))
                        offset = "00";
                    if (d1 > j)
                        c1 = offset + "FF" + offset;
                    if (d2 > j)
                        c2 = "FFFF" + offset;
                    if (d3 > j)
                        c3 = "FF" + offset + "" + offset;
                    if (d4 > j)
                        c4 = offset + "" + offset + "FF";
                    colours += c1 + "|" + c2 + "|" + c3 + "|" + c4;
                }
                %>
                <img src="http://chart.apis.google.com/chart?cht=pc&chl=Culture|Politics|Economy|Ecology&chs=600x400&chd=t:50,50,50,50|50,50,50,50|50,50,50,50|50,50,50,50|50,50,50,50&chco=<%= colours %>"/>
            </div>
            <%
            }
            %>
        </div>
    </body>
</html>