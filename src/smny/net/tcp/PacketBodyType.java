package smny.net.tcp;
//Tcp���ݰ�������
public interface PacketBodyType{
	int getBodyID();
	String getName();
	String getSendBodyName();
	String getReceiveBodyName();
	String getBodyCallbackName();
	String getBodyRemark();
	
}