package com.smallyuan.labs.netty.handler;

import com.smallyuan.labs.netty.protocol.Packet;
import com.smallyuan.labs.netty.protocol.PacketCodeC;
import com.smallyuan.labs.netty.protocol.request.LoginRequestPacket;
import com.smallyuan.labs.netty.protocol.request.MessageRequestPacket;
import com.smallyuan.labs.netty.protocol.response.LoginResponsePacket;
import com.smallyuan.labs.netty.protocol.response.MessageResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 获取到客户端数据
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(new Date() + ": 客户端开始登录...");

        ByteBuf requestByteBuf = (ByteBuf) msg;

        // 解码
        Packet packet = PacketCodeC.INSTANCE.decode(requestByteBuf);

        // 判断是否是登录请求数据包
        if (packet instanceof LoginRequestPacket) {
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;
            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            loginResponsePacket.setVersion(loginRequestPacket.getVersion());
            // 登录校验
            if (valid(loginRequestPacket)) {
                // 校验成功
                loginResponsePacket.setSuccess(true);
                System.out.println(new Date() + ": 客户端登录成功！");
            } else {
                // 校验失败
                loginResponsePacket.setSuccess(false);
                loginResponsePacket.setReason("账号密码校验失败！");
            }

            // 服务端响应 编码
//            ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(),loginResponsePacket);
            ctx.channel().writeAndFlush(loginResponsePacket);
        } else if (packet instanceof MessageRequestPacket) {
            MessageRequestPacket messageRequestPacket = (MessageRequestPacket) packet;
            System.out.println(new Date() + ": 收到客户端消息：" + messageRequestPacket.getMessage());

            MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
            messageResponsePacket.setMessage("服务端回复【" + messageRequestPacket.getMessage() + "】");
//            ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(),messageResponsePacket);
            ctx.channel().writeAndFlush(messageResponsePacket);
        }
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
