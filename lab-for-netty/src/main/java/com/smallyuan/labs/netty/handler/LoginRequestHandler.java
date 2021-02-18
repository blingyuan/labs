package com.smallyuan.labs.netty.handler;

import com.smallyuan.labs.netty.protocol.request.LoginRequestPacket;
import com.smallyuan.labs.netty.protocol.response.LoginResponsePacket;
import com.smallyuan.labs.netty.session.Session;
import com.smallyuan.labs.netty.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;
import java.util.UUID;

@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    public static final LoginRequestHandler INSTANCE = new LoginRequestHandler();

    public LoginRequestHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) throws Exception {
        System.out.println(new Date() + ": 客户端开始登录...");

        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(loginRequestPacket.getVersion());
        loginResponsePacket.setUserName(loginRequestPacket.getUserName());
        // 登录校验
        if (valid(loginRequestPacket)) {
            // 校验成功
            String userId = randomUserId();
            loginResponsePacket.setSuccess(true);
            loginResponsePacket.setUserId(userId);

            SessionUtil.bindSession(new Session(userId,loginRequestPacket.getUserName()),ctx.channel());
            System.out.println(new Date() + "[ 用户 ：" + loginRequestPacket.getUserName() + " ] 登录成功！");
        } else {
            // 校验失败
            loginResponsePacket.setSuccess(false);
            loginResponsePacket.setReason("账号密码校验失败！");
            System.out.println(new Date() + "[" + loginRequestPacket.getUserName() + "]登录失败！");
        }

        // 服务端响应 编码
//        ctx.fireChannelRead(loginResponsePacket);
        ctx.channel().writeAndFlush(loginResponsePacket);
    }

    private String randomUserId() {

        return UUID.randomUUID().toString().split("-")[0];
    }

    // 用户断线后取消绑定
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        Channel channel = ctx.channel();

        if(channel.isActive()) {
            ctx.close();
        }
    }
}
