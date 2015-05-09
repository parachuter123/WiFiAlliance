package RouterGuide.WeiXin;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * PACKAGE_NAME： webaction
 * <p/>
 * <微信公众平台转接http接口>
 * <p/>
 * 作者：  SatanWang
 * 创建时间： 14-2-12.
 */
@WebServlet(name = "wxapi", urlPatterns = "/wxapi")
public class wxapi extends HttpServlet {

    //微信事件处理驱动
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    		doGet(request,response);
    }

    //验证微信接口的合法性
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String result = "";
        String AppUid = request.getParameter("uid");
        String echostr = request.getParameter("echostr");
        
        String EncryptType = request.getParameter("encrypt_type");
        String MsgSignature = request.getParameter("msg_signature");
        
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String signature = request.getParameter("signature");
        
        try {
		        result = new WechatProcess(AppUid)
		        						.setNonce(nonce)
		        						.setEchoStr(echostr)
		        						.setSignature(signature)
		        						.setTimestamp(timestamp)
		        						.setEncryptType(EncryptType,MsgSignature)
		        						.verifyUrl()
		        						.processWechatMag(readString(request));
        } catch (Exception e) {
            result = e.getMessage();
        }
        response.getWriter().println(result);
    }
    //读取接收信息
    public String readString(HttpServletRequest request) {
        StringBuilder buffer = new StringBuilder(1000);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return buffer.toString();
    }

}
