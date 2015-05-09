package smny.net.tcp;
import java.util.HashMap;
import java.util.Set;
import java.util.Collection;
import java.io.File;
import java.io.InputStream;
import java.io.FileNotFoundException;
import smny.util.FileToolkit;

public class PacketStructureImp extends HashMap<String,PacketBody> implements PacketStructure{
	
	private PacketHead packetHead;
	public PacketStructureImp(){
		super();
	}
	
	/*
	*实现接口:PacketStructure
	*/
	public PacketHead getPacketHead(){
		return packetHead;
	}
	public PacketBody getPacketBody(int BodyID){
		return this.getPacketBody(packetHead.getPacketBodyType(BodyID).getName());
	}
	public PacketBody getPacketBody(String PacketName){
		return super.get(PacketName);
	}
	public int getPacketBodySize(){
		return super.size();
	}
	public Set<String> BodyNameSet(){
		return super.keySet();
	}
	public Collection<PacketBody> BodySet(){
		return super.values();
	}
	public void InitFile(String FileName)throws FileNotFoundException{
			InitFile(FileToolkit.getConfigFile(FileName));
	}
	public void InitFile(File ConfigFile)throws FileNotFoundException{
  		if(ConfigFile==null){
  				throw new FileNotFoundException("Tcp配置文件对象为空！");
  		}
  		//测试此抽象路径名表示的文件或目录是否存在。
  		if(!ConfigFile.exists()){
  				throw new FileNotFoundException("Tcp配置文件:"+ConfigFile+" 不存在！");
  		}
  		//测试此抽象路径名表示的文件是否是一个标准文件。
  		if(!ConfigFile.isFile()){
  				throw new FileNotFoundException("Tcp配置文件:"+ConfigFile+" 对象表示的不是一个标准文件！");
  		}
  		//测试应用程序是否可以读取此抽象路径名表示的文件。
  		if(!ConfigFile.canRead()){
  				throw new FileNotFoundException("Tcp配置文件:"+ConfigFile+" 无法读取！");
  		}
	    //java.io.File file=new java.io.File("smny/net/tcp/TcpProtocol.xml");  
	    InitFile(new java.io.FileInputStream(ConfigFile));
	}
	public void InitFile(InputStream ConfigInp){
			try {
			   org.dom4j.Document document = new org.dom4j.io.SAXReader().read(ConfigInp);
		     Init(document);
			} catch (org.dom4j.DocumentException e) {
			   throw new RuntimeException("配置文件错误",e);
			} catch (RuntimeException e) {
			   throw e;
			} catch (Exception e) {
			   throw new RuntimeException(e);
			}
	}
	public void InitConfig(String Config){
			try {
		      org.dom4j.Document document = org.dom4j.DocumentHelper.parseText(Config);
		      Init(document);
			} catch (org.dom4j.DocumentException e) {
			   throw new RuntimeException("配置文件错误",e);
			} catch (RuntimeException e) {
			   throw e;
			} catch (Exception e) {
			   throw new RuntimeException(e);
			}
	}
	public void Init(org.dom4j.Document document){
      ParsingXml Xml = new ParsingXml(this);
      if(!Xml.ToParse(document)){
      		super.clear();
      }
	}
	
	
	/*
	*本地方法:
	*/
	public void setPacketHead(PacketHead Head){
			this.packetHead = Head;
	}
}