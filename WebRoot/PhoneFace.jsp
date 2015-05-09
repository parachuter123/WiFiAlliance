<%--
  User: parachuter
  Date: 15-01-05
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>分享任务</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <link rel="shortcut icon" href="./img/headshot.png"/>

    <link href="./css/bootstrap.min.css" rel="stylesheet"/>
    <link href="./css/bootstrap-responsive.min.css" rel="stylesheet"/>

    <link href="./css/font-awesome.css" rel="stylesheet"/>

    <link href="./css/adminia.css" rel="stylesheet"/>
    <link href="./css/adminia-responsive.css" rel="stylesheet"/>

    <link href="./css/jquery.visualize.css" rel="stylesheet"/>
    <script src="./js/menu.js"></script>
    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
</head>

<style type="text/css">
    #adduser {
        float: right;
        margin-top: 6px;
        margin-right: 4px;
    }

    #Pages {
        float: right;
    }


</style>

<script>
    function ShowPreview() {
        document.getElementById("PreviewPage").innerHTML = document.getElementById('PageCode').value;
    }

</script>

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
                        getMenu(${sessionScope.UserType}, "PhoneFace");
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
                    <i class="icon-picture"></i>
                                                     分享任务
                </h1>
                <div class="widget widget-table">
                    <div class="widget-header">
                        <i class="icon-picture"></i>
                        <h3>分享任务</h3>
                        <div id="adduser">
                            <div class="btn-group">
                                <button type="button" class="btn btn-default" onclick="OpenPhoneFace('')">新增</button>
                                <!-- 
                                <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                    	操作<span class="caret"></span>
                                </button>
                                <ul class="dropdown-menu">
                                    <li><a href="#" data-toggle="modal" data-target="#myModal">删除</a></li>
                                    <li><a href="#" data-toggle="modal" data-target="#ChangeModal">修改</a></li>
                                </ul> -->
                            </div>
                        </div>
                    </div>
                    <!-- /widget-header -->
                    <div class="widget-content">
                        <table class="table table-striped table-bordered" style="white-space: nowrap;">
                            <thead>
                            <tr>
                                <th>分享任务名称</th>
                                <th>分享标题</th>
                                <th>分享模板</th>
                                <th>地址戳号</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${requestScope.ShareTaskList}" var="an" varStatus="sta">
                                <tr>
                                    <td>${an.get("Name")}</td>
                                    <td>${an.get("Title")}</td>
                                    <td>${an.get("ShareTemplateKey")}</td>
                                    <td>${an.get("poiId")}</td>
                                    <td class="action-td">
                                        <a href="javascript:;" class="btn btn-small"
                                           onclick="OpenPhoneFace('${an.get('ShareID')}')">
                                            <i class="icon-cog" title="修改"></i>
                                        </a>
                                        <a href="javascript:;" class="btn btn-small  btn-danger"
                                           onclick="DelPhoneFace('${an.get('ShareID')}')">
                                            <i class="icon-remove-sign" title="删除"></i>
                                        </a>
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
            <!-- /span9 -->
        </div>
        <!-- /row -->
    </div>
    <!-- /container -->
</div>
<!-- /content -->
<jsp:include page="footer.jsp"></jsp:include>
<!-- /footer -->

<!-- 新建分享任务信息Start -->
<div class="modal fade" id="CreatePhoneFace">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="CreatePF">新建分享</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label for="Name">分享任务名称<span style="color:#E53333;">*</span></label>
                    <input type="text" class="form-control" id="Name" placeholder="请输入分享任务名称">
                </div>
                <div class="form-group">
                    <label for="Title">分享标题<span style="color:#E53333;">*</span></label>
                    <input type="text" class="form-control" id="Title" placeholder="请输入分享标题">
                </div>
                <div class="form-group">
                    <label for="ImgUrl">分享图片链接<span style="color:#E53333;">*</span></label>
                    <input type="text" class="form-control" id="ImgUrl" placeholder="请输入分享图片链接">
                </div>
                <div class="form-group">
                    <label for="LineLink">分享跳转链接<span style="color:#E53333;">*</span></label>
                    <input type="text" class="form-control" id="LineLink" placeholder="请输入分享跳转链接">
                </div>
                <div class="form-group">
                    <label for="Desc">分享描述<span style="color:#E53333;">*</span></label>
                    <textarea class="form-control" id="Desc" placeholder="请输入分享描述"></textarea>
                </div>
                <div class="form-group">
                    <label for="ShareTemplateKey">分享模板<span style="color:#E53333;">*</span></label>
                    <select id="ShareTemplateKey" class="GroupSelect" title="分享模板"
                          onchange="this.options[this.selectedIndex].value" name="ShareTemplateKey">
                          <option value="">无</option>
                      <c:forEach items="${requestScope.PhoneFaceShareTaskList}" var="st" varStatus="sta">
                          <option value="${st.get('Key')}">${st.get('Name')}</option>
                      </c:forEach>
                  	</select>
                </div>
                <div class="form-group">
                    <label for="poiId">地址戳号<span style="color:#E53333;">*</span></label>
                    <input type="text" class="form-control" id="poiId" placeholder="请输入地址戳号">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="PhoneCommit" onclick="" data-dismiss="modal">确认</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<!-- 新建分享任务信息End -->

