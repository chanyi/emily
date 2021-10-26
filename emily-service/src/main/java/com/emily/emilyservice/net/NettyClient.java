package com.emily.emilyservice.net;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class NettyClient {
    public static void main(String[] args) {
        new NettyClient().start();
    }
    private void start(){
        EventLoopGroup clientWorks = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(clientWorks)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        System.out.println("初始化通道...");
                        socketChannel.pipeline().addLast(new ClientHandler());
                    }
                });

        try {
            System.out.println("连接成功");
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1",9999).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler extends ChannelInboundHandlerAdapter{
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("通道已经建立...");
        final ChannelFuture channelFuture = ctx.writeAndFlush(Unpooled.copiedBuffer("你好".getBytes()));
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                System.out.println("消息发送成功");
            }
        });
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //读取服务器发回的消息
        try {
            ByteBuf byteBuf = (ByteBuf) msg;
            System.out.println("服务端发来消息："+byteBuf.toString(CharsetUtil.UTF_8));
        }finally {
            ReferenceCountUtil.release(msg);
        }


    }
}
