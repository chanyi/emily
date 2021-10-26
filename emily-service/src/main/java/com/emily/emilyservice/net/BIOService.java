package com.emily.emilyservice.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketImpl;

public class BIOService {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(8899));
        byte[] bytes =new byte[1024];
        while (true){
            System.out.println("等待连接...");
            Socket accept = serverSocket.accept();//阻塞方法
            System.out.println("连接成功");
            new Thread(()->{
                try {
                    while (true){
                        int read = accept.getInputStream().read(bytes);
                        System.out.println("线程："+Thread.currentThread().getName()+"--接受到消息-read:"+read);
                        System.out.println("线程："+Thread.currentThread().getName()+"--接受到消息-bytes:"+
                                new String(bytes,0,read));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }


}
