package smny.net.tcp;
import java.io.IOException;
import java.io.EOFException;
//TcpͨѶ���ӿ�
public interface TcpStream{
	void ParsingTcpDataPacket(TcpDataPacket Packet)throws EOFException,IOException;
	void Send(TcpDataPacket Achieve)throws IOException;
}