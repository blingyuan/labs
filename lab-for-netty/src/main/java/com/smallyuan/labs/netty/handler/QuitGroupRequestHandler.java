package com.smallyuan.labs.netty.handler;

import com.smallyuan.labs.netty.protocol.request.QuitGroupRequestPacket;
import com.smallyuan.labs.netty.protocol.response.QuitGroupResponsePacket;
import com.smallyuan.labs.netty.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * 退出群聊的处理
 */
@ChannelHandler.Sharable
public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {

    public static QuitGroupRequestHandler INSTANCE = new QuitGroupRequestHandler();

    public QuitGroupRequestHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupRequestPacket msg) throws Exception {
        // 获取群聊id 和 对应的 groupChannel
        String groupId = msg.getGroupId();
        ChannelGroup channels = SessionUtil.getChannelGroup(groupId);
        channels.remove(ctx.channel());
        // 退群响应

        QuitGroupResponsePacket quitGroupResponsePacket = new QuitGroupResponsePacket();

        quitGroupResponsePacket.setGroupId(groupId);
        quitGroupResponsePacket.setSuccess(true);

        ctx.channel().writeAndFlush(quitGroupResponsePacket);
    }
}
