package com.emily.emilyservice.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AIOMultiThreadServer {


    public static void main(String[] args) throws IOException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        AsynchronousChannelGroup asynchronousChannelGroup = AsynchronousChannelGroup.withCachedThreadPool(executorService,1);

        final AsynchronousServerSocketChannel serverSocketChannel = AsynchronousServerSocketChannel
                .open(asynchronousChannelGroup)
                .bind(new InetSocketAddress(9090));
        System.out.println("等待客户端连接...");
        serverSocketChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
            @Override
            public void completed(AsynchronousSocketChannel client, Object attachment) {
                serverSocketChannel.accept(null,this);

                ByteBuffer buffer = ByteBuffer.allocate(1024);
                client.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                    @Override
                    public void completed(Integer result, ByteBuffer attachment) {
                        attachment.flip();
                        System.out.println("接受到客户端的消息："+new String(attachment.array(),0,result));
                        client.write(ByteBuffer.wrap("消息已接收到".getBytes(StandardCharsets.UTF_8)));
                        client.read(attachment,attachment,this);
                    }

                    @Override
                    public void failed(Throwable exc, ByteBuffer attachment) {
                        System.out.println("接受消息处理失败");
                    }
                });
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                System.out.println("连接失败");
            }
        });

        CountDownLatch latch = new CountDownLatch(1);
        latch.await();
    }

}
