<%@ page import="edu.rmit.sustainability.client.*" %>
<%
   Object tmp = request.getAttribute("entry");
   CoSSelfAssessment entry = null;
   if (tmp != null)
       entry = (CoSSelfAssessment)tmp;
   String entryName = ((entry != null) ? entry.getOrganisationName() : "");
   String entryId = ((entry != null) ? entry.getId().toString() : "");
%>

<h1>Circles of Sustainability: Self-Assessment Exercise</h1>
<div id="header">
    <em>Assessment created for: <%= entryName %></em>.
</div>
<div id="nav">
    Return to <a href="/assessment/">home</a> |
    Step 1 - <a href="/selfassessment?step=1&id=<%= entryId %>">general info</a> |
    Step 2 - <a href="/selfassessment?step=2&id=<%= entryId %>">background</a> |
    Step 3 - <a href="/selfassessment?step=3&id=<%= entryId %>">domains</a> |
    Step 4 - <a href="/selfassessment?step=4&id=<%= entryId %>">themes</a> |
    Step 5 - <a href="/selfassessment?step=5&id=<%= entryId %>">report</a>
</div>
