<%--
  Created by IntelliJ IDEA.
  User: Ronan
  Date: 15-01-16
  Time: 下午5:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <title>商家管理</title>

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
    <script src="./js/Industry.js"></script>
    <script src="./js/AreaSelect.js"></script>
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

        #AreaSelectDIV select{
            width: 100px;
        }

        #IndustrySelectDIV select{
            width: 100px;
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
                        getMenu(${sessionScope.UserType},"ShopManager");
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
                    商家管理
                    <div class="input-group" id="SearchGroup">
                        <input id="SearchKeyWords" type="text">
                        <div class="btn-group" style="display: inline-block">
                            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" >
                                搜索<span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu">
                                <li><a href="#">商户名称</a></li>
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
                                <button type="button" class="btn btn-default" onclick="CreateShop()">新增</button>
                            </div>
                        </div>
                    </div> <!-- /widget-header -->
                    <div class="widget-content">
                        <table class="table table-striped table-bordered" style="white-space: nowrap;">
                            <thead>
                            <tr>
                                <th>商户名称</th>
                                <th>地址</th>
                                <th>负责人</th>
                                <th>联系人</th>
                                <th>行业</th>
                                <th>单位性质</th>
                                <th>是否共享WIFI</th>
                                <th>操作</th>
                            </tr>
                            </thead>

                            <tbody>

                            <c:forEach items="${requestScope.list}" var="an" varStatus="sta" >
                                <tr>
                                    <td class="limitBoundary">${an.get("BusinesName")}</td>
                                    <td class="limitBoundary">${an.get("BusinesAddres")}</td>
                                    <td class="limitBoundary">${an.get("BusinesBoss")}</td>
                                    <td class="limitBoundary">${an.get("BusinesContact")}</td>
                                    <td class="limitBoundary">${an.get("IndustryID")}</td>
                                    <td class="limitBoundary">${an.get("NatureID")}</td>
                                    <td class="limitBoundary">${an.get("WifiShare")?"共享":"不共享"}</td>
                                    <td>
                                        <a href="javascript:;" class="btn btn-small" onclick="">
                                            <i class="icon-cog" title="用户信息" onclick="ChangeShopInfo('${an.get('BusinesShopID')}')"> 修改</i>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div> <!-- /widget-content -->
                    <ul class="pagination" id="Pages">
                    	<li><a href="javascript:moveToFirst();">首页</a></li>
                        <li><a href="javascript:moveTo(-1);">&laquo;</a></li>
                        <li class="active"><a href="#">${requestScope.PageIndex}</a></li>
                        <li><a href="javascript:moveTo(1);">&raquo;</a></li>
                    	<li><a href="javascript:moveToEnd();">末页</a></li>
                    </ul>
                </div> <!-- /widget -->
            </div> <!-- /span9 -->
        </div> <!-- /row -->
    </div> <!-- /container -->
</div> <!-- /content -->

