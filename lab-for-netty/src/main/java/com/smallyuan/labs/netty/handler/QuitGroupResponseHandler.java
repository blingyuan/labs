package com.smallyuan.labs.netty.handler;

import com.smallyuan.labs.netty.protocol.response.QuitGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 创建群聊的处理
 */
public class QuitGroupResponseHandler extends SimpleChannelInboundHandler<QuitGroupResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupResponsePacket msg) throws Exception {
        if (msg.isSuccess()) {
            System.out.println("退出群[" + msg.getGroupId() + "]成功!");
        } else {
            System.err.println("退出群[" + msg.getGroupId() + "]失败，原因为：" + msg.getReason());
        }
    }
}
