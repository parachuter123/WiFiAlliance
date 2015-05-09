package smny.net.tcp;
//Tcp数据包共同体
public interface PacketUnion extends Element{
	Element getUnionKeyElement();
	String getUnionKeyElementName();
	UnionElement getElement(int index);
	int getSize();
}