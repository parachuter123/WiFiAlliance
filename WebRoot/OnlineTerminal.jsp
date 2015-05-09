<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                        <th>所属商家</th>
                    </tr>
                    </thead>

                    <tbody>

                    <c:forEach items="${requestScope.onlineTerminalslist}" var="an" varStatus="sta">
                        <tr>
                            <td>${an.get('TerminalMac')}</td>
                            <td>${an.get('TerminalIp')}</td>
                            <td>${an.get('AccessTimeStr')}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${an.get('VerificationResult')==1}">
                                        <span class="label label-default" title="">
                                            	白名单
                                        </span>
                                        &nbsp;&nbsp;
                                        <span class="label label-info">
                                                ${requestScope.BlackWhiteListGET.getData(an.get('TerminalMac').concat(':').concat(an.get('WifiRouterID').split(":")[0])).get("Remark")}
                                        </span>

                                    </c:when>
                                    <c:when test="${an.get('VerificationResult')==2}">
                                        <span class="label label-danger">黑名单</span>
                                        &nbsp;&nbsp;
                                        <span class="label label-info">
                                                ${requestScope.BlackWhiteListGET.getData(an.get("TerminalMac").concat(':').concat(an.get("WifiRouterID").split(":")[0])).get("Remark")}
                                        </span>
                                    </c:when>
                                    <c:when test="${an.get('VerificationResult')==3}">	
                                        <span class="label label-success">本商家</span>
                                        &nbsp;&nbsp;
                                        <span class="label label-info">
                                                ${requestScope.FollowersGET.getData(an.get("TerminalID").split(">")[1]).get("Nickname")}
                                        </span>
                                    </c:when>
                                    <c:when test="${an.get('VerificationResult')==4}">
                                        <span class="label label-warning">本商家未验证</span>
                                        &nbsp;&nbsp;
                                        <span class="label label-info">
                                                ${requestScope.FollowersGET.getData(an.get("TerminalID").split(">")[1]).get("Nickname")}
                                        </span>

                                    </c:when>
                                    <c:when test="${an.get('VerificationResult')==5}">
                                        <span class="label label-success">本商家已验证</span>
                                        &nbsp;&nbsp;
                                        <span class="label label-info">
                                                ${requestScope.FollowersGET.getData(an.get("TerminalID").split(">")[1]).get("Nickname")}
                                        </span>
                                    </c:when>
                                    <c:when test="${an.get('VerificationResult')==6}">
                                        <span class="label label-info">同代理</span>
                                        &nbsp;&nbsp;
                                        <span class="label label-info">
                                                ${requestScope.FollowersGET.getData(an.get("TerminalID").split(">")[1]).get("Nickname")}
                                        </span>
                                    </c:when>
                                    <c:when test="${an.get('VerificationResult')==7}">
                                        <span class="label label-warning">同代理未验证</span>
                                        &nbsp;&nbsp;
                                        <span class="label label-info">
                                                ${requestScope.FollowersGET.getData(an.get("TerminalID").split(">")[1]).get("Nickname")}
                                        </span>
                                    </c:when>
                                    <c:when test="${an.get('VerificationResult')==8}">
                                        <span class="label label-success">同代理已验证</span>
                                        &nbsp;&nbsp;
                                        <span class="label label-info">
                                                ${requestScope.FollowersGET.getData(an.get("TerminalID").split(">")[1]).get("Nickname")}
                                        </span>
                                    </c:when>
                                    <c:when test="${an.get('VerificationResult')==9}">
                                        <span class="label label-info">代理共享</span>
                                        &nbsp;&nbsp;
                                        <span class="label label-info">
                                                ${requestScope.FollowersGET.getData(an.get("TerminalID").split(">")[1]).get("Nickname")}
                                        </span>
                                    </c:when>
                                    <c:when test="${an.get('VerificationResult')==10}">
                                        <span class="label label-warning">代理共享未验证</span>
                                        &nbsp;&nbsp;
                                        <span class="label label-info">
                                                ${requestScope.FollowersGET.getData(an.get("TerminalID").split(">")[1]).get("Nickname")}
                                        </span>
                                    </c:when>
                                    <c:when test="${an.get('VerificationResult')==11}">
                                        <span class="label label-success">代理共享已验证</span>
                                        &nbsp;&nbsp;
                                        <span class="label label-info">
                                                ${requestScope.FollowersGET.getData(an.get("TerminalID").split(">")[1]).get("Nickname")}
                                        </span>
                                    </c:when>
                                    <c:when test="${an.get('VerificationResult')==12}">
                                        <span class="label label-warning">未知用户</span>
                                    </c:when>
                                    <c:when test="${an.get('VerificationResult')==13}">
                                        <span class="label label-default">终端初次绑定成功</span>
                                        &nbsp;&nbsp;
                                        <span class="label label-info">
                                                ${requestScope.FollowersGET.getData(an.get("TerminalID").split(">")[1]).get("Nickname")}
                                        </span>
                                    </c:when>
                                    <c:when test="${an.get('VerificationResult')==14}">
                                        <span class="label label-danger">踢出</span>
                                        &nbsp;&nbsp;
                                        <span class="label label-info">
                                                ${requestScope.FollowersGET.getData(an.get("TerminalID").split(">")[1]).get("Nickname")}
                                        </span>
                                    </c:when>

                                </c:choose>

                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${an.get('VerificationResult')==1}">
                                        ${requestScope.BusinessShopGET.getData(BlackWhiteListGET.getData(an.get('TerminalMac').concat(':').concat(an.get('WifiRouterID').split(":")[0])).get("BusinessShopID")).get("BusinessName")}
                                    </c:when>
                                    <c:otherwise>${requestScope.BusinessShopGET.getData(an.get("TerminalID").split(":")[0]).get("BusinessName")}</c:otherwise>
                                </c:choose>
                            </td>
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