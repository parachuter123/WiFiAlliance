package com.smny.wifiAlliance.webaction;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import smny.MyBatisFactory;
import smny.util.ServletRefactorMethod;
import smny.util.SetSystemProperty;
import smny.util.StringToolkit;

import com.smny.wifiAlliance.entity.logic.AppEntranceLogic;
import com.smny.wifiAlliance.entity.logic.BusinessShopLogic;


/**
 * PACKAGE_NAME： webaction
 * <p/>
 * <关注者列表>
 * <p/>
 * 作者：  parachuter
 * 创建时间： 14-12-08.
 */
@WebServlet(name = "followerlist", urlPatterns = "/followerlist.htm")
public class followerlist extends HttpServlet {

    private static final int DEFAULTPAGEINDEX = 1;
    private static final int DEFAULTPAGESIZE = 20;
    private static int DEFAULTSTATUSID = 1;
    private static String DEFAULTGROUPID = "MyFollList";

    //消息接口设置 修改
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	 Map<String,Object> ServletRefactorMethodMap = ServletRefactorMethod.start(request, response);
    	if(ServletRefactorMethodMap==null){
    		return;
    	}
         Integer UserType = (Integer)ServletRefactorMethodMap.get("UserType");
         String Operator = (String)ServletRefactorMethodMap.get("Operator");
        

        /**************************未修改部分，有用*****************************/
        /*//商户
        if ("4".equals(UserType.intValue())) {
            String AppEntraceID = request.getParameter("AppEntraceID");

            if (StringToolkit.isNullOrEmpty(AppEntraceID)) {
                response.getWriter().println("公众账号异常：ID不存在！");
                return;
            }

            AppEntrance app = AppEntrance.getData(AppEntraceID);
            if (app == null) {
                response.getWriter().println("公众账号不存在！！");
                return;
            }

            ci_util.MessageInterface Wx = ClientGets.getWxWebClient(app);

            Object[] Openids = Wx.getAllFollowers();

            if (Openids == null || Openids.length <= 0) {
                response.getWriter().println("获取OpenID列表失败！");
                return;
            }
            AppRecord apprecord = AppRecord.getData(app.getAppEntranceID());
            apprecord.setTotalCount(Openids.length);
            try {
                apprecord.Update();
            } catch (Exception e) {
                response.getWriter().println("记录总数个更新失败：" + e.toString());
                return;
            }

            for (Object o : Openids) {
                Followers Foll = Followers.getData(AppEntraceID, o.toString());
                try {
                    if (Foll == null) {
                        Foll = new Followers();
                        Foll.setGroupID(app.getDefaultGroupID());
                        Foll.setFansID(o.toString());
                        Foll.setAppEntranceID(AppEntraceID);
                        Foll.setSubscribeTime(System.currentTimeMillis());

                        Wx.GetUserInfo(Foll);
                        Foll.Create();
                    } else {
                        Wx.GetUserInfo(Foll);
                        Foll.Update();
                    }


                } catch (Exception e) {
                    response.getWriter().println("用户信息刷新失败：" + Foll.getFollowersID());
                    System.out.println("用户信息刷新失败：" + Foll.getFollowersID());
                }

            }
            response.getWriter().println("用户信息刷新成功");
            return;

        }*/
    }

    //消息接口列表
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
				//商户
				case 4:
					Map<String,Object> BusinessShop = sqlSession.selectOne("RouterBatis.BusinesShopMapper.SelectBusinesShopID",Operator);
					if (BusinessShop == null) {
						request.setAttribute("Operat","3");
						request.setAttribute("ErrorMessage","当前商家不存在或已删除");
						request.getRequestDispatcher("/AuthHtmlError.jsp").forward(request, response);
						return;
					}
		            //获取用户APP
		            List<Map<String,Object>> Applist = AppEntranceLogic.getDatasBusinessShopID(sqlSession,Operator);
		            request.setAttribute("Applist", Applist);
		            if (Applist==null || Applist.size() == 0) {
						request.setAttribute("Applist", null);
						request.getRequestDispatcher("followerlist.jsp").forward(request, response);
						return;
		            }		            
		            //获取用户分组
		            List<Map<String,Object>> Grouplist = BusinessShopLogic.getFollowersGroup(sqlSession, (Integer)BusinessShop.get("AgentID"),Operator);
		            
		            String AppEntranceID = request.getParameter("AppEntraceID");
					if (StringToolkit.isNullOrEmpty(AppEntranceID)) {
							AppEntranceID = (String)Applist.get(0).get("AppEntranceID");
					}
					String StatusID = request.getParameter("StatusID");
					String GroupID = request.getParameter("GroupID");
					String pageindex = request.getParameter("PageIndex");
					String followernickname = request.getParameter("followernickname");
					
					int DefaultStatusID = DEFAULTSTATUSID;
					String DefaultGroupID = DEFAULTGROUPID;
					int PageIndex = DEFAULTPAGEINDEX;
					String FollowerNickName = "";
					
					if (!StringToolkit.isNullOrEmpty(StatusID)) {
							DefaultStatusID = Integer.parseInt(StatusID);
					}
					if (!StringToolkit.isNullOrEmpty(GroupID)) {
							DefaultGroupID = GroupID;
					}
					if (!StringToolkit.isNullOrEmpty(pageindex)) {
							PageIndex = Integer.parseInt(pageindex);
					}
					if (!StringToolkit.isNullOrEmpty(followernickname)) {
						FollowerNickName = followernickname;
					}
					
					Map<String,Object> WhereMap = new HashMap<String,Object>();
					WhereMap.put("AppEntranceID", AppEntranceID);
					WhereMap.put("StatusID", DefaultStatusID);
					WhereMap.put("GroupID", DefaultGroupID);
					WhereMap.put("NickName", followernickname);
		            request.setAttribute("Grouplist", Grouplist);
		            int FollowersCout = (Integer)sqlSession.selectOne("RouterBatis.FollowersMapper.SelectCount",WhereMap);
					int StartNumber = (PageIndex-1)*DEFAULTPAGESIZE;
					if(FollowersCout <= StartNumber){
						PageIndex = (int)Math.ceil((double)FollowersCout/DEFAULTPAGESIZE);
						StartNumber = (PageIndex-1)*DEFAULTPAGESIZE;
					}
					//获取关注者
					List<Map<String,Object>> FollowersList = sqlSession.selectList("RouterBatis.FollowersMapper.SelectFollowers",WhereMap,new RowBounds(StartNumber,DEFAULTPAGESIZE));
					request.setAttribute("FollowersList",FollowersList); 
					request.setAttribute("FollowersCout",FollowersCout);
					request.setAttribute("APPID", AppEntranceID);
					request.setAttribute("StatusID", DefaultStatusID);
					request.setAttribute("GroupID", DefaultGroupID);
					request.setAttribute("count", FollowersCout);
					request.setAttribute("PageIndex", PageIndex);
					request.setAttribute("followernickname", FollowerNickName);
					request.getRequestDispatcher("/followerlist.jsp").forward(request, response);
					break;
				default:
					request.setAttribute("Operat","-8");
					request.setAttribute("ErrorMessage","角色选择错误:"+UserType);
					request.setAttribute("ShowBug",properties.getValue("ShowBug"));
					request.setAttribute("SysErrorPointOut",properties.getValue("SysErrorPointOut"));
					request.getRequestDispatcher("/AuthHtmlError.jsp").forward(request, response);
					break;
			}
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
