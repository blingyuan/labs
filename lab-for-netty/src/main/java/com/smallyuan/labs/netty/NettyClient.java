package com.smallyuan.labs.netty;


import com.smallyuan.labs.netty.codec.PacketDecoder;
import com.smallyuan.labs.netty.codec.PacketEncoder;
import com.smallyuan.labs.netty.codec.Spliter;
import com.smallyuan.labs.netty.handler.*;
import com.smallyuan.labs.netty.protocol.ConsoleCommandManager;
import com.smallyuan.labs.netty.protocol.LoginConsoleCommand;
import com.smallyuan.labs.netty.protocol.request.MessageRequestPacket;
import com.smallyuan.labs.netty.util.LoginUtil;
import com.smallyuan.labs.netty.util.SessionUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class NettyClient {
    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();

        NioEventLoopGroup group = new NioEventLoopGroup();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
//                ch.pipeline().addLast(new FirstClientHandler());
                ch.pipeline().addLast(new Spliter());
//                ch.pipeline().addLast(new LifeCyCleTestHandler());
                ch.pipeline().addLast(new PacketDecoder());
                ch.pipeline().addLast(new LoginResponseHandler());
                ch.pipeline().addLast(new MessageResponseHandler());
                ch.pipeline().addLast(new CreateGroupResponseHandler());
                ch.pipeline().addLast(new JoinGroupResponseHandler());
                ch.pipeline().addLast(new QuitGroupResponseHandler());
                ch.pipeline().addLast(new ListGroupMembersResponseHandler());
                ch.pipeline().addLast(new GroupMessageResponseHandler());
                ch.pipeline().addLast(new PacketEncoder());
            }
        });

//        bootstrap.connect("127.0.0.1",1000);
        connect(bootstrap,"127.0.0.1",8000,MAX_RETRY);

    }

    private static final int MAX_RETRY = 5;

    private static void connect(Bootstrap bootstrap,String host,int port, int retry){
        bootstrap.connect(host,port).addListener(future ->{
            if (future.isSuccess()) {
                System.out.println("连接成功！");

                // 启动控制台线程
                Channel channel = ((ChannelFuture) future).channel();

//                startSendMessage(channel);


                startConsoleThread(channel);

            } else if (retry == 0) {
                System.out.println("重试次数已用尽，放弃连接！");
            } else {
                int order = (MAX_RETRY - retry) + 1; // 第几次重连
                int delay = 1 << order; // 本次重连时间间隔
                System.out.println(new Date() + ": 连接失败，第" + order + "次重连...");
                bootstrap.config().group().schedule(() -> connect(bootstrap,host,port,retry-1),delay, TimeUnit.SECONDS);
            }
        });
    }

    private static void startConsoleThread(Channel channel) {
        ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
        Scanner scanner = new Scanner(System.in);

        new Thread(() ->{
            while (!Thread.interrupted()) {
                if (!SessionUtil.hasLogin(channel)) {

                    loginConsoleCommand.exec(scanner,channel);

                } else {

                    consoleCommandManager.exec(scanner, channel);

                }
            }
        }).start();
    }




    private static void startSendMessage(Channel channel) {

        new Thread(()->{
            while (!Thread.interrupted()) {
                if (LoginUtil.hasLogin(channel)) {
                    for (int i = 0; i < 1000; i++) {
                        MessageRequestPacket packet = new MessageRequestPacket();
                        String context = "hello, world!仿写微信 IM 即时通讯系统!!!!!!!!!!!!!!!!!!!!!!!!!!!!";
                        packet.setMessage("index "+(i+1)+" :"+context);

                        channel.writeAndFlush(packet);
                    }
                }
            }
        }).start();

    }
}
