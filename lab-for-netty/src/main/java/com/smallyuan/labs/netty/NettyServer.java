package com.smallyuan.labs.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class NettyServer {
    public static void main(String[] args) throws InterruptedException {


        NioEventLoopGroup boss = new NioEventLoopGroup(); // 监听端口，accept新连接的线程组
        NioEventLoopGroup worker = new NioEventLoopGroup(); // 处理连接的线程的读写的线程组
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap(); // 引导服务启动
        serverBootstrap.group(boss,worker) // 设置线程组
                .channel(NioServerSocketChannel.class) // 指定IO模型 （NIO）
//                .option(ChannelOption.SO_BACKLOG, 1024)
                .handler(new SimpleServerHandler())
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {

                    }
                });
//                .childOption(ChannelOption.SO_KEEPALIVE, true)
//                .childOption(ChannelOption.TCP_NODELAY, true)
//                .childHandler(new ChannelInitializer<NioSocketChannel>() { // 连接读写处理逻辑
//                    @Override
//                    protected void initChannel(NioSocketChannel ch) throws Exception {
////                        ch.pipeline().addLast(new FirstServerHandler());
//                        ch.pipeline().addLast(new Spliter());
////                        ch.pipeline().addLast(new PacketDecoder());
//                        ch.pipeline().addLast(PacketCodecHandler.INSTANCE);
//                        ch.pipeline().addLast(LoginRequestHandler.INSTANCE);
//                        ch.pipeline().addLast(AuthHandler.INSTANCE);
//                        ch.pipeline().addLast(MessageRequestHandler.INSTANCE);
//                        ch.pipeline().addLast(CreateGroupRequestHandler.INSTANCE);
//                        ch.pipeline().addLast(JoinGroupRequestHandler.INSTANCE);
//                        ch.pipeline().addLast(QuitGroupRequestHandler.INSTANCE);
//                        ch.pipeline().addLast(ListGroupMembersRequestHandler.INSTANCE);
//                        ch.pipeline().addLast(GroupMessageRequestHandler.INSTANCE);
////                        ch.pipeline().addLast(new PacketEncoder());
//                    }
//                });
//        bind(serverBootstrap,8000);

            ChannelFuture f = serverBootstrap.bind(8000).sync();
            f.channel().closeFuture().sync();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("端口[" + port +"]绑定成功!");
                } else {
                    System.out.println("端口[" + port +"]绑定失败!");
                    bind(serverBootstrap,port+1);
                }
            }
        });
    }

    private static class SimpleServerHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("channelActive");
            ctx.channel().eventLoop().execute(new Runnable() {
                @Override
                public void run() {

                }
            });
        }

        @Override
        public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
            System.out.println("channelRegistered");
        }

        @Override
        public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
            System.out.println("handlerAdded");
        }
    }

}
