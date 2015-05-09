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
	*ʵ�ֽӿ�:Element
	*/
	//��������
	public String getName(){
		return Name;
	}
	//��������
	public DataType getDataType(){
		return dataType;
	}
	//������������
	public boolean isDirect(){
		return Direct;
	}
	//�����������������Ա����Ա����
	public String getLengthNmae(){
		return LengthNmae;
	}
	//��������
	public int getLength(){
		return Length;
	}
	//�������ֽ���
	public int getByteNumber(){
		return dataType.getByteNumber();
	}
	//����Ĭ��ֵ
	public Number getDefaultValue(){
		return DefaultValue;
	}
	
	/*
	*���෽��
	*/
	public void setName(String name){
		this.Name = name;
	}
	public void setDataType(DataType type){
		this.dataType = type;
	}
	public void setLengthNmae(String lengthName){
		if(lengthName==null || "".equals(lengthName.trim())){
			throw new NullPointerException("LengthNmae����Ϊ��");
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
	*		Լ��
	*		
	*		
	*		��������:getName()							//
	*		��������:getDataType()					//
	*		��������:isDirect()							//�Ƿ�ֱ������,Ĭ��Ϊ��
	*		��������:getLengthNmae()				//���ݸ���,Ĭ��1���޷�������
	*		Ĭ��ֵ:getDefaultValue()				//����Ĭ��ֵ
	*		����:getLength()								//���ݸ���,Ĭ��1���޷�������
	*		����:getByteNumber()						//�ֽ���,Ĭ��0���޷�������
	*		����:getSize()									//��������,Ĭ��0���޷�������
	*		
	*		
	*		
	*		
	*/