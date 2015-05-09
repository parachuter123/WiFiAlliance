<%--
  User: Ronan
  Date: 14-12-08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>广告设置</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <link rel="shortcut icon" href="./img/headshot.png"/>
    
    <link href="./css/bootstrap.min.css" rel="stylesheet"/>
    <link href="./css/bootstrap-responsive.min.css" rel="stylesheet"/>
    
    <link href="./css/font-awesome.css" rel="stylesheet"/>

    <link href="./css/adminia.css" rel="stylesheet"/>
    <link href="./css/adminia-responsive.css" rel="stylesheet"/>

    <link href="./css/jquery.visualize.css" rel="stylesheet"/>
    <link rel="stylesheet" type="text/css" href="./css/jquery.bigcolorpicker.css" />
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
                        getMenu(${sessionScope.UserType}, "Ad");
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
                    <i class="icon-shopping-cart"></i>
                    广告设置
                </h1>


                <div class="widget widget-table">

                    <div class="widget-header">
                        <i class="icon-shopping-cart"></i>

                        <h3>广告列表</h3>

                        <div id="adduser">
                            <div class="btn-group">
                                <button type="button" class="btn btn-default" onclick="OpenAddAd()">新增</button>
                                <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                    	操作<span class="caret"></span>
                                </button>
                                <ul class="dropdown-menu">
                                    <li><a href="#" data-toggle="modal" data-target="#myModal">删除</a></li>
                                    <li><a href="#" data-toggle="modal" data-target="#ChangeModal">修改</a></li>
                                </ul>

                            </div>
                        </div>


                    </div>
                    <!-- /widget-header -->

                    <div class="widget-content">


                        <table class="table table-striped table-bordered" style="white-space: nowrap;">
                            <thead>
                            <tr>
                                <th>类别</th>
                                <th>页面名称</th>
                                <th>备注</th>
                                <th>操作</th>
                            </tr>
                            </thead>

                            <tbody>

                            <%--<tr>--%>
                            <%--<td>1</td>--%>
                            <%--<td>促销广告</td>--%>
                            <%--<td>2013-2-15</td>--%>
                            <%--<td  class="action-td" >--%>
                            <%--<a href="javascript:;" class="btn btn-small" data-toggle="modal" data-target="#ChangeModal" >--%>
                            <%--<i class="icon-cog" title="修改"></i>--%>
                            <%--</a>--%>
                            <%--<a href="javascript:;" class="btn btn-small  btn-danger">--%>
                            <%--<i class="icon-remove-sign" title="删除"></i>--%>
                            <%--</a>--%>
                            <%--</td>--%>
                            <%--</tr>--%>
                            <c:forEach items="${requestScope.list}" var="an" varStatus="sta">
                                <tr>
                                    <td>${an.get('Type')}</td>
                                    <td>${an.get('Name')}</td>
                                    <td>${an.get('Remark')}</td>
                                    <td class="action-td">
                                        <a href="javascript:;" class="btn btn-small"
                                           onclick="OpenAdContent('${an.get('Key')}','${an.get('Model')}','${an.get('TemplateID')}','${an.get('Name')}')">
                                            <i class="icon-cog" title="修改"></i>
                                        </a>
                                        <a href="javascript:;" class="btn btn-small  btn-danger"
                                           onclick="DelAd('${an.get('Key')}')">
                                            <i class="icon-remove-sign" title="删除"></i>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>

                    </div>
                    <div>注（类别）：
						1.浏览器引导&nbsp;&nbsp;
                        2.关注&nbsp;&nbsp;
                       	3.过期&nbsp;&nbsp;
                       	4.广告通知&nbsp;&nbsp;
                       	5.分享任务&nbsp;&nbsp;
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

<!-- 提示信息Start -->
<div class="modal fade" id="myModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">信息提示</h4>
            </div>
            <div class="modal-body">
                <span id="InfoSpan"></span>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="history.go(0)" data-dismiss="modal">确认</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<!-- 提示信息End -->

