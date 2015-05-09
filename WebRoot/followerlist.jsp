<%--
     关注者列表
            Created by parachuter
            Date: 14-12-08
            Time: 上午9:13
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
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
            getMenu('${sessionScope.UserType}', "followerlist");
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
        <i class="icon-group"></i>
        会员管理
        <div class="input-group" id="SearchGroup">
            <input id="SearchKeyWords" type="text">

            <div class="btn-group" style="display: inline-block">
                <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                    搜索<span class="caret"></span>
                </button>
                <ul class="dropdown-menu">
                    <li><a href="#" onclick="SearchNickName()">会员名称</a></li>
                </ul>
            </div>
        </div>
        <!-- /input-group -->
    </h1>


    <div class="widget widget-table">

        <div class="widget-header">
            <i class="icon-group"></i>

            <h3>会员列表 &nbsp;&nbsp;<span class="badge badge-info">${requestScope.FollowersCout}</span></h3>

            <div id="adduser">
                <select id="APPID" class="form-control" style=" margin-bottom: 20px; width: auto;"
                        onchange="GetChange(this.id)">
                    <c:forEach items="${requestScope.Applist}" var="an" varStatus="sta">
                        <option value="${an.get('AppEntranceID')}">${an.get('AppName')}</option>
                    </c:forEach>
                </select>
                <select id="UserStatus" class="form-control" style=" margin-bottom: 20px; width: auto;"
                        onchange="GetChange(this.id)">
                    <option value="0">未关注</option>
                    <option value="1" selected="selected">关注中</option>
                    <option value="-1">信息超时</option>
                    <option value="-2">取消关注</option>
                    <option value="-10">未知状态</option>
                </select>
                <select id="UserGroup" class="form-control" style=" margin-bottom: 20px; width: auto;"
                        onchange="GetChange(this.id)">
                    <c:forEach items="${requestScope.Grouplist}" var="an" varStatus="sta">
                        <option value="${an.get('GroupID')}">${an.get('GroupName')}</option>
                    </c:forEach>
                </select>

                <div class="btn-group">
                    <button type="button" class="btn btn-default" data-toggle="modal"
                            data-target="#ModifyGroup">分组设置
                    </button>
                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                        操作<span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu">
                        <li><a href="#" onclick="OpenSenderBatch()">发送信息</a></li>
                        <li><a href="#" onclick="OpenAddSenderBatch()">弹出广告</a></li>

                        <li class="divider"></li>
                        <li><a href="#" onclick="DelSelect()">删除</a></li>
                    </ul>

                </div>
            </div>


        </div>
        <!-- /widget-header -->

        <div class="widget-content">


            <table class="table table-striped table-bordered" style="white-space: nowrap;">
                <thead>
                <tr>
                    <th>
                        <input class="frm_checkbox js_select" type="checkbox" value="-1" id="checkall">全选
                    </th>
                    <th>关注者</th>
                    <th>性别</th>
                    <th>关注时间</th>
                    <th>用户状态</th>
                    <th>分组</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>


                <c:forEach items="${requestScope.FollowersList}" var="an" varStatus="sta">
                    <tr>
                        <td><input type="checkbox" value="${an.get('FollowersID')}" class="MultiSelects"></td>
                        <td><img
                                src='${an.get("AvatarURL")}64'
                                width="20px"
                                title='${an.get("Country")}&#10;${an.get("Province")}<${an.get("City")}>'/>&nbsp;
                            &nbsp; ${an.get('Nickname')}</td>
                        <td>${(Integer.parseInt(an.get('Sex').toString())==1)?"男":"女"}</td>
                        <td><fmt:formatDate  value="${an.get('SubscribeTime')}" type="both" pattern="yyyy-MM-dd HH:mm:ss" /></td> 
                        <td>
                                ${(Integer.parseInt(an.get('StatusID').toString())>0)?"<span class='label label-success'>在线</span>":"<span class='label label-default'>离线</span>"}
                        </td>
                        <td>${an.get('GroupID')}</td>
                        <td class="action-td">
                            <a href="javascript:;" class="btn btn-small btn-primary"
                               onclick="OpenSender('${an.get('FollowersID')}')">
                                <i class="icon-comment" title="发送信息"></i>
                            </a>
                            <a href="javascript:;" class="btn btn-small btn-info"
                               onclick="OpenAddSender('${an.get(FollowersID)}')">
                                <i class="icon-envelope" title="弹出广告"></i>
                            </a>
                            <a href="javascript:;" class="btn btn-small"
                               onclick='UserInfo("${an.get("FollowersID")}")'>
                                <i class="icon-cog" title="用户信息"></i>
                            </a>
                            <a href="javascript:;" class="btn btn-small  btn-danger"
                               onclick="Del('${an.get('FollowersID')}')">
                                <i class="icon-remove-sign" title="加入黑名单"></i>
                            </a>
                        </td>
                    </tr>
                </c:forEach>

                </tbody>
            </table>


        </div>
        <!-- /widget-content -->

        <ul class="pagination" id="Pages">
            <li id="backward">
                <a href="javascript:moveTo(-1);">&laquo;</a>
            </li>
            <li class="active"><a href="#">${requestScope.PageIndex}</a></li>
            <li id="forward">
                <a href="javascript:moveTo(1);">&raquo;</a>
            </li>
        </ul>

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

