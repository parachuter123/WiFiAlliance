package RouterGuide;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.Map;
import java.util.Date;
import java.util.HashMap;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import smny.BindingMapper;
import smny.MyBatisFactory;
import smny.util.StringToolkit;

import org.apache.ibatis.session.SqlSession;  

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import org.apache.commons.codec.binary.Base64;

import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * PACKAGE_NAME： RouterGuide
 * <p/>
 * <Mac绑定>
 * <p/>
 * 作者：  信念
 * 创建时间： 14-10-19.
 */
@WebServlet(name = "MacBinding", urlPatterns = "/MacBinding.do")
public class MacBinding extends HttpServlet{
		//路由认证状态通知
		public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				request.setCharacterEncoding("UTF-8");
        String MacBinding = request.getParameter("MacBinding");
        if (StringToolkit.isNullOrEmpty(MacBinding)){
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<script type='text/javascript'>alert('请指明绑定类型');</script>");
            return;
        }
				ParamHandle(request,response);
        if("Auto".equals(MacBinding)){
        		Auto(request,response);
        }else if("Binding".equals(MacBinding)){
        		Binding(request,response);
        }else if("Manual".equals(MacBinding)){
        		ManualBinding(request,response);
        }else if("Js".equals(MacBinding)){
        		String Referer = request.getHeader("referer");
        		if(!StringToolkit.isNullOrEmpty(Referer) && Referer.startsWith("http://mp.weixin.qq.com/s?ShareID=")){
        				request.getRequestDispatcher("./ShareTask.do?ShareID="+Referer.replaceAll(".*ShareID=(.*)","$1")).forward(request,response);
        				return;
        		}
        		request.getRequestDispatcher("./JsAdvert.do").forward(request,response);
        }else{
        		
        }
		}
		public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				doGet(request,response);
		}
		
		
		
		
		
		//认证服务号自动绑定-------向微信请求用户OpenID
		public void Auto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				request.setCharacterEncoding("UTF-8");
				ParamHandle(request,response);
        String HardSeq = (String)request.getAttribute("HardSeq");
        String MacAddr = (String)request.getAttribute("MacAddr");
        String ipAddr = (String)request.getAttribute("ipAddr");
        
        String OriginNumber = request.getParameter("OriginNumber");
        String RedirectUri = request.getParameter("RedirectUri");
        String State = request.getParameter("State");
        String Scope = request.getParameter("Scope");
				if(StringToolkit.isNullOrEmpty(OriginNumber)){
						if(!StringToolkit.isNullOrEmpty(OriginNumber)){
		      			StringBuilder ThiUrl = new StringBuilder(300);
		      			ThiUrl.append(RedirectUri);
		      			if(!RedirectUri.contains("?")){
		      					ThiUrl.append("?");
		      			}else if(!RedirectUri.endsWith("&")){
		      					ThiUrl.append("&");
		      			}
		      			ThiUrl.append("&HardSeq=");
		      			ThiUrl.append(HardSeq);
		      			ThiUrl.append("&MacAddr=");
		      			ThiUrl.append(MacAddr);
		      			ThiUrl.append("&ipAddr=");
		      			ThiUrl.append(ipAddr);
								response.sendRedirect(ThiUrl.toString());
								return;
						}
						response.setContentType("text/html; charset=UTF-8");
						response.getWriter().println("<script type='text/javascript'>alert('参数“OriginNumber”不能为空！');</script>");
						return;
				}
				if(StringToolkit.isNullOrEmpty(Scope)){
						Scope = "snsapi_base";
				}
        
				SqlSession sqlSession = MyBatisFactory.openSession();
				try {
						Map<String,Object> AppEntrance = sqlSession.selectOne("RouterBatis.AppEntranceMapper.SelectOriginNumber",OriginNumber);
						if(AppEntrance == null){
								response.setContentType("text/html; charset=UTF-8");
								response.getWriter().println("<script type='text/javascript'>alert('RouterBatis.AppEntranceMapper.SelectOriginNumber=>为空!');</script>");
								return;
						}
						String AppID = (String)AppEntrance.get("AppID");
						String AppSecret = (String)AppEntrance.get("AppSecret");
						if(StringToolkit.isNullOrEmpty(AppID,AppSecret)){
								response.setContentType("text/html; charset=UTF-8");
								response.getWriter().println("<script type='text/javascript'>alert('参数“AppID”和“AppSecret”不能为空!');</script>");
								return;
						}
						//编码
						if(!StringToolkit.isNullOrEmpty(RedirectUri)){
								Base64 base64 = new Base64();
								RedirectUri = base64.encodeToString(RedirectUri.getBytes());
						}
						/*
						 * 解码
						if(!StringToolkit.isNullOrEmpty(RedirectUri)){
								Base64 base64 = new Base64();
								RedirectUri = new String(Base64.decodeBase64(RedirectUri));
						}
						*/
						response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + AppID + 
                             "&response_type=code" + 
                                     "&scope=" +  Scope +
                                     "&state=" + State +
                              "&redirect_uri=" + 
                                      URLEncoder.encode(
                                                 "http://sys.wanwifi.com/MacBinding.do?MacBinding=Binding" + 
                                                             "&OriginNumber="+OriginNumber+
                                                             "&RedirectUri="+RedirectUri
                                                 ,"UTF-8") + 
                              "#wechat_redirect"
						                 );
				} catch (Exception e) {
            e.printStackTrace();
        } finally {
						sqlSession.close();
				}
        
						/*
						* http://sys.wanwifi.com/MacBinding.do?MacBinding=Auto
						*					&OriginNumber = 
						*					&RedirectUri = 
						*					&state = 
						*					&State = 
						*/
        
		}
		//认证服务号自动绑定-------微信回复用户OpenID
		public void Binding(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				request.setCharacterEncoding("UTF-8");
				ParamHandle(request,response);
        String HardSeq = (String)request.getAttribute("HardSeq");
        String MacAddr = (String)request.getAttribute("MacAddr");
        String ipAddr = (String)request.getAttribute("ipAddr");
				
        String OriginNumber = request.getParameter("OriginNumber");
        String RedirectUri = request.getParameter("RedirectUri");
        String state = request.getParameter("state");
        String Code = request.getParameter("code");
        
    		if(StringToolkit.isNullOrEmpty(RedirectUri)){
						if(StringToolkit.isNullOrEmpty(OriginNumber)){
								response.setContentType("text/html; charset=UTF-8");
								response.getWriter().println("<script type='text/javascript'>alert('参数“OriginNumber”不能为空！');</script>");
								return;
						}
		        if(StringToolkit.isNullOrEmpty(HardSeq,MacAddr,ipAddr)){
		        		response.setContentType("text/html; charset=UTF-8");
		        		response.getWriter().println("<script type='text/javascript'>alert('自动绑定只能在WiFi下进行！');</script>");
		        		return;
		        }
						
						CloseableHttpClient HttpClient = HttpClients.custom().build();
						SqlSession sqlSession = MyBatisFactory.openSession();
						try {
								Map<String,Object> AppEntrance = sqlSession.selectOne("RouterBatis.AppEntranceMapper.SelectOriginNumber",OriginNumber);
								if(AppEntrance == null){
										response.setContentType("text/html; charset=UTF-8");
										response.getWriter().println("<script type='text/javascript'>alert('RouterBatis.AppEntranceMapper.SelectOriginNumber=>为空!');</script>");
										return;
								}
								String AppID = (String)AppEntrance.get("AppID");
								String AppSecret = (String)AppEntrance.get("AppSecret");
								if(StringToolkit.isNullOrEmpty(AppID,AppSecret)){
										response.setContentType("text/html; charset=UTF-8");
										response.getWriter().println("<script type='text/javascript'>alert('参数“AppID”和“AppSecret”不能为空!');</script>");
										return;
								}
								HttpUriRequest GetOpenID = RequestBuilder.post()
								        .setUri(new URI("https://api.weixin.qq.com/sns/oauth2/access_token"))
								        .addParameter("appid", AppID)
								        .addParameter("secret", AppSecret)
								        .addParameter("code", Code)
								        .addParameter("grant_type", "authorization_code")
								        .build();
								CloseableHttpResponse GetOpenIDResponse = HttpClient.execute(GetOpenID);
								try {
										HttpEntity entity = GetOpenIDResponse.getEntity();
										String ResponseContent = EntityUtils.toString(entity);
										String OpenID = getOpenID(ResponseContent);
										EntityUtils.consume(entity);
										if(StringToolkit.isNullOrEmpty(OpenID)){
												response.setContentType("text/html; charset=UTF-8");
												response.getWriter().println("<script type='text/javascript'>alert('自动绑定失败“OpenID”');</script>"+ResponseContent);
												return;
										}
										AppEntrance.put("FansID",OpenID);
										Map<String,Object> Followers = sqlSession.selectOne("RouterBatis.FollowersMapper.SelectOpenID",AppEntrance);
										if(Followers == null){
												Followers = new HashMap<String,Object>();
												Followers.put("GroupID",AppEntrance.get("DefaultGroupID"));
												Followers.put("FansID",OpenID);
												Followers.put("AppEntranceID",AppEntrance.get("AppEntranceID"));
												Followers.put("StatusID",0);
												Followers.put("SubscribeTime",new Date());
												sqlSession.insert("RouterBatis.FollowersMapper.insertFollowers",Followers);
												sqlSession.commit();
												
												Followers = sqlSession.selectOne("RouterBatis.FollowersMapper.SelectOpenID",AppEntrance);
										}
										Map<String,Object> WifiRouter = sqlSession.selectOne("RouterBatis.WifiRouterMapper.SelectHardSeq",HardSeq);
										
										Followers.put("TerminalMac",MacAddr);
										Followers.put("WifiRouterID",WifiRouter.get("WifiRouterID"));
										BindingTerminal(sqlSession,Followers);
										
										
										if(!StringToolkit.isNullOrEmpty((String)AppEntrance.get("AppHomePageUrl"))){
												response.sendRedirect((String)AppEntrance.get("AppHomePageUrl"));
										}else{
												Map<String,Object> BusinesShop = sqlSession.selectOne("RouterBatis.BusinesShopMapper.SelectBusinesShopID",AppEntrance.get("BusinesShopID"));
												if(BusinesShop==null || StringToolkit.isNullOrEmpty((String)BusinesShop.get("HomeAddres"))){
														response.sendRedirect("http://www.wanwifi.com");
												}else{
														response.sendRedirect((String)BusinesShop.get("HomeAddres"));
												}
										}
								} finally {
										GetOpenIDResponse.close();
								}
						} catch (Exception e) {
		            e.printStackTrace();
		        } finally {
								try{
										HttpClient.close();
								}catch(IOException e){
								
								}
								sqlSession.close();
						}
    		}else{
						Base64 base64 = new Base64();
						RedirectUri = new String(Base64.decodeBase64(RedirectUri));
      			StringBuilder ThiUrl = new StringBuilder(300);
      			ThiUrl.append(RedirectUri);
      			if(!RedirectUri.contains("?")){
      					ThiUrl.append("?");
      			}else if(!RedirectUri.endsWith("&")){
      					ThiUrl.append("&");
      			}
      			ThiUrl.append("Code=");
      			ThiUrl.append(Code);
      			ThiUrl.append("&HardSeq=");
      			ThiUrl.append(HardSeq);
      			ThiUrl.append("&MacAddr=");
      			ThiUrl.append(MacAddr);
      			ThiUrl.append("&ipAddr=");
      			ThiUrl.append(ipAddr);
      			if(!StringToolkit.isNullOrEmpty(state)){
      					ThiUrl.append("&state=");
      					ThiUrl.append(state);
      			}
      			response.sendRedirect(ThiUrl.toString());
    		}
		}
		//手动绑定
		public void ManualBinding(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				request.setCharacterEncoding("UTF-8");
				response.setContentType("text/html; charset=UTF-8");
				
				String JumpAddr = (String)request.getAttribute("JumpAddr");
        String HardSeq = (String)request.getAttribute("HardSeq");
        String MacAddr = (String)request.getAttribute("MacAddr");
        String ipAddr = (String)request.getAttribute("ipAddr");
				
        if(StringToolkit.isNullOrEmpty(HardSeq,MacAddr,ipAddr)){
        		response.getWriter().println("<script type='text/javascript'>window.location.replace('"+JumpAddr+"');</script>");
        		return;
        }
				
				String Captcha = request.getParameter("Captcha");
        if(StringToolkit.isNullOrEmpty(Captcha)){
        		response.getWriter().println("<script type='text/javascript'>alert('验证码为空！');</script>");
        		return;
        }
				String FollKey = BindingMapper.getFollKey(Captcha);
        if(StringToolkit.isNullOrEmpty(Captcha)){
        		response.getWriter().println("<script type='text/javascript'>alert('验证码已过期！');</script>");
        		return;
        }
				SqlSession sqlSession = MyBatisFactory.openSession();
				try {
						Map<String,Object> Followers = sqlSession.selectOne("RouterBatis.FollowersMapper.SelectFollowersID",FollKey);
						
						Followers.put("TerminalMac",MacAddr);
						
						Map<String,Object> WifiRouter = sqlSession.selectOne("RouterBatis.WifiRouterMapper.SelectHardSeq",HardSeq);
						
						Followers.put("WifiRouterID",WifiRouter.get("WifiRouterID"));
						
						BindingTerminal(sqlSession,Followers);
						
						response.getWriter().println("<script type='text/javascript'>alert('绑定成功！');window.location.replace('"+JumpAddr+"');</script>");
				} finally {
					sqlSession.close();
				}
		}
		
		
    //正则获取网页验证标识Token
    private static void BindingTerminal(SqlSession sqlSession,Map<String,Object> Followers) {
				Map<String,Object> WifiTerminal = sqlSession.selectOne("RouterBatis.WifiTerminalMapper.SelectFollowersID",Followers);
				
				if(WifiTerminal == null){
						WifiTerminal = new HashMap<String,Object>();
						WifiTerminal.put("WifiRouterID",Followers.get("WifiRouterID"));
						WifiTerminal.put("FollowersID",Followers.get("FollowersID"));
						WifiTerminal.put("TerminalMac",Followers.get("TerminalMac"));
						WifiTerminal.put("FinalTime",new Date());
						sqlSession.insert("RouterBatis.WifiTerminalMapper.insertTerminal",WifiTerminal);
				}else{
						WifiTerminal.put("WifiRouterID",Followers.get("WifiRouterID"));
						WifiTerminal.put("FollowersID",Followers.get("FollowersID"));
						WifiTerminal.put("FinalTime",new Date());
						sqlSession.update("RouterBatis.WifiTerminalMapper.updateTerminal",WifiTerminal);
				}
				sqlSession.commit();
    }
		//
		/*
			
			http://www.wanwifi.com/?
			AuthAaddr=192.168.10.10%3a8080&
			Type=Unknown&
			TerminalID=90-18-7C-4F-D5-88&
			t=1416452556529&
			deviceid=22201402286AE9189&
			ipaddr=192.168.82.102&
			mac=90-18-7C-4F-D5-88&
			basname=22201402286AE9189&
			basip=192.168.82.2&
			ld=http://xw.qq.com/index.htm
			
		*/
    public static void ParamHandle(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
        //商家或地址 来源
        String JumpAddr = req.getParameter("AppHomePageUrl");
        if (StringToolkit.isNullOrEmpty(JumpAddr)) {
            JumpAddr = "http://www.wanwifi.com";
        } else {
            JumpAddr = URLDecoder.decode(JumpAddr,"UTF-8");
        }
        //
        String Referer = req.getHeader("referer");
        if (StringToolkit.isNullOrEmpty(Referer)) {
            Referer = req.getParameter("referer");
        }
        //路由串号
        String HardSeq = req.getHeader("Router-Seq");
        if (StringToolkit.isNullOrEmpty(HardSeq)){
            HardSeq = req.getParameter("deviceid");
        }
        //终端Mac地址
        String MacAddr = req.getHeader("Local-Mac");
        if (StringToolkit.isNullOrEmpty(MacAddr)){
            MacAddr = req.getParameter("mac");
        }
        //终端IP
        String ipAddr = req.getHeader("Local-Ip");
        if (StringToolkit.isNullOrEmpty(ipAddr)){
            ipAddr = req.getParameter("ipaddr");
        }
        if (!StringToolkit.isNullOrEmpty(MacAddr)){
            MacAddr = MacAddr.replace(":", "-");
        }
        req.setAttribute("JumpAddr",JumpAddr);
        req.setAttribute("Referer", Referer);
        req.setAttribute("HardSeq",HardSeq);
        req.setAttribute("MacAddr",MacAddr);
        req.setAttribute("ipAddr", ipAddr);
    }
    //正则获取网页验证标识Token
    private static String getOpenID(String str) {
        Pattern p = Pattern.compile("\"openid\":\"(.*)\"");
        Matcher m = p.matcher(str);
        String TEM = "";
        while (m.find()) {
            TEM = m.group(1);
        }
        //System.out.println(str+"\r\n\t"+TEM);
        return TEM;
    }
}
