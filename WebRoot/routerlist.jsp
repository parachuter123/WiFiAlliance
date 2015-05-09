<%--
  User: parachuter
  Date: 14-12-10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="smny.util.StringToolkit" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>路由列表</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <link rel="shortcut icon" href="./img/headshot.png"/>
	
    <script src="./js/jquery-1.7.2.min.js"></script>
    <script src="./js/Verification.js"></script>
    <script type="text/javascript" src="js/layer/layer.min.js"></script>
    <script src="./js/bootstrap3.min.js"></script>
    <script charset="utf-8" src="http://map.qq.com/api/js?v=2.exp"></script>


    <link href="./css/bootstrap.min.css" rel="stylesheet"/>
    <link href="./css/bootstrap-responsive.min.css" rel="stylesheet"/>

    <link href="./css/font-awesome.css" rel="stylesheet"/>

    <link href="./css/adminia.css" rel="stylesheet"/>
    <link href="./css/adminia-responsive.css" rel="stylesheet"/>

    <link href="./css/pages/plans.css" rel="stylesheet"/>
    <script src="./js/menu.js"></script>

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
		.limitBoundary{
		    width:60%;
		    word-break:keep-all;/* 不换行 */
		    white-space:nowrap;/* 不换行 */
		    overflow:hidden;/* 内容超出宽度时隐藏超出部分的内容 */
		    text-overflow:ellipsis;/* 当对象内文本溢出时显示省略标记(...) ；需与overflow:hidden;一起使用。*/
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
                        getMenu(${sessionScope.UserType}, "routerlist");
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
                    	路由器列表
                    <div class="input-group" id="SearchGroup">
                        <input id="SearchKeyWords" type="text">

                        <div class="btn-group" style="display: inline-block">
                            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                搜索<span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu">
                                <li><a href="#">会员名称</a></li>
                                <li><a href="#">登陆ID</a></li>
                                <li><a href="#">联系电话</a></li>
                                <li class="divider"></li>
                                <li><a href="#">备注</a></li>
                            </ul>
                        </div>
                    </div>
                    <!-- /input-group -->
                </h1>
                <div class="widget widget-table">
                    <div class="widget-header">
						<div class="widget-header">
	                        <i class="icon-group"></i>
	                        <h3>路由列表</h3>
	                        <c:if test="${sessionScope.UserType == '4'}">
		                        <div id="adduser" >
		                            <div class="btn-group">
		                                <button type="button" class="btn btn-default" onclick="createRouter('${requestScope.getRandomString}')">添加路由</button>
		                            </div>
		                        </div>
	                        </c:if>
                    	</div>
                    </div>
                    <!-- /widget-header -->

                    <div class="widget-content">

                        <table class="table table-striped table-bordered" style="white-space: nowrap;">
                            <thead>
                            <tr>
                                <th>设备名称</th>
                                <th>户主姓名</th>
                                <th>联系电话</th>
                                <th>地址</th>
                                <th>设备串号</th>
                                <th>最大连接数量</th>
                                <th style="width: 197px;">操作</th>
                            </tr>
                            </thead>

                            <tbody>
                            <c:forEach items="${requestScope.list}" var="an" varStatus="sta">
                                <tr>
                                    <td class="limitBoundary"><a href="#" title="${an.get('DevName')}"
                                           onclick="OpenList('${an.get('WifiRouterID')}','${an.get('DevName')}')" >${an.get("DevName")}</a></td>
                                    <td class="limitBoundary">${an.get("RouterCont")}</td>
                                    <td class="limitBoundary">${an.get("RouterTel")}</td>
                                    <td class="limitBoundary">${an.get("RouterAddr")}</td>
                                    <td class="limitBoundary">${an.get("HardSeq")}</td>
                                    <td class="limitBoundary">${an.get("MaxConnectCount")}</td>
                                    <td>
                                        <a href="javascript:;" class="btn btn-small"
                                           onclick="setting('${an.get('WifiRouterID')}')">
                                            <i class="icon-cog" title="修改信息"> 修改</i>
                                        </a>
                                        <a href="javascript:;" class="btn btn-small Open"
                                           style="display: none"
                                           onclick="Open('${an.get('WifiRouterID')}')">
                                            <i class="icon-cog" title="Open"> Open</i>
                                        </a>
                                        <c:if test="${sessionScope.UserType == '4' || sessionScope.UserType == '3' || sessionScope.UserType == '2'}">
                                            <a href="javascript:;" class="btn btn-small Open"
                                               onclick="Delete('${an.get('WifiRouterID')}')">
                                                <i class="icon-remove-sign" title="Open"> 删除</i>	
                                            </a>
                                        </c:if>
                                        <c:if test="${sessionScope.UserType == '4' || sessionScope.UserType == '3' || sessionScope.UserType == '2'}">
                                            <a href="javascript:;" class="btn btn-small Open" 
                                            	onclick="RestartRouter('${an.get('HardSeq')}')">
                                                <i class="icon-off" title="Open"> 重启路由</i>	
                                            </a>
                                        </c:if>
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

<!-- 修改路由器信息Start -->
<div class="modal fade" id="RouterSetting">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="ChangeModalLabel">路由器信息</h4>
            </div>
            <div class="modal-body">
                <form role="form" id="CreateForm">
                    <%--<div class="form-group">--%>
                    <%--<label for="Name">姓名</label>--%>
                    <%--<input type="text" class="form-control" id="Name">--%>
                    <%--</div>--%>

                    <div class="form-group">
                        <label for="AppEntrance">默认消息接口</label>
                        <select class="form-control" id="AppEntrance"
                                onchange="this.options[this.selectedIndex].value;">
                            <option value="">无微信认证</option>
                            <c:forEach items="${requestScope.Applist}" var="App" varStatus="sta">
                                <option value="${App.get('AppEntranceID')}">${App.get("AppName")}</option>
                            </c:forEach>
                        </select>

                    </div>

                    <div class="form-group">
                        <label for="DevName">路由器名称</label>
                        <input class="form-control" id="DevName"/>
                    </div>
                    <div class="form-group">
                        <label for="RouterAddr">路由器地址</label>
                        <input class="form-control" id="RouterAddr"/>
                    </div>
                    <div class="form-group">
                        <label for="RouterCont">联系人</label>
                        <input class="form-control" id="RouterCont"/>
                    </div>
                    <div class="form-group">
                        <label for="RouterTel">联系电话</label>
                        <input class="form-control" id="RouterTel"/>
                    </div>
                    <div class="form-group">
                        <label for="IntroductionURL">主页</label>
                        <input class="form-control" id="IntroductionURL"/>
                    </div>
                    <div class="form-group">
                        <label for="UrlData">URL白名单&nbsp;&nbsp;&nbsp;<span style="color:#E53333;">可以添加若干域名，域名之间用分号间隔</span></label>
                        <input class="form-control" id="UrlData"/>
                    </div>
                    <div class="form-group">
                        <label for="BrowseAuthGuide">浏览器放行方式</label>
                        <select id="BrowseAuthGuide" onchange="this.options[this.selectedIndex].value;"
                                class="form-control" style=" margin-bottom: 20px; width: auto;">
                            <option value="Sms">短信认证</option>
                            <option value="WeiXin">微信认证</option>
                            <option value="Try">试用</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="BrowseGuide">手机浏览器引导</label>
                        <select id="BrowseGuide" onchange="this.options[this.selectedIndex].value;"
                                class="form-control" style=" margin-bottom: 20px; width: auto;">
                            <option value="">默认</option>
                            <c:forEach items="${requestScope.Adlist}" var="ad" varStatus="sta">
                                <option value="${ad.get('Key')}">${ad.get("Name")}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="PCBrowseGuide">电脑浏览器引导</label>
                        <select id="PCBrowseGuide" onchange="this.options[this.selectedIndex].value;"
                                class="form-control" style=" margin-bottom: 20px; width: auto;">
                            <option value="">默认</option>
                            <c:forEach items="${requestScope.Adlist}" var="ad" varStatus="sta">
                                <option value="${ad.get('Key')}">${ad.get("Name")}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="ShareTaskID">分享任务</label>
                        <select id="ShareTaskID" onchange="this.options[this.selectedIndex].value;"
                                class="form-control" style=" margin-bottom: 20px; width: auto;">
                            <option value="">无</option>
                            <c:forEach items="${requestScope.ShareTaskList}" var="ad" varStatus="sta">
                                <option value="${ad.get('ShareID')}">${ad.get("Name")}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="Longitude">经度</label>
                        <input class="form-control" id="Longitude"/>
                    </div>
                    <div class="form-group">
                        <label for="Latitude">纬度</label>
                        <input class="form-control" id="Latitude"/>
                    </div>
                    <div style="width:510px;height:206px" id="map"></div>

                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="ChangeSetting();" data-dismiss="modal">确认
                </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<!-- 修改路由器信息End -->

<!-- 新建路由信息Start -->
<div class="modal fade" id="CreateRouter">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="ChangeModalLabel">路由信息</h4>
            </div>
            <div class="modal-body">
                <form role="form" id="CreateForm">
                    <div class="form-group">
                        <label for="CDevName">随机码<span style="color:#E53333;">*必填</span></label>
                        <input type="text" class="form-control" id="CDevName" disabled="true" value="${requestScope.getRandomString}">
                    </div>
                    <div class="form-group">
                        <label for="CRouterCont">户主姓名<span style="color:#E53333;">*必填</span></label>
                        <input type="text" class="form-control" id="CRouterCont">
                    </div>
                    <div class="telphone">
                        <label for="CRouterTel">联系电话<span style="color:#E53333;">*必填</span></label>
                        <input type="text" class="form-control" id="CRouterTel">
                    </div>
                    <div class="Address">
                        <label for="CRouterAddr">地址<span style="color:#E53333;">*必填</span></label>
                        <input type="text" class="form-control" id="CRouterAddr">
                    </div>
                    <div class="form-group">
                        <label for="AddAppEntrance">默认消息接口</label>
                        <select class="form-control" id="AddAppEntrance"
                                onchange="this.options[this.selectedIndex].value;">
                            <option value="">无微信认证</option>
                            <c:forEach items="${requestScope.Applist}" var="App" varStatus="sta">
                                <option value="${App.get('AppEntranceID')}">${App.get("AppName")}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="AddBrowseAuthGuide">浏览器放行方式</label>
                        <select id="AddBrowseAuthGuide" onchange="this.options[this.selectedIndex].value;"
                                class="form-control">
                            <option value="Sms">短信认证</option>
                            <option value="WeiXin">微信认证</option>
                            <option value="Try">试用</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="AddBrowseGuide">手机浏览器引导</label>
                        <select id="AddBrowseGuide" onchange="this.options[this.selectedIndex].value;"
                                class="form-control" style=" margin-bottom: 20px; width: auto;">
                            <option value="">默认</option>
                            <c:forEach items="${requestScope.Adlist}" var="ad" varStatus="sta">
                                <option value="${ad.get('Key')}">${ad.get("Name")}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="AddPCBrowseGuide">电脑浏览器引导</label>
                        <select id="AddPCBrowseGuide" onchange="this.options[this.selectedIndex].value;"
                                class="form-control" style=" margin-bottom: 20px; width: auto;">
                            <option value="">默认</option>
                            <c:forEach items="${requestScope.Adlist}" var="ad" varStatus="sta">
                                <option value="${ad.get('Key')}">${ad.get("Name")}</option>
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
<!-- 新建路由信息End -->


<!-- Le javascript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->


<script type="text/javascript">
    var OpratorRouterID = ""; 
    var TotalCount = ${requestScope.WifiRouterCount};
    var PageIndex = ${requestScope.PageIndex}; 
    function createRouter(randomString) {
    	$("#CreateRouter").modal("show");
    	
    	if("${requestScope.Applist.size()>0?requestScope.Applist.size():""}"){
    		$("#AddAppEntrance").val("${requestScope.Applist.size()>0?requestScope.Applist.get(0).get('AppEntranceID'):""}");
    	}
    	document.getElementById("ModelBtn").onclick = function(){
    		CreateRouter1(randomString);
    	};
    }
    function CreateRouter1(randomString){
    	if ($("#CRouterTel").val() != "") {
            if (!isTel($("#CRouterTel").val())) {
                alert("电话号码格式错误！");
                return;
            }
        }
    	$.ajax({
    		type:"POST",
    		url:"/routerlist.htm",
    		datatype:"json",
    		data:{
			 Type:"creatRouter",
			 DevName:randomString,
			 RouterCont:$("#CRouterCont").val(),
			 RouterTel:$("#CRouterTel").val(),
			 RouterAddr:$("#CRouterAddr").val(),
			 AddAppEntrance:$("#AddAppEntrance").val(),
			 AddBrowseGuide:$("#AddBrowseGuide").val(),
			 AddPCBrowseGuide:$("#AddPCBrowseGuide").val(),
			 AddBrowseAuthGuide:$("#AddBrowseAuthGuide").val()
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
    function setting(rid) {
        $.ajax({
            type: "POST",
            url: "/routerlist.htm",
            dataType: "json",
            data: {
                Type: "getRouterInfo",
                RouterID: rid
            },
            success: function (data) {
                if (data != null) {
                    $("#DevName").val(decodeURI(data.DevName));
                    $("#RouterAddr").val(decodeURI(data.RouterAddr));
                    $("#RouterCont").val(decodeURI(data.RouterCont));
                    $("#RouterTel").val(data.RouterTel);
                    $("#IntroductionURL").val(data.IntroductionURL);
                    $("#Longitude").val(data.Longitude);
                    $("#Latitude").val(data.Latitude);   	 
                    $("#AppEntrance").val(decodeURI(data.AppEntranceID));
                    $("#BrowseGuide").val(data.BrowseGuide);
                    $("#BrowseAuthGuide").val(data.BrowseAuthGuide);
                    $("#ShareTaskID").val(data.ShareTaskID);
                    $("#UrlData").val(data.UrlData);
                    $("#RouterSetting").modal("show");
                    init(data.Latitude, data.Longitude);
                    OpratorRouterID = rid;
                }
            }
        });
    }
	
    function ChangeSetting() {
        if ($("#RouterTel").val() != "") {
            if (!isTel($("#RouterTel").val())) {
                alert("电话号码格式错误！");
                return;
            }
        }
        var reg1 = /[\u4e00-\u9fa5]/;
        var reg2 = /[；]/;
        if(reg1.test($("#UrlData").val())){
        	alert("Url白名单中不许有汉字！！！");
        	return;
        }else if (reg2.test($("#UrlData").val())){
        	$("#UrlData").val($("#UrlData").val().replace(/[；]/g, ";"));
        }
        $.ajax({
            type: "POST",
            url: "/routerlist.htm",
            dataType: "json",
            data: {
                Type: "Change",
                RouterID: OpratorRouterID,
                DevName: $("#DevName").val(),
                RouterAddr: $("#RouterAddr").val(),
                RouterCont: $("#RouterCont").val(),
                RouterTel: $("#RouterTel").val(),
                IntroductionURL: $("#IntroductionURL").val(),
                Longitude: $("#Longitude").val(),
                Latitude: $("#Latitude").val(),
                DefaultAppid: $("#AppEntrance").val(),
                BrowseGuide: $("#BrowseGuide").val(),
                PCBrowseGuide: $("#PCBrowseGuide").val(),
                BrowseAuthGuide: $("#BrowseAuthGuide").val(),
                ShareTaskID:$("#ShareTaskID").val(),
                UrlData:$("#UrlData").val()
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

    function OpenList(routerid,devName) {
        $.layer({
            type: 2,
            title: false,
            fix: false,
            closeBtn: false,
            shadeClose: true,
            shade: [0.1, '#111', true],
            border: [5, 0.3, '#666', true],
            offset: ['10px', ''],
            area: ['800px', '600px'],
            iframe: {src: 'OnlineTerminalList.htm?WifiRouterID=' + routerid + '&DevName=' + encodeURI(encodeURI( devName ))},
            success: function () {
            }
        });
    }

    function Open(routerid) {
        window.open("./RouterLogin?RouterID=" + routerid);
    }

    function Delete(rid) {
    	if(!window.confirm("确定删除吗？")){
    		return;
    	}
        $.ajax({
            type: "POST",
            url: "/routerlist.htm",
            dataType:"json",
            data: {
                Type: "Delete",
                RouterID: rid
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
	function RestartRouter(hardSeq){
		if(!window.confirm("确定要重启路由器吗？重启大概需要三至五分钟左右")){
    			return;
    		}
		$.ajax({
			type:"POST",
			url:"http://sys.txly.com/MacBinding.do?MacBinding=Reboot&HardSeq=" + hardSeq,
			dataType:"json",
			success:function(data){
				alert(data.Prompt);
                		history.go(0);
			},
	       	 	error:function(data,textStatus,errorThrown){
	        	alert("网络超时，请耐心等待！！！");
	        }
		});
	}
	/* 分页处理 start*/
	function moveToFirst(){
		window.location="./routerlist.htm?PageIndex=1";
	}
	function moveToEnd(){
		var index = Math.ceil(TotalCount/8);
		window.location="./routerlist.htm?PageIndex=" + index;
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
        window.location = "./routerlist.htm?PageIndex=" + index;
    }
    /* 分页处理 end*/
    
    var map, center, marker;

    $(document).ready(function () {
        if (document.cookie.indexOf("smnyadmin") != -1) {
            $(".Open").show();
        }

        center = new qq.maps.LatLng(36.098198, 114.36347);
        map = new qq.maps.Map(document.getElementById('map'), {
            center: center,
            zoom: 17
        });
        marker = new qq.maps.Marker({
            position: center,
            title: "请选择你想指定的位置",
            Draggable: true,
            map: map
        });

    });

    var init = function (x, y) {
        if (x < y) {
            var temp = x;
            x = y;
            y = temp;
        }

        if (x == 0) {
            x = 36.098198;
        }
        if (y == 0) {
            y = 114.36347;
        }
        document.getElementById("Longitude").value = y;
        document.getElementById("Latitude").value = x;
        marker.setMap(null);


        marker = new qq.maps.Marker({
            position: new qq.maps.LatLng(x, y),
            title: "请选择你想指定的位置",
            Draggable: true,
            map: map
        });
        map.panTo(new qq.maps.LatLng(x, y));

        qq.maps.event.addListener(marker, "dragend", function (e) {
            var x = e.latLng.getLat();
            var y = e.latLng.getLng();
            document.getElementById("Longitude").value = y.toFixed(5);
            document.getElementById("Latitude").value = x.toFixed(5);
        });
        qq.maps.event.addListener(map, "click", function (e) {
            marker.setPosition(e.latLng);
            var x = e.latLng.getLat();
            var y = e.latLng.getLng();
            document.getElementById("Longitude").value = y.toFixed(5);
            document.getElementById("Latitude").value = x.toFixed(5);
        });
    };

</script>

</body>
</html>
