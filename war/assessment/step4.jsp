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

            <h2>Step 4 - Rate the specific awareness of key social themes</h2>

        <form name="assess" method="post" action="/selfassessment">
        <input type="hidden" name="step" value="5"/>
        <input type="hidden" name="id" value="<%= request.getAttribute("id") %>"/>
        <input type="hidden" name="q" value="<%= request.getAttribute("q") %>"/>

                Choose a theme:
                 <select name="theme">
                    <option value="1">participation-authority</option>
                    <option value="2">difference-identity</option>
                    <option value="3">security-risk</option>
                    <option value="4">equality-autonomy</option>
                    <option value="5">needs-limits</option>
                    <option value="6">belonging-mobility</option>
                    <option value="7">inclusion-exclusion</option>
                 </select>

                <br/>
                [Should repeat the 4 questions x 4 domains here.]
               <div>
               <input type="submit" value="Next question...">
               </div>
             </form>
        </div>
    </body>
</html>