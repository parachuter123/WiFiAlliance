<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page errorPage="page404.htm"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>账户其他设置</title>

<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<link rel="shortcut icon" href="./img/headshot.png" />

<link href="./css/bootstrap.min.css" rel="stylesheet" />
<link href="./css/bootstrap-responsive.min.css" rel="stylesheet" />

<link href="./css/font-awesome.css" rel="stylesheet" />

<link href="./css/adminia.css" rel="stylesheet" />
<link href="./css/adminia-responsive.css" rel="stylesheet" />


<link href="./css/pages/plans.css" rel="stylesheet" />
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

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>

<body>

	<div class="navbar navbar-fixed-top">

		<div class="navbar-inner">

			<div class="container">

				<a class="btn btn-navbar" data-toggle="collapse"
					data-target=".nav-collapse"> <span class="icon-bar"></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span>
				</a> 
				
				<jsp:include page="Title.jsp"></jsp:include>

				<div class="nav-collapse">

					<ul class="nav pull-right">
						<li><a href="#"><span class="badge badge-warning">7</span></a>
						</li>

						<li class="divider-vertical"></li>

						<li class="dropdown"><a data-toggle="dropdown"
							class="dropdown-toggle " href="#"> 我的面板 <b class="caret"></b>
						</a>

							<ul class="dropdown-menu">
								<script language="javascript">
									getFunctionBord(${sessionScope.UserType});
								</script>
							</ul></li>
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
							<img src="${sessionScope.LogoURL}" alt="" class="thumbnail" />
						</div>
						<!-- /account-avatar -->

						<div class="account-details">

							<span class="account-name">${sessionScope.Name}</span> <span
								class="account-role">${sessionScope.Role}</span> <span
								class="account-actions"> <a href="javascript:;">档案</a> |

								<a href="javascript:;">修改设置</a>
							</span>

						</div>
						<!-- /account-details -->

					</div>
					<!-- /account-container -->

					<hr />

					<ul id="main-nav" class="nav nav-tabs nav-stacked">

						<script language="javascript">
							getMenu(${sessionScope.UserType}, "account");
						</script>

					</ul>


					<hr />

					<jsp:include page="Introduction.jsp"></jsp:include>
					<!-- .sidebar-extra -->

					<br />

				</div>
				<!-- /span3 -->
				<div class="span9">
					<h1 class="page-title">
						<i class="icon-th-large"></i> 账户信息
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
											<li class="active"><a href="#1" data-toggle="tab">资料</a>
											</li>
										</ul>

										<br />

										<div class="tab-content">
											<div class="tab-pane active" id="1">
												<form id="edit-profile" class="form-horizontal"
													action="Account.htm" method="post" onsubmit="return Save(this)">
													<fieldset>
														<div class="control-group">
															<label class="control-label" for="LoginName">登陆名称</label>

															<div class="controls">
																<input type="text" class="input-medium disabled"
																	id="LoginName"
																	value="${requestScope.Agent.get('LoginName') }" disabled="" />

																<p class="help-block">登陆名称是您的标识，无法修改.</p>
															</div>
															<!-- /controls -->
														</div>
														<!-- /control-group -->

														<div class="control-group">
															<label class="control-label" for="AgentName">代理名称</label>

															<div class="controls">
																<input type="text" class="input-medium"
																	id="AgentName"
																	value="${requestScope.Agent.get('AgentName') }"
																	name="AgentName" />
															</div>
															<!-- /controls -->
														</div>
														<!-- /control-group -->
														<c:if test="${sessionScope.UserType==3 }">
														<div class="control-group">
															<label class="control-label" for="JumpAddr">会员跳转地址</label>

															<div class="controls">
																<input type="text" class="input-medium"
																	id="JumpAddr"
																	value="${requestScope.Agent.get('JumpAddr') }"
																	name="JumpAddr" />
															</div>
															<!-- /controls -->
														</div>
														</c:if>
														<!-- /control-group -->
														
														<c:if test="${sessionScope.UserType==2 }">
															<div class="control-group">
																<label class="control-label" for="LogoUrl">代理LoGo外链地址</label>
	
																<div class="controls">
																	<input type="text" class="input-medium"
																		id="LogoUrl"
																		value="${requestScope.Agent.get('LogoUrl') }"
																		name="LogoUrl" />
																</div>
																<!-- /controls -->
															</div>
														</c:if>
														<!-- /control-group -->
														
														<c:if test="${sessionScope.UserType==2 }">
															<div class="control-group">
																<label class="control-label" for="SeniorAgentTitle">平台标题设置</label>
																<div class="controls">
																	<input type="text" class="input-medium"
																		id="SeniorAgentTitle"
																		value="${requestScope.Agent.get('SeniorAgentTitle') }"
																		name="SeniorAgentTitle" />
																</div>
																<!-- /controls -->
															</div>
														</c:if>
														<!-- /control-group -->
														
														<c:if test="${sessionScope.UserType==2 }">
															<div class="control-group">
																<label class="control-label" for="SeniorAgentInstruction">高级代理介绍</label>
																<div class="controls">
																	<textarea rows="4" cols="8" name="SeniorAgentInstruction">${requestScope.Agent.get('SeniorAgentInstruction') }
																	</textarea>	
																	<%-- <input type="text" class="input-medium"
																		id="SeniorAgentInstruction"
																		value="${requestScope.Agent.get('SeniorAgentInstruction') }"
																		name="SeniorAgentInstruction" /> --%>
																</div>
																<!-- /controls -->
															</div>
														</c:if>
														<!-- /control-group -->
														
														<c:if test="${sessionScope.UserType==2 }">
															<div class="control-group">
																<label class="control-label" for="SeniorAgentFooter">高级代理页脚设置</label>
																<div class="controls">
																	<input type="text" class="input-medium"
																		id="SeniorAgentFooter"
																		value="${requestScope.Agent.get('SeniorAgentFooter') }"
																		name="SeniorAgentFooter" />
																</div>
																<!-- /controls -->
															</div>
														</c:if>
														<!-- /control-group -->


														<br /> <br />

														<div class="control-group">
															<label class="control-label" for="password1">原密码</label>

															<div class="controls">
																<input type="password" class="input-medium"
																	id="password1" name="password1" value="" />
															</div>
															<!-- /controls -->
														</div>
														<!-- /control-group -->
														<div class="control-group">
															<label class="control-label" for="password2">输入新设密码</label>

															<div class="controls">
																<input type="password" class="input-medium"
																	id="password2" name="password2" value="" />
															</div>
															<!-- /controls -->
														</div>
														<div class="control-group">
															<label class="control-label" for="password3">再次输入新设密码</label>

															<div class="controls">
																<input type="password" class="input-medium"
																	id="password3" name="password3" value="" />
															</div>
															<!-- /controls -->
														</div>
														<!-- /control-group -->
														<br />
														<div class="form-actions">
															<button  type="button" onClick="$('#edit-profile').submit();" class="btn btn-primary">保存</button>
															<button class="btn">取消</button>
														</div>
														<!-- /form-actions -->
													</fieldset>
												</form>
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
            	}
            }else if((isNull($("#password1").val()))&&(isNull($("#password2").val()))&&(isNull($("#password3").val()))){
            	return true;
            }
            return false;
		}
	</script>
</body>
</html>

