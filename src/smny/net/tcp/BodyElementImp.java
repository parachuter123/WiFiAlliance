package smny.net.tcp;
public class BodyElementImp extends ElementImp implements BodyElement{
	private PacketBody packetBody;
	private String BodyName;
	private int ByteNumber;
	public BodyElementImp(){
		
	}
	/*
	*覆盖ElementImp的方法，以实现自己的行为
	*/
	
	//变量类型
	public DataType getDataType(){
		return DataType.PacketBodyType;
	}
	//变量点字节数
	public int getByteNumber(){
		return ByteNumber;
	}
	/*
	*实现接口:BodyElement
	*/
	public String getPacketBodyName(){
		return BodyName;
	}
	public PacketBody getPacketBody(){
		return packetBody;
	}
	/*
	*本类方法
	*/
	public void setPacketBody(PacketBody Body){
		this.packetBody = Body;
	}
	public void setPacketBodyName(String bodyName){
		this.BodyName = bodyName;
	}
	//变量点字节数
	public void addByteNumber(int byteNumber){
		this.ByteNumber += byteNumber;
	}
	public void setByteNumber(int byteNumber){
		this.ByteNumber = byteNumber;
	}
}