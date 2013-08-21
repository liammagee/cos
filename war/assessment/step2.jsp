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
            <h2>Step 2 - Background Considerations</h2>
            <form action="/selfassessment" method="post">
                <input type="hidden" name="step" value="3"/>
                <% if (entry != null) { %>
                    <input type="hidden" name="id" value="<%= entry.getId() %>"/>
                <% } %>
                <div>
                What kinds of things indicate that a city is sustainable?<br/> 
                <textarea name="q1" rows="5" cols= "40"><%= entry.getBackgroundConsiderations()[0] %></textarea>
                </div>
                <div>
                What kinds of things (when missing or present) indicate that a city is unsustainable?<br/>
                <textarea name="q2" rows="5" cols= "40"><%= entry.getBackgroundConsiderations()[1] %></textarea>
                </div>
                <div>
                Who benefits and who loses in the current situation and how might this be changed?<br/>
                <textarea name="q3" rows="5" cols= "40"><%= entry.getBackgroundConsiderations()[2] %></textarea>
                </div>
                <div>
                What does it mean, in relation to current norms, to negotiate these matters?<br/>
                <textarea name="q4" rows="5" cols= "40"><%= entry.getBackgroundConsiderations()[3] %></textarea>
                </div>

                <input type="submit" name="submit" value="Next step...">
            </form>

        </div>

    </body>
</html>