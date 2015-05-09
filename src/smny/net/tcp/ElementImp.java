package smny.net.tcp;
public class ElementImp implements Element{
	private String Name;
	private DataType dataType;
	private boolean Direct = true;
	private String LengthNmae;
	private int Length = 1;
	private int ByteNumber;
	private Number DefaultValue = Integer.valueOf(0);
	public ElementImp(){
		
	}
	/*
	*实现接口:Element
	*/
	//变量名称
	public String getName(){
		return Name;
	}
	//变量类型
	public DataType getDataType(){
		return dataType;
	}
	//变量数量类型
	public boolean isDirect(){
		return Direct;
	}
	//变量数量来自另外成员，成员名称
	public String getLengthNmae(){
		return LengthNmae;
	}
	//变量数量
	public int getLength(){
		return Length;
	}
	//变量点字节数
	public int getByteNumber(){
		return dataType.getByteNumber();
	}
	//变量默认值
	public Number getDefaultValue(){
		return DefaultValue;
	}
	
	/*
	*本类方法
	*/
	public void setName(String name){
		this.Name = name;
	}
	public void setDataType(DataType type){
		this.dataType = type;
	}
	public void setLengthNmae(String lengthName){
		if(lengthName==null || "".equals(lengthName.trim())){
			throw new NullPointerException("LengthNmae不能为空");
		}
		this.LengthNmae = lengthName.trim();
		this.Length = 0;
		this.Direct = false;
	}
	public void setLength(int lengt){
		this.Length = lengt;
		this.LengthNmae = null;
		this.Direct = true;
	}
	public void setDefaultValue(Number defaultValue){
		this.DefaultValue = defaultValue;
	}
}

	/*
	*		约定
	*		
	*		
	*		变量名称:getName()							//
	*		变量类型:getDataType()					//
	*		数量类型:isDirect()							//是否直接数量,默认为真
	*		数量名称:getLengthNmae()				//数据个数,默认1，无符号整数
	*		默认值:getDefaultValue()				//变量默认值
	*		数量:getLength()								//数据个数,默认1，无符号整数
	*		长度:getByteNumber()						//字节数,默认0，无符号整数
	*		容量:getSize()									//容器容量,默认0，无符号整数
	*		
	*		
	*		
	*		
	*/