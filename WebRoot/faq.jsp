<%--
  User: parachuter
  Date: 14-12-09
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>微路由</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Cache-Control" content="no-cache"/>
    <meta http-equiv="Expires" content="0"/>

    <link href="./css/bootstrap.min.css" rel="stylesheet"/>
    <link href="./css/bootstrap-responsive.min.css" rel="stylesheet"/>

    <link href="./css/font-awesome.css" rel="stylesheet"/>

    <link href="./css/adminia.css" rel="stylesheet"/>
    <link href="./css/adminia-responsive.css" rel="stylesheet"/>


    <link href="./css/pages/faq.css" rel="stylesheet"/>
    <script src="./js/menu.js"></script>
    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
</head>

<body>

<div class="navbar navbar-fixed-top">

    <div class="navbar-inner">

        <div class="container">

            <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </a>

            <jsp:include page="Title.jsp"></jsp:include>

            <div class="nav-collapse">

                <ul class="nav pull-right">
                    <li>
                        <a href="#"><span class="badge badge-warning">7</span></a>
                    </li>

                    <li class="divider-vertical"></li>

                    <li class="dropdown">

                        <a data-toggle="dropdown" class="dropdown-toggle " href="#">
                            我的面板 <b class="caret"></b>
                        </a>

                        <ul class="dropdown-menu">
                            <script language="javascript">
                                getFunctionBord(${sessionScope.UserType});
                            </script>
                        </ul>
                    </li>
                </ul>

            </div>
            <!-- /nav-collapse -->

        </div>
        <!-- /container -->

    </div>
    <!-- /navbar-inner -->

</div>
<!-- /navbar -->


<div id="content">

    <div class="container">

        <div class="row">

            <div class="span3">

                <div class="account-container">

                    <div class="account-avatar">
                        <img src="${sessionScope.LogoURL}" alt="" class="thumbnail"/>
                    </div>
                    <!-- /account-avatar -->

                    <div class="account-details">

                        <span class="account-name">${sessionScope.Name}</span>

                        <span class="account-role">${sessionScope.Role}</span>

						<span class="account-actions">
							<a href="javascript:;">档案</a> |

							<a href="javascript:;">修改设置</a>
						</span>

                    </div>
                    <!-- /account-details -->

                </div>
                <!-- /account-container -->

                <hr/>

                <ul id="main-nav" class="nav nav-tabs nav-stacked">


                    <script language="javascript">
                        getMenu(${sessionScope.UserType}, "faq");
                    </script>


                </ul>


                <hr/>

                <jsp:include page="Introduction.jsp"></jsp:include>
                <!-- .sidebar-extra -->

                <br/>

            </div>
            <!-- /span3 -->


            <div class="span9">

                <h1 class="page-title">
                    <i class="icon-pushpin"></i>
                    常见问题
                </h1>

                <div class="widget">

                    <div class="widget-content">

                        <h3>搜索...</h3>


                        <ol class="faq-list">

                            <li>
                                <h4>怎么向用户发送消息？</h4>

                                <p>在左侧导航列表点击"会员列表"进入会员管理页面,然后在指定用户的操作按钮列单击<a href="#" class="btn btn-small btn-primary"><i
                                        class="icon-comment" title="发送信息"></i></a></p>

                                <p>也可以批量发送信息：在会员管理页面右侧单击全选或勾选指定客户，在右上角表头的操作菜单里面选择发送信息，即可批量发送信息。</p>
                            </li>

                            <li>
                                <h4>怎么向用户发送广告？</h4>

                                <p>在左侧导航列表点击"会员列表"进入会员管理页面,然后在指定用户的操作按钮列单击<a href="#" class="btn btn-small btn-info"><i
                                        class="icon-envelope" title="弹出广告"></i></a></p>

                                <p>也可以批量发送广告：在会员管理页面右侧单击全选或勾选指定客户，在右上角表头的操作菜单里面选择弹出广告，即可批量发送广告。</p>
                            </li>

                        </ol>

                    </div>
                    <!-- /widget-content -->

                </div>
                <!-- /widget -->


            </div>
            <!-- /span9 -->


        </div>
        <!-- /row -->

    </div>
    <!-- /container -->

</div>
<!-- /content -->


<jsp:include page="footer.jsp"></jsp:include>
<!-- /footer -->


<!-- Le javascript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="./js/jquery-1.7.2.min.js"></script>


<script src="./js/bootstrap.js"></script>
<script src="./js/faq.js"></script>

<script>

    $(function () {

        $('.faq-list').goFaq();

    });

</script>

</body>
</html>
