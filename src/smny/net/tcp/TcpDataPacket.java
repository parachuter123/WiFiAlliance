package smny.net.tcp;
//Tcp���ݰ�ģ�ͽӿ�
import smny.util.SoftObjectReuse;
public interface TcpDataPacket extends Cloneable,SoftObjectReuse{
	
	String getPacketName();
	void setPacketName(String PacketName);
	PacketStructure getPacketStructure();
	
	PacketBody getPacketBodyStructure();
	
	Object get(String ElementName);
	Object put(String ElementName,Object Value);
	void clear();
	Object clone();
}