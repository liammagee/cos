<%@ page import="java.util.Iterator" %>
<%@ page import="edu.rmit.sustainability.model.Project" %>
<%@ page import="java.util.List" %>
<%@ page import="edu.rmit.sustainability.model.Subdomain" %>
<%@ page import="edu.rmit.sustainability.model.Domain" %>
<%--
  Created by IntelliJ IDEA.
  User: E65691
  Date: 9/05/11
  Time: 2:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="indicator" scope="session" class="edu.rmit.sustainability.model.Indicator"/>


<div id="internalNav">
    Back to: <a href="#" id="backToIndicators">indicators</a>
</div>


<h2>DISPLAYING INDICATOR '<%= indicator.getName() %>'</h2>


    <div>
        Indicator name:<br/>
        <%= indicator.getName() %>
    </div>

    <div>
        Description:<br/>
        <%= indicator.getDescription() %>
    </div>

    <div>
        Code:<br/>
        <%= indicator.getIdentifyingCode() %>
    </div>

    <div>How is this indicator measured?<br/>
        <%= indicator.getUnitOfMeasure() %>
    </div>

    <div>Specify a target value for this indicator:<br/>
        <%= indicator.getTarget().getValue() %>
    </div>

    <div>
        Choose a Subdomain:<br/>
            <% if (indicator.getSubdomains() != null)  {
                for (Iterator<Subdomain> subdomainIterator = indicator.getSubdomains().iterator(); subdomainIterator.hasNext(); ) {
                    Subdomain subdomain = subdomainIterator.next();

            %>
                <div>
                    <%= subdomain.getName() %>
                </div>
            <%
                    }
                }
            %>
    </div>

<script>
    $('#backToIndicators').click(CoS.loadIndicators);

</script>