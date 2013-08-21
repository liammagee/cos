<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="edu.rmit.sustainability.model.*" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    CriticalIssue criticalIssue = ((CriticalIssue)session.getAttribute("issue"));
    List projectIndicators = (List)request.getAttribute("indicators");
    List standardIndicatorSets = (List)request.getAttribute("indicatorSets");
%>

<h2>Project Indicators</h2>

<div>
    Project indicators are indicators (variables, measures, data points) that you
    can use to monitor a particular issue and objective.
</div>

<a href="indicator_listing.jsp#" id="newIndicator">Create a new indicator</a>
<% if (projectIndicators != null && projectIndicators.size() > 0) {
%>
<table>
    <tr>
        <th>Indicator</th>
        <th>Code</th>
        <th>Target</th>
        <th>Subdomain</th>
        <th></th>
    </tr>

    <%	Iterator i = projectIndicators.iterator();
        while (i.hasNext()) {
            Indicator indicator = (Indicator)i.next();
    %>
    <tr>
        <td>
            <p><a href="#" class="indicatorLinks" id="<%= indicator.getRdfId().value() %>"><%= indicator.getName() %></a></p>
        </td>
        <td>
            <%= indicator.getIdentifyingCode() %>
        </td>
        <td>
            <%= indicator.getTarget().getValue() %>
        </td>
        <td>
            <% if (indicator.getSubdomain() != null)  { %>
            <%= indicator.getSubdomain().getName() %>
            <% } %>
        </td>
        <td>
            <a href="#" class="editIndicatorLinks" id="<%= indicator.getId() %>"><img src="/images/icons/report_edit.png"/></a> |
            <a href="#" class="deleteIndicatorLinks" id="del_<%= indicator.getId() %>"><img src="/images/icons/delete.png"/></a>
            <% if (criticalIssue != null && criticalIssue.getName() != null)  { %>
            | <a href="#" class="assignIndicatorLinks" id="assign_<%= indicator.getRdfId().value() %>">
            Add to <%= criticalIssue.getName() %>
        </a>
            <% } %>
        </td>
    </tr>
    <% } %>
</table>
<% } %>


<h2 style="margin-top: 1em">Standard Indicators</h2>

<div>
    Standard indicators are those developed by other organisations, which may be useful for your project and issues.
</div>

<%
    if (standardIndicatorSets != null && standardIndicatorSets.size() > 0) {
%>
<table>
    <tr>
        <th>Indicator</th>
        <th>Code</th>
        <th>Target</th>
        <th>Subdomain</th>
        <th>Actions</th>
    </tr>
<%
    Iterator i = standardIndicatorSets.iterator();
    while (i.hasNext()) {
        IndicatorSet indicatorSet = (IndicatorSet)i.next();
 %>
    <tr>
        <td colspan="5">
            <strong><a href="#" class="publicIndicatorSetLinks" id="<%= indicatorSet.getId() %>"><%= indicatorSet.getName() %></a></strong>
        </td>
    </tr>
    <%
        for (Iterator<Indicator> iterator = indicatorSet.getIndicators().iterator(); iterator.hasNext(); ) {
            Indicator indicator = iterator.next();
    %>
    <tr>
        <td>
            <p><a href="#" class="publicIndicatorLinks" id="<%= indicator.getId() %>"><%= indicator.getName() %></a></p>
        </td>
        <td>
            <p><%= indicator.getIdentifyingCode() %></p>
        </td>
        <td>
            <p><%= indicator.getTarget().getValue() %></p>
        </td>
        <td>
            <p>
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
            </p>
        </td>
        <td>
            <p>
                <% if (criticalIssue != null && criticalIssue.getName() != null)  { %>
                    <a href="#" class="assignIndicatorLinks" id="assign_<%= indicator.getRdfId().value() %>">
                        Add to <%= criticalIssue.getName() %>
                    </a>
                <% } %>
            </p>
        </td>
    </tr>
    <%
        }
    %>
<% } %>
</table>
<% } %>


<script>

$("#newIndicator").click(function () {
        $('#dataEntryContainer').html('<img src="/images/ajax-large-loader.gif"/>').load("/indicator", { action: "new" });
        return false;
    });

    $(".publicIndicatorLinks").click(function () {
        var indicatorID = $(this).attr('id');

        $('#dataEntryContainer').html('<img src="/images/ajax-large-loader.gif"/>').load("/indicator", { action: "show", indicatorID: indicatorID });
        return false;
    });

    $(".indicatorLinks").click(function () {
        var indicatorID = $(this).attr('id');

        $('#dataEntryContainer').html('<img src="/images/ajax-large-loader.gif"/>').load("/indicator", { action: "show", indicatorID: indicatorID });
        return false;
    });

    $(".editIndicatorLinks").click(function () {
        var indicatorID = $(this).attr('id');

        $('#dataEntryContainer').html('<img src="/images/ajax-large-loader.gif"/>').load("/indicator", { action: "edit", indicatorID: indicatorID });
        return false;
    });

    $(".deleteIndicatorLinks").click(function () {
        var indicatorID = $(this).attr('id');
        indicatorID = indicatorID.split('del_')[1];

        $('#dataEntryContainer').html('<img src="/images/ajax-large-loader.gif"/>').load("/indicator", { action: "delete", indicatorID: indicatorID });
        return false;
    });

    $(".assignIndicatorLinks").click(function () {
        // Strip ID
        var indicatorId = $(this).attr('id');
        if (indicatorId.indexOf("assign_") == 0)
            indicatorId = indicatorId.split("assign_")[1];

        $('#dataEntryContainer').html('<img src="/images/ajax-large-loader.gif"/>').load("/criticalIssue", { action: "assign", indicatorID: indicatorId });
        return false;
    });
</script>