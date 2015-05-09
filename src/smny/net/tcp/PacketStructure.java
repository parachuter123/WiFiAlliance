package smny.net.tcp;
import java.util.Set;
import java.util.Collection;
import java.io.File;
import java.io.InputStream;
import java.io.FileNotFoundException;
//Tcp���ݰ��ṹ�ӿ�
public interface PacketStructure{
	PacketHead getPacketHead();
	PacketBody getPacketBody(int BodyID);
	PacketBody getPacketBody(String PacketName);
	
	int getPacketBodySize();
	
	Set<String> BodyNameSet();
	Collection<PacketBody> BodySet();
	
	
	void InitFile(File ConfigFile)throws FileNotFoundException;
	void InitFile(String Config)throws FileNotFoundException;
	void InitFile(InputStream ConfigInp);
}