<%--
  Created by IntelliJ IDEA.
  User: parachuter
  Date: 14-3-6
  Time: 上午9:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <title>微路由</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
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

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <style type="text/css">
        #adduser {
            float: right;
            margin-top: 6px;
            margin-right: 4px;
        }
        #SearchGroup {
            float: right;
            margin-top: 4px;
        }
        #SearchGroup #SearchKeyWords {
            margin-top: -10px;
        }

        #Pages {
            float: right;
        }


    </style>
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

            </div> <!-- /nav-collapse -->

        </div> <!-- /container -->

    </div> <!-- /navbar-inner -->

</div> <!-- /navbar -->




<div id="content">

<div class="container">

<div class="row">

<div class="span3">

    <div class="account-container">

        <div class="account-avatar">
            <img src="${sessionScope.LogoURL}" alt="" class="thumbnail" />
        </div> <!-- /account-avatar -->

        <div class="account-details">

            <span class="account-name">${sessionScope.Name}</span>

            <span class="account-role">${sessionScope.Role}</span>

						<span class="account-actions">
							<a href="javascript:;">档案</a> |

							<a href="javascript:;">修改设置</a>
						</span>

        </div> <!-- /account-details -->

    </div> <!-- /account-container -->

    <hr />

    <ul id="main-nav" class="nav nav-tabs nav-stacked">
        <script language="javascript">
            getMenu(${sessionScope.UserType},"AgentManager");
        </script>

    </ul>



    <hr />

    <jsp:include page="Introduction.jsp"></jsp:include>
     <!-- .sidebar-extra -->

    <br />

</div> <!-- /span3 -->



<div class="span9">

    <h1 class="page-title">
        <i class="icon-th-list"></i>
        用户管理
        <div class="input-group" id="SearchGroup">
            <input id="SearchKeyWords" type="text">
            <div class="btn-group" style="display: inline-block">
                <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" >
                    搜索<span class="caret"></span>
                </button>
                <ul class="dropdown-menu">
                    <li><a href="#">用户名称</a></li>
                    <li><a href="#">登陆ID</a></li>
                    <li><a href="#">联系电话</a></li>
                    <li class="divider"></li>
                    <li><a href="#">备注</a></li>
                </ul>
            </div>
        </div><!-- /input-group -->
    </h1>



    <div class="widget widget-table">

        <div class="widget-header">


            <div id="adduser">
                <div class="btn-group">
                    <button type="button" class="btn btn-default" onclick="CreateAgent()">新增</button>
                </div>
            </div>


        </div> <!-- /widget-header -->

        <div class="widget-content">



            <table class="table table-striped table-bordered" style="white-space: nowrap;">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>代理商</th>
                    <th>登陆名</th>
                    <th>是否共享WIFI</th>
                    <th>类别</th>
                    <th>操作</th>
                </tr>
                </thead>

                <tbody>

                <c:forEach items="${requestScope.list}" var="an" varStatus="sta" >
                <tr>
                    <td>${an.get("AgentID")}</td>
                    <td>${an.get("AgentName")}</td>
                    <td>${an.get("LoginName")}</td>
                    <td>${an.get("WifiShare")?"共享":"不共享"}</td>
                    <td>
                        ${an.get("SeniorAgent")?"<span class=\"label label-info\">高级代理商</span>":"<span class=\"label label-success\">代理商</span>"}
                    </td>
                    <td>
                        <a href="javascript:;" class="btn btn-small" onclick="">
                            <i class="icon-cog" title="修改" onclick="ChangeAgent('${an.get('AgentID')}')">修改</i>
                        </a>
                    </td>
                </tr>

                </c:forEach>

                </tbody>
            </table>




        </div> <!-- /widget-content -->

        <ul class="pagination" id="Pages">
            <li><a href="#">&laquo;</a></li>
            <li><a href="#">1</a></li>
            <li><a href="#">2</a></li>
            <li class="active"><a href="#">3</a></li>
            <li><a href="#">4</a></li>
            <li><a href="#">5</a></li>
            <li><a href="#">&raquo;</a></li>
        </ul>

    </div> <!-- /widget -->



