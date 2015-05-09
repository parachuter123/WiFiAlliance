<%--
  Created by parachuter
  Date: 14-12-13
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>微路由</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <link rel="shortcut icon" href="./img/headshot.png"/>

    <link href="./css/bootstrap.min.css" rel="stylesheet"/>
    <link href="./css/bootstrap-responsive.min.css" rel="stylesheet"/>

    <link href="./css/font-awesome.css" rel="stylesheet"/>

    <link href="./css/adminia.css" rel="stylesheet"/>
    <link href="./css/adminia-responsive.css" rel="stylesheet"/>


    <link href="./css/pages/plans.css" rel="stylesheet"/>
    <script src="./js/menu.js"></script>
    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <style>
    	
        .GroupSelect {
            margin-bottom: 0px;
            margin-top:5px;
            float:right;
        }
        
        
        .file-box{ position:relative;width:800px}
		.txt{ height:22px; border:1px solid #cdcdcd; width:180px;margin-bottom:1px;margin-left:20px;}
		.btn{ background-color:#FFF; border:1px solid #CDCDCD;height:24px; width:70px;}
		.file{ position:absolute; margin-top:5px;margin-bottom:2px;top:0; left:20px; height:30px; filter:alpha(opacity:0);opacity: 0;width:253px !important; padding-right:20px;} 
    </style>


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

                        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
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
                        getMenu(${sessionScope.UserType}, "MemberTel");
                    </script>
                </ul>


                <hr/>

                <jsp:include page="Introduction.jsp"></jsp:include>
                <!-- .sidebar-extra -->

                <br/>

            </div>
            <!-- /span3 -->

            <div class="span9" id="Inter">
                <h1 class="page-title">
                    <i class="icon-th-large"></i>
                    会员管理<!-- 
                    <button type="button" class="btn btn-default" ID="NewInterface" data-toggle="modal"
                            data-target="#CreateModal">新建会员组
                    </button> -->
                </h1>
                <%-- <c:forEach items="${requestScope.list}" var="an" varStatus="sta"> --%>
                    <div class="widget">
			<div class="widget-header">
				<div class="file-box">
					<form action="MemberTel.htm" method="post" enctype="multipart/form-data">
						<select id="" class="GroupSelect" title="是否跳转到指定网页" style="width:6%;" name="ListType">
							<option value="true">是</option>
							<option value="false">否</option>
						</select> 
						<input type="text" class="GroupSelect" name="Remark" value="http://"> 
						<input type="file" name="fileField" class="file" id="fileField" size="28" onchange="document.getElementById('textfield').value=this.value" /> 
						<input type='text' name='textfield' id='textfield' class='txt' /> <input type='button' class='btn' value='浏览...' /> 
						<input type="submit" name="submit" class="btn" value="上传" />
					</form>
				</div>

				<!-- <h3 style="width:28%;"><span class="glyphicon glyphicon-stats"></span>
                              <form action="MemberTel.htm" method="POST" enctype="multipart/form-data">
				            <input id='uploadfile' type="file" name="file" style="width:160px;" class="btn" value="选择上传excel文件"/>
				            <input type="submit" name="submit" value="上传" class="btn">
				        </form>
                          </h3> -->

			</div>
		    </div>
                    <!-- /widget -->
               <%--  </c:forEach> --%>
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

<!-- 修改信息接口Start -->
<div class="modal fade" id="ChangeModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">修改接口</h4>
            </div>
            <div class="modal-body">

                <form role="form" id="ChangeForm">
                    <div class="form-group">
                        <label for="c_AppID">AppID</label>
                        <input type="text" class="form-control" id="c_AppID" readonly="true">
                    </div>
                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="ChangeApp()" data-dismiss="modal">确认</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<!-- 修改信息接口End -->



<!-- Le javascript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="./js/jquery-1.7.2.min.js"></script>
<script src="./js/Verification.js"></script>
<script src="./js/bootstrap.js"></script>

<SCRIPT type="text/javascript">
</SCRIPT>

</body>
</html>