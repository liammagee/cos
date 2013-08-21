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

<jsp:useBean id="criterion" scope="session" class="edu.rmit.sustainability.model.ahp.Criterion"/>

<div id="internalNav">
    Back to: <a href="#" id="backToIssues">issues</a>
</div>


<form id="updateCriterion" action="/rankIssues" method="post">

    <input name="action" value="updateCriterion" type="hidden"/>

    <p>Issue name:<br/>
        <input name="name" value="<%= criterion.getName() %>">
    </p>

    <p>Description:<br/>
        <textarea name="description" rows="5" cols="50"><%= criterion.getDescription() %></textarea>
    </p>


    <p>Rate the perceived significance of this criterion:<br/>
        <select name="initialWeight">
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
                    if (key.equals(criterion.getInitialWeight()))
                        selected = "selected";
                    String value = next.getValue();
                    if (value.length() > 0)
                        value = " - " + value;
            %>
                <option value="<%= key %>"  <%= selected %>><%= key %> <%= value %></option>
            <% } %>
        </select>
    </p>


    <p><input type="submit" value="Save Criterion" /> </p>
</form>



<script>
    $('#backToIssues').click(CoS.loadIssues);
    $('#updateCriterion').ajaxForm({ target: $('#dataEntryContainer') });

</script>