</div> <!-- /span9 -->


</div> <!-- /row -->

</div> <!-- /container -->

</div> <!-- /content -->


<jsp:include page="footer.jsp"></jsp:include>
 <!-- /footer -->

<!--   新增代理商Start -->
<div class="modal fade" id="CreateAgent">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="ChangeModalLabel">新建</h4>
            </div>
            <div class="modal-body">
                <form role="form" id="CreateForm">
                    <div class="form-group">
                        <label for="AgentName">代理商名称<span style="color:#E53333;">*</span></label>
                        <input type="text" class="form-control" id="AgentName">
                    </div>
                    <div class="form-group">
                        <label for="LoginName">登录名<span style="color:#E53333;">*输入登录名称只能以英文字母开头，不分大小写，长度大于3，小于10</span></label>
                        <input type="text" class="form-control" id="LoginName">
                    </div>
                    <c:if test="${sessionScope.UserType==1}">
	                    <div class="form-group">
	                        <label for="SeniorAgent">是否高级代理<span style="color:#E53333;">*</span></label>
	                        <select id="SeniorAgent" class="form-control"  onchange="this.options[this.selectedIndex].value;">
	                          <option value="true">是</option>
	                          <option value="false">否</option>
	                        </select>
	                    </div>
                    </c:if>
                    <div class="form-group">
                        <label for="WifiShare">是否共享ＷＩＦＩ<span style="color:#E53333;">*</span></label>
                        <select id="WifiShare" class="form-control"  onchange="this.options[this.selectedIndex].value;">
                          <option value="true">共享</option>
                          <option value="false">不共享</option>
                        </select>
                    </div>
                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="PostData()" data-dismiss="modal">确认</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<!--  新增代理商End -->



<!-- Le javascript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="./js/jquery-1.7.2.min.js"></script>
<script src="./js/bootstrap.js"></script>
<script src="./js/Verification.js"></script>

<script>

    var AGENT = "";
    //新建
    function CreateAgent(){
        $("#AgentName").val("");
        $("#LoginName").val("");
        $("#WifiShare").val("");
        document.getElementById("ChangeModalLabel").innerText ="新建代理商";
        $("#CreateAgent").modal("show");
    }

    //修改
    function ChangeAgent(AgentID){
        //获取代理商信息
        $.ajax({
            type:"POST",
            url:"/AgentList.htm",
            dataType:"json",
            data:{
            	Type:"getAgentInfo",
            	AgentID:AgentID
            },
            success:function(data){
                if(data != null){
                    $("#AgentName").val(data.AgentName);
                    $("#LoginName").val(data.LoginName);
                    $("#WifiShare").val(data.WifiShare);
                    document.getElementById("ChangeModalLabel").innerText ="修改信息";
                    $("#CreateAgent").modal("show");
                }else{
                    alert("获取代理商信息错误！！！");
                }
                AGENT = AgentID;
            }
        });
    }

    function PostData(){
        if(document.getElementById("ChangeModalLabel").innerText == "新建代理商"){
        	var reg = /^[a-zA-Z]{1}[a-zA-Z0-9]{2,9}$/;
        	if(!reg.test($("#LoginName").val())){
        		alert("输入登录名称只能以英文字母开头，不分大小写，长度大于3，小于10");
        		return;
        	}
        	
            if(isNull($("#AgentName").val()) || isNull($("#LoginName").val())) {
                alert("必要参数不能为空！");
            }

            $.ajax({
                type:"POST",
                url:"/AgentList.htm",
                dataType:"json",
                data:{
                	Type:"createAgent",
                	AgentName:$("#AgentName").val(),
                    LoginName:$("#LoginName").val(),
                    WifiShare:$("#WifiShare").val(),
                    SeniorAgent:$("#SeniorAgent").val()
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
        }else{
            $.ajax({
                type:"POST",
                url:"/AgentList.htm",
                data:{
                	Type:"changeAgentInfo",
                    AgentID:AGENT,
                    AgentName:$("#AgentName").val(),
                    LoginName:$("#LoginName").val(),
                    WifiShare:$("#WifiShare").val()
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
    }
</script>


</body>
</html>

