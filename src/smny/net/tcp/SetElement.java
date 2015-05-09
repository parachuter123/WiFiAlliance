package smny.net.tcp;
//Tcp数据元集合
public interface SetElement{
	boolean isExist(String ElementName);
	Element getElement(int index);
	int getSize();
}