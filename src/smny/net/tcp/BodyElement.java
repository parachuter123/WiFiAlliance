package smny.net.tcp;
//Tcp���ݰ���ͬ������Ԫ�ṹ
public interface BodyElement extends Element{
	String getPacketBodyName();
	PacketBody getPacketBody();
}