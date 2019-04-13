package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerNode {
    public static void main(String[] args) throws IOException {
        //创建服务端socket,并且绑定端口号
        ServerSocket serverSocket = new ServerSocket(8888);

        System.out.println("服务端socket开始启动.");

        //调用accept方法开始监听，等待客户端连接.accept方法返回socket实例
        Socket so = serverSocket.accept();
        //获取一个输入流，并且读取客户端信息.socket使用的是字节输入流，所以可以把它做成字符输入流,再包上一层bufferReader提高效率
        BufferedReader bisr = new BufferedReader(new InputStreamReader(so.getInputStream()));
        String info = null;
        while ((info=bisr.readLine())!=null){
            //服务端打印出来
            System.out.println("我是服务器，客户端输入的是："+info);
        }
        //关闭输入流
        so.shutdownInput();
        //关闭流
        bisr.close();
        //关闭socket
        serverSocket.close();
    }
}
