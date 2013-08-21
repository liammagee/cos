<%@ page import="edu.rmit.sustainability.messaging.feedback.Suggestion" %>
<%@ page import="edu.rmit.sustainability.messaging.feedback.SimpleSuggestion" %>
<%@ page import="edu.rmit.sustainability.messaging.feedback.Feedback" %>
<%@ page import="edu.rmit.sustainability.messaging.feedback.Notification" %>
<%@ page import="edu.rmit.sustainability.model.Indicator" %>
<%--
  Created by IntelliJ IDEA.
  User: E65691
  Date: 9/05/11
  Time: 2:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<h4>SUGGESTIONS</h4>
<p><a href="%" id="clearSuggestions">Clear Suggestions</a></p>
<script>
    $("#clearSuggestions").click(function () {
        $("#suggestionList").empty();
        return false;
    });
</script>
<ul>

<div id="suggestionList">
<%
Suggestion[] suggestions = (Suggestion[])request.getAttribute("suggestions");
if (suggestions != null) {
    int maxSuggestions = (suggestions.length > 10 ? 10 : suggestions.length);
for (int i = 0; i < maxSuggestions; i++) {
    Suggestion suggestion = suggestions[i];
    if (suggestion != null) {

%>
    <li style="color: black;"><%= suggestion.toString() %></li>
    <% if (suggestion.getContent() != null && suggestion.getContent() instanceof Indicator) {
        Indicator indicator = (Indicator)(suggestion).getContent();
    %>
       <ul><li>ID: <a href="#" class="assignIndicatorLinks" id="assign_<%= indicator.getId() %>"><%= indicator.getName() %></a>   </li></ul>
<%
        }
    }
 }
}
%>
</ul>
</div>

<script>

    $(".assignIndicatorLinks").click(function () {
        // Strip ID
        var indicatorId = $(this).attr('id');
        if (indicatorId.indexOf("assign_") == 0)
            indicatorId = indicatorId.split("assign_")[1];
        $.get("/criticalIssue", { action: "assign", indicatorID: indicatorId }, function(data) {
            $('#dataEntryContainer').html(data);
        }, "html");
        return false;
    });
</script>