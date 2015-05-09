package smny.net.tcp;
//Tcp数据包共同体数据元结构
public interface BodyElement extends Element{
	String getPacketBodyName();
	PacketBody getPacketBody();
}