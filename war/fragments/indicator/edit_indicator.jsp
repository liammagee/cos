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


<h2>EDITING INDICATOR '<%= indicator.getName() %>'</h2>


<form id="updateIndicator" action="/indicator" method="post">

    <input name="action" value="update" type="hidden"/>

    <div>
        <label for="indicatorName">Indicator name:</label>
        <input type="text" id="indicatorName" name="name" value="<%= indicator.getName() %>">
    </div>
    <p>
    </p>

    <div>
        <label for="indicatorDescription">Description:</label>
        <textarea id="indicatorDescription" name="description" rows="5" cols="50"><%= indicator.getDescription() %></textarea>
    </div>

    <div>
        <label for="indicatorIdentifyingCode">Code:</label>
        <input type="text" id="indicatorIdentifyingCode" name="identifyingCode" value="<%= indicator.getIdentifyingCode() %>"> 
    </div>

    <div>
        <label for="indicatorUnitOfMeasure">How is this indicator measured?</label>
        <input type="text" id="indicatorUnitOfMeasure" name="unitOfMeasure" value="<%= indicator.getUnitOfMeasure() %>"> 
    </div>

    <div>
        <label for="indicatorTarget">Specify a target value for this indicator:</label>
        <input type="text" id="indicatorTarget" name="target.value" value="<%= indicator.getTarget().getValue() %>"> 
    </div>

    <div>
        <label for="subdomainId">
            Choose one or more subdomains (a relevant area or field) for this indicator:
        </label>

        <select id="subdomainId" name="subdomainId" multiple="true">
            <% for (Iterator iterator = ((List<Domain>)request.getAttribute("domains")).iterator(); iterator.hasNext(); ) {
                Domain domain =  (Domain)iterator.next();
            %>
            <option value="" disabled="true"><%= domain.getName() %></option>
            <% for (Iterator<Subdomain> iterator1 = domain.getSubdomains().iterator(); iterator1.hasNext(); ) {
                Subdomain subdomain = iterator1.next();
                String selected = "";
                if (indicator.getSubdomains().contains(subdomain))
                    selected = "selected";
            %>
            <option value="<%= subdomain.getRdfId().value() %>" <%= selected %>>---<%= subdomain.getName() %></option>
            <% } %>
            <% } %>
        </select>
    </div>

    <div>
        <input type="submit" value="Save Indicator" />
    </div>
</form>
<script>
    $('#backToIndicators').click(CoS.loadIndicators);

    $('#updateIndicator').ajaxForm({ target: $('#dataEntryContainer') });

</script>
