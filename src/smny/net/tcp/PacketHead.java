package smny.net.tcp;
//Tcp���ݰ�ͷ�ӿ�
public interface PacketHead extends SetElement{
	//���ݰ�����Element
	String getPacketLenElementName();			//�ֶ�����
	//��������Element
	String getBodyTypeElementName();			//�ֶ�����
	//��������PacketBodyType
	PacketBodyType getPacketBodyType(int BodyID);						//ID
}