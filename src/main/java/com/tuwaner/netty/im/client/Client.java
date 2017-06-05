package com.tuwaner.netty.im.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client {

    public static void main(String[] args) throws InterruptedException, IOException {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        final int port = 8876;
        final String serverIP = "127.0.0.1";
        b.group(group)
                .channel(NioSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                //.option(ChannelOption.SO_BACKLOG, 1024)
                //.remoteAddress(new InetSocketAddress(serverIP, port))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        /*ch.pipeline().addLast(new ObjectDecoder(1024, ClassResolvers
                                .cacheDisabled(this.getClass()
                                        .getClassLoader())));
                        ch.pipeline().addLast(new ObjectEncoder());*/
                        ch.pipeline().addLast(new StringDecoder());
                        ch.pipeline().addLast(new StringEncoder());
                        ch.pipeline().addLast(new ClientHandler());
                    }
                });
        ChannelFuture cf = b.connect(serverIP, port).sync();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("登陆成功！");
        while (true) {
            cf.channel().writeAndFlush(in.readLine());
        }
    }
}