<!--   新增商家Start -->
<div class="modal fade" id="CreateShop">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="ChangeModalLabel">新增商家</h4>
            </div>
            <div class="modal-body">
                <form role="form" id="CreateForm">
                    <div class="form-group">
                        <label for="BusinessName">商家名称<span style="color:#E53333;">*</span></label>
                        <input type="text" class="form-control" id="BusinessName" autocomplete="on">
                    </div>
                    <div class="form-group">
                        <label for="LoginName">登录名称<span style="color:#E53333;">*输入登录名称只能以英文字母开头，不分大小写，长度大于3，小于10</span></label>
                        <input type="text" class="form-control" id="LoginName" autocomplete="on">
                    </div>
                    <div class="form-group">
                        <label for="BusinessAddress">地址<span style="color:#E53333;">*</span></label>
                        <input type="text" class="form-control" id="BusinessAddress" autocomplete="on">
                    </div>
                    <div class="form-group">
                        <label for="BusinessBoss">负责人<span style="color:#E53333;">*</span></label>
                        <input type="text" class="form-control" id="BusinessBoss" autocomplete="on">
                    </div>
                    <div class="form-group">
                        <label for="BossMobile">负责人电话<span style="color:#E53333;">*</span></label>
                        <input type="text" class="form-control" id="BossMobile" autocomplete="on">
                    </div>
                    <div class="form-group">
                        <label for="BusinessContact">联系人<span style="color:#E53333;">*</span></label>
                        <input type="text" class="form-control" id="BusinessContact" autocomplete="on">
                    </div>
                    <div class="form-group">
                        <label for="ContactMobile">联系人电话<span style="color:#E53333;">*</span></label>
                        <input type="text" class="form-control" id="ContactMobile" autocomplete="on">
                    </div>
                    <div class="form-group">
                        <label for="BusinessFax">传真</label>
                        <input type="text" class="form-control" id="BusinessFax" autocomplete="on">
                    </div>


                    <input type="hidden" class="form-control" id="BusinessNature" name="BusinessNature" value="0" autocomplete="on">
                    <input type="hidden" class="form-control" id="ProCity" name="ProCity"  value="0" autocomplete="on">
                    <input type="hidden"  class="form-control" id="Industry" name="Industry"  value="0" autocomplete="on">
                    <div class="form-group">
                        <label for="AreaSelectDIV">城市</label>
                        <div id="AreaSelectDIV">
                            <select id="AreaSelect" onchange="showNextAreaSelect(this.options[this.selectedIndex].value)">
                                <script>
                                    getAreaSelect();
                                </script>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="IndustrySelectDIV">行业</label>
                        <div id="IndustrySelectDIV">
                            <select id="IndustrySelect" onchange="shownext(this.options[this.selectedIndex].value)">
                                <script>
                                    getmainselect();
                                </script>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="WifiShare">是否共享ＷＩＦＩ</label>
                        <select id="WifiShare" class="form-control"  onchange="this.options[this.selectedIndex].value;">
                            <option value="true">共享</option>
                            <option value="false">不共享</option>
                        </select>
                    </div>

                </form>

                <!-- Start ListMenu -->
                <div id="menuContent" class="menuContent" style="display:none; position: absolute;">
                    <ul id="AreaList" class="ztree" style="margin-top:0; width:112px; height:auto;"></ul>
                    <ul id="KindList" class="ztree" style="margin-top:0; width:112px; height:auto;"></ul>
                    <ul id="IndustryList" class="ztree" style="margin-top:0; width:112px; height:auto;"></ul>
                </div>
                <!-- End ListMenu -->

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="CreatePost()" data-dismiss="modal">确认</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<!--  新增商家End -->


<jsp:include page="footer.jsp"></jsp:include>
 <!-- /footer -->

<!-- Le javascript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="./js/jquery-1.7.2.min.js"></script>
<script src="./js/Verification.js"></script>
<script src="./js/bootstrap.js"></script>

