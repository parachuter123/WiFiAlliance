<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <link href="./css/bootstrap.min.css" rel="stylesheet"/>
    <link href="./css/bootstrap-responsive.min.css" rel="stylesheet"/>
    <link href="./css/font-awesome.css" rel="stylesheet"/>
    <link href="./css/adminia.css" rel="stylesheet"/>
    <link href="./css/adminia-responsive.css" rel="stylesheet"/>
    <title>路由器在线设备列表</title>
	<Style>
		.widget-table .table {
			margin-bottom: 0;
			width: 722px;
			table-layout: fixed;
			border: none;
		}
	</Style>
</head>
<body>
<div id="content">

    <div class="container">

        <div class="widget widget-table">
            <div class="widget-header">
                <i class="icon-th-list"></i>
                &nbsp&nbsp共计 ${requestScope.onlineTerminalslist.size()} 个设备接入&nbsp&nbsp&nbsp&nbsp
                                          当前路由器设备名称为：${requestScope.DevName}
            </div>
            <!-- /widget-header -->

            <div class="widget-content">


                <table class="table table-striped table-bordered" style="white-space: nowrap;">
                    <thead>
                    <tr>
                        <th>设备Mac</th>
                        <th>IP地址</th>
                        <th>接入时间</th>
                        <th>接入类型</th>
                    </tr>
                    </thead>

                    <tbody>

                    <c:forEach items="${requestScope.onlineTerminalslist}" var="an" varStatus="sta">
                        <tr>
                            <td>${an.get('TerminalMac')}</td>
                            <td>${an.get('TerminalIp')}</td>
                            <td><fmt:formatDate  value="${an.get('AccessTime')}" type="both" pattern="yyyy.MM.dd HH:mm:ss" /></td>
                            <td>${an.get('VerificationResult')}</td>
                            
                        </tr>
                    </c:forEach>

                    </tbody>
                </table>


            </div>
            <!-- /widget-content -->

            <ul class="pagination" id="Pages">
                <li><a href="#">&laquo;</a></li>
                <li><a href="#">1</a></li>
                <li><a href="#">2</a></li>
                <li class="active"><a href="#">3</a></li>
                <li><a href="#">4</a></li>
                <li><a href="#">5</a></li>
                <li><a href="#">&raquo;</a></li>
            </ul>

        </div>
        <!-- /widget -->
    </div>
</div>
</body>
</html>