package com.smny.wifiAlliance.webaction;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;

import smny.MyBatisFactory;
import smny.util.ServletRefactorMethod;
import smny.util.SetSystemProperty;
import smny.util.StringToolkit;

import com.smny.wifiAlliance.entity.logic.BlackWhiteListLogic;
import com.smny.wifiAlliance.entity.logic.BusinessShopLogic;
import com.smny.wifiAlliance.entity.logic.FollowersLogic;

/**
 * PACKAGE_NAME： webaction
 * <p/>
 * <在线终端列表>
 * <p/>
 * 作者：  Ronan
 * 创建时间： 14-12-04.
 */
@WebServlet(name = "OnlineTerminalList", urlPatterns = "/OnlineTerminalList.htm")
public class OnlineTerminalList extends HttpServlet {

	private static final long serialVersionUID = 1L;

	//消息接口设置 修改
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	Map<String,Object> ServletRefactorMethodMap = ServletRefactorMethod.start(request, response);
    	if(ServletRefactorMethodMap==null){
    		return;
    	}
        Integer UserType = (Integer)ServletRefactorMethodMap.get("UserType");
        String Operator = (String)ServletRefactorMethodMap.get("Operator");
        SetSystemProperty properties = (SetSystemProperty) ServletRefactorMethodMap.get("ssp");
		SqlSession sqlSession = MyBatisFactory.openSession();
		try {
				switch(UserType.intValue()){
					case 4:
						
						break;
					default:
						request.setAttribute("Operat","-8");
						request.setAttribute("ErrorMessage","角色选择错误:"+UserType);
						request.setAttribute("ShowBug",properties.getValue("ShowBug"));
						request.setAttribute("SysErrorPointOut",properties.getValue("SysErrorPointOut"));
						request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
						return;
				}
		} catch (Exception e) {
			request.setAttribute("Operat","-9");
			request.setAttribute("ErrorMessage",e.getMessage().replace("\r\n", ""));
			request.setAttribute("ShowBug",properties.getValue("ShowBug"));
			request.setAttribute("SysErrorPointOut",properties.getValue("SysErrorPointOut"));
			request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
		}finally{
			sqlSession.close();
		}
    }

    //消息接口列表
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	Map<String,Object> ServletRefactorMethodMap = ServletRefactorMethod.start(request, response);
    	if(ServletRefactorMethodMap==null){
    		return;
    	}
        Integer UserType = (Integer)ServletRefactorMethodMap.get("UserType");
        String Operator = (String)ServletRefactorMethodMap.get("Operator");
        SetSystemProperty properties = (SetSystemProperty) ServletRefactorMethodMap.get("ssp");
        //获取到的WifiRouterID 格式：BussessID:出场路由ID  例如：1_ayxsxlr:219949
        String WifiRouterID = request.getParameter("WifiRouterID");
        String DevName = java.net.URLDecoder.decode(request.getParameter("DevName"),"utf-8"); 
        if(StringToolkit.isNullOrEmpty(WifiRouterID)){
			request.setAttribute("ErrorMessage","获取失败：路由器ID不存在");
			request.setAttribute("Operat","2");
			request.setAttribute("ShowBug",properties.getValue("ShowBug"));
			request.setAttribute("SysErrorPointOut",properties.getValue("SysErrorPointOut"));
			request.getRequestDispatcher("/AuthHtmlError.jsp").forward(request, response);
			return;
		}
        WifiRouterID = WifiRouterID.replaceAll(".*\\.(.*):.*","$1");
        SqlSession sqlSession = MyBatisFactory.openSession();
        try {
        	//根据WifiRouterID获取在线设备数据对象
			List<Map<String,Object>> onlineTerminalslist = sqlSession.selectList("RouterBatis.OnlineTerminalMapper.SelectWifiRouterID", WifiRouterID);
			request.setAttribute("onlineTerminalslist", onlineTerminalslist);
			request.setAttribute("DevName", DevName);	
			request.setAttribute("BlackWhiteListGET", new BlackWhiteListLogic());
			request.setAttribute("BusinessShopGET", new BusinessShopLogic());
			request.setAttribute("FollowersGET", new FollowersLogic());
			request.getRequestDispatcher("OnlineTerminal_1.jsp").forward(request, response);
		} catch (Exception e) {
			request.setAttribute("Operat","-9");
			request.setAttribute("ErrorMessage",e.getMessage().replace("\r\n", ""));
			request.setAttribute("ShowBug",properties.getValue("ShowBug"));
			request.setAttribute("SysErrorPointOut",properties.getValue("SysErrorPointOut"));
			request.getRequestDispatcher("/AuthHtmlError.jsp").forward(request, response);
		}finally{
			sqlSession.close();
		}
    }
}