<!-- 修改广告Start -->
<div class="modal fade" id="ChangeModal" style="width: 900px;margin: -250px 0 0 -450px; ">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="ChangeModalLabel">修改广告内容</h4>
            </div>
            <div class="modal-body">
                <%--<textarea rows="8" style="width: 98%" id="PageCode"></textarea>--%>
                <%--<hr/>--%>
                <%--<button type="button" class="btn btn-info" onclick="ShowPreview()">↓↓↓↓↓↓↓↓↓预览↓↓↓↓↓↓↓↓↓</button>--%>
                <%--<hr/>--%>
                <%--<div class="widget">--%>

                <%--<div class="widget-content" id="PreviewPage">--%>

                <%--</div>--%>

                <%--</div>--%>
                <%--<!-- /widget -->--%>
				
                <script id="editor" name="editor" type="text/plain">
					
                </script>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="SaveContent()" data-dismiss="modal">确认</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<!-- 修改广告End -->

<!-- 新建广告Start -->
<div class="modal fade" id="CreateAdModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="CreateAdT">新建广告</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label for="Name">页面名称<span style="color:#E53333;">*</span></label>
                    <input type="email" class="form-control" id="Name" placeholder="请输入广告页面名称">
                </div>
                <div class="form-group">
                    <label for="Type">广告类型</label>
                    <select id="Type" class="form-control" onchange="chooseType()">
                        <option value="1">浏览器引导</option>
                        <option value="2">关注</option>
                        <option value="3">过期</option>
                        <option value="4">广告通知</option>
                        <option value="5">分享任务</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="Model">模板</label>
                    <select id="Model" class="form-control" onchange="chooseModel(this.options[this.options.selectedIndex].value)">
                        <option value="1">直接内容</option>
                        <option value="2">内部模板</option>
                        <option value="3">外部链接</option>
                    </select>
                </div>
                <div class="form-group" id="UrlPrev">
                    <label for="Url">请输入链接，以http://开头</label>
                    <input type="text" id="Url" placeholder="请输入URL" >
                </div>
                <div class="form-group" id="TemplatePrev">
                	<label for="innerTemplate">请选择模板<span style="color:#E53333">*</span></label>
                	<select id="innerTemplate" class="form-control">
	                          
                    	</select>
                </div>
		<div class="form-group">
                    <label for="Remark">备注</label>
                    <input class="form-control" id="Remark" placeholder="请输入备注">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="addad()" data-dismiss="modal">确认</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<!-- 新建广告End -->



<!-- 修改模板Start -->
<div class="modal fade" id="ChangeTemplate" style="width: 900px;margin: -250px 0 0 -450px; ">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="ChangeModalLabel">修改模板</h4>
            </div>
            <div class="modal-body">
            	<div class="form-group" id="modifyTemplatePrev">
                	
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="SaveModel()" data-dismiss="modal">确认</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- 修改模板End -->

<!-- 修改链接Start -->
<div class="modal fade" id="ChangeUrl" style="width: 900px;margin: -250px 0 0 -450px; ">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="ChangeModalLabel">修改链接</h4>
            </div>
            <div class="modal-body">
            	<div class="form-group" id="UrlPrev">
                    <label for="updateUrl">请输入链接，以http://开头</label>
                    <input type="text" id="updateUrl" placeholder="请输入广告页面内容" >
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="SaveUrl()" data-dismiss="modal">确认</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- 修改链接End -->




<!-- Le javascript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="./js/jquery-1.8.3.js"></script>
<script src="./js/excanvas.min.js"></script>
<script src="./js/Verification.js"></script>
<script src="./js/bootstrap.js"></script>
<script src="./js/json2.js"></script>
<script src="./js/ajaxfileupload.js"></script>
<script type="text/javascript" src="./js/jquery.bigcolorpicker.min.js"></script>

<!-- 配置文件 -->
<script type="text/javascript" src="js/ueditor/ueditor.config.js"></script>
<!-- 编辑器源码文件 -->
<script type="text/javascript" src="js/ueditor/ueditor.all.js"></script>

