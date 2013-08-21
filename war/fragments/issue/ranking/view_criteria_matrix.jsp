<%@ page import="java.util.Iterator" %>
<%@ page import="edu.rmit.sustainability.model.Project" %>
<%@ page import="java.util.List" %>
<%@ page import="edu.rmit.sustainability.model.CriticalIssue" %>
<%@ page import="java.util.Map" %>
<%@ page import="edu.rmit.sustainability.model.ahp.*" %>
<%@ page import="java.util.TreeMap" %>
<%--
  Created by IntelliJ IDEA.
  User: E65691
  Date: 9/05/11
  Time: 2:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="project" scope="session" class="edu.rmit.sustainability.model.Project"/>

<h3>Criteria Matrix</h3>

<div>
    <a href="view_criteria_matrix.jsp#" id="simpleRanking">Simple Ranking</a> | <a href="view_criteria_matrix.jsp#" id="criteriaRanking">Criteria-based Ranking</a>
</div>


<hr/>




<h4>Criteria</h4>
<form id="updateRanking" action="/rankIssues" method="post">
    <input name="action" value="updateCriteriaMatrix" type="hidden"/>
<table>
    <%
        Map<Double, String> scales = new TreeMap<Double, String>();
        scales.put((double)1 / 9, "1 / 9 - Extremely Weak");
        scales.put((double)1 / 8, "1 / 8");
        scales.put((double)1 / 7, "1 / 7 - Very Weak");
        scales.put((double)1 / 6, "1 / 6");
        scales.put((double)1 / 5, "1 / 5 - Weak");
        scales.put((double)1 / 4, "1 / 4");
        scales.put((double)1 / 3, "1 / 3 - Moderate");
        scales.put((double)1 / 2, "1 / 2");
        scales.put((double)1, "1 - Equal");
        scales.put((double)2, "2");
        scales.put((double)3, "3 - Moderate");
        scales.put((double)4, "4");
        scales.put((double)5, "5 - Strong");
        scales.put((double)6, "6");
        scales.put((double)7, "7 - Very Strong");
        scales.put((double)8, "8");
        scales.put((double)9, "9 - Extremely Strong");

        AHP ahp = project.getAhp();
        CriteriaMatrix matrix = ahp.getCriteriaMatrix();
        List<MatrixRow> matrixRows = matrix.getMatrixRows();
    %>
    <tr>
        <th colspan="<%= matrixRows.size() + 1 %>">
            Consistency statistics
            <%--Consistency statistics (will only update after 'Update Criteria Matrix' is clicked).--%>
        </th>
    </tr>
    <tr>
        <td><em>Consistency Index:</em></td>
        <td colspan="<%= matrixRows.size() %>"><%= matrix.getConsistencyIndex() %></td>
    </tr>
    <tr>
        <td><em>Consistency Ratio:</em></td>
        <td colspan="<%= matrixRows.size() %>"><%= matrix.getConsistencyRatio() %></td>
    </tr>
    <tr>
        <td><em>Consistent?</em></td>
        <td colspan="<%= matrixRows.size() %>"><%= matrix.isConsistent() %></td>
    </tr>

    <tr>
        <th></th>
    <%
        for (Criterion criterion : ahp.getCriteria()) {
    %>
        <th><%= criterion.getName() %></th>
    <% } %>
    </tr>
    <%
        int rowCounter = 0;
        for (MatrixRow matrixRow : matrixRows) {
    %>
        <tr>
            <td>
                <strong><%= ahp.getCriteria().get(rowCounter).getName() %></strong>
            </td>
            <%
                int columnCounter = 0;
                for (MatrixCell matrixCell : matrixRow.getCells()) {
                    int cellID = rowCounter * matrixRows.size() + columnCounter;
            %>
                <td>
                    <% if (rowCounter >= columnCounter) { %>
                    <div id="display_<%= cellID %>">
                        <%= matrixCell.getValue() %>
                    </div>
                    <input type="hidden" id="<%= cellID %>" name="<%= matrixCell.getId() %>" value="<%= matrixCell.getValue() %>"/>
                    <% } else { %>
                    <select class="pairwiseValues" name="<%= matrixCell.getId() %>" id="<%= cellID %>" >
                    <%--<%= matrixCell.getId()%>--%>
                        <%
                            for (Iterator<Map.Entry<Double, String>> iterator = scales.entrySet().iterator(); iterator.hasNext(); ) {
                                Map.Entry<Double, String> next = iterator.next();
                                String selected = "";
                                double value = next.getKey();
                                if (value == matrixCell.getValue())
                                    selected = "selected";
                        %>
                            <option value="<%= next.getKey()%>" <%= selected%>><%= next.getValue() %></option>
                        <%
                            }
                        %>
                    </select>
                    <% }  %>

                </td>
            <%
                columnCounter++;
                }
            %>

        </tr>
    <%
            rowCounter++;
        }
    %>
</table>

    <p><input type="submit" value="Update Criteria Matrix" /> </p>
</form>





<script>
    $('#backToIssues').click(CoS.loadIssues);

    $('#updateRanking').ajaxForm({ target: $('#dataEntryContainer') });

    $("#simpleRanking").click(function () {
        $.get("/rankIssues", { action: "simpleRanking" }, function(data) {
            $('#dataEntryContainer').html(data);
        }, "html");
        return false;
    });

    $("#criteriaRanking").click(function () {
        $.get("/rankIssues", { action: "criteriaRanking" }, function(data) {
            $('#dataEntryContainer').html(data);
        }, "html");
        return false;
    });

    $("#newCriterion").click(function () {
        $.get("/rankIssues", { action: "newCriterion" }, function(data) {
            $('#dataEntryContainer').html(data);
        }, "html");
        return false;
    });

    $("#viewCriteriaMatrix").click(function () {
        $.get("/rankIssues", { action: "viewCriteriaMatrix" }, function(data) {
            $('#dataEntryContainer').html(data);
        }, "html");
        return false;
    });

    $(".criteriaLinks").click(function () {
        $.get("/rankIssues", { action: "editCriterion", criterionID: $(this).attr('id') }, function(data) {
            $('#dataEntryContainer').html(data);
        }, "html");
        return false;
    });

    $(".pairwiseValues").change(function () {
        var cellCount = parseInt('<%= matrixRows.size() %>');
        var cellID = parseInt($(this).attr("id"));
        var cellRow = Math.floor(cellID / cellCount);
        var cellColumn = cellID % cellCount;
        var reciprocalID = cellColumn * cellCount + cellRow;
        var reciprocalValue = 1 / parseFloat($(this).val());
        $('#display_' + reciprocalID).text( reciprocalValue.toString() );
        $('#' + reciprocalID).val( reciprocalValue.toString() );
    });


</script>
