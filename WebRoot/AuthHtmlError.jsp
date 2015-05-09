<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <title>认证错误界面</title>
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
  </head>
  <body>
  	<div class="container">
  		<div class="row">
  			<div class="panel panel-default">
			   <div class="panel-heading">
			     	<h3 class="panel-title">错误原因</h3>
			   </div>
			   <div class="panel-body">			   		
			   </div>
			   <button class="btn" onclick="history.back();" value="">返回</button>
			</div>
  		</div>
  	</div>
<!-- Le javascript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->    
<script src="./js/jquery-1.7.2.min.js"></script>
<script src="./js/bootstrap.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			 if (${requestScope.ShowBug=="true"}) {
				$(".panel-title").text("错误代码：" + "${requestScope.Operat}");
               	$(".panel-body").text("内容：" + "${requestScope.ErrorMessage}");
	         } else if(${requestScope.Operat>1}){
	        	 $(".panel-body").text("${requestScope.ErrorMessage}");
	         }else {
	        	$(".panel-body").text("${requestScope.SysErrorPointOut}");
	         }
		});
	</script>
	
  </body>
</html>
