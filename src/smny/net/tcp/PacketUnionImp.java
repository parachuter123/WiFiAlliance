package smny.net.tcp;
import java.util.ArrayList;
public class PacketUnionImp extends ElementImp implements PacketUnion{
	
	private int ByteNumber;
	private String UnionKeyName;
	private Element UnionKeyElement;
	private ArrayList<UnionElement> UnionElementList = new ArrayList<UnionElement>();
	public PacketUnionImp(){
		
	}
	/*
	*实现接口:Element
	*/
	public DataType getDataType(){
		return DataType.UnionType;
	}
	/*
	*实现接口:PacketUnion
	*/
	public String getUnionKeyElementName(){
		return UnionKeyName;
	}
	public Element getUnionKeyElement(){
		return UnionKeyElement;
	}
	public UnionElement getElement(int index){
		return UnionElementList.get(index);
	}
	public int getSize(){
		return UnionElementList.size();
	}
	
	/*
	*本类方法
	*/
	public void addElement(UnionElement element){
		UnionElementList.add(element);
	}
	public void addElement(int index,UnionElement element){
		UnionElementList.add(index,element);
	}
	
	public void setUnionKeyElement(Element element){
		this.UnionKeyElement = element;
	}
	public void setUnionKeyElementName(String KeyName){
		this.UnionKeyName = KeyName;
	}
}