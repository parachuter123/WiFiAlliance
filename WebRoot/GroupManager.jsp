<%--
  User: parachuter
  Date: 14-12-08
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

    <style type="text/css">
        #adduser {
            float: right;
            margin-top: 6px;
            margin-right: 4px;
            display: inline-block;
        }

        #adduser .btn-group {
            display: inline-block;
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
                        getMenu('${sessionScope.UserType}', "GroupManager");
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
                    <i class="icon-comment"></i>
                    分组设置
                </h1>

                <div class="widget widget-table">

                    <div class="widget-header">
                        <i class="icon-group"></i>

                        <h3>分组列表</h3>

                        <div id="adduser">
                            <div class="btn-group">
                                <button type="button" class="btn btn-default" onclick="OpenModel('')">新建分组</button>
                            </div>
                        </div>


                    </div>
                    <!-- /widget-header -->

                    <div class="widget-content">


                        <table class="table table-striped table-bordered" style="white-space: nowrap;">
                            <thead>
                            <tr>
                                <th>分组名称</th>
                                <th>上行网速</th>
                                <th>下行网速</th>
                                <th>上行流量</th>
                                <th>下行流量</th>
                                <th>是否通知</th>
                                <th>通知广告</th>
                                <th>操作</th>
                            </tr>
                            </thead>

                            <tbody>

                            <c:forEach items="${requestScope.Grouplist}" var="an" varStatus="sta">
                                <tr>
                                    <td>${an.get('GroupName')}</td>
                                    <td>${an.get('UplinkSpeed')}</td>
                                    <td>${an.get('DownSpeed')}</td>
                                    <td>${an.get('UplinkFlow')}</td>
                                    <td>${an.get('DownFlow')}</td>
                                    <td>${an.get('Notice')?'通知':'不通知'}</td>
                                    <td>${(an.get('ReasonURL')).split('#')[1]}</td>
                                    <td>
                                        <a href="javascript:;" class="btn btn-small"
                                           onclick='OpenModel("${an.get("GroupID")}")'>
                                            <i class="icon-cog" title="用户信息"></i>
                                        </a>
                                        <!--<a href="javascript:;" class="btn btn-small  btn-danger">-->
                                        <!--<i class="icon-remove-sign" title="加入黑名单"></i>-->
                                        <!--</a>-->
                                    </td>
                                </tr>
                            </c:forEach>

                            </tbody>
                        </table>


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

<!-- 修改分组信息Start -->
<div class="modal fade" id="GroupInfo">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="ChangeModalLabel">分组</h4>
            </div>
            <div class="modal-body">
                <form role="form" id="CreateForm">
                    <div class="form-group">
                        <label for="groupName">分组名称<span style="color:#E53333;">*</span></label>
                        <input type="text" class="form-control" id="GroupName">
                    </div>
                    <div class="form-group">
                        <label for="UplinkSpeed">上行网速</label>
                        <input type="text" class="form-control" id="UplinkSpeed">
                    </div>
                    <div class="form-group">
                        <label for="DownSpeed">下行网速</label>
                        <input type="text" class="form-control" id="DownSpeed">
                    </div>
                    <div class="form-group">
                        <label for="UplinkFlow">上行流量</label>
                        <input type="text" class="form-control" id="UplinkFlow">
                    </div>
                    <div class="form-group">
                        <label for="DownFlow">下行流量</label>
                        <input type="text" class="form-control" id="DownFlow">
                    </div>
                    <div class="form-group">
                        <label for="Notice">是否通知</label>
                        <select id="Notice" class="form-control" onchange="this.options[this.selectedIndex].value;">
                            <option value="true">通知</option>
                            <option value="false">不通知</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="ReasonURL">通知广告</label>
                        <select id="ReasonURL" class="form-control" style=" margin-bottom: 20px; width: auto;">
                            <option value="">无</option>
                            <c:forEach items="${requestScope.InfoTemplateList}" var="gp" varStatus="sta">
                                <option value="${(gp.get('Key'))}">${(gp.get('Key')).split('#')[1]}</option>
                            </c:forEach>
                        </select>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="ModelBtn" onclick="" data-dismiss="modal">确认</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<!-- 修改分组信息End -->


