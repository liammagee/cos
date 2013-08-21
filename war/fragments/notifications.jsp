<%@ page import="edu.rmit.sustainability.messaging.feedback.Suggestion" %>
<%@ page import="edu.rmit.sustainability.messaging.feedback.SimpleSuggestion" %>
<%@ page import="edu.rmit.sustainability.messaging.feedback.Feedback" %>
<%@ page import="edu.rmit.sustainability.messaging.feedback.Notification" %>
<%@ page import="edu.rmit.sustainability.model.Indicator" %>
<%@ page import="edu.rmit.sustainability.model.User" %>
<%--
  Created by IntelliJ IDEA.
  User: E65691
  Date: 9/05/11
  Time: 2:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<h4>NOTIFICATIONS</h4>

<div style="color: #fff">
    Welcome <%= ((User)session.getAttribute("user")).getUsername()%>!
</div>


<div id="notificationList">
<%
    Notification[] notifications = (Notification[])request.getAttribute("notifications");
    if (notifications != null) {
%>
<ul>
<%
for (int i = 0; i < notifications.length; i++) {
    Notification notification = notifications[i];
    if (notification != null) {
%>
    <li><%= notification.toString() %></li>
<%
    }
 }
%>
</ul>
<p><a href="%" id="toggleNotifications">Clear Notifications</a></p>
<script>
    $("#toggleNotifications").click(function () {
        if (CoS.doNotifications) {
            $("#notificationList").empty();
            $("#toggleNotifications").html("Show Notifications");
            CoS.doNotifications = false;
        }
        else {
            $("#toggleNotifications").html("Clear Notifications");
            CoS.doNotifications = true;
        }
        return false;
    });
</script>
<%
    }
%>

</div>
