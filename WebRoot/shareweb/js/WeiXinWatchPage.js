$(function(){
		$('<style>@charset "utf-8";body,html{height:100%}'+
	'a{text-decoration:none}img{border:0;vertical-align:middle}'+
	'p{color:#666;font-size:2em}small{font-size:80%;color:#999}'+
	'.am-btn{display:inline-block;margin-bottom:0;padding:.625em 1em;vertical-align:middle;font-size:1.6rem;font-weight:400;line-height:1.2;text-align:center;white-space:nowrap;background-image:none;border:1px solid transparent;border-radius:0;cursor:pointer;outline:0;-webkit-appearance:none;-webkit-user-select:none;-moz-user-select:none;-ms-user-select:none;user-select:none;-webkit-transition:background-color 300ms ease-out,border-color 300ms ease-out;transition:background-color 300ms ease-out,border-color 300ms ease-out;color:#fff;background-color:#5eb95e;border-color:#5eb95e;margin-top:1%;border-radius:8px;width:150px;height:15px;line-height:15px}'+
	'.wrapper{text-align:center;width:100%;height:100%;position:relative}'+
	'.logo{position:fixed;top:10%;width:100%;height:28%;margin:0 auto;text-align:center;font-weight:normal}'+
	'.imglogo{height:100%;margin:0 auto;border:1px #000 solid;border-radius:100%;box-shadow:2px 2px 5px #ccc;-moz-box-shadow:2px 2px 5px #ccc;-webkit-box-shadow:2px 2px 5px #ccc;overflow:hidden}'+
	'.notecont{width:100%;height:40%;top:50%;position:fixed}'+
	'.notecont p{margin-top:-1.5em;margin-bottom:2.5em;padding:0;color:#666;font-size:1.4em}'+
	'.notecont .nextline{margin:20px 0 30px 0;color:#333;font-size:1em;text-align:center}'+
	'#footer{text-align:center;left:0;height:10%;width:100%;bottom:1%;position:fixed}'+
'<\/style>').appendTo(document.head);
$("body").html('<div id="div1" class="wrapper test transition">'+
	'<h1 class="logo">'+
		'<div class="imglogo" id="imglogo">'+
			'<img src="http://smny-photo.qiniudn.com/WANWIFI.png" height="100%" alt="圣玛尼亚信息技术">'+
		'</div>'+
	'</h1>'+
	'<div class="notecont">'+
  	'<p>圣玛尼亚信息技术</p>'+
    '<a href="#"" id="gz" class="am-btn" role="button" onclick="addContact()">关注</a>'+
    '<p class="nextline">关注该品牌，畅游全城免费WiFi</p>'+
	'</div>'+
  '<div id="footer">'+
    '<small style="font-size:16px">已有<font style="color:#FF8080">100</font>人使用本WiFi</small><br />'+
		'<small>技术支持 圣玛尼亚</small>'+
  '</div>'+
'</div>');
var a=document.getElementById("imglogo");
a.style.width=a.offsetHeight+"px";

})
();