<!-- Le javascript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="./js/jquery-1.7.2.min.js"></script>
<script src="./js/bootstrap.js"></script>
<script src="./js/Verification.js"></script>
<script>

    function OpenModel(GroupID) {
        //创建分组
        if (GroupID == "") {
            CleanIpunt();
            $("#GroupInfo").modal("show");
            document.getElementById("ModelBtn").onclick = CreateGroup;
            return;
        } else {
            getGroupInfo(GroupID);
            $("#GroupInfo").modal("show");
            document.getElementById("ModelBtn").onclick = function () {
                ChangeGroup(GroupID);
            };
        }
    }

    function CreateGroup() {

        if (isNull($("#GroupName").val())) {
            alert("分组名称不能为空！");
            return;
        }

        if (!isNull($("#UplinkSpeed").val())) {
            if (!isInteger($("#UplinkSpeed").val())) {
                alert("必须为数字！");
                return;
            }
        }

        if (!isNull($("#DownSpeed").val())) {
            if (!isInteger($("#DownSpeed").val())) {
                alert("必须为数字！");
                return;
            }
        }
        if (!isNull($("#UplinkFlow").val())) {
            if (!isInteger($("#UplinkFlow").val())) {
                alert("必须为数字！");
                return;
            }
        }
        if (!isNull($("#DownFlow").val())) {
            if (!isInteger($("#DownFlow").val())) {
                alert("必须为数字！");
                return;
            }
        }
        if ($("#Notice").val() == "true") {
            if (isNull($("#ReasonURL").val())) {
                alert("通知URL不能为空！！");
                return;
            }
        }
        $.ajax({
            type: "POST",
            url: "/GroupManager.htm",
            dataType:"json",
            data: {
                Type: "CreateGroup",
                GroupName: $("#GroupName").val(),
                UplinkSpeed: $("#UplinkSpeed").val(),
                DownSpeed: $("#DownSpeed").val(),
                UplinkFlow: $("#UplinkFlow").val(),
                DownFlow: $("#DownFlow").val(),
                Notice: $("#Notice").val(),
                ReasonURL: $("#ReasonURL").val()
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

    function ChangeGroup(GroupID) {
        $.ajax({
            type: "POST",
            url: "/GroupManager.htm",
            dataType: "json",
            data: {
                Type: "UpdateGroupInfo",
                GroupID: GroupID,
                UplinkSpeed: $("#UplinkSpeed").val(),
                DownSpeed: $("#DownSpeed").val(),
                UplinkFlow: $("#UplinkFlow").val(),
                DownFlow: $("#DownFlow").val(),
                Notice: $("#Notice").val(),
                ReasonURL: $("#ReasonURL").val()
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

    function getGroupInfo(GroupID) {
        $.ajax({
            type: "POST",
            url: "/GroupManager.htm",
            dataType: "json",
            data: {
                Type: "GetGroupInfo",
                GroupID: GroupID
            },
            success: function (data) {
                if (data == null) {
                    CleanIpunt();
                } else {
                    $('#GroupName').val(data.GroupName);
                    $('#UplinkSpeed').val(data.UplinkSpeed);
                    $('#DownSpeed').val(data.DownSpeed);
                    $('#UplinkFlow').val(data.UplinkFlow);
                    $('#DownFlow').val(data.DownFlow);
                    $('#Notice').val(data.Notice);
                    $('#ReasonURL').val(data.ReasonURL);
                }
            }
        });
    }
    function CleanIpunt() {
        $("#GroupName").val("");
        $("#UplinkSpeed").val("");
        $("#DownSpeed").val("");
        $("#UplinkFlow").val("");
        $("#DownFlow").val("");
        $("#Notice").val("");
    }

</script>
</body>
</html>

