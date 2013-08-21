<%@ page import="edu.rmit.sustainability.messaging.feedback.Suggestion" %>
<%@ page import="edu.rmit.sustainability.messaging.feedback.SimpleSuggestion" %>
<%--
  Created by IntelliJ IDEA.
  User: E65691
  Date: 9/05/11
  Time: 2:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<form method="post" action="/login" id="loginForm" style="display: none">
    <table>
        <tr>
            <td>Login: </td>
            <td><input type="text" name="login" id="login" value="Liam"></td>
        </tr>
        <tr>
            <td>Password: </td>
            <td><input type="password" name="password" id="password" value="1234"></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" id="loginSubmit" name="submit" value="submit"/></td>
        </tr>
    </table>


</form>