<script>
    var ADKEY = "";
    var ue = UE.getEditor('editor');
    
    
    function OpenAdContent(id,modelid,templateID,name) {
    	if(modelid==1){
    		$.ajax({
                type: "GET",
                url: "/Adinfo",
                dataType: "json",
                data: {
                    adkey: encodeURI(id)
                },
                success: function (data) {
                	if(data.State == 1){
                		ue.setContent(data.Content);
                	}else if(data.State>1){
                		alert(data.Message);
                	}else{
                       	 if (data.ShowBug=="true") {
       		                   	alert(data.Message);
       		             } else {
       		                   	alert(data.SysErrorPointOut);
       		             }
                	}
                    ADKEY = data.adkey;
                }
            });
    		$("#ChangeModal").modal("show");
    	}else if(modelid==2){/*************************************************修改模板展示*****************************************************/
    		$.ajax({
                type: "POST",
                url:"/InfoTemplate.htm",
                dataType: "html",
                data: {
                    Choose: "TemplateAdvancedSet",
                    TemplateID: templateID,
                    Name:name
                },
                success: function (data) {
                	$("#modifyTemplatePrev").html(data);
                	$(".color").each(function(){
                		$(".color").val("#FFFFFF");
                		$(this).bigColorpicker(function(el,color){
                			$(el).val(color);
                		});
                	});
                	
                	$(".mineimage").bind('click',function(){
                		$(this).next().click();
                		$(this).next().on('change',function(){
                			$.ajaxFileUpload({  
	                	            url:"/UploadFile.htm", 
	                	            type: "POST",
	                	            secureuri:false,  
	                	            fileElementId:$(this).attr("id"),		//file标签的id  
	                	            dataType: "json",				//返回数据的类型  
	                	            data:{
						fileId:$(this).attr("id")
	                	            },							//一同上传的数据  
	                	            success: function (data, status) {
	                	            	var obj = jQuery.parseJSON(data);  
	                	            	if(obj.State == 1){
		                	                $("#" + obj.fileId).prev().attr("src", "./upload/" + obj.Operator + "/"+(encodeURI(obj.newfilename)));  
	                                	}else if(obj.State>1){
	                                		alert(obj.Message);
	                                	}else{
	                                       	 if (obj.ShowBug=="true") {
	                       		                   	alert(obj.Message);
	                       		             } else {
	                       		                   	alert(obj.SysErrorPointOut);
	                       		             }
	                                	} 
	                	               
	                	            },  
	                	            error: function (data, status, e) {  
	                	                alert(e);  
	                	            }  
	                	        });  
                		});
                	});
                	
	               ADKEY = id;
                }
            });
    		$("#ChangeTemplate").modal("show");
    	}else if(modelid==3){
    		$.ajax({
                type: "GET",
                url: "/Adinfo",
                dataType: "json",
                data: {
                    adkey: id
                },
                success: function (data) {
                	if(data.State == 1){
                		$("#updateUrl").val(data.Content);
                	}else if(data.State>1){
                		alert(data.Message);
                	}else{
                       	 if (data.ShowBug=="true") {
       		                   	alert(data.Message);
       		             } else {
       		                   	alert(data.SysErrorPointOut);
       		             }
                	}
                    ADKEY = id;
                }
            });
    		$("#ChangeUrl").modal("show");
    	}
    }

    function DelAd(adkey) {
    	if(!window.confirm("确定要删除吗？")){
		return;
	}
        $.ajax({
            type: "POST",
            url: "/InfoTemplate.htm",
            dataType: "json",
            data:{
            	Choose: "Del",
            	id: adkey
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
    function SaveContent() {
        var con = ue.getContent();
        if (con == "") {
            alert("修改无效！");
            return;
        }
        $.ajax({
            type: "POST",
            url: "/InfoTemplate.htm",
            dataType: "json",
            data:{
            	Choose: "updateContent",
            	adkey: encodeURI(ADKEY),
            	Content: con
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
    /*************************************************保存修改后的模板*****************************************************/
    function SaveModel(){
    	var param = $("#modifyTemplatePrev :text");
    	var obj = new Object();
    	for(var i=0;i<param.length;i++){
    		obj[param[i].id] =param[i].value;
    	}
    	var json_param = JSON.stringify(obj);
    	
    	var imgs = $("#modifyTemplatePrev :file").prev();
    	var imgsobj = new Object();
    	for(var j=0;j<imgs.length;j++){
    		imgsobj[imgs[j].id] = imgs[j].src;
    	}
    	var json_imgs = JSON.stringify(imgsobj);
    	
    	$(".mineimage").bind('change',function(){
    		$(this).next().click();
    	});
    	
    	$.ajax({
            type: "POST",
            url: "/InfoTemplate.htm",
            dataType: "json",
            data:{
        	Choose: "updateModel",
        	adkey: ADKEY,
        	Content: json_param,
        	imgsjson:json_imgs
            } , 
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
	function SaveUrl(){
		var reg =new RegExp("^http:\/\/[\.a-zA-Z0-9\/?<=&]*");
		if(!reg.test($("#updateUrl").val())){
			alert("URL必须以http://格式开头!");
           		 return;
		}
		if (isNull($("#updateUrl").val())) {
	            alert("URL不能为空或者格式不对!");
	            return;
	        }
		$.ajax({
	            type: "POST",
	            url: "/InfoTemplate.htm",
	            dataType: "json",
	            data:{
	            	Choose: "updateUrl",
	            	adkey: ADKEY,
	            	Content: $("#updateUrl").val()
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

    function OpenAddAd() {
        $("#CreateAdModal").modal("show");
    }

    function addad() {
        if (isNull($("#Name").val())) {
            alert("广告名称不能为空!");
            return;
        }
        var i = $("#Model").val();
        if(i==1){
        	$.ajax({
                type: "POST",
                url: "/InfoTemplate.htm",
                dataType: "json",
                data: {
                	Choose:"Content",
                	Name: $("#Name").val(),
                    Type: $("#Type").val(),
                    Model: $("#Model").val(),
                    Remark: $("#Remark").val(),
                    Content: ""
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
        }else if(i==2){
        	$.ajax({
                type: "POST",
                url: "/InfoTemplate.htm",
                dataType: "json",
                data: {
                	Choose:"Model",
                	Name: $("#Name").val(),
                    	Type: $("#Type").val(),
                    	Model: $("#Model").val(),
                    	Content:$("#innerTemplate").val(),
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
        }else if(i==3){
        	var reg =new RegExp("^http:\/\/[\.a-zA-Z0-9\/?<=&]*");
    		if(!reg.test($("#Url").val())){
    			alert("URL必须以http://格式开头!");
                return;
    		}
        	if(isNull($("#Url").val)){
        		alert("外部链接不能为空");
        		return;
        	}
        	$.ajax({
                type: "POST",
                url: "/InfoTemplate.htm",
                dataType: "json",
                data: {
                	Choose:"Url",
                	Name: $("#Name").val(),
                	Content: $("#Url").val(),
                    Type: $("#Type").val(),
                    Model: $("#Model").val(),
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
    }
    //选择对应的广告内容
    function chooseModel(index){
    	$("#TemplatePrev").attr("style","display:none;");
    	$("#UrlPrev").attr("style","display:none;");
    	if(index==1){
    		$("#TemplatePrev").attr("style","display:none;");
    		$("#UrlPrev").attr("style","display:none;");
    		return 1;
    	}else if(index==2){
    		$("#TemplatePrev").attr("style","display:block;");
    		$("#UrlPrev").attr("style","display:none;");
    		$.ajax({
    			 type: "POST",
	                 url: "/InfoTemplate.htm",
	                 dataType: "html",
	                 data: {
	                 	Choose:"getTemplateList",
	                 	Type:$("#Type").val()
	                 },
	                 success:function(data){
	                 	$("#innerTemplate").html(data);
	                 }
    		});
    		return 2;
    	}else if(index==3){
    		$("#UrlPrev").attr("style","display:block;");
    		$("#TemplatePrev").attr("style","display:none;");
    		return 3;
    	}
    }
    function chooseType(){
    	$("#TemplatePrev").attr("style","display:none;");
    	$("#UrlPrev").attr("style","display:none;");
    	$("#Model").val(1);
    }
    $(function(){
    	chooseModel($("#Model").val());
    });	
</script>
</body>
</html>
