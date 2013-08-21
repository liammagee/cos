<%@ page import="edu.rmit.sustainability.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Circles of Sustainability</title>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
    <link href="javascripts/jquery-ui/css/smoothness/jquery-ui-1.8.20.custom.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet/less" type="text/css" href="stylesheets/ungccp.less">
    <link href="stylesheets/additional.css" rel="stylesheet" type="text/css"/>


    <!-- Please feel free to use this code in any way that you would like.  If you want to be cool, you can credit me - Mani Sheriar | www.ManiSheriar.com | Design@ManiSheriar.com.  Enjoy! -->

    <script type="text/javascript" language="javascript" src="javascripts/jquery-ui/js/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" language="javascript" src="javascripts/jquery-ui/js/jquery-ui-1.8.20.custom.min.js"></script>
    <%--<script type="text/javascript" language="javascript" src="javascripts/jquery-1.6.min.js"></script>--%>
    <script type="text/javascript" language="javascript" src="javascripts/jquery.form.js"></script>
    <script type="text/javascript" language="javascript" src="javascripts/jquery.balloon.min.js"></script>
    <script type="text/javascript" language="javascript" src="javascripts/jquery-tooltip/jquery.tooltip.min.js"></script>
    <script type="text/javascript" language="javascript" src="javascripts/less-1.3.0.min.js"></script>
    <script type="text/javascript" language="javascript" src="javascripts/sustainability.js"></script>
    <script type="text/javascript" language="javascript" src="javascripts/assessment.js"></script>

</head>

<body>


<div id="page-wrap">

    <div id="header"><!-- begin header -->

        <div class="wrapper">
            <div id="logo">
                <span class="hidden">Circles of Sustainability</span>
            </div>

            <div id="menu">
                <div id="nav">
                    <div id="controlLinks">
                        <ul id="menu-main">
                            <li><a href="#" class="loadable" id="projectsLink">Projects</a></li>
                            <li><a href="#" class="loadable" id="assessLink">Assessments</a></li>
                            <li><a href="#" class="loadable" id="issuesLink">Issues</a></li>
                            <li><a href="#" class="loadable" id="indicatorsLink">Indicators</a></li>
                            <li><a href="#" class="loadable" id="reportsLink">Reports</a></li>
                            <li><a href="#" class="loadable" id="collaboratorsLink">Collaborators</a></li>
                            <li><a href="#" class="loadable" id="helpLink">Help</a></li>
                            <li><a href="/user?action=logout" id="logoutLink">Logout</a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>

    </div>
    <!-- end header -->

    <div id="body">

        <div class="wrapper">

        <!-- Side Menu -->
        <div id="side-menu">
            <div id="projectStatus" class="basic">
                <jsp:include page="fragments/project_status.jsp" />
            </div>
        </div>
            <div id="main-content">
                <div id="notifications" class="basic">
                </div>

                <div id="centercol" class="basic">
                    <div id="dataEntryContainer">
                        <jsp:include page="fragments/project_listing.jsp" />

                    </div>
                </div>


            </div>

        </div>

        <!-- Suggestions -->
        <div id="rightcol">
            <div id="suggestions" class="basic">

            </div>
        </div>
        <div style="clear:both;"></div>
    </div>
</div>



<div id="footer">
    <cite>Copyright @ RMIT University 2011, 2012</cite>
</div>

</body>
</html>