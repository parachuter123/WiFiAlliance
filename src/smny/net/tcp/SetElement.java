package smny.net.tcp;
//Tcp����Ԫ����
public interface SetElement{
	boolean isExist(String ElementName);
	Element getElement(int index);
	int getSize();
}