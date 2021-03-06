<%-- Created by Ronan. --%>
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
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Cache-Control" content="no-cache"/>
    <meta http-equiv="Expires" content="0"/>
    <link rel="shortcut icon" href="./img/headshot.png"/>

    <link href="./css/bootstrap.min.css" rel="stylesheet"/>
    <link href="./css/bootstrap-responsive.min.css" rel="stylesheet"/>

    <link href="./css/font-awesome.css" rel="stylesheet"/>

    <link href="./css/adminia.css" rel="stylesheet"/>
    <link href="./css/adminia-responsive.css" rel="stylesheet"/>

    <link href="./css/pages/dashboard.css" rel="stylesheet"/>

    <script src="./js/menu.js"></script>
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <!-- <script type="text/javascript" src="./js/jquery-1.8.3.min.js"></script> -->
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.8.3.min.js"></script>
    <script type="text/javascript">

	var authTime = [];
	var WeiXinCount = [];
	var SmsCount = [];
	var TryCount = [];
	var RouterAuthRecordList = '${requestScope.RouterAuthRecordList}';
	var RouterAuthRecordListJson = JSON.parse(RouterAuthRecordList);
	function routerListJson(RouterAuthRecordListJson){
		$.each(RouterAuthRecordListJson,function(key,value){
			authTime.push(value.AuthTime); 
			WeiXinCount.push(value.WeiXinCount);
			SmsCount.push(value.SmsCount);
			TryCount.push(value.TryCount); 
		}); 
	}
	function containerResult(){
		$('#container').highcharts({
            title: {
                text: '认证人数统计',
                x: -20 //center
            },
            subtitle: {
                text: '来源：圣玛尼亚',
                x: -20
            },
            xAxis: {
                categories: authTime
            },
            yAxis: {
                title: {
                    text: '人数(个)'
                },
                plotLines: [{
                    value: 0,
                    width: 1,
                    color: '#808080'
                }]
            },
            tooltip: {
                valueSuffix: '个'
            },
            legend: {
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'middle',
                borderWidth: 0
            },
            credits: {
                enabled: false
            },
            series: [{
                name: '微信',
                data: WeiXinCount
            }, {
                name: '短信',
                data: SmsCount
            }, {
                name: '试用',
                data: TryCount
            }]
        });
	}
	$(function(){
		var key = '${requestScope.key}';
		key= key.substring(1, key.length-1);
		var keyarr = key.split(",");
		var value = ${requestScope.value};
		$("#highcharts").highcharts({
		            chart: {
		                type: 'bar'
		            },
		            title: {
		                text: '每个路由器关注总数量'
		            },
		            subtitle: {
		                text: '关注总数量'
		            },
		            xAxis: {
		                categories: keyarr,
		                title: {
		                    text: null
		                }
		            },
		            yAxis: {
		                min: 0,
		                title: {
		                    text: '关注数量（个）',
		                    align: 'high'
		                },
		                labels: {
		                    overflow: 'justify'
		                }
		            },
		            tooltip: {
		                valueSuffix: '个'
		            },
		            plotOptions: {
		                bar: {
		                    dataLabels: {
		                        enabled: true
		                    }
		                }
		            },
		            legend: {
		                layout: 'vertical',
		                align: 'right',
		                verticalAlign: 'top',
		                x: -40,
		                y: 20,
		                floating: true,
		                borderWidth: 1,
		                backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor || '#FFFFFF'),
		                shadow: true
		            },
		            credits: {
		                enabled: false
		            },
		            series: [{
		                name: '关注数量',
		                data: value
		            }]
		});
		routerListJson(RouterAuthRecordListJson);
		containerResult();
	}); 
    </script>
    
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
                        getMenu(${sessionScope.UserType}, "Dashboard");
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
                    <i class="icon-home"></i>
                    统计面板
                </h1>


                <div class="widget">

                    <div class="widget-header">
                        <h3><span class="glyphicon glyphicon-stats"></span>系统状态</h3>
                    </div>
                    <!-- /widget-header -->

                    <div class="widget-content">
	                        <strong>在线总人数：<span class="label label-success">${requestScope.OnlineCount}</span></strong><br/>
	                        <strong>在线已认证人数</strong><br/>
	                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;微信认证人数：<span class="label label-success">${requestScope.WeiXinCount}</span>
	                        	  短信认证人数：<span class="label label-success">${requestScope.SmsCount}</span>
	                        	  试用认证人数：<span class="label label-success">${requestScope.TryCount}</span><br/><hr/>
	                        <%-- <strong>累计认证人数</strong><br/>
	                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;微信认证人数：<span class="label label-success">${requestScope.SumWeiXinCount}</span>
	                        	  短信认证人数：<span class="label label-success">${requestScope.SumSmsCount}</span>
	                        	  试用认证人数：<span class="label label-success">${requestScope.SumTryCount}</span><br/>
					        <strong>选择时间分割点
						        <select name="overrallperiodOfTime" id="overrallperiodOfTime" style="width:70px; margin-bottom:3px;" onchange="overrallperiodOfTime(this.options[this.selectedIndex].value)">
							      <option selected="selected" value="1">今日</option>
							      <option value="7">本周</option>
							      <option value="30">本月</option>
							      <option value="90">本季</option>
						        </select>
					        </strong><br/>
					        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;微信认证人数：<span class="label label-success" id="overrallSumWeixinCount">${requestScope.SumWeiXinCount}</span>
	                        	  短信认证人数：<span class="label label-success" id="overrallSumSmsCount">${requestScope.SumSmsCount}</span>
	                        	  试用认证人数：<span class="label label-success" id="overrallSumTryCount">${requestScope.SumTryCount}</span> --%>
	                        <select name="WifiRouterID" id="WifiRouterID" style="width:70px; margin-bottom:3px;" onchange="overrallperiodOfTime(this.options[this.selectedIndex].value,' ')">
		                        <option selected="selected" value="">moren</option>
		                        <c:forEach items="${requestScope.Routerlist}" var="Router" varStatus="sta">
		                        	 
		                        	l<option value="${Router.get('WifiRouterID') }">${Router.get('WifiRouterDevName') }</option>
		                        </c:forEach>
	                        </select>
	                        <select name="WifiRouterTime" id="WifiRouterTime" style="width:70px; margin-bottom:3px;" onchange="overrallperiodOfTime(' ',this.options[this.selectedIndex].value)">
		                          <option selected="selected" value="7">7天</option>
							      <option value="14">14天</option>
							      <option value="30">一个月</option>
	                        </select>
	                        <div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>	  
                        <hr/>
                        ${requestScope.AgentNum}
                        <c:forEach items="${requestScope.Routerlist}" var="Router" varStatus="sta">
                            <c:choose>
                                <c:when test="${Router.get('isOnline')}">
                                    [${Router.get("WifiRouterDevName")}]<span class='label label-success'>在线</span>
                                    <br/>
                                </c:when>
                                <c:otherwise>
                                    [${Router.get("WifiRouterDevName")}] <span class='label label-important'>不在线</span>
                                    <br/>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>

                    </div>

                </div>
                <!-- /widget -->
		
		<c:if test="${sessionScope.UserType==3||sessionScope.UserType==2 }">
                <div class="widget">

                    <div class="widget-header">
                        <h3><span class="glyphicon glyphicon-stats"></span>路由器状态统计</h3>
                        商家总数：${requestScope.BusinessShopCount }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        路由器总数：${requestScope.RouterCount }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        在线路由器总数：${requestScope.OnlineRouterCount }
                    </div>
                    <!-- /widget-header -->

                    <div class="widget-content">
                    	<table class="table table-striped table-bordered" style="white-space: nowrap;">
                            <thead>
                            <tr>
                                <th>商家名称</th>
                                <th>路由器总数</th>
                                <th>在线路由器总数</th>
                                <th style="width: 197px;">操作</th>
                            </tr>
                            </thead>

                            <tbody>
				<c:forEach items="${requestScope.EachBusinesShopList}" var="Router" varStatus="sta">
	                            <tr>
	                            	<td>${Router.get("BusinesName")}</td>
	                            	<td>${Router.get("EachBusinessShopWifiRouterCount")}</td>
	                            	<td>${Router.get("OnlineCount")}</td>
	                            	<td>
		                            	<a href="javascript:;" class="btn btn-small"
	                                           onclick="getRouterDetail('${Router.get('BusinesShopID')}')">
	                                            <i class="icon-cog" title="查看详情"> 查看详情</i>
	                                        </a>
                                        </td>
	                            </tr>
	                        </c:forEach>
                           </tbody>
                         </table>  
                    </div>

                </div>
                <!-- /widget -->
                </c:if>
                
                <!-- 路由器详细信息Start -->
		<div class="modal fade" id="RouterDetail">
		    <div class="modal-dialog">
		        <div class="modal-content">
		            <div class="modal-header">
		                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		                <h4 class="modal-title" id="ChangeModalLabel">路由器详细信息</h4>
		            </div>
		            <div class="modal-body" id="Detail">
				<c:forEach items="${requestScope.WifiRouterDetailList}" var="Router" varStatus="sta">
	                            <c:choose>
	                                <c:when test="${requestScope.isOnline}">
	                                    [${Router.get("WifiRouterDevName")}] 在线设备数目：<span class="label label-info"></span>
	                                    <br/>
	                                </c:when>
	                                <c:otherwise>
	                                    [${Router.get("WifiRouterDevName")}] 当前路由器不在线！！
	                                    <br/>
	                                </c:otherwise>
	                            </c:choose>
	                        </c:forEach>
		            </div>
		            <div class="modal-footer">
		                <button type="button" class="btn btn-default" data-dismiss="modal">确认</button>
		            </div>
		        </div>
		        <!-- /.modal-content -->
		    </div>
		    <!-- /.modal-dialog -->
		</div>
		<!-- /.modal -->
		<!-- 路由器详细信息End -->

                <%-- <c:forEach items="${requestScope.Applist}" var="App" varStatus="sta">
                    <div class="widget">

                        <div class="widget-header">
                            <h3>${App.get("AppName")}</h3>
                        </div>
                        <!-- /widget-header -->

                        <div class="widget-content">

                            <div class="stat-container">

                                <div class="stat-holder">
                                    <div class="stat">
                                        <span>${requestScope.AppRecordGET.getData(App.get("AppEntranceID")).getTotalCount()}</span>
                                        会员总数
                                    </div>
                                    <!-- /stat -->
                                </div>
                                <!-- /stat-holder -->

                                <div class="stat-holder">
                                    <div class="stat">
                                        <span>${requestScope.AppRecordGET.getData(App.get("AppEntranceID")).getNewCount()}</span>
                                        今日新增
                                    </div>
                                    <!-- /stat -->
                                </div>
                                <!-- /stat-holder -->

                                <div class="stat-holder">
                                    <div class="stat">
                                        <span>${requestScope.AppRecordGET.getData(App.get("AppEntranceID")).getCancelCount()}</span>
                                        今日取消
                                    </div>
                                    <!-- /stat -->
                                </div>
                                <!-- /stat-holder -->

                                <div class="stat-holder">
                                    <div class="stat">
                                        <span>${requestScope.AppRecordGET.getData(App.get("AppEntranceID")).getActivityCount()}</span>
                                        活跃度
                                    </div>
                                    <!-- /stat -->
                                </div>
                                <!-- /stat-holder -->

                            </div>
                            <!-- /stat-container -->

                        </div>
                        <!-- /widget-content -->

                    </div>
                    <!-- /widget -->
                </c:forEach> --%>
		<c:if test="${sessionScope.UserType==4 }">
			<div class="widget">
	
	                    <div class="widget-header">
	                        <h3><span class="glyphicon glyphicon-stats"></span>短信验证码关注统计总数量：${requestScope.PhoneWatchTotal}</h3>
	                    </div>
	                    <!-- /widget-header -->
	
	                    <div class="widget-content">
	                        不同时间段关注总数量，请选择计算周期：
	                        <select name="periodOfTime" id="periodOfTime" style="width:70px; margin-bottom:3px;" onchange="periodOfTime(this.options[this.selectedIndex].value)">
				      <option selected="selected" value="day">今日</option>
				      <option value="week">本周</option>
				      <option value="month">本月</option>
				      <option value="season">本季</option>
				</select>
				<span id="TodayPhoneWatch" class="badge badge-warning" style="margin-left:20px;">${requestScope.TodayPhoneWatch}</span>
	                        <hr/>
	                        <div id="highcharts" style="min-width:300px;height:400px;margin:0 auto;"></div>
	                    </div>
	                    
	                </div>
	                <!-- /widget -->
		</c:if>

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

