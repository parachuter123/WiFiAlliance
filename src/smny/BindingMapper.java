package smny;

import org.apache.ibatis.session.SqlSession;  
import java.util.Date;
import java.util.HashMap;
//关注者
public class BindingMapper{
		
		public static class Binding{
				private String FollKey;
				private String Captcha;
				private Date CaptchaTime;
				
		    public String getFollKey() {
		        return FollKey;
		    }
		    public void setFollKey(String FollKey) {
		        this.FollKey = FollKey;
		    }
		    public String getCaptcha() {
		        return Captcha;
		    }
		    public void setCaptcha(String Captcha) {
		        this.Captcha = Captcha;
		    }
		    public Date getCaptchaTime() {
		        return CaptchaTime;
		    }
		    public void setCaptchaTime() {
		        CaptchaTime = new Date();
		    }
		}
		
		
		

    private static HashMap<String, Binding> FollCaptcha = new HashMap<String, Binding>();


    public static String getCaptcha(String FollKey) {
        synchronized (FollCaptcha) {
            int count = 0;
            int srv_seq = 0;
            Binding Foll = null;
            while (count < 8999) {
                srv_seq = (int) Math.round(Math.random() * (9999 - 1000) + 1000);
                String Captcha = String.format("%1$04d", srv_seq);
                if (FollCaptcha.containsKey(Captcha)) {
                    Foll = FollCaptcha.remove(Captcha);
                    if (Foll.getFollKey().equals(FollKey) || Foll.getCaptchaTime().getTime() + 600000 > System.currentTimeMillis()) {
                        Foll.setCaptchaTime();
                        Foll.setCaptcha(Captcha);
                        Foll.setFollKey(FollKey);
                        FollCaptcha.put(Captcha, Foll);
                        return Captcha;
                    }
                } else {
                    Foll = new Binding();
                    Foll.setCaptchaTime();
                    Foll.setCaptcha(Captcha);
                    Foll.setFollKey(FollKey);
                    FollCaptcha.put(Captcha, Foll);
                    return Captcha;
                }
                count++;
            }
        }
        throw new RuntimeException("服务器忙，请稍后再试！！");
    }

    

    public static String getFollKey(String Captcha) {
        Binding Foll = null;
        synchronized (FollCaptcha) {
            Foll = FollCaptcha.remove(Captcha);
        }
        if (Foll != null && Foll.getCaptchaTime().getTime() + 600000 < System.currentTimeMillis()) {
            return Foll.getFollKey();
        }
        return null;
    }
    
    
    
    
    
    
    
    
    
    
    
}