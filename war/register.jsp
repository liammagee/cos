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

        <!-- Suggestions -->
        <div id="rightcol" class="basic">
                <div>
                    <h1>Register</h1>
                    <form method="post" action="/user" id="loginForm">
                        <input type="hidden" name="action" value="doRegistration"/>
                        <table>
                            <tr>
                                <td>Username: </td>
                                <td><input type="text" name="login" id="login" value="" size="12"></td>
                            </tr>
                            <tr>
                                <td>Password: </td>
                                <td><input type="password" name="password" id="password" value="" size="12"></td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <input type="submit" id="loginSubmit" name="submit" value="Register"/>
                                </td>
                            </tr>
                        </table>
                    </form>
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