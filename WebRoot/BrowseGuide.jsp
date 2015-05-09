<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<meta content="text/html; charset=utf-8" http-equiv="Content-Type"/>
	<meta charset="utf-8"/><meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"/><meta name="apple-mobile-web-app-capable" content="yes"/>
	<meta name="apple-mobile-web-app-status-bar-style" content="black"/>
	<meta name="format-detection" content="telephone=no"/>
	<meta name="apple-mobile-web-app-capable" content="yes"/>
	<title>${requestScope.BusinessShop.get('BusinesName')}</title>
	<link href="css/web.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="js/s.js"></script>
</head>
<body>
<div class="head">
	<div class="logo">
		<div class="jz" id="jz"><img src="${requestScope.BusinessShop.get('LogoURL')}" onload="pos()"/></div>
		<div class="mr" id="mr"><img src="images/logo.png" /></div>
	</div>
	<div class="logotxt">
		<span>已有<span class="col" >323人</span>使用本WiFi</span><br />由${requestScope.BusinessShop.get('BusinesName')}提供
	</div>
</div>
<div class="bg">
	<div class="scroll" id="scroll">
		<div class="slide_01" id="slide_01">
			<div class="mod_01" id="mod_01"><img src="images/banner1.jpg" /></div>
			<div class="mod_01" id="mod_02"><img src="images/banner2.jpg" /></div>
			<div class="mod_01" id="mod_03"><img src="images/banner3.jpg" /></div>
		</div>
		<div class="dotModule_new">
			<div id="slide_01_dot"></div>
		</div>
	</div>
</div>
<div class="cont">
	<div class="start" id="start">
		<div class="dt">
			<a href="###"><img id="dt" src="images/dt.png" /></a>
		</div>
		<a id="ks" onclick="UserTry()">确认开始</a>
		<div class="x"></div>
	</div>
	<div class="starttxt">
		<div class="r"> <div class="dg" id="dg"><input type="checkbox" id="xy"  checked="true" onClick="javascript:a()"></div>我同意<span>WanWiFi</span>使用协议</div>
	</div>
</div>
<div class="foot">
技术支持 ${requestScope.Agent.get('AgentName')}
</div>
<iframe id="ruiWechatFrame" style="display:none;" >

<div class="rui-authen-weixin" >
          <p id="ruiOpeningWechat" class="rui-openwx-tips"></p>
</div>


</iframe>
<script type="text/javascript" src="js/pc.js"></script>
<script type="text/javascript">
var w=document.documentElement.clientWidth;
if(w<360){
	var dg=document.getElementById("dg");
	dg.style.left=23+"%";
	}
var scrw=document.getElementById("scroll").offsetWidth;
var mod1=document.getElementById("mod_01");
mod1.style.width=scrw+"px";
var mod2=document.getElementById("mod_02");
mod2.style.width=scrw+"px";
var mod3=document.getElementById("mod_03");
mod3.style.width=scrw+"px";

if(document.getElementById("slide_01")){
	var slide_01 = new ScrollPic();
	slide_01.scrollContId   = "slide_01"; //内容容器ID
	slide_01.dotListId      = "slide_01_dot";//点列表ID
	slide_01.dotOnClassName = "selected";
	slide_01.arrLeftId      = "sl_left"; //左箭头ID
	slide_01.arrRightId     = "sl_right";//右箭头ID
	slide_01.frameWidth     = scrw;
	slide_01.pageWidth      = scrw;
	slide_01.upright        = false;
	slide_01.speed          = 10;
	slide_01.space          = 30; 
	slide_01.initialize(); //初始化
}

  	function UserTry(){
    		var Try = "${requestScope.WifiRouter.get('BrowseGuideType')}";
    		if(Try=="WeiXin"){
    				rptk_openning_wechat();
    				rptk_open_wechat('http://sys.txly.com/open.html');
    				//window.location.replace("/AuthJs/WeiXinBrowseGuide.jsp?t="+new Date().getTime())
    		}else if(Try=="Try"){
    				if("RG"=="${param.deviceid}"){
    						alert("${header.referer}");
    				}else{
    						window.location.replace("http://"+decodeURIComponent('${param.AuthAaddr}')+"/UserAuthorized.do?Type=Log&mac=${param.mac}&ipaddr=${param.ipaddr}&HardSeq=${param.deviceid}&TerminalID=${param.TerminalID}")
    				}
    		}else{
    				window.location.replace("/AuthJs/SmsBrowseGuide.jsp?t="+new Date().getTime())
    		}
  	}
  	function CaptchaTry(){
    		var Captcha = prompt("请先用手机关注“${requestScope.BusinessShop.get('BusinesName')}”后,回复“验证”,取得验证码后填入","");
    		if(Captcha==null || Captcha==""){
    				alert("验证码不能为空！");
    				return;
    		}
    		window.location.replace("http://sys.txly.com/MacBinding.do?MacBinding=CaptchaTry&AuthAaddr="+decodeURIComponent("${param.AuthAaddr}")+"&Captcha="+Captcha)
  	}
  	
  	var opening = document.getElementById('ruiOpeningWechat');
  	var wechat_open_count = 0;
  	var max_wechat_open_count = 10;
  	function rptk_openning_wechat() {
  	  if (wechat_open_count == 0) {
  	    document.getElementById('ruiOpeningWechat').style("display","block");
  	    opening.innerHTML = "跳转到微信,请等待";
  	  } else if (wechat_open_count <= max_wechat_open_count) {
  	    opening.innerHTML += ' ·';
  	  } else {
  	    opening.innerHTML = "该浏览器可能不支持跳转到微信，请手动打开";
  	  }
  	  if (wechat_open_count <= max_wechat_open_count) {
  	    wechat_open_count += 1;
  	    setTimeout(rptk_openning_wechat, 500);
  	  } else {
  	    wechat_open_count = 0;
  	  }
  	}

</script>
</body>

<script type="text/javascript">
function pos(){
	var jz=document.getElementById("jz");
	jz.style.display="block";
	var mr=document.getElementById("mr")
	mr.style.display="none";
	}
function a(){
var xy=document.getElementById("xy");
var Start=document.getElementById("start");
if(xy.checked== true){
	Start.innerHTML="<div class='dt'><img src='images/dt.png' /></div><a id='ks' onclick='UserTry()'> 确认开始 </a> <div class='x'></div>";
	Start.style.background="#7564ce";
ks();
	}else{
	Start.innerHTML="<div class='dt'><img src='images/dt.png' /></div><a  id='ks' onclick='UserTry()'> 确认开始 </a><div class='x'>";
	Start.style.background="#cdcdcf";
	ks()
		}
}
function ks(){
var h=document.documentElement.clientHeight;
document.body.style.height=h+"px";
var ks=document.getElementById("ks");
var ksh=ks.offsetHeight;
ks.style.lineHeight=ksh+"px";
}
ks();

function rptk_open_wechat(url) {
  //var iframe = document.getElementById("ruiWechatFrame");
//alert(iframe.src);
  if (url != undefined) {
    window.location.href = "weixin://dl/privacy";
    //rptk_openning_wechat();
    //setTimeout(rptk_open_wechat, 5000);
  } else {
    //iframe.src = '';
  }
}

//rptk_open_wechat('http://sys.txly.com/open.html')
</script>
</html>