<!-- 修改用户信息Start -->
<div class="modal fade" id="UserInfo">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="ChangeModalLabel">用户信息</h4>
            </div>
            <div class="modal-body">
                <form role="form" id="CreateForm">
                    <div class="form-group">
                        <label for="Name">姓名</label>
                        <input type="text" class="form-control" id="Name">
                    </div>
                    <div class="form-group">
                        <label for="IDNumber">身份证号</label>
                        <input type="text" class="form-control" id="IDNumber">
                    </div>
                    <div class="form-group">
                        <label for="Address">地址</label>
                        <input type="text" class="form-control" id="Address">
                    </div>
                    <div class="form-group">
                        <label for="Phone">手机号</label>
                        <input type="text" class="form-control" id="Phone">
                    </div>
                    <div class="form-group">
                        <label for="Weixin">微信</label>
                        <input type="text" class="form-control" id="Weixin">
                    </div>
                    <div class="form-group">
                        <label for="QQ">QQ</label>
                        <input type="text" class="form-control" id="QQ">
                    </div>
                    <div class="form-group">
                        <label for="Remark">备注</label>
                        <input type="text" class="form-control" id="Remark">
                    </div>

                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="ChangeUserInfo()" data-dismiss="modal">确认
                </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<!-- 修改用户信息End -->

<!-- 发送信息Start -->
<div class="modal fade" id="SendMessage">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="SendMessageLabel">发送信息</h4>
            </div>
            <div class="modal-body">
                <textarea id="MessageText" style="width: 520px; height: 200px;">

                </textarea>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="SendMessage()" data-dismiss="modal">确认</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<!-- 发送信息End -->

<!-- 发送广告Start -->
<div class="modal fade" id="SendAd">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="SendAdLabel">发送广告</h4>
            </div>
            <div class="modal-body">
                <select id="AdSelect" class="form-control" style=" margin-bottom: 20px; width: auto;">
                    <c:forEach items="${requestScope.Adlist}" var="ad" varStatus="sta">
                        <option value="${ad.get('Adkey')}">${ad.get('Adname')}</option>
                    </c:forEach>
                </select>

                <hr/>
                <div class="widget">

                    <div class="widget-content" id="PreviewPage">

                    </div>

                </div>
                <!-- /widget -->

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="AddNotice()" data-dismiss="modal">确认发送</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<!-- 发送广告End -->

