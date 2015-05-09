<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page errorPage="page404.htm" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>账户设置</title>

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
        .web {
            background-image: url('StaticPage/webpage.jpg');
            background-repeat: no-repeat;
            background-size: 432px 720px;
            height: 720px;
            width: 432px;
            margin-left: auto;
            margin-right: auto;
        }

        .web img {
            border: 1px solid transparent;
        }

        .web img:hover {
            border: 1px red solid;
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
			            getMenu(${sessionScope.UserType}, "account");
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
				    <i class="icon-th-large"></i>
				    账户信息
				</h1>
				<div class="row">
			
			<div class="span9">
			
			<div class="widget">
			
			<div class="widget-header">
			    <h3>基本信息</h3>
			</div>
			<!-- /widget-header -->
			
			<div class="widget-content">
			
			
			<div class="tabbable">
			<ul class="nav nav-tabs">
			    <li class="active">
			        <a href="#1" data-toggle="tab">资料</a>
			    </li>
			    <li><a href="#2" data-toggle="tab">设定</a></li>
			</ul>
			
			<br/>
			
			<div class="tab-content">
			<div class="tab-pane active" id="1">
			    <form id="edit-profile" class="form-horizontal" action="Account.htm" method="post"  onsubmit="return Save(this)">
			        <fieldset>
			            <div class="control-group">
			                <label class="control-label" for="username">登陆名称</label>
			
			                <div class="controls">
			                    <input type="text" class="input-medium disabled" id="username"
			                           value="${requestScope.bs.get('LoginName')}" disabled=""/>
			
			                    <p class="help-block">登陆名称是您的标识，无法修改.</p>
			                </div>
			                <!-- /controls -->
			            </div>
			            <!-- /control-group -->
			
			            <div class="control-group">
			                <label class="control-label" for="BusinessName">商家名称</label>
			
			                <div class="controls">
			                    <input type="text" class="input-medium" id="BusinessName"
			                           value="${requestScope.bs.get('BusinesName')}" name="BusinessName"/>
			                </div>
			                <!-- /controls -->
			            </div>
			            <!-- /control-group -->
			            <div class="control-group">
			                <label class="control-label" for="BusinessAddress">地址</label>
			
			                <div class="controls">
			                    <input type="text" class="input-large" id="BusinessAddress"
			                           value="${requestScope.bs.get('BusinesAddres')}" name="BusinessAddress"/>
			                </div>
			                <!-- /controls -->
			            </div>
			            <!-- /control-group -->
			            <div class="control-group">
			                <label class="control-label" for="BusinessBoss">负责人</label>
			
			                <div class="controls">
			                    <input type="text" class="input-medium" id="BusinessBoss"
			                           value="${requestScope.bs.get('BusinesBoss')}" name="BusinessBoss"/>
			                </div>
			                <!-- /controls -->
			            </div>
			            <!-- /control-group -->
			            <div class="control-group">
			                <label class="control-label" for="Bossmobile">负责人电话</label>
			
			                <div class="controls">
			                    <input type="text" class="input-medium" id="BossMobile"
			                           value="${requestScope.bs.get('BossMobile')}" name="BossMobile"/>
			                </div>
			                <!-- /controls -->
			            </div>
			            <!-- /control-group -->
			            <div class="control-group">
			                <label class="control-label" for="BusinessContact">联系人</label>
			
			                <div class="controls">
			                    <input type="text" class="input-medium" id="BusinessContact"
			                           value="${requestScope.bs.get('BusinesContact')}" name="BusinessContact"/>
			                </div>
			                <!-- /controls -->
			            </div>
			            <!-- /control-group -->
			            <div class="control-group">
			                <label class="control-label" for="ContactMoblie">联系人电话</label>
			
			                <div class="controls">
			                    <input type="text" class="input-medium" id="ContactMobile"
			                           value="${requestScope.bs.get('ContactMobile')}" name="ContactMobile"/>
			                </div>
			                <!-- /controls -->
			            </div>
			            <!-- /control-group -->
			
			            <div class="control-group">
			                <label class="control-label" for="BusinessPhone">固定电话</label>
			
			                <div class="controls">
			                    <input type="text" class="input-medium" id="BusinessPhone"
			                           value="${requestScope.bs.get('BusinesPhone')}" name="BusinessPhone"/>
			                </div>
			                <!-- /controls -->
			            </div>
			            <!-- /control-group -->
			
			            <div class="control-group">
			                <label class="control-label" for="HomeAddres">商家主页</label>
			
			                <div class="controls">
			                    <input type="text" class="input-large" id="HomeAddres"
			                           value="${requestScope.bs.get('HomeAddres')}"
			                           name="HomeAddres"/>
			                </div>
			                <!-- /controls -->
			            </div>
			            <!-- /control-group -->
						
						<div class="control-group">
			                <label class="control-label" for="AgentGuestGroupID">代理来客分组</label>
			                <div class="controls">
			                    <select id="AgentGuestGroupID" class="GroupSelect" title="代理来客分组"
                                    onchange="this.options[this.selectedIndex].value" name="AgentGuestGroupID">
                                <c:forEach items="${requestScope.AgentGuestAndShareGuestList}" var="gn" varStatus="sta">
                                    <option value="${gn.get('Key')}" ${requestScope.bs.get('AgentGuestGroupID') == gn.get('Key')?"selected":""}>${gn.get('Name')}</option>
                                </c:forEach>
                            	</select>
			                </div>
			            </div>
			            
			            <div class="control-group">
			                <label class="control-label" for="ShareGuestGroupID">代理分享来客分组</label>
			                <div class="controls">
			                    <select id="ShareGuestGroupID" class="GroupSelect" title="代理分享来客分组"
                                    onchange="this.options[this.selectedIndex].value" name="ShareGuestGroupID">
                                <c:forEach items="${requestScope.AgentGuestAndShareGuestList}" var="gn" varStatus="sta">
                                    <option value="${gn.get('Key')}" ${requestScope.bs.get('ShareGuestGroupID') == gn.get('Key')?"selected":""}>${gn.get('Name')}</option>
                                </c:forEach>
                            	</select>
			                </div>
			            </div>
			            
			
			            <br/><br/>
			
			            <div class="control-group">
			                <label class="control-label" for="password1">原密码</label>
			
			                <div class="controls">
			                    <input type="password" class="input-medium" id="password1"
			                           name="password1" value=""/>
			                </div>
			                <!-- /controls -->
			            </div>
			            <!-- /control-group -->
			            <div class="control-group">
			                <label class="control-label" for="password2">输入新设密码</label>
			
			                <div class="controls">
			                    <input type="password" class="input-medium" id="password2"
			                           name="password2" value=""/>
			                </div>
			                <!-- /controls -->
			            </div>
			            <div class="control-group">
			                <label class="control-label" for="password2">再次输入新设密码</label>
			
			                <div class="controls">
			                    <input type="password" class="input-medium" id="password3"
			                           name="password3" value=""/>
			                </div>
			                <!-- /controls -->
			            </div>
			            <!-- /control-group -->
			            <br/>
			            <div class="form-actions">
			                <button type="button" onClick="$('#edit-profile').submit();" class="btn btn-primary">保存</button>
			                <button class="btn">取消</button>
			            </div>
			            <!-- /form-actions -->
			        </fieldset>
			    </form>
			</div>
			
			<div class="tab-pane" id="2">
			    <form id="edit-profile2" class="form-horizontal" action="Account.htm" method="post">
			        <fieldset>
			
			            <div class="control-group" style="display: none">
			                <label class="control-label" for="Logo">Logo</label>
			
			                <div class="controls">
			                    <input type="text" class="input-large" id="Logo"
			                           value="${requestScope.bs.get('LogoURL')}" name="LogoURL"/>
			                </div>
			                <!-- /controls -->
			            </div>
			            <!-- /control-group -->
			
			            <div class="control-group" style="display: none">
			                <label class="control-label" for="WelcomePic">欢迎页面图片</label>
			
			                <div class="controls">
			                    <input type="text" class="input-large" id="WelcomePic"
			                        value="${requestScope.bs.get('WelcomePictureURL')}"
			                        name="WelcomePictureURL"/>
			                </div>
			                <!-- /controls -->
			            </div>
			            <!-- /control-group -->
			
			            <br/>
			            <h2 style="text-align: center">路由器未认证强制跳转界面</h2>
			                <h3 style="text-align: center">点击图片上传并更换:</h3>
			            <div class="web">
			
				            <img src="${requestScope.bs.get('LogoURL')}" style="height: 40px; width: 40px; margin-top: 72px;"
				                 onclick="ChangeLogo()" id="logoimg"/>
				
				            <img src="${requestScope.WelcomePictureURL}"
				                 style="height: 400px; width: 400px; margin-top: 5px; margin-left: 15px;" onclick="Changebackpic()"
				                 id="backpic"/>
			                 
			            </div>
			
			            <div class="form-actions">
			                <button type="button" onClick="$('#edit-profile').submit();" class="btn btn-primary">保存</button>
			                <button type="button" class="btn">取消</button>
			            </div>
			        </fieldset>
			    </form>
			
			    <div style="display: none">
			        <form id="backpicfileform" action="/FileUpload" method="POST" target="frameFile"
			              enctype="multipart/form-data">
			            <input id='backpicfile' type="file" name="file1" style="width:160px;"
			                   onchange="submitbackpic()"/>
			        </form>
			    </div>
			
			    <div style="display: none">
			        <form id="logoform" action="/FileUpload" method="POST" target="frameFile"
			              enctype="multipart/form-data">
			            <input id='logofile' type="file" name="file1" style="width:160px;" onchange="submitlogo()"/>
			        </form>
			    </div>
			
			    <iframe id='frameFile' name='frameFile' style='display: none;'></iframe>
			</div>
			
			</div>
			
			
			</div>
			
			
			</div>
			<!-- /widget-content -->
			
			</div>
			<!-- /widget -->
			
			</div>
			<!-- /span9 -->
			
			</div>
			<!-- /row -->
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
<script src="./js/Verification.js"></script>
<script src="./js/bootstrap.js"></script>

<script type="text/javascript">
	function Save(){
	    var reg = /^[a-zA-Z0-9]{3,20}$/;
	    if((!isNull($("#password2").val()))&&(!isNull($("#password3").val()))){
	    	if((!reg.test($("#password2").val()))||(!reg.test($("#password3").val()))){
	    		alert("输入密码不区分大小写，长度大于3，小于20");
	    		return false;
	    	}else if((isNull($("#password1").val()))&&(isNull($("#password2").val()))&&(isNull($("#password3").val()))){
            	return true;
            }
	    }else if(isNull($("#password1").val())&&isNull($("#password2").val())&&isNull($("#password3").val())){
	    	return true;
	    }
	    return false;
	}
	
    function prelogo() {
        $("#logopic").attr("src", $("#Logo").val());
    }

    function prewpic() {
        $("#wpic").attr("src", $("#WelcomePic").val());
    }

    function ChangeLogo() {
        document.getElementById("logofile").click();
    }

    function submitbackpic() {
        document.getElementById("backpicfileform").submit();
        document.getElementById("backpic").src = "./StaticPage/UpLoad/" + "${requestScope.BusinessShopID}" + "/" + document.getElementById("backpicfile").files[0].name;
        document.getElementById("WelcomePic").value = "./StaticPage/UpLoad/" + "${requestScope.BusinessShopID}" + "/" + document.getElementById("backpicfile").files[0].name;
    }

    function submitlogo() {
        document.getElementById("logoform").submit();
        document.getElementById("logoimg").src = "./StaticPage/UpLoad/" + "${requestScope.bs.get('BusinesShopID')}" + "/" + document.getElementById("logofile").files[0].name;
        document.getElementById("Logo").value = "./StaticPage/UpLoad/" + "${sessionScope.Operator}" + "/" + document.getElementById("logofile").files[0].name;
    }

    function Changebackpic() {
        document.getElementById("backpicfile").click();
    }
</script>


</body>
</html>

