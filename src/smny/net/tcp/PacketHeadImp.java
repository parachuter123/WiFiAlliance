package smny.net.tcp;
import java.util.HashMap;
import java.util.ArrayList;
public class PacketHeadImp extends ArrayList<Element> implements PacketHead{
	private Element PacketLenElement;
	private Element BodyTypeElement;
	private HashMap<Integer,PacketBodyType> BodyTypeMap = new HashMap<Integer,PacketBodyType>();
	public PacketHeadImp(){
		
	}
	
	/*
	*ʵ�ֽӿ�:SetElement
	*/
	public boolean isExist(String ElementName){
		for(Element ele:this){
			if(ElementName.equals(ele.getName())){
				return true;
			}
		}
		return false;
	}
	public Element getElement(int index){
		return super.get(index);
	}
	public int getSize(){
		return super.size();
	}
	/*
	*ʵ�ֽӿ�:PacketHead
	*/
	//���ݰ�����Element
	public String getPacketLenElementName(){
		return PacketLenElement.getName();
	}
	public Element getPacketLenElement(){
		return PacketLenElement;
	}
	//��������Element
	public String getBodyTypeElementName(){
		return BodyTypeElement.getName();
	}
	public Element getBodyTypeElement(){
		return BodyTypeElement;
	}
	//��������PacketBodyType
	public PacketBodyType getPacketBodyType(int BodyID){
		return BodyTypeMap.get(BodyID);
	}
	
	/*
	*���෽��
	*/
	public void addElement(Element element){
		super.add(element);
	}
	public void addElement(int index,Element element){
		super.add(index,element);
	}
	public void setPacketLenElement(Element element){
		this.PacketLenElement = element;
	}
	public void setBodyTypeElement(Element element){
		this.BodyTypeElement = element;
	}
	public void putBodyType(PacketBodyType BodyType){
		this.BodyTypeMap.put(BodyType.getBodyID(),BodyType);
	}
	
}