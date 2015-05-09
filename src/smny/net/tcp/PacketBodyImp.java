package smny.net.tcp;
import java.util.ArrayList;
public class PacketBodyImp extends ArrayList<Element> implements PacketBody{
	
	private String BodyName;
	public PacketBodyImp(String bodyName){
		this.BodyName = bodyName;
	}
	
	/*
	*ʵ�ֽӿ�:PacketBody
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
	*ʵ�ֽӿ�:PacketBody
	*/
	public String getBodyName(){
		return BodyName;
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
	
}