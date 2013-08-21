
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<h2>SUSTAINABILITY REPORT TEMPLATE</h2>

<div>
    <a id="viewReport" href="#">Click here</a> to view your project report template.
</div>




<script>

    $("#viewReport").click(function () {
        $.get("/report", { action: "view" }, function(data) {
            $('#dataEntryContainer').html(data);
        }, "html");
        return false;
    });

    $("#newReport").click(function () {
        $.get("/report", { action: "new" }, function(data) {
            $('#dataEntryContainer').html(data);
        }, "html");
        return false;
    });

    $(".reportLinks").click(function () {
        $.get("/report", { action: "open", projectID: $(this).attr('id') }, function(data) {
            $('#dataEntryContainer').html(data);
        }, "html");
        return false;
    });
</script>