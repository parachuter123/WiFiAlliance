package smny.net.tcp;
//Tcp���ݰ���ͬ��
public interface PacketUnion extends Element{
	Element getUnionKeyElement();
	String getUnionKeyElementName();
	UnionElement getElement(int index);
	int getSize();
}