package com.smallyuan.labs.netty.handler;

import com.smallyuan.labs.netty.protocol.response.LoginResponsePacket;
import com.smallyuan.labs.netty.session.Session;
import com.smallyuan.labs.netty.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    /**
     * 客户端连接成功后调用
     * @param ctx
     * @throws Exception
     */
//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        System.out.println(new Date() + ": 客户端开始登录...");
//
//        // 构建登录对象
//        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
//        loginRequestPacket.setUserId(UUID.randomUUID().toString());
//        loginRequestPacket.setUserName("yuan");
//        loginRequestPacket.setPassword("pwd");

        // 编码  ctx.alloc() 获取的是与当前连接相关的ByteBuf分配器
//        ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(),loginRequestPacket);

        // 写数据 ctx.channel() 获取当前连接（Netty对连接的抽象为Channel）
//        ctx.channel().writeAndFlush(loginRequestPacket);
//    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket) throws Exception {
        String userId = loginResponsePacket.getUserId();
        String userName = loginResponsePacket.getUserName();
        if (loginResponsePacket.isSuccess()) {
//            LoginUtil.markAsLogin(ctx.channel());
            System.out.println("[" + userName + "]登录成功，userId 为: " + loginResponsePacket.getUserId());
            SessionUtil.bindSession(new Session(userId,userName),ctx.channel());
        } else {
            System.out.println(new Date() + ": 客户端登录失败，原因：" + loginResponsePacket.getReason());
        }
        ctx.channel().writeAndFlush(loginResponsePacket);
//        ctx.fireChannelRead(loginResponsePacket);
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("客户端连接被关闭!");
    }
}