<!-- Le javascript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="./js/jquery-1.7.2.min.js"></script>
<script src="./js/excanvas.min.js"></script>
<script src="./js/Verification.js"></script>
<script src="./js/bootstrap.js"></script>
<script>	
	function OpenPhoneFace(ShareID){
		if (ShareID == "") {
            CleanIpunt();
            $("#CreatePhoneFace").modal("show");
            document.getElementById("PhoneCommit").onclick = addPhoneFace;
            return;
        } else {
        	getPhoneFaceInfo(ShareID);
            $("#CreatePhoneFace").modal("show");
            document.getElementById("PhoneCommit").onclick = function () {
            	updatePhoneFace(ShareID);
            };
        }
	}
	function getPhoneFaceInfo(ShareID){
		$.ajax({
			type: "POST",
            url: "/PhoneFace.htm",
            dataType: "json",
            data: {
                Type: "getPhoneFaceInfo",
                ShareID: ShareID
            },
            success: function(data){
            	if (decodeURI(data.State)==1) {
            		$("#Name").val(data.Name);
                    $("#ImgUrl").val(data.ImgUrl);
                    $("#Title").val(data.Title);
                    $("#LineLink").val(data.LineLink);
                    $("#Desc").val(data.Desc);
                    $("#ShareTemplateKey").val(data.ShareTemplateKey);
                    $("#poiId").val(data.poiId);
                    $("#ChangePhoneFace").modal("show");
                }else {
                	alert("错误状态码：" +decodeURI(data.State) + "," + "错误信息：" + decodeURI(data.Message));
                }
            }
		});
	}
	function updatePhoneFace(Shareid){
		$.ajax({
			type: "POST",
            url: "/PhoneFace.htm",
            dataType: "json",
            data: {
                Type: "updatePhoneFace",
                ShareID: Shareid,
                Name: $("#Name").val(),
                ImgUrl: $("#ImgUrl").val(),
                Title: $("#Title").val(),
                LineLink: $("#LineLink").val(),
                Desc: $("#Desc").val(),
                ShareTemplateKey: $("#ShareTemplateKey").val(),
                poiId: $("#poiId").val()
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
	function addPhoneFace(){
		if (isNull($("#Name").val())) {
            alert("分享任务名称不允许为空！");
            return;
        }
		if (isNull($("#ImgUrl").val())) {
            alert("图片地址不允许为空！");
            return;
        }
		if (isNull($("#Title").val())) {
            alert("标题不允许为空！");
            return;
        } 
		if (isNull($("#LineLink").val())) {
            alert("跳转地址不允许为空！");
            return;
        }
		if (isNull($("#Desc").val())) {
            alert("分享描述不允许为空！");
            return;
        }
		if (isNull($("#poiId").val())) {
            alert("地址戳号不允许为空！");
            return;
        }
		$.ajax({
			type: "POST",
            url: "/PhoneFace.htm",
            dataType: "json",
            data: {
                Type: "createPhoneFace",
                Name: $("#Name").val(),
                ImgUrl: $("#ImgUrl").val(),
                Title: $("#Title").val(),
                LineLink: $("#LineLink").val(),
                Desc: $("#Desc").val(),
                ShareTemplateKey: $("#ShareTemplateKey").val(),
                poiId: $("#poiId").val()
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
	function DelPhoneFace(shareID){
		if(!window.confirm("确定要删除吗？")){
			return;
		}
		$.ajax({
			type:"POST",
			url:"/PhoneFace.htm",
			dataType:"json",
			data:{
				Type:"DelPhoneFace",
				ShareID:shareID
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
	function CleanIpunt() {
        $("#Name").val("");
        $("#ImgUrl").val("");
        $("#Title").val("");
        $("#LineLink").val("");
        $("#Desc").val("");
        $("#ShareTemplateKey").val("1");
        $("#poiId").val("");
    }
</script>
</body>
</html>
