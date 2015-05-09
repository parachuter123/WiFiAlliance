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
        #NewInterface,#setup {
            float: right;
            margin-top: 4px;
        }

        .GroupSelect {
            margin-bottom: 4px;
            margin-left: 40px;
        }

        .btn {
            margin-bottom: 4px;
        }
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
                        getMenu(${sessionScope.UserType}, "MessageInterface");
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
                    消息接口
                    <button type="button" class="btn btn-info" ID="setup" onclick="oneKeyBingdingOpen()">一键绑定</button>
                    <button type="button" class="btn btn-default" ID="NewInterface" data-toggle="modal"
                            data-target="#CreateModal">新建接口
                    </button>
                </h1>
                <c:forEach items="${requestScope.list}" var="an" varStatus="sta">
                    <div class="widget">
                        <div class="widget-header">
                       			<h3 style="width:18%;"><span class="glyphicon glyphicon-stats"></span>
	                                <a data-toggle="collapse" data-toggle="collapse" data-parent="#Inter"
	                                   href="#${an.get('AppName')}">
	                                        ${an.get("AppName")}
	                                </a>
	                            </h3>
                       			<button type="button" class="btn btn-default" ID="oBtn"
	                                    onclick="chgSpan('${an.get('AppEntranceID')}')">修改
	                            </button>
                       			<select id="${an.get('AppName')}_select" class="GroupSelect" title="默认分组" style="width:12%;"
	                                    onchange="ChangeDefaultGroupID('${an.get('AppEntranceID')}',this.options[this.selectedIndex].value)">
	                                <c:forEach items="${requestScope.Grouplist}" var="gn" varStatus="sta">
	                                    <option value="${gn.get('GroupID')}" ${gn.get('GroupID') == an.get('DefaultGroupID')?"selected":""}>${gn.get('GroupName')}</option>
	                                </c:forEach>
	                            </select>
                       			<select id="${an.get('AppName')}_select" class="GroupSelect" title="默认模板" style="width:12%;"
	                                    onchange="ChangeWatchHandleJs('${an.get('AppEntranceID')}',this.options[this.selectedIndex].value)">
	                                <option value="">默认</option>
	                                <c:forEach items="${requestScope.InfoTemplatelist}" var="it" varStatus="sta">
	                                    <option value="${it.get('Key')}" ${it.get('Key') == an.get('WatchHandleJs')?"selected":""}>${it.get('Name')}</option>
	                                </c:forEach>
	                            </select>
                       			<button style="margin-left: 40px;" type="button" class="btn btn-info" ID="DataSynchronous"
	                                    onclick="DataSynchronous('${an.get('AppEntranceID')}')">微信同步
	                            </button>
                        	
                            
                        </div>
                        <!-- /widget-header -->
                        <div class="widget-content collapse" id="${an.get('AppName')}">
                            <br/>

                            <div class="span1">APPID:</div>
                            <div class="span7">
                                <span class="label label-info" id="APPID">${an.get('AppID')}</span>
                            </div>
                            <div class="span1">APPSecret:</div>
                            <div class="span7">
                                <span class="label label-info" id="APPSecret">${an.get('AppSecret')}</span>
                            </div>
                            <div class="span1">第三方URL:</div>
                            <div class="span7">
                                <span class="label label-info" id="TURL">${an.get('AppURL')}</span>
                            </div>
                            <div class="span1">APPToken:</div>
                            <div class="span7">
                                <span class="label label-info">${an.get('AppToken')}</span>
                            </div>
                            <div class="span1">会员数量:</div>
                            <div class="span7">
                                <span class="label label-info">${an.get('FansQuantity')}</span>
                            </div>
                            <div class="span1">App类型:</div>
                            <div class="span7">
                                <span class="label label-info">微信接口</span>
                            </div>
                            <div class="span1">默认分组:</div>
                            <div class="span7">
                                <span class="label label-info" id="GROUP">${an.get('DefaultGroupID')}</span>
                            </div>

                        </div>

                    </div>
                    <!-- /widget -->
                </c:forEach>
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


