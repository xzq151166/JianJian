package test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientNode {
    public static void main(String[] args) throws IOException {
        //利用socket来传输，所以首先要new一个socket
        Socket socket = new Socket("localhost", 8888);
        OutputStream os = socket.getOutputStream();
        //获取输出流，向服务器端发送登陆的信息
        BufferedWriter bw = new BufferedWriter(new PrintWriter(os));
        bw.write("用户名：密码");
        bw.flush();
        socket.shutdownOutput();
        bw.close();
    }
}
