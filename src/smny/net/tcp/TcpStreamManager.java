package smny.net.tcp;
import java.net.Socket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
//Tcp通讯主接口工厂类
public class TcpStreamManager{
    public static TcpStream getTcpStream(Socket socket)throws IOException{
        return getTcpStream(socket.getInputStream(),socket.getOutputStream());
    }
    public static TcpStream getTcpStream(InputStream ConfigInp,OutputStream ConfigOut)throws IOException{
        TcpStreamImp TcpImp = new TcpStreamImp();
        TcpImp.setInputStream(ConfigInp);
        TcpImp.setOutputStream(ConfigOut);
        return TcpImp;
    }
}