<!-- 新建信息接口Start -->
<div class="modal fade" id="CreateModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="ChangeModalLabel">新建接口</h4>
            </div>
            <div class="modal-body">

                <form role="form" id="CreateForm">
                    <div class="form-group">
                        <label for="LoginEmail">登录邮箱<span style="color:#E53333;">*必填项</span></label>
                        <input type="email" class="form-control" id="LoginEmail" placeholder="请输入公众平台登陆邮箱">
                    </div>
                    <div class="form-group">
                        <label for="LoginPassword">登陆密码<span style="color:#E53333;">*必填项</span></label>
                        <input type="password" class="form-control" id="LoginPassword" placeholder="请输入公众平台登陆密码">
                    </div>
                    <!-- <div class="form-group">
                        <label for="AppName">App名称<span style="color:#E53333;">*</span></label>
                        <input type="text" class="form-control" id="AppName" placeholder="请输入App名称">
                    </div>
                    <div class="form-group">
                        <label for="App_ID">AppID<span style="color:#E53333;">*</span></label>
                        <input type="text" class="form-control" id="App_ID" placeholder="请输入AppID">
                    </div> -->
                    <div class="form-group">
                        <label for="App_Secret">AppSecret<span style="color:#E53333;">选填项</span></label>
                        <input type="text" class="form-control" id="App_Secret" placeholder="请输入AppSecret">
                    </div>
                    <!-- <div class="form-group">
                        <label for="Origin_ID">原始ID<span style="color:#E53333;">*</span></label>
                        <input type="text" class="form-control" id="Origin_ID" placeholder="请输入原始ID">
                    </div>
                    <div class="form-group">
                        <label for="ThirdURL">第三方URL</label>
                        <input type="text" class="form-control" id="ThirdURL" placeholder="请输入第三方URL">
                    </div>
                    <div class="form-group">
                        <label for="ThirdURL">第三方Token</label>
                        <input type="text" class="form-control" id="ThirdToken" placeholder="请输入第三方Token">
                    </div> -->
                    <div class="form-group">
                        <label for="AppHomePageUrl">App跳转主页<span style="color:#E53333;">选填项</span></label>
                        <input type="text" class="form-control" id="AppHomePageUrl" placeholder="以http://开头">
                    </div>
                    <!-- <div class="form-group">
                        <label for="Introduction">App介绍性说明</label>
                        <input type="text" class="form-control" id="Introduction">
                    </div> -->
                    <div class="form-group">
                        <label for="Kind">接口类型<span style="color:#E53333;">*</span></label>
                        <select id="Kind" class="form-control">
                            <option value="1">微信接口</option>
                        </select>
                    </div>
                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="AddApp()" data-dismiss="modal">确认</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<!-- 新建信息接口End -->

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
                    <div class="form-group">
                        <label for="c_AppSecret">AppSecret</label>
                        <input type="text" class="form-control" id="c_AppSecret">
                    </div>
                    <div class="form-group">
                        <label for="c_AppName">接口名称</label>
                        <input type="text" class="form-control" id="c_AppName">
                    </div>
                    <div class="form-group">
                        <label for="c_AppURL">AppURL</label>
                        <input type="text" class="form-control" id="c_AppURL">
                    </div>
                    <div class="form-group">
                        <label for="c_AppToken">AppToken</label>
                        <input type="text" class="form-control" id="c_AppToken">
                    </div>
                    <div class="form-group">
                        <label for="c_Email">登录邮箱</label>
                        <input type="Email" class="form-control" id="c_Email">
                    </div>
                    <div class="form-group">
                        <label for="c_LoginPassword">登录密码</label>
                        <input type="text" class="form-control" id="c_LoginPassword">
                    </div>
                    <div class="form-group">
                        <label for="c_SearchAccount">搜索名称</label>
                        <input type="text" class="form-control" id="c_SearchAccount">
                    </div>
                    <div class="form-group">
                        <label for="c_Authenticate">是否认证</label>
                        <input type="text" class="form-control" id="c_Authenticate">
                    </div>
                    <div class="form-group">
                        <label for="c_AppHomePageUrl">App跳转主页</label>
                        <input type="text" class="form-control" id="c_AppHomePageUrl">
                    </div>
                    <div class="form-group">
                        <label for="c_Introduction">App介绍性说明</label>
                        <input type="text" class="form-control" id="c_Introduction">
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

