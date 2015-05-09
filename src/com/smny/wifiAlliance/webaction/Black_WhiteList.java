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


/**
 * PACKAGE_NAME： webaction
 * <p/>
 * <黑白名单列表>
 * <p/>
 * 作者：  SatanWang
 * 创建时间： 14-4-9.
 */
@WebServlet(name = "BlackWhiteList", urlPatterns = "/BlackWhiteList.htm")
public class Black_WhiteList extends HttpServlet {

	private static final long serialVersionUID = 1L;
	//默认页码
    private static final int DEFAULTPAGEINDEX = 1;
    //默认页大小
    private static final int DEFAULTPAGESIZE = 8;
    //默认是否临时黑白名单
    private static final boolean ISTEMP = false;
    //默认黑名单
    private static final boolean LISTTYPE = false;
    
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
					String Type = request.getParameter("Type");
					if("Create".equals(Type)){
						String BusinessShopID = Operator;
						if (StringToolkit.isNullOrEmpty(BusinessShopID)) {
							request.setAttribute("Operat","2");
							request.setAttribute("ErrorMessage","户主名称不能为空！");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
							return;
						}
			            String Mac = request.getParameter("Mac");
			            if (StringToolkit.isNullOrEmpty(Mac)) {
							request.setAttribute("Operat","3");
							request.setAttribute("ErrorMessage","MAC不能为空！");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
							return;
						}
			            String ListType = request.getParameter("ListType");
			            if (StringToolkit.isNullOrEmpty(Mac)) {
							request.setAttribute("Operat","4");
							request.setAttribute("ErrorMessage","黑白名单不能为空！");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
							return;
						}
			            String Remark = request.getParameter("Remark");
			            if (StringToolkit.isNullOrEmpty(Mac)) {
							request.setAttribute("Operat","5");
							request.setAttribute("ErrorMessage","备注不能为空！");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
							return;
						}
			            Map<String,Object> BlackWhite = new HashMap<String,Object>();
			            BlackWhite.put("BusinesShopID", BusinessShopID);
			            BlackWhite.put("Mac", Mac);
			            BlackWhite.put("ListType", Boolean.parseBoolean(ListType)?1:0);
			            BlackWhite.put("Remark", Remark);
			            try{
			            	Map<String,Object> BW = sqlSession.selectOne("RouterBatis.BlackWhiteListMapper.SelectBusinesBlackWhiteList", BlackWhite);
			            	if(BW==null){
			            		sqlSession.insert("RouterBatis.BlackWhiteListMapper.insertBlackWhiteList",BlackWhite);
								sqlSession.commit(); 
								request.setAttribute("Operat","1");
								request.setAttribute("ErrorMessage","黑白名单创建成功！！");
			            	}else{
			            		sqlSession.update("RouterBatis.BlackWhiteListMapper.updateNonEmpty", BlackWhite);
			            		sqlSession.commit();
			            		request.setAttribute("Operat","1");
								request.setAttribute("ErrorMessage","黑白名单更新成功！！");
			            	}
			            }catch(Exception e){
							request.setAttribute("Operat","-4");
							request.setAttribute("ErrorMessage",e.getMessage().replace("\r\n", ""));
							request.setAttribute("ShowBug",properties.getValue("ShowBug"));
							request.setAttribute("SysErrorPointOut",properties.getValue("SysErrorPointOut"));
			            }
			            request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
						return;
					}
					if("Delete".equals(Type)){
						String BlackWhiteListID = request.getParameter("BlackWhiteListID");
						if (StringToolkit.isNullOrEmpty(BlackWhiteListID)) {
							request.setAttribute("Operat","6");
							request.setAttribute("ErrorMessage","黑白名单ID不能为空");
							request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
							return;
						}
						try{
							sqlSession.delete("RouterBatis.BlackWhiteListMapper.deleteBlackWhiteListID",BlackWhiteListID);
							sqlSession.commit();
							request.setAttribute("Operat","1");
							request.setAttribute("ErrorMessage","删除成功");
						}catch(Exception e){
							request.setAttribute("Operat","-4");
							request.setAttribute("ErrorMessage",e.getMessage().replace("\r\n", ""));
							request.setAttribute("ShowBug",properties.getValue("ShowBug"));
							request.setAttribute("SysErrorPointOut",properties.getValue("SysErrorPointOut"));
						}
						request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
						return;
					}
				break;
				default:
					request.setAttribute("Operat","-2");
					request.setAttribute("ErrorMessage","角色选择错误:"+UserType);
					request.setAttribute("ShowBug",properties.getValue("ShowBug"));
					request.setAttribute("SysErrorPointOut",properties.getValue("SysErrorPointOut"));
					request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
				break;
			}
		} catch (Exception e) {
			request.setAttribute("Operat","-5");
			request.setAttribute("ErrorMessage",e.getMessage().replace("\r\n", ""));
			request.setAttribute("ShowBug",properties.getValue("ShowBug"));
			request.setAttribute("SysErrorPointOut",properties.getValue("SysErrorPointOut"));
			request.getRequestDispatcher("/AuthJsonError.jsp").forward(request, response);
		}finally{
			sqlSession.close();
		}
    }

    //黑白名单列表
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	Map<String,Object> ServletRefactorMethodMap = ServletRefactorMethod.start(request, response);
    	if(ServletRefactorMethodMap==null){
    		return;
    	}
        Integer UserType = (Integer)ServletRefactorMethodMap.get("UserType");
        String Operator = (String)ServletRefactorMethodMap.get("Operator");
        SetSystemProperty properties = (SetSystemProperty) ServletRefactorMethodMap.get("ssp");
        String isTem = request.getParameter("isTem");
        String listtype = request.getParameter("listtype");
        String PageIndex = request.getParameter("PageIndex");
        String SearchKeyWords = request.getParameter("SearchKeyWords");

        if (StringToolkit.isNullOrEmpty(isTem)) {
            isTem = null;
        }
        if (StringToolkit.isNullOrEmpty(listtype)) {
            listtype = null;
        }
        if (StringToolkit.isNullOrEmpty(SearchKeyWords)) {
            SearchKeyWords = null;
        }
        int pageIndex = DEFAULTPAGEINDEX;
        if (!StringToolkit.isNullOrEmpty(PageIndex)) {
        	pageIndex = Integer.parseInt(PageIndex);
        }
        org.apache.ibatis.logging.LogFactory.useLog4JLogging(); 
        SqlSession sqlSession = MyBatisFactory.openSession();
        try {
        	int BlackWhiteCout = 0;
        	List<Map<String,Object>> BlackWhite = null;
        	Map<String,Object> WhereMap = new HashMap<String,Object>();
        	WhereMap.put("ListType",listtype);
			WhereMap.put("listtype_int",Boolean.valueOf(listtype)?1:0);
			WhereMap.put("TemBlackList",isTem);
			WhereMap.put("temblacklist_int",Boolean.valueOf(isTem)?1:0);
			WhereMap.put("SearchKeyWords",SearchKeyWords);
			switch(UserType.intValue()){
				//管理员
				case 1:
					WhereMap.put("BusinesShopID", request.getParameter("BusinessID"));
					break;
				//商户
				case 4:
					WhereMap.put("BusinesShopID",Operator);
					BlackWhiteCout = (Integer)sqlSession.selectOne("RouterBatis.BlackWhiteListMapper.SelectBlackWhiteListCount",WhereMap);
					int StartNumber = (pageIndex-1)*DEFAULTPAGESIZE;
					if(BlackWhiteCout <= StartNumber){
						pageIndex = (int)Math.ceil((double)BlackWhiteCout/DEFAULTPAGESIZE);
						StartNumber = (pageIndex-1)*DEFAULTPAGESIZE;
					}
					BlackWhite = sqlSession.selectList("RouterBatis.BlackWhiteListMapper.SelectBlackWhiteList",WhereMap,new RowBounds(StartNumber,DEFAULTPAGESIZE));
					request.setAttribute("BlackWhite",BlackWhite);
					request.setAttribute("BlackWhiteCout",BlackWhiteCout);
					request.setAttribute("PageIndex", pageIndex);
					request.getRequestDispatcher("/BlackWhiteList.jsp").forward(request, response);
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
