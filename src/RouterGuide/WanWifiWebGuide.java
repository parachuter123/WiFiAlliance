package RouterGuide;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.Map;


import smny.MyBatisFactory;
import smny.util.StringToolkit;

import org.apache.ibatis.session.SqlSession;  

/**
 * PACKAGE_NAME： RouterGuide
 * <p/>
 * <Mac绑定>
 * <p/>
 * 作者：  信念
 * 创建时间： 14-10-19.
 */
@WebServlet(name = "WanWifiWebGuide", urlPatterns = "/WanWifiWebGuide.do", loadOnStartup = 1)
public class WanWifiWebGuide extends HttpServlet{
		/*static{
				try{
						GuideRouterServer Guide = GuideRouterServer.getGuideRouterServer();
						Guide.start();
				}catch(Exception NoS){
            NoS.printStackTrace();
				}
		}*/
		//路由认证状态通知
		protected void service(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException{
				req.setCharacterEncoding("UTF-8");
        MacBinding.ParamHandle(req,resp);
				String QueryString = req.getQueryString();
        String Mac = (String)req.getAttribute("MacAddr");
        String IpAddr = (String)req.getAttribute("ipAddr");
        String HardSeq = (String)req.getAttribute("HardSeq");
        String TerminalID = req.getParameter("TerminalID");
        String Type = req.getParameter("Type");
        if (StringToolkit.isNullOrEmpty(Mac)){
						System.out.println("MAC=>null");
        }
        if (StringToolkit.isNullOrEmpty(IpAddr)){
						System.out.println("IpAddr=>null");
        }
        if (StringToolkit.isNullOrEmpty(HardSeq)){
						System.out.println("HardSeq=>null");
        }
        if (StringToolkit.isNullOrEmpty(TerminalID)){
						System.out.println("TerminalID=>null");
        }
        if (StringToolkit.isNullOrEmpty(Type)){
						System.out.println("Type=>null");
        }
        if (StringToolkit.isNullOrEmpty(Mac,IpAddr,HardSeq,TerminalID,Type)){
						if(StringToolkit.isNullOrEmpty(QueryString)){
								resp.sendRedirect("http://www.wanwifi.com/");
						}else{
								resp.sendRedirect("http://www.wanwifi.com/?"+QueryString);
						}
						return;
        }
        if("Unknown".equals(Type)){
        		if(isWeiXin(req)){
		        		StringBuilder SB = new StringBuilder(255);
		        		SB.append("http://mp.weixin.qq.com/s?");
		        		SB.append(QueryString);
		        		SB.append("&Mac=");
		        		SB.append(Mac);
		        		SB.append("&IpAddr=");
		        		SB.append(IpAddr);
		        		resp.sendRedirect(SB.toString());
        		}else if(isAlipay(req)){
	        			req.getRequestDispatcher("/AlipayAuth.do").forward(req, resp);
        		}else{
        				req.getRequestDispatcher("/InterceptAdvert.do").forward(req, resp);
        		}
        }else if("Notice".equals(Type)){
        		req.getRequestDispatcher("/AdvertNotice.do").forward(req, resp);
        }else if("Expired".equals(Type)){
        		if(isWeiXin(req)){
		        		StringBuilder SB = new StringBuilder(255);
		        		SB.append("http://mp.weixin.qq.com/s?");
		        		SB.append(QueryString);
		        		SB.append("&Mac=");
		        		SB.append(Mac);
		        		SB.append("&IpAddr=");
		        		SB.append(IpAddr);
		        		resp.sendRedirect(SB.toString());
        		}else if(isAlipay(req)){
	        			req.getRequestDispatcher("/AlipayAuth.do").forward(req, resp);
        		}else{
        				req.getRequestDispatcher("/AuthJs/WeiXinExpired.js").forward(req, resp);
        		}
        }else {
        		req.getRequestDispatcher("/InterceptAdvert.do").forward(req, resp);
        }
        
		}
    public static boolean isWeiXin(HttpServletRequest req){
        String UserAgent = req.getHeader("user-agent");
				if(StringToolkit.isNullOrEmpty(UserAgent) || !UserAgent.contains("MicroMessenger")){
						return false;
				}
				return true;
    }
    public static boolean isAlipay(HttpServletRequest req){
        return false;
    }
}
