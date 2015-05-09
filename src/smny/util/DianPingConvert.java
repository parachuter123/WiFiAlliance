package smny.util;

import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class DianPingConvert {
	
	public static double[] DianPingAddressConvert(String poiId) throws Exception{
		//String poiId ="21652095";
        try {
            String content=null;
            HttpUriRequest ForwardHttp = RequestBuilder.get()
                    .setUri(new URI("http://www.dianping.com/shop/"+poiId 

            ))
                    .setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.172 Safari/537.22")
                    .setConfig(RequestConfig.custom()
                               .setSocketTimeout(3000)
                               .setConnectTimeout(3000)
                               .build()
                              )
                    .build();
            CloseableHttpResponse response = HttpClients.createDefault().execute(ForwardHttp);
            try {
                HttpEntity entity = response.getEntity();
                content = EntityUtils.toString(entity);
                EntityUtils.consume(entity);
            } finally {
                response.close();
            }
            if(StringToolkit.isNullOrEmpty(content) || !content.contains("poi")){
            	throw new Exception("无地址戳号指定的商家！！！");
            }
            content=content.replaceAll("\\s|'|\"","").replaceAll(".*poi:(\\w*)\\,.*","$1");
            
            double[] decode = dianPingPoiToLatLng(content);
            return decode;
        } catch (Exception e) {
            throw e;
        }
	}
	public static double[] dianPingPoiToLatLng(String poi) {
		double[] decode = new double[2];
	    int I=-1;
	    int H=0;
	    StringBuilder B = new StringBuilder();
	    int J=poi.length()-1;
	    int G=(int)poi.charAt(J);
	    poi=poi.substring(0,J);
	    for(int E=0;E<J;E++){
	        int D=Character.digit(poi.charAt(E),36)-10;
	        if (D>=10) {
	            D=D-7;
	        }
	        B.append(Integer.toString(D,36));
	        if (D>H) {
	            I=E;
	            H=D;
	        }
	    }
	    int A = Integer.parseInt(B.substring(0, I),16);
	    int F = Integer.parseInt(B.substring(I + 1),16);
	    decode[1] = (A+F-G)/2;
	    decode[0] = (F-decode[1])/100000;
	    decode[1] /= 100000;
			return decode;
	}
}
