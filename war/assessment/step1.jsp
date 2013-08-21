<%@ page import="edu.rmit.sustainability.client.*" %>
 <%@ page import="java.util.ArrayList" %>
 <%@ page import="java.util.List" %>
 <%@ page import="java.text.SimpleDateFormat" %>

 <%@include file="getentry.jsp" %>

 <html>
    <head>
        <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
		<link href="/stylesheets/index.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
    </head>
    <body>
        <%@include file="nav.jsp" %>
        <div>
            <h2>Step 1 - Enter basic details about your city or organisation</h2>
        </div>
        <div>
            <form action="/selfassessment" method="post">
                <input type="hidden" name="step" value="2"/>
                <% if (entry != null) { %>
                    <input type="hidden" name="id" value="<%= entry.getId() %>"/>
                <% } %>
                Enter the name of your city or organisation:
                <br/><input type="text" name="organisationName" value="<%= entryName %>"/>
                <div>
                <input type="submit" name="submit" value="Continue...">
                </div>
            </form>

        </div>



    </body>
</html>