package smny.net.tcp;
import java.io.File;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Attribute;
import java.util.Iterator;
import java.util.HashMap;
public class TcpDataPacketImp extends HashMap<String,Object> implements TcpDataPacket{
	
	
	
	private final static PacketStructure packetStructure = new PacketStructureImp();
	private static void init(){
      try{
      		File FileName = new File(TcpDataPacketImp.class.getResource("TcpProtocol.xml").toURI());
      		packetStructure.InitFile(FileName);
      }catch (Exception e){
         throw new AssertionError(e);
      }
	}
	static{
		init();
	}
	
	private String PacketName;
	public TcpDataPacketImp(){
		super();
	}
	/*
	*ʵ�ֽӿ�:TcpDataPacket
	*/
	public String getPacketName(){
		return PacketName;
	}
	public void setPacketName(String packetName){
		this.PacketName = packetName;
	}
	public Object get(String ElementName){
		return super.get(ElementName);
	}
	public Object put(String ElementName,Object Value){
		return super.put(ElementName,Value);
	}
	public PacketStructure getPacketStructure(){
		return packetStructure;
	}
	public PacketBody getPacketBodyStructure(){
		return packetStructure.getPacketBody(PacketName);
	}
	
	/*
	*���ط���
	*/
	
	public void clear(){
		this.PacketName = null;
		super.clear();
	}
	public static void main(String args[]){
		
		TcpDataPacketImp Packet = new TcpDataPacketImp();
		
		
		
			PacketStructure packetStructure = Packet.getPacketStructure();
			PacketHead packetHead = packetStructure.getPacketHead();
			
			//ȷ��������
			String TypeElementName = packetHead.getBodyTypeElementName();
			Object obj = Packet.get(TypeElementName);
			if(obj == null){
					throw new NullPointerException("���ݰ���������Ϊ��");
			}
			if(!(obj instanceof Number)){
					throw new NumberFormatException("���ݰ��������ݲ�������");
			}
	}
	
}
