<%@ page import="java.util.Iterator" %>
<%@ page import="edu.rmit.sustainability.model.Project" %>
<%@ page import="java.util.List" %>
<%@ page import="edu.rmit.sustainability.model.CriticalIssue" %>
<%--
  Created by IntelliJ IDEA.
  User: E65691
  Date: 9/05/11
  Time: 2:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="project" scope="session" class="edu.rmit.sustainability.model.Project"/>

<div id="internalNav">
    Back to: <a href="#" id="backToIssues">issues</a>
</div>

<h3>Relating Issues</h3>

<div>
    <p>
        Issues often relate to each other in complex ways.
        Obtaining an objective for one issue (such as improving environmental health) often comes at the expense
        of others (such as income).

    </p>
    <p>
        We will be making available a tool for supporting decision making around how issues are related shortly.
    </p>
    <p>
        In the meantime, download the <a href="/files/SustainabilityIssueMaterialityMatrix.xls" target="_blank">assessment matrix in Excel</a>.
    </p>
</div>



<script>
    $('#updateRanking').ajaxForm({ target: $('#dataEntryContainer') });

    $('#backToIssues').click(CoS.loadIssues);

    $("#rankIssues").click(function () {
        $('#dataEntryContainer').load("/criticalIssue", { action: "rank" });
        return false;
    });

</script>