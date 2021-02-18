package com.smallyuan.labs.netty.handler;


import com.smallyuan.labs.netty.protocol.Packet;
import com.smallyuan.labs.netty.protocol.PacketCodeC;
import com.smallyuan.labs.netty.protocol.request.LoginRequestPacket;
import com.smallyuan.labs.netty.protocol.response.LoginResponsePacket;
import com.smallyuan.labs.netty.protocol.response.MessageResponsePacket;
import com.smallyuan.labs.netty.util.LoginUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;
import java.util.UUID;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 客户端连接成功后调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + ": 客户端开始登录...");

        // 构建登录对象
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUserName("yuan");
        loginRequestPacket.setPassword("pwd");

        // 编码  ctx.alloc() 获取的是与当前连接相关的ByteBuf分配器
//        ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(),loginRequestPacket);

        // 写数据 ctx.channel() 获取当前连接（Netty对连接的抽象为Channel）
        ctx.channel().writeAndFlush(loginRequestPacket);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;

        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);

        if (packet instanceof LoginResponsePacket) {
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;

            if (loginResponsePacket.isSuccess()) {
                LoginUtil.markAsLogin(ctx.channel());
                System.out.println(new Date() + ": 客户端登录成功");
            } else {
                System.out.println(new Date() + ": 客户端登录失败，原因：" + loginResponsePacket.getReason());
            }
        } else if (packet instanceof MessageResponsePacket) {
            MessageResponsePacket messageResponsePacket = (MessageResponsePacket) packet;
            System.out.println(new Date() + ": 收到服务端的消息: " + messageResponsePacket.getMessage());
        }
    }


}