<script src="./js/excanvas.min.js"></script>
<script src="./js/jquery.flot.js"></script>
<script src="./js/jquery.flot.pie.js"></script>
<script src="./js/jquery.flot.orderBars.js"></script>
<!-- <script src="./js/jquery.flot.resize.js"></script>干扰highcharts图表自动显示-->
<script src="http://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>

<script src="./js/bootstrap.js"></script> 
<script src="./js/highcharts/highcharts.js"></script>
<script src="./js/highcharts/exporting.js"></script>

<script type="text/javascript">
	
	function getRouterDetail(businessShopID){
		$.ajax({
			type:"POST",
			url:"/Dashboard.htm",
			dataType:"html",
			data:{
				type:"getRouterDetail",
				businessShop:businessShopID
			},
			success:function(data){
				$("#RouterDetail").modal("show");
				$("#Detail").html(data);
			}
		});
	}
	function periodOfTime(Smsselectval){
		$.ajax({
			type:"POST",
			url:"/Dashboard.htm",
			dataType:"json",
			data:{
				type:"SMSstatistics",
				timeperiods:Smsselectval
			},
			success:function(data){
				$("#TodayPhoneWatch").html(data.TodayPhoneWatch);
			}
		});
	}
	function overrallperiodOfTime(selectval,time){
		var tempTime,tempselectval;
		if(selectval== ' '){
			tempselectval = $("#WifiRouterID").val();
			alert(tempselectval);
		}else if(time== ' '){
			tempTime = $("#WifiRouterTime").val();
		}
		$.ajax({
			type:"POST",
			url:"/Dashboard.htm",
			dataType:"json",
			data:{
				type:"overrallStatistics",
				timeperiods:tempTime,
				WifiRouterChoose:tempselectval
			},
			success:function(data){
				var RouterAuthRecordList = data.RouterAuthRecordList;
				console.log(RouterAuthRecordList);
				var RouterAuthRecordListJson = JSON.parse(RouterAuthRecordList);
				
				routerListJson(RouterAuthRecordListJson);
				containerResult();
			}
		});
	}
</script>

</body>
</html>
