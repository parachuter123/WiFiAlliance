package smny.net.tcp;
//Tcp数据元结构
public interface Element{
	//变量名称
	String getName();
	//变量类型
	DataType getDataType();
	//变量数量类型
	boolean isDirect();
	//变量数量来自另外成员，成员名称
	String getLengthNmae();
	//变量数量
	int getLength();
	//变量默认值
	Number getDefaultValue();
	//变量点字节数
	int getByteNumber();
}