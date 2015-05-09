package smny.net.tcp;
//Tcp数据包体类型
public interface PacketBodyType{
	int getBodyID();
	String getName();
	String getSendBodyName();
	String getReceiveBodyName();
	String getBodyCallbackName();
	String getBodyRemark();
	
}