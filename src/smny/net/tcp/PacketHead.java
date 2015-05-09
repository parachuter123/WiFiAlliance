package smny.net.tcp;
//Tcp数据包头接口
public interface PacketHead extends SetElement{
	//数据包长度Element
	String getPacketLenElementName();			//字段名称
	//包体类型Element
	String getBodyTypeElementName();			//字段名称
	//包体类型PacketBodyType
	PacketBodyType getPacketBodyType(int BodyID);						//ID
}