<script>
    var SHOPID = "";
    var TotalCount = ${requestScope.ShopListCount};
    var PageIndex = ${requestScope.PageIndex}; 

    function CreateShop(){
        document.getElementById("ChangeModalLabel").innerText ="新建用户";
        $("#CreateShop").modal("show");
    }

    function CreatePost(){
        if(document.getElementById("ChangeModalLabel").innerText == "新建用户"){

            if(isNull($("#BusinessName").val())){
                alert("商家不能为空！");
                return;
            }
            var reg = /^[a-zA-Z]{1}[a-zA-Z0-9]{2,9}$/;
        	if(!reg.test($("#LoginName").val())){
        		alert("输入登录名称只能以英文字母开头，不分大小写，长度大于3，小于10");
        		return;
        	}
            if(isNull($("#LoginName").val())){
                alert("登陆名称不能为空！");
                return;
            }
            if(isNull($("#BusinessAddress").val())){
                alert("商家地址不能为空！");
                return;
            }
            if(isNull($("#BusinessBoss").val())){
                alert("商家负责人电话不能为空！");
                return;
            }
            if(isNull($("#BossMobile").val())){
                alert("商家电话不能为空！");
                return;
            }
            if(isNull($("#BusinessContact").val())){
                alert("商家联系人不能为空！");
                return;
            }
            if(isNull($("#ContactMobile").val())){
                alert("商家联系人电话不能为空！");
                return;
            }
            if(!isTel($("#BossMobile").val()) || !isTel($("#ContactMobile").val())){
                alert("手机号码格式错误！");
                return;
            }

            $.ajax({
                type:"POST",
                url:"/ShopList.htm",
                data:{
                	Type:"createBusinessShop",
                    BusinessName:$("#BusinessName").val(),
                    LoginName:$("#LoginName").val(),
                    BusinessAddress:$("#BusinessAddress").val(),
                    BusinessBoss:$("#BusinessBoss").val(),
                    BossMobile:$("#BossMobile").val(),
                    BusinessContact:$("#BusinessContact").val(),
                    ContactMobile:$("#ContactMobile").val(),
                    BusinessFax:$("#BusinessFax").val(),
                    Industry:$("#Industry").val(),
                    BusinessNature:$("#BusinessNature").val(),
                    ProCity:$("#ProCity").val(),
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
        }else{
            $.ajax({
                type:"POST",
                url:"/ShopList.htm",
                dataType:"json",
                data:{
                	Type:"updateBusinessShop",
                    ShopID:SHOPID,
                    BusinessName:$("#BusinessName").val(),
                    LoginName:$("#LoginName").val(),
                    BusinessAddress:$("#BusinessAddress").val(),
                    BusinessBoss:$("#BusinessBoss").val(),
                    BossMobile:$("#BossMobile").val(),
                    BusinessContact:$("#BusinessContact").val(),
                    ContactMobile:$("#ContactMobile").val(),
                    BusinessFax:$("#BusinessFax").val(),
                    Industry:$("#Industry").val(),
                    BusinessNature:$("#BusinessNature").val(),
                    ProCity:$("#ProCity").val(),
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
  	//获取商户信息
    function ChangeShopInfo(ShopID){
        $.ajax({
            type:"POST",
            url:"/ShopList.htm",
            dataType:"json",
            data:{
            	Type:"getShopInfo",
            	ShopID:ShopID
            },
            success:function(data){
                if(data != null){
                    $("#BusinessName").val(data.BusinessName);
                    $("#LoginName").val(data.LoginName);
                    $("#BusinessAddress").val(data.BusinessAddress);
                    $("#BusinessBoss").val(data.BusinessBoss);
                    $("#BossMobile").val(data.BossMobile);
                    $("#BusinessContact").val(data.BusinessContact);
                    $("#ContactMobile").val(data.ContactMobile);
                    $("#BusinessFax").val(data.BusinessFax);
                    $("#Industry").val(data.Industry);
                    $("#BusinessNature").val(data.BusinessNature);
                    $("#ProCity").val(data.ProCity);
                    $("#WifiShare").val(data.WifiShare);
                    document.getElementById("ChangeModalLabel").innerText ="修改用户";
                    $("#LoginName").attr({disabled:"true"});
                    $("#CreateShop").modal("show");
                }else{
                    alert("Bugy...");
                }
                SHOPID = ShopID;
            }
        });
    }

	/* 分页处理 start*/
	function moveToFirst(){
		window.location="./ShopList.htm?PageIndex=1";
	}
	function moveToEnd(){
		var index = Math.ceil(TotalCount/8);
		window.location="./ShopList.htm?PageIndex=" + index;
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
        window.location = "./ShopList.htm?PageIndex=" + index;
    }
    /* 分页处理 end*/

</script>

</body>
</html>

