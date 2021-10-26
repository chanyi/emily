package com.emily.emilyservice.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousByteChannel;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

public class AIOServer {

    public static void main(String[] args) throws IOException {
        final AsynchronousServerSocketChannel serverSocketChannel = AsynchronousServerSocketChannel
                .open()
                .bind(new InetSocketAddress(7788));
        System.out.println("服务器已经启动...");
        serverSocketChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
            @Override
            public void completed(AsynchronousSocketChannel client, Object attachment) {
                serverSocketChannel.accept(null,this);
                try {
                    System.out.println("客户端："+client.getRemoteAddress()+"--已经连接");
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    System.out.println("等待接受消息...");
                    client.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                        @Override
                        public void completed(Integer result, ByteBuffer attachment) {
                            System.out.println("客户端发来消息--");
                            attachment.flip();
                            //打印出从客户端读取的消息
                            try {
                                System.out.println("客户端"+client.getRemoteAddress()+"发来消息："
                                        +new String(attachment.array(),0,result));
                            }catch (Exception e){
                                System.out.println("获取客户端地址错误");
                            }
                            client.write(ByteBuffer.wrap("消息已收到".getBytes(StandardCharsets.UTF_8)));
                            //每次读取都会将事件从queue中取出来，所以这里需要重新放进去，以便继续通信，不然只能收到一次客户端的消息
                            client.read(attachment,attachment,this);
                        }

                        @Override
                        public void failed(Throwable exc, ByteBuffer attachment) {
                            System.out.println("处理消息失败");
                        }
                    });
                } catch (IOException e) {
                    System.out.println("出现异常："+e.getMessage());
                }
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                System.out.println("连接失败");
            }
        });
        //让程序执行下去。不要终止
        while (true){
            try {
                CountDownLatch latch = new CountDownLatch(1);
                latch.await();
            }catch (Exception e){
                System.out.println("await exception");
            }
        }
    }
}
