package com.smallyuan.labs.netty.handler;

import com.smallyuan.labs.netty.protocol.request.GroupMessageRequestPacket;
import com.smallyuan.labs.netty.protocol.response.GroupMessageResponsePacket;
import com.smallyuan.labs.netty.session.Session;
import com.smallyuan.labs.netty.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

@ChannelHandler.Sharable
public class GroupMessageRequestHandler extends SimpleChannelInboundHandler<GroupMessageRequestPacket> {

    public static GroupMessageRequestHandler INSTANCE = new GroupMessageRequestHandler();

    public GroupMessageRequestHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageRequestPacket groupMessageRequestPacket) throws Exception {

        ChannelGroup channels = SessionUtil.getChannelGroup(groupMessageRequestPacket.getToGroupId());

        GroupMessageResponsePacket groupMessageResponsePacket = new GroupMessageResponsePacket();
        groupMessageResponsePacket.setFromGroupId(groupMessageRequestPacket.getToGroupId());
        Session session = SessionUtil.getSession(ctx.channel());
        groupMessageResponsePacket.setFromUser(session);
        groupMessageResponsePacket.setMessage(groupMessageRequestPacket.getMessage());

        channels.writeAndFlush(groupMessageResponsePacket);
    }
}
