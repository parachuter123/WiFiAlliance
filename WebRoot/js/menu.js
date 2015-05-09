/*
 生成指定用户的导航菜单
 ————2014-12-03
 */

//待处理消息数目

var messagecount = 5;

var introduction = "圣玛尼亚团队致力于改善企业信息化进程。推出高质量的一站式解决方案，将企业软件需求策划、设计开发、软件周边硬件、实施推广、定期售后服务等一系列环节融合到一起。专注企业信息化建设解决方案提供商。";

var copyright = "© 2007-2014 SMNY.cn WIFI联盟";

//菜单映射 Start
var Dashboard = "<a href='./Dashboard.htm'><i class='icon-home'></i>统计面板</a>";

var ShopManager = "<a href='./ShopList.htm'><i class='icon-group'></i>商家管理</a>";

var AgentManager = "<a href='./AgentList.htm'><i class='icon-group'></i>代理商管理</a>";

var NewRouterList = "<a href='./RouterVerifyList.htm'><i class='icon-th-list'></i>路由器审核</a>";

//var MessageSend = "<a href='./MessageSend.jsp'><i class='icon-comment'></i>消息发送<span class='label label-warning pull-right'>"+messagecount+"</span></a>";

var MessageInterface = "<a href='./AppEntranceManager.htm'><i class='icon-th-large'></i>消息接口设置</a>";

var Ad = "<a href='./InfoTemplate.htm'><i class='icon-shopping-cart'></i>广告设置</a>";

var account = "<a href='./Account.htm'><i class='icon-user'></i>账户其他设置</a>";

var faq = "<a href='./faq.jsp'><i class='icon-pushpin'></i>功能说明</a>";

var followerlist ="<a href='./followerlist.htm'><i class='icon-comment'></i>会员列表</a>";

var GroupManager ="<a href='./GroupManager.htm'><i class='icon-group'></i>分组设置</a>";

var routerlist ="<a href='./routerlist.htm'><i class='icon-th-list'></i>路由列表</a>";

var BlackWhiteList = "<a href='./BlackWhiteList.htm'><i class='icon-th-list'></i>黑白名单</a>";

var MemberTel = "<a href='./MemberTel.htm'><i class='icon-th-list'></i>会员管理</a>";

var PhoneFace = "<a href='./PhoneFace.htm'><i class='icon-picture'></i>分享任务</a>";

var ModelAnalysis = "<a href='./ModelAnalysis.asp'><i class='icon-phone'></i>手机设备类型</a>";
//菜单映射 End

var AcountSettig = "<li><a href='./Account.htm'><i class='icon-user'></i> 账户设置  </a></li>";
var ChangePassword = "<li><a href='./Account.htm'><i class='icon-lock'></i> 修改密码</a></li>";
var LogOut = "<li><a href='login.jsp'><i class='icon-off'></i> 退出登录</a></li>";
var deliver = "<li class='divider'></li>";

/*
 ID<====>角色
 1: 管理员
 2：高级代理商
 3：代理商
 4：用户
 */
function getMenu(RoleID,PageName){

    var menustr = "";

    menustr += isActive(PageName=="Dashboard",Dashboard);
    menustr += isActive(PageName=="routerlist",routerlist);

    if(RoleID==3 || RoleID==2 || RoleID==1){
        menustr += isActive(PageName=="ShopManager",ShopManager);
        //menustr += isActive(PageName=="NewRouterList",NewRouterList);
    }
    if(RoleID==2 || RoleID==1){
        menustr += isActive(PageName=="AgentManager",AgentManager);
    }
    if(RoleID==1){
        //menustr += isActive(PageName=="NewRouterList",NewRouterList);
        //menustr += isActive(PageName=="BlackWhiteList",BlackWhiteList);
        menustr += isActive(PageName=="ModelAnalysis",ModelAnalysis);
    }
    if(RoleID==3){
        menustr += isActive(PageName=="MemberTel",MemberTel);
    }

    if(RoleID==3 || RoleID==2){
        menustr += isActive(PageName=="account",account);
    }

    if(RoleID==4){
        //menustr += isActive(PageName=="MessageSend",MessageSend);
        menustr += isActive(PageName=="MessageInterface",MessageInterface);
        menustr += isActive(PageName=="GroupManager",GroupManager);
        menustr += isActive(PageName=="followerlist",followerlist);
        menustr += isActive(PageName=="Ad",Ad);
        menustr += isActive(PageName=="PhoneFace",PhoneFace);
        menustr += isActive(PageName=="account",account);
        menustr += isActive(PageName=="BlackWhiteList",BlackWhiteList);
        menustr += isActive(PageName=="faq",faq);
    }
    document.write(menustr);
}

function isActive(isactive,Str){
    if(isactive){
        return "<li class='active'>"+Str+"</li>";
    }else{
        return "<li>"+Str+"</li>";
    }
}

function getFunctionBord(RoleID){
    var menustr = "";

    if(RoleID == 4){
        menustr += AcountSettig;
        menustr += ChangePassword;
    }
    menustr += deliver;
    menustr += LogOut;

    document.write(menustr);
}
function getIntroductionStr(){
	document.write(introduction);
}

function getcopyright(){
    document.write(copyright);
}