<!-- 修改分组Start -->
<div class="modal fade" id="ModifyGroup">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">修改分组</h4>
            </div>
            <div class="modal-body">
                <select id="ChangeUserGroup" class="form-control" style=" margin-bottom: 20px; width: auto;">

                    <option value="-1">请选择分组</option>
                    <c:forEach items="${requestScope.Grouplist}" var="an" varStatus="sta">
                        <option value="${an.get('GroupID')}">${an.get('GroupName')}</option>
                    </c:forEach>

                </select>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="ChangeFollowersGroup()" data-dismiss="modal">确认
                </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<!-- 修改分组End -->


<!-- Le javascript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="./js/jquery-1.7.2.min.js"></script>
<script src="./js/bootstrap.js"></script>
<script type="text/javascript" charset="utf-8">
var StatusID = '${requestScope.StatusID}';
var GroupID = '${requestScope.GroupID}';
var APPID = '${requestScope.APPID}';
var FOLLOWER = "";
var TotalCount = ${requestScope.count};
var PageIndex = ${requestScope.PageIndex};
var followernickname = '${requestScope.followernickname}';

$("#APPID").val(APPID);
$("#UserStatus").val(StatusID);
$("#UserGroup").val(GroupID);

function GetChange(objid) {
    if (objid == "APPID") {
        APPID = $("#APPID").val();
    }
    if (objid == "UserStatus") {
        StatusID = $("#UserStatus").val();
    }
    if (objid == "UserGroup") {
        GroupID = $("#UserGroup").val();
    }
    window.location = "followerlist.htm?AppEntraceID=" + APPID +
            "&StatusID=" + StatusID +
            "&GroupID=" + GroupID +
            "&followernickname=" + followernickname;
}

function SearchNickName() {
    if ($('#SearchKeyWords').val() != "" && $('#SearchKeyWords').val() != null) {

        window.location = "followerlist.htm?AppEntraceID=" + APPID +
                "&StatusID=" + StatusID +
                "&GroupID=" + GroupID +
                "&followernickname=" + $('#SearchKeyWords').val();
    }
}

function moveTo(num) {
    var index = PageIndex + num;
    if (index <= 0) {
        alert("当前已是第一页！");
        return;
    }
    if (PageIndex * 20 > TotalCount && num > 0) {
        alert("当前已是最后一页！");
        return;
    }
    window.location = "./followerlist.htm?AppEntraceID=" + APPID + "&StatusID=" + StatusID + "&GroupID=" + GroupID +
            "&PageIndex=" + index +
            "&followernickname=" + followernickname;
}

function OpenSender(FID) {
    FOLLOWER = FID;
    $("#MessageText").val("");
    $("#SendMessage").modal("show");
}

function OpenSenderBatch() {
    FOLLOWER = GetSelectedGroupStr();
    if (FOLLOWER != null) {
        $("#MessageText").val("");
        $("#SendMessage").modal("show");
    }
}

function OpenAddSender(FID) {
    FOLLOWER = FID;
    $("#SendAd").modal("show");
}

function OpenAddSenderBatch() {
    FOLLOWER = GetSelectedGroupStr();
    if (FOLLOWER != null) {
        $("#SendAd").modal("show");
    }

}


function UserInfo(FollowerID) {
    //获取用户信息
    $.ajax({
        type: "GET",
        url: "/followerinfo.jn",
        dataType: "json",
        data: "FollowersID=" + FollowerID,
        success: function (data) {
            if (data == null) {
                $('#Name').val("");
                $('#IDNumber').val("");
                $('#Address').val("");
                $('#Phone').val("");
                $('#Weixin').val("");
                $('#QQ').val("");
                $('#Remark').val("");
                $('#UserInfo').modal('show');
            } else {
                $('#Name').val(data.Name);
                $('#IDNumber').val(data.IDNumber);
                $('#Address').val(data.Address);
                $('#Phone').val(data.Phone);
                $('#Weixin').val(data.Weixin);
                $('#QQ').val(data.QQ);
                $('#Remark').val(data.Remark);
                $('#UserInfo').modal('show');
            }
            FOLLOWER = FollowerID;
        }
    });
}

