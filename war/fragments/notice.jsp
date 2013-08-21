<%--
  Created by IntelliJ IDEA.
  User: Liam
  Date: 14/05/11
  Time: 9:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    if (request.getAttribute("notice") != null) {
        String notice = (String)request.getAttribute("notice");
%>
    <div class="notice"><%= notice %></div>
<% } %>