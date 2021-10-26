package com.emily.emilyservice.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class AIOClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("127.0.0.1",7788));
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入内容...");
        while(true){
            String text = scanner.next();
            System.out.println("客户端发送消息："+text);
            socket.getOutputStream().write(text.getBytes(StandardCharsets.UTF_8));;
        }
    }
}