function ChangeUserInfo() {
    if ("" == FOLLOWER) {
        alert("请指定用户！");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/followerinfo.jn",
        data: {
            FollowersID: FOLLOWER,
            Name: $('#Name').val(),
            IDNumber: $('#IDNumber').val(),
            Address: $('#Address').val(),
            Phone: $('#Phone').val(),
            Weixin: $('#Weixin').val(),
            QQ: $('#QQ').val(),
            Remark: $('#Remark').val()
        },
        success: function (data) {
            alert(data);
            $('#Name').val("");
            $('#IDNumber').val("");
            $('#Address').val("");
            $('#Phone').val("");
            $('#Weixin').val("");
            $('#QQ').val("");
            $('#Remark').val("");
            history.go(0);
        }
    });
}

function AddNotice() {
    //获取用户ID
    $.ajax({
        type: "POST",
        url: "/Ad",
        data: {
            FollowersID: FOLLOWER,
            Adkey: $("#AdSelect").val()
        },
        success: function (data) {
            alert(data);
            FOLLOWER = '';
        }
    });
}

function SendMessage() {

    $.ajax({
        type: "POST",
        url: "/SendMessage",
        data: "FollowersID=" + FOLLOWER + "&MessageContent=" + $("#MessageText").val(),
        success: function (data) {
            alert(data);
            FOLLOWER = '';
        }
    });
}


//全选
function SelectAll() {
    $(".MultiSelects").each(function () {
        $(this).get(0).checked = true;
    });
}

//全不选
function DisSelectAll() {
    $(".MultiSelects").each(function () {
        $(this).get(0).checked = false;
    });
}

//获取选择ID
function GetSelectedGroupStr() {
    var SelectedGroupStr = '';
    if ($('input:checked').length == 0) {
        alert("请选定用户！！");
        return;
    }

    $('input:checked').each(function () {
        var userid_temp = $(this).get(0).value;
        if (userid_temp != -1) {
            SelectedGroupStr += "卐" + userid_temp;
        }
    });
    return SelectedGroupStr.substring(1, SelectedGroupStr.length);
}

function Del(Foid) {
    if (window.confirm('你确定要放入黑名单吗？')) {
        $.ajax({
            type: "POST",
            url: "/BlackWhiteList.htm",
            data: {
                Type: 'followers',
                FollowersID: Foid
            },
            success: function (data) {
                alert(data);
                history.go(0);
            }
        });
    }
}

function DelSelect() {
    FOLLOWER = GetSelectedGroupStr();
    if (FOLLOWER == null) {
        alert("请选定用户进行批量删除！！");
        return;
    }

    if (window.confirm('你确定要批量放入黑名单吗？')) {
        $.ajax({
            type: "POST",
            url: "/BlackWhiteList.htm",
            data: {
                Type: 'followers',
                FollowersID: FOLLOWER
            },
            success: function (data) {
                alert(data);
                history.go(0);
            }
        });
    }
}

function ChangeFollowersGroup() {
    var foid = GetSelectedGroupStr();
    var groupid = $('#ChangeUserGroup').val();

    if (foid.length == '') {
        alert('请选择用户进行修改分组设置！');
        return;
    }

    if (groupid == -1) {
        alert('请选择分组！');
        return;
    }

    $.ajax({
        type: "POST",
        url: "/GroupManager.htm",
        data: {
            Type: 'ChangeGroup',
            FollowerID: foid,
            GroupID: groupid
        },
        success: function (data) {
            alert(data);
            if (data.contains('成功')) {
                history.go(0);
            }
        }
    });
}
//页面加载函数
$(document).ready(function () {
    $("#checkall").click(function () {
        if ($("#checkall").is(":checked")) {
            SelectAll();
        } else {
            DisSelectAll();
        }
    });

    if (PageIndex * 20 > TotalCount) {
        $("#forward").addClass("disabled");
    }

    if (PageIndex == 1) {
        $("#backward").addClass("disabled");
    }
    $('#SearchKeyWords').val('${requestScope.followernickname}');
});
</script>
</body>
</html>

