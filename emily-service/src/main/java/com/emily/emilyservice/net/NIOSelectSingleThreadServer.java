package com.emily.emilyservice.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Set;

//使用单线程实现select
public class NIOSelectSingleThreadServer {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(9988));
        serverSocketChannel.configureBlocking(false);//设置为非阻塞的
        System.out.println("开始监听客户端...");
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            selector.select();//阻塞方法，进行轮询，如果没有连接请求或者需要读取的请求，则会一直阻塞在这里
            Set<SelectionKey> keys = selector.selectedKeys();//轮询完成之后得到所有有标记的请求
            System.out.println("轮询得到的key集合长度为：" + keys.size());
            for (SelectionKey selectionKey : keys) {
                //处理完成之后，将对应的有标记key的socket从集合中删除,下次再轮询获取
                keys.remove(selectionKey);
                //对不同的key做不同的处理，key的可能情况有：accept read write connect
                handle(selectionKey);

            }
        }

    }

    static void handle(SelectionKey selectionKey) {
        //如果key是accept，表示是一个连接的请求
        if (selectionKey.isAcceptable()) {
            System.out.println("当前socket是连接请求...");
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
            SocketChannel socketChannel = null;
            try {
                socketChannel = serverSocketChannel.accept();
                System.out.println("接受连接");
                //设置为非阻塞
                socketChannel.configureBlocking(false);
                //设置当前的请求为read，因为已经连接上，所以下次肯定是要发送消息，所以服务端需要read发过来的信息
                socketChannel.register(selectionKey.selector(), SelectionKey.OP_READ);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        //如果key是read，表示是一个写的请求，需要多写的内容进行读取
        if (selectionKey.isReadable()) {
            System.out.println("处理线程:" + Thread.currentThread().getName() + "--当前请求为read请求...");
            SocketChannel socketChannel = null;
            //拿到对应的socket
            socketChannel = (SocketChannel) selectionKey.channel();
            //读取socket中发送来的数据
            ByteBuffer buffer = ByteBuffer.allocate(512);
            buffer.clear();
            int len = 0;
            try {
                len = socketChannel.read(buffer);
                //打印出socket中发送过来的信息
                if (len != -1) {
                    System.out.println("接受到的消息：" + new String(buffer.array(), 0, len));
                }
                //写消息给客户端，说明已经接受到消息
                ByteBuffer byteBufferWrite = ByteBuffer.wrap("你好客户端，请求已经收到！".getBytes(StandardCharsets.UTF_8));
                socketChannel.write(byteBufferWrite);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
            }
        }
    }
}
