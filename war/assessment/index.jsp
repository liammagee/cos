<%@ page import="edu.rmit.sustainability.client.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>

<html>
    <head>
        <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
		<link href="/stylesheets/index.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
    </head>
    <body>
        <h1>Circles of Sustainability: Self-Assessment Exercise</h1>
        <div>
The Circles of Sustainability are an indicators set which offers an interpretation of a city's or a community's
sustainability. The indicators in each segment of the large circle establish the breadth and scope of sustainability,
while the small circles offer more depth, and focus attention on key social themes. The Circles of Sustainability have been
developed by the Global Cities Institute and the United Nations Global Compact Cities Programme. 
        </div>

        <div>
            <h2>New Assessment</h2>
            Create a new <a href="/selfassessment?step=1">assessment</a>
        </div>
        <div>
            <h2>Previous Assessments</h2>
 <%
 Long keyOffsetId = null;
 String keyOffset = request.getParameter("page");
 if (keyOffset != null) keyOffsetId = Long.decode(keyOffset);
 if (keyOffset == null) keyOffset = "";
 String indexString = request.getParameter("startIndex");
 if (indexString == null) indexString = "0";
 int indexOffset = Integer.parseInt(indexString);
 List<CoSSelfAssessment> entries = new ArrayList<CoSSelfAssessment>(
 CoSSelfAssessmentUtils.getPage(keyOffsetId, indexOffset));
 CoSSelfAssessment lastEntry = null;
 if (entries.size() > 3) {
   lastEntry = entries.get(3);
   entries.remove(3);
 }

 if (entries.isEmpty()) {%>
 There are no entries.
 <%} else {
    String DATE_FORMAT = "dd/MM/yyyy";
    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

   for (CoSSelfAssessment currentEntry : entries) {
     %>
     <div>
       <div>
       <a href="/selfassessment?step=1&id=<%= currentEntry.getId() %>"><%= currentEntry.organisationName %></a>
       - <%= sdf.format(currentEntry.entryDate) %>
       <a href="/selfassessment?step=1&method=delete&id=<%= currentEntry.getId() %>">delete</a></div>
     </div>
     <br>
     <%
   }

   if (lastEntry != null) {%>
   <a href="/assessment/step1.jsp?page=<%= lastEntry.getId().toString() %>">Next page by key offset</a>
   <a href="/assessment/step1.jsp?startIndex=<%= indexOffset + 3 %>">Next page by index offset</a>
     <%
   } else {
     %>
     No more pages of entries.
     <%
   }
 }%>
        </div>
    </body>
</html>