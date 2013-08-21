<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Circles of Sustainability</title>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
    <link rel="stylesheet/less" type="text/css" href="stylesheets/ungccp.less">
    <link href="stylesheets/additional.css" rel="stylesheet" type="text/css"/>


	<!-- Please feel free to use this code in any way that you would like.  If you want to be cool, you can credit me - Mani Sheriar | www.ManiSheriar.com | Design@ManiSheriar.com.  Enjoy! -->

    <script type="text/javascript" language="javascript" src="javascripts/jquery-1.6.min.js"></script>
    <script type="text/javascript" language="javascript" src="javascripts/jquery.form.js"></script>
    <script type="text/javascript" language="javascript" src="javascripts/less-1.3.0.min.js"></script>
</head>

<body>

<div id="page-wrap">

    <div id="header"><!-- begin header -->

        <div class="wrapper">
            <div id="logo">
                <span class="hidden"><a href="/">Circles of Sustainability</a></span>
            </div>

            <div id="menu">
                <div id="nav">
                    <div id="controlLinks">
                    </div>
                </div>
            </div>
        </div>

    </div>
    <!-- end header -->

    <div id="body">


        <!-- Side Menu -->
        <div id="side-menu">
        </div>
        <div class="wrapper homepage-wrapper">
            <div id="main-content" class="basic">
                <h1>ACCOUNTING FOR SUSTAINABILITY</h1>
                <div id="centercol">
                    <img class="alignright" title="skyo4nm" src="http://citiesprogramme.com/wp-content/uploads/2011/11/skyo4nm-300x149.jpg" alt="">
                    <p>
                        Sustainability is increasingly being measured by organisations, cities, companies and countries around the world.
                    </p>

                    <strong>Defining Sustainability</strong>
                    <p>
                        A key difficulty is defining "sustainability" in ways that make sense to stakeholders, and also comply with
                        emerging reporting and accounting standards.
                    <em>Circles of Sustainability</em> is a system that aims to support both of these aims.
                        It uses a general database of indicators developed by organisations such as the GRI (Global Reporting Initiative), OECD and others.
                        However it also supports the development of custom indicators, to measure specific local sustainability issues.
                    </p>

                    <strong>Measuring Sustainability</strong>
                    <p>
                        Sustainability as a concept is very difficult to measure.
                        Especially in establishing academically sound and practical outcomes that are of use to stakeholders and that meet reporting standards.
                        Circles of Sustainability is a system that aims to support both of these aims.
                        It uses a general database of indicators developed by organisations such as the GRI (Global Reporting Initiative), OECD and others.
                        However it also supports the development of custom indicators, to measure specific local sustainability issues.
                    </p>

                    <p>
                    It serves as an important tool in articulating measurable goals for project administrators.
                        This is achieved by establishing stakeholder concerns and then working through to develop more specific indicators to assesses sustainability.
                        A project is defined by ascertaining what the broad issue is and then selecting indicators to describe and facilitate a measurable goal.
                        <em>CoS</em> is a good instrument to stimulate an initial sustainability test.
                    </p>

                    <strong>Introducing <em>Circles of Sustainability</em></strong>
                    <p>
                    Circles of Sustainability (<em>CoS</em>) is a practical user-friendly tool that project administrators can utilise to implement and track their sustainable projects with measurable outcomes.
                            It also serves to develop a reporting template. <em>CoS</em> main objectives are to develop methodology for communities and develop ways of measuring sustainability that reflect the values that relate to the particular city or community.
                            In this respect sustainability is defined in relation to a project.
                    </p>

                <p>
                <em>CoS</em> seeks to provide an online portal for sustainable development based projects to have more focus and direction by providing a template to work off for initial evaluation.
                        By filling out background questionnaire-based questions surrounding an existing problem, <em>CoS</em> acts as an effective tool for developing a reporting template to inform primary evaluation.
                        It begins with a broad project idea and narrows down to key issues and indicators to solve the problem at hand.
                        Issues are as a result more specific and can be viewed visually to ensure they are effectively implemented within the given community.
                        As such it provides a tangible user-driven resource to assesses’ levels of sustainability to more effectively manage sustainability projects.
                    </p>
                </div>
            </div>

        <!-- Suggestions -->
        <div id="rightcol" class="basic">
                <div>
                    <h1>Login</h1>
                    <form method="post" action="/user" id="loginForm">
                        <input type="hidden" name="action" value="login"/>
                        <table>
                            <tr>
                                <td>Login: </td>
                                <td><input type="text" name="login" id="login" value="Liam" size="12"></td>
                            </tr>
                            <tr>
                                <td>Password: </td>
                                <td><input type="password" name="password" id="password" value="1234" size="12"></td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <input type="submit" id="loginSubmit" name="submit" value="Login"/>
                                </td>
                            </tr>
                        </table>
                    </form>
                    <p>New user? Please create an <a href="/user?action=register">account here</a>.</p>
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