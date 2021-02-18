package com.smallyuan.labs.netty.handler;

import com.smallyuan.labs.netty.protocol.request.JoinGroupRequestPacket;
import com.smallyuan.labs.netty.protocol.response.JoinGroupResponsePacket;
import com.smallyuan.labs.netty.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * 创建群聊的处理
 */
@ChannelHandler.Sharable
public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket> {

    public static JoinGroupRequestHandler INSTANCE = new JoinGroupRequestHandler();

    public JoinGroupRequestHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupRequestPacket msg) throws Exception {
        // 获取 groupId
        String groupId = msg.getGroupId();

        // 获取对应的GroupChannel， 将此channel 加入进去
        ChannelGroup channels = SessionUtil.getChannelGroup(groupId);
        channels.add(ctx.channel());

        // 加入群聊结果的响应
        JoinGroupResponsePacket joinGroupResponsePacket = new JoinGroupResponsePacket();
        joinGroupResponsePacket.setSuccess(true);
        joinGroupResponsePacket.setGroupId(groupId);

        // 给群聊成员发送进群消息
        ctx.channel().writeAndFlush(joinGroupResponsePacket);
    }
}
