<%@ page import="edu.rmit.sustainability.model.Project" %>
<%@ page import="edu.rmit.sustainability.model.Indicator" %>
<%@ page import="edu.rmit.sustainability.model.Subdomain" %>
<%@ page import="edu.rmit.sustainability.model.Domain" %>
<%@ page import="java.util.*" %>
<%--
  Created by IntelliJ IDEA.
  User: E65691
  Date: 9/05/11
  Time: 2:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="issue" scope="session" class="edu.rmit.sustainability.model.CriticalIssue"/>

<div id="internalNav">
    Back to: <a href="#" id="backToIssues">issues</a>
</div>


<h2>EDITING ISSUE '<%= issue.getName() %>'</h2>

<form id="updateIssue" action="/criticalIssue" method="post">

    <input name="action" value="update" type="hidden"/>
        <div>
            <label for="issueName">Issue name:</label>
            <input type="text" id="issueName" name="name" value="<%= issue.getName() %>">
        </div>

    <div>
        <label for="issueDescription">Description:</label>
        <textarea id="issueDescription" name="description" rows="5" cols="50"><%= issue.getDescription() %></textarea>
    </div>

    <div>
        <label for="objectiveDescription">Statement of Objective:</label>

        <%
            String description = "";
            if (issue.getAssociatedObjective() != null) {
                description = issue.getAssociatedObjective().getDescription();
            }
        %>
        <textarea id="objectiveDescription" name="associatedObjective.description" rows="5" cols="50"><%= description %></textarea>
    </div>

    <div>
        <label for="objectiveDirection">
            Indicate whether the associated objective aims to <em>increase</em> (as opposed to decrease)
            the level of something associated with the issue?
        </label>

        <div>
            For example, if the issue is 'water scarcity', then the associated objective might be to <em>increase</em> water levels.
            On the other hand, an issue might be 'political corruption'; in this case the objective would be to <em>decrease</em> corruption levels.
        </div>
        <%
            String checked = "";
            if (issue.getAssociatedObjective() != null && issue.getAssociatedObjective().getDesiredDirection() == 1)
                checked = "checked";
        %>

        <span>Objective is to <em>increase</em> something?</span>
        <input type="checkbox" id="objectiveDirection" name="associatedObjective.desiredDirection" value="1" checked="<%= checked%>"/>
    </div>

    <div>
        <label for="perceivedSignificance">
            On a scale of 1 (least significant) to 9 (most significant), rate the perceived significance of this issue:
        </label>

        <select id="perceivedSignificance" name="perceivedSignificance">
            <%
                Map<Integer, String> significance = new TreeMap<Integer, String>();
                significance.put(1, "Least Significant");
                significance.put(2, "");
                significance.put(3, "");
                significance.put(4, "");
                significance.put(5, "Moderately Significant");
                significance.put(6, "");
                significance.put(7, "");
                significance.put(8, "");
                significance.put(9, "Most Significant");
                for (Iterator<Map.Entry<Integer, String>> iterator = significance.entrySet().iterator(); iterator.hasNext(); ) {
                    String selected = "";
                    Map.Entry<Integer, String> next = (Map.Entry<Integer, String>) iterator.next();
                    Integer key = next.getKey();
                    if (key.equals(issue.getPerceivedSignificance()))
                        selected = "selected";
                    String value = next.getValue();
                    if (value.length() > 0)
                        value = " - " + value;
            %>
                <option value="<%= key %>"  <%= selected %>><%= key %> <%= value %></option>
            <% } %>
        </select>
    </div>


    <div>
        <label for="subdomainId">
            Choose one or more subdomains (a relevant area or field) for this issue:
        </label>

        <select id="subdomainId" name="subdomainId" multiple="true">
            <% for (Iterator iterator = ((List<Domain>)request.getAttribute("domains")).iterator(); iterator.hasNext(); ) {
                Domain domain =  (Domain)iterator.next();
            %>
            <option value="" disabled="true"><%= domain.getName() %></option>
            <% for (Iterator<Subdomain> iterator1 = domain.getSubdomains().iterator(); iterator1.hasNext(); ) {
                Subdomain subdomain = iterator1.next();
                String selected = "";
                if (issue.getSubdomains().contains(subdomain))
                    selected = "selected";
            %>
            <option value="<%= subdomain.getRdfId().value() %>" <%= selected %>>---<%= subdomain.getName() %></option>
            <% } %>
            <% } %>
        </select>
    </div>

    <div><input type="submit" value="Save Issue" /> </div>
</form>



<h2>INDICATORS FOR '<%= issue.getName() %>'</h2>

<% if (issue.getIndicators() != null && issue.getIndicators().size() > 0) { %>
<h3>Indicators measuring this issue</h3>

<table>
    <tr>
        <th>Indicator</th>
        <th>Code</th>
        <%--<th>Domain</th>--%>
        <th>Subdomain</th>
        <th>Target</th>
    </tr>
<%
    for (Iterator<Indicator> iterator = issue.getIndicators().iterator(); iterator.hasNext(); ) {
        Indicator indicator = iterator.next();
        if (indicator != null && indicator.getRdfId() != null) {
%>
    <tr>
        <td><p>Edit <a href="#" class="indicatorLinks" id="<%= indicator.getRdfId().value() %>"><%= indicator.getName() %></a> </p></td>
        <td><%= indicator.getIdentifyingCode() %></td>
        <%--<td><%= dName %></td>--%>
        <td>
            <% if (indicator.getSubdomains() != null) {
                for (Subdomain subdomain : indicator.getSubdomains()) {
            %>
            <div><%= subdomain.getName() %> </div>
            <%
                    }
                }
            %>
        </td>
        <td><%= indicator.getTarget().getValue() %></td>
    </tr>

<%      } %>
<%  } %>
</table>
<% } %>

<div><a href="#" id="addIndicator">Add a new Indicator</a></div>


<div><a href="#" id="chooseIndicator">Choose an existing Indicator</a></div>


<script>
    $('#backToIssues').click(CoS.loadIssues);

    $('#updateIssue').ajaxForm({ target: $('#dataEntryContainer') });

    $("#addIndicator").click(function () {
        $.get("/indicator", { action: "new" }, function(data) {
            $('#dataEntryContainer').html(data);
        }, "html");
        return false;
    });

    $("#chooseIndicator").click(function () {
        $.get("/indicator", { action: "list" }, function(data) {
            $('#dataEntryContainer').html(data);
        }, "html");
        return false;
    });

    $(".indicatorLinks").click(function () {
        $.get("/indicator", { action: "edit", indicatorID: $(this).attr('id') }, function(data) {
            $('#dataEntryContainer').html(data);
        }, "html");
        return false;
    });
</script>