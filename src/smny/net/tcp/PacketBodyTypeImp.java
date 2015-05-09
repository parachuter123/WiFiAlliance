package smny.net.tcp;
public class PacketBodyTypeImp implements PacketBodyType{
	private int BodyID;
	private String Name;
	private String SendBodyName;
	private String ReceiveBodyName;
	private String BodyCallbackName;
	private String BodyRemark;
	public PacketBodyTypeImp(){
		
	}
	/*
	*实现接口:PacketBodyType
	*/
	public int getBodyID(){
		return BodyID;
	}
	public String getName(){
		return Name;
	}
	public String getSendBodyName(){
		return SendBodyName;
	}
	public String getReceiveBodyName(){
		return ReceiveBodyName;
	}
	public String getBodyCallbackName(){
		return BodyCallbackName;
	}
	public String getBodyRemark(){
		return BodyRemark;
	}
	/*
	*本类方法
	*/
	public void setBodyID(int bodyID){
		this.BodyID = bodyID;
	}
	public void setName(String name){
		this.Name = name;
	}
	public void setSendBodyName(String name){
		this.SendBodyName = name;
	}
	public void setReceiveBodyName(String name){
		this.ReceiveBodyName = name;
	}
	public void setBodyCallbackName(String CallbackName){
		this.BodyCallbackName = CallbackName;
	}
	public void setBodyRemark(String bodyRemark){
		this.BodyRemark = bodyRemark;
	}
}