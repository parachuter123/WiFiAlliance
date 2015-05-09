<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>路由列表</title>

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
        #NewBW {
            float: right;
            margin-top: 4px;
        }
        #SearchGroup {
            margin-top: -10px;
        }
        #Pages {
            float: right;
        }
		.widget-table .table {
			margin-bottom: 0;
			width: 868px;
			table-layout: fixed;
			border: none;
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
                        getMenu(${sessionScope.UserType}, "BlackWhiteList");
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
                    <i class="icon-th-list"></i>
                  	  黑<a href="./BlackWhiteList.htm?listtype=true">白</a>名单列表
					<button type="button" class="btn btn-default" id="NewBW" data-toggle="modal" onclick="newBlack()">新建记录</button>
                </h1>
                <div class="widget widget-table">
                    <div class="widget-header">
                        <div class="btn-group" style="display: inline-block;float: right;">
                            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"
                                    style="margin-top: 5px;margin-right: 5px;margin-left: 5px;">
                                	搜索<span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu">
                                <li><a href="#" onclick="Search()">MAC地址</a></li>
                                <li><a href="#" onclick="Search()">备注名称</a></li>
                            </ul>
                        </div>

                        <div style="float: right; margin-bottom: 4px;">
                            <input id="SearchKeyWords" type="text" style=" margin-bottom: 4px; ">
                        </div>

                    </div>
                    <!-- /widget-header -->

                    <div class="widget-content">

                        <table class="table table-striped table-bordered" style="white-space: nowrap;">
                            <thead>
                            <tr>
                                <th>MAC地址</th>
                                <th>类型</th>
                                <th>备注</th>
                                <th>操作</th>
                            </tr>
                            </thead>

                            <tbody>
                            <c:forEach items="${requestScope.BlackWhite}" var="an" varStatus="sta">
                                <tr>
                                    <td>
                                        <button type="button" onclick="Change(this)" class="btn btn-toolbar"
                                                id="${an.get('Mac')}">${an.get('Mac')}</button>
                                    </td>
                                    <td>${an.get('ListType')?'白名单':'黑名单'}</td>
                                    <td>${an.get('Remark')}</td>
                                    <td>
                                        <a href="javascript:;" class="btn btn-small"
                                           onclick="del('${an.get('BlackWhiteListID')}')">
                                            <i class="icon-remove-sign" title="用户信息"> 删除</i>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>

                    </div>
                    <!-- /widget-content -->

                    <ul class="pagination" id="Pages">
                    	<li><a href="javascript:moveToFirst();">首页</a></li>
                        <li><a href="javascript:moveTo(-1);">&laquo;</a></li>
                        <li class="active"><a href="#">${requestScope.PageIndex}</a></li>
                        <li><a href="javascript:moveTo(1);">&raquo;</a></li>
                    	<li><a href="javascript:moveToEnd();">末页</a></li>
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

<!-- 新建黑白名单信息Start -->
<div class="modal fade" id="CreateModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="ChangeModalLabel">新建黑白名单</h4>
            </div>
            <div class="modal-body">
                <form role="form" id="CreateForm">

                    <div class="form-group">
                        <label for="ListType">类型</label>
                        <select class="form-control" id="ListType" onchange="this.options[this.selectedIndex].value;">
                            <option value="true">白名单</option>
                            <option value="false">黑名单</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="MAC">Mac地址<span style="color:#E53333;">*</span></label>
                        <input class="form-control" id="MAC"/>
                    </div>
                    <div class="form-group">
                        <label for="Remark">备注<span style="color:#E53333;">*</span></label>
                        <input class="form-control" id="Remark"/>
                    </div>
                </form>

            </div>
            <div class="modal-footer">
                <button type="reset" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="Create();" data-dismiss="modal">确认</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<!-- 新建黑白名单信息End -->

<!-- Le javascript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="./js/jquery-1.7.2.min.js"></script>
<script src="./js/Verification.js"></script>
<script src="./js/bootstrap.js"></script>

<script type="text/javascript">

    var OpratorRouterID = "";

    var TotalCount = ${requestScope.BlackWhiteCout};
    var PageIndex = ${requestScope.PageIndex};
    
    function del(bid) {
        if (window.confirm('你确定要删除吗？')) {
            $.ajax({
                type: "POST",
                url: "/BlackWhiteList.htm",
                dataType: "json",
                data: {
                    Type: "Delete",
                    BlackWhiteListID: bid
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
            return;
        } else {
            return;
        }

    }

    //关键词搜索
    function Search() {
        if ($('#SearchKeyWords').val() != "" && $('#SearchKeyWords').val() != null) {
            window.location = "./BlackWhiteList.htm?SearchKeyWords=" + $('#SearchKeyWords').val();
        }
    }

    function newBlack() {
        $("#MAC").val("");
        $("#ListType").val("true");
        $("#Remark").val("");
        $("#CreateModal").modal("show");
    }

    function Change(obj) {

        $("#MAC").val($(obj).text());

        $("#Remark").val($(obj).parent().next().next().text());

        $("#CreateModal").modal("show");
        $("#ListType").val(($(obj).parent().next().text() == "白名单").toString());
		//alert($(obj).text()+"|"+$(obj).parent().next().text()+"|"+$(obj).parent().next().next().text());
    }

    function Create() {
        if (isNull($("#MAC").val())) {
            alert("MAC地址不允许为空！");
            return;
        }
        if (isNull($("#Remark").val())) {
            alert("备注不允许为空,请备注您设置的设备名称以作标识！");
            return;
        }
        $.ajax({
            type: "POST",
            url: "/BlackWhiteList.htm",
            dataType: "json",
            data: {
                Type: "Create",
                Mac: $("#MAC").val(),
                ListType: $("#ListType").val(),
                Remark: $("#Remark").val()
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
	function moveToFirst(){
		window.location="./BlackWhiteList.htm?PageIndex=1";
	}
	function moveToEnd(){
		var index = Math.ceil(TotalCount/8);
		window.location="./BlackWhiteList.htm?PageIndex=" + index;
	}
    function moveTo(num) {
        var index = PageIndex + num;
        if (index <= 0) {
            alert("当前已是第一页！");
            return;
        }
        if (PageIndex * 8 > TotalCount && num > 0) {
            alert("当前已是最后一页！");
            return;
        }
        window.location = "./BlackWhiteList.htm?PageIndex=" + index;
    }
</script>

</body>
</html>
