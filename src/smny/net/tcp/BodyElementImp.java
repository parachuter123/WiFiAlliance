package smny.net.tcp;
public class BodyElementImp extends ElementImp implements BodyElement{
	private PacketBody packetBody;
	private String BodyName;
	private int ByteNumber;
	public BodyElementImp(){
		
	}
	/*
	*����ElementImp�ķ�������ʵ���Լ�����Ϊ
	*/
	
	//��������
	public DataType getDataType(){
		return DataType.PacketBodyType;
	}
	//�������ֽ���
	public int getByteNumber(){
		return ByteNumber;
	}
	/*
	*ʵ�ֽӿ�:BodyElement
	*/
	public String getPacketBodyName(){
		return BodyName;
	}
	public PacketBody getPacketBody(){
		return packetBody;
	}
	/*
	*���෽��
	*/
	public void setPacketBody(PacketBody Body){
		this.packetBody = Body;
	}
	public void setPacketBodyName(String bodyName){
		this.BodyName = bodyName;
	}
	//�������ֽ���
	public void addByteNumber(int byteNumber){
		this.ByteNumber += byteNumber;
	}
	public void setByteNumber(int byteNumber){
		this.ByteNumber = byteNumber;
	}
}