<!-- 一键绑定消息接口Start -->
<div class="modal fade" id="oneKeyBingding">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">一键绑定公众账号</h4>
            </div>
            <div class="modal-body">
                <form role="form" id="ChangeForm">
                    <div class="form-group" style="display:none;">
                        <label for="BusinesID">商家ID</label>
                        <input type="text" placeholder="商家ID" id="BusinesID" value="${requestScope.BusinesShopID}">
                    </div>
                    <div class="form-group">
                        <label for="LoginName">公众账号邮箱</label>
                        <input type="text" placeholder="公众账号邮箱" id="LoginName" autocomplete="off">
                    </div>
                    <div class="form-group">
                        <label for="Password">公众账号登录密码</label>
                        <input type="password" placeholder="公众账号登录密码" id="Password" autocomplete="off" >
                    </div>
                    <span style="color:red;">提示：在此输入您的微信公众账号登陆邮箱和密码，即可一键绑定消息接口！！！</span>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="oneKeyBingding()" data-dismiss="modal">确定绑定</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<!-- 一键绑定消息接口End -->


<!-- Le javascript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="./js/jquery-1.7.2.min.js"></script>
<script src="./js/Verification.js"></script>
<script src="./js/bootstrap.js"></script>

<SCRIPT type="text/javascript">
    var APPID = "";

    function chgSpan(appid) {
        $.ajax({
            type: "POST",
            url: "/AppEntranceManager.htm",
            dataType: "json",
            data: {
                Type: "getinfo",
                appid: appid
            },
            success: function (data) {
                if (data.State == 1) {
                    $("#c_AppID").val(data.AppID);
                    $("#c_AppSecret").val(data.AppSecret);
                    $("#c_AppName").val(data.AppName);
                    $("#c_AppURL").val(data.AppURL);
                    $("#c_AppToken").val(data.AppToken);
                    $("#c_Email").val(data.Email);
                    $("#c_LoginPassword").val(data.LoginPassword);
                    $("#c_SearchAccount").val(data.SearchAccount);
                    $("#c_Authenticate").val(data.Authenticate);
                    $("#c_OriginalAtuoAttention").val(data.OriginalAtuoAttention);
                    $("#c_VerificationPicture").val(data.VerificationPicture);
                    $("#c_AppHomePageUrl").val(data.AppHomePageUrl);
                    $("#c_Introduction").val(data.Introduction);
                    $("#ChangeModal").modal("show");
                    APPID = appid;
                }else{
                	alert("数据错误");
                }
            }
        });
    }
    function oneKeyBingdingOpen(){
    	$("#LoginName").val("");
    	$("#Password").val("");
    	$("#oneKeyBingding").modal("show");
    }
  	//一键绑定消息接口
    function oneKeyBingding(){
        if($("#LoginName").val() =="" || $("#Password").val()==""||$("#BusinesID").val()=="" ){
            alert("用户名密码商家ID不能为空");
            return;
        }
        $.ajax({
            type: "POST",
            url: "/PPWizard",
            data: {
                Operation:"Binding",
                BusinesID:$("#BusinesID").val(),
                LoginName:$("#LoginName").val(),
                Password:$("#Password").val()
            },
            success:function(data){
            	if(data.State == 1){
            		alert(data.Message);
                    history.go(0);
            	}else if(data.State>1){
            		alert(data.Message);
            	}else{
                   	 if (data.ShowBug=="true") {
   		                   	alert(data.Message);
   		             } else {
   		                   	alert(data.SysErrorPointOut);
   		             }
            	}
            },
            error:function(data,textStatus,errorThrown){
            	alert("time out");
            }
        });
    }
    function ChangeApp() {
        if (APPID == "") {
            alert("Error!!!");
            return;
        }
        $.ajax({
            type: "POST",
            url: "/AppEntranceManager.htm",
            dataType:"json",
            data: {
                Type: "AppUpdate",
                App_ID: APPID,
                AppID: $("#c_AppID").val(),
                AppSecret: $("#c_AppSecret").val(),
                AppName: $("#c_AppName").val(),
                AppURL: $("#c_AppURL").val(),
                AppToken: $("#c_AppToken").val(),
                Email: $("#c_Email").val(),
                LoginPassword: $("#c_LoginPassword").val(),
                SearchAccount: $("#c_SearchAccount").val(),
                Authenticate: $("#c_Authenticate").val(),
                OriginalAtuoAttention: $("#c_OriginalAtuoAttention").val(),
                VerificationPicture: $("#c_VerificationPicture").val(),
                AppHomePageUrl: $("#c_AppHomePageUrl").val(),
                Introduction: $("#c_Introduction").val()
            },
            success:function(data){
            	if(data.State == 1){
            		alert(data.Message);
                    history.go(0);
            	}else if(data.State>1){
            		alert(data.Message);
            	}else{
                   	 if (data.ShowBug=="true") {
   		                   	alert(data.Message);
   		             } else {
   		                   	alert(data.SysErrorPointOut);
   		             }
            	}
            },
            error:function(data,textStatus,errorThrown){
            	alert("time out");
            }
        });
    }

    function ChangeDefaultGroupID(AppID, selectval) {
        $.ajax({
            type: "POST",
            url: "/AppEntranceManager.htm",
            dataType: "json",
            data: {
                Type: 'SetDefualtGroup',
                App_ID: AppID,
                Group_ID: selectval
            },
            success:function(data){
            	if(data.State == 1){
            		alert(data.Message);
                    history.go(0);
            	}else if(data.State>1){
            		alert(data.Message);
            	}else{
                   	 if (data.ShowBug=="true") {
   		                   	alert(data.Message);
   		             } else {
   		                   	alert(data.SysErrorPointOut);
   		             }
            	}
            },
            error:function(data,textStatus,errorThrown){
            	alert("time out");
            }
        });
    }
    //改变关注模板
    function ChangeWatchHandleJs(AppID, selectval) {
        $.ajax({
            type: "POST",
            url: "/AppEntranceManager.htm",
            dataType: "json",
            data: {
                Type: 'SetWatchHandleJs',
                App_ID: AppID,
                Template_ID: selectval
            },
            success:function(data){
            	if(data.State == 1){
            		alert(data.Message);
                    history.go(0);
            	}else if(data.State>1){
            		alert(data.Message);
            	}else{
                   	 if (data.ShowBug=="true") {
   		                   	alert(data.Message);
   		             } else {
   		                   	alert(data.SysErrorPointOut);
   		             }
            	}
            },
            error:function(data,textStatus,errorThrown){
            	alert("time out");
            }
        });
    }

    function setup(){
        window.location = "steps/step1.jsp?BusinesID=${requestScope.BusinesShopID}";
    }
    
    function DataSynchronous(appid) {
           $.ajax({
               type: "GET",
               url: "/PPWizard",
               dataType:"json",
               data: {
                   AppEntranceID: appid
               },
               success: function (data) {
            	   if(data.State == 1){
               		alert(data.Message);
                       history.go(0);
	               	}else if(data.State>1){
	               		alert(data.Message);
	               	}else{
	                      	 if (data.ShowBug=="true") {
	      		                   	alert(data.Message);
	      		             } else {
	      		                   	alert(data.SysErrorPointOut);
	      		             }
	               	}
               },
               error:function(data,textStatus,errorThrown){
               		alert("time out");
               }
           });
    }
    function AddApp() {

        if (!isEmail($("#LoginEmail").val())) {
            alert("邮件格式错误！！");
            return;
        }

        if (isNull($("#LoginPassword").val())) {
            alert("登陆密码不能为空！");
            return;
        }

        if (isNull($("#Origin_ID").val())) {
            alert("原始ID不能为空！！");
            return;
        }

        $.ajax({
            type: "POST",
            url: "/AppEntranceManager.htm",
            data: {
                Type: 'Create',
                LoginEmail: $("#LoginEmail").val(),
                LoginPassword: $("#LoginPassword").val(),
                //AppName: $("#AppName").val(),
                //App_ID: $("#App_ID").val(),
                App_Secret: $("#App_Secret").val(),
                //Origin_ID: $("#Origin_ID").val(),
                //ThirdURL: $("#ThirdURL").val(),
                //ThirdToken: $("#ThirdToken").val(),
                AppHomePageUrl: $("#AppHomePageUrl").val(),
                //Introduction: $("#Introduction").val()
            },
            success:function(data){
            	if(data.State == 1){
            		alert(data.Message);
                    history.go(0);
            	}else if(data.State>1){
            		alert(data.Message);
            	}else{
                   	 if (data.ShowBug=="true") {
   		                   	alert(data.Message);
   		             } else {
   		                   	alert(data.SysErrorPointOut);
   		             }
            	}
            },
            error:function(data,textStatus,errorThrown){
            	alert("time out");
            }
        });
    }
</SCRIPT>

</body>
</html>