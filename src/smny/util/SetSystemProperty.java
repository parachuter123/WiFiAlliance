package smny.util;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
public class SetSystemProperty {   
	 public  String getValue(String key) throws IOException{
		 	InputStream inputStream = null;
			try{
				Properties prop = new Properties();
		    	inputStream  = this.getClass().getClassLoader().getResourceAsStream("Config.properties"); 
				prop.load(inputStream);
				String value = (String)prop.get(key);
				//value = new String(value.getBytes("ISO8859-1"), "utf8");
				return value;
			}catch (Exception e) {
				e.printStackTrace();
			}finally{
				inputStream.close();
			}
			return null;
		}
	 public static void main(String[] args) throws IOException {
		 SetSystemProperty ssp = new SetSystemProperty();
		 System.out.println(ssp.getValue("ShowBug"));
	}
}   