package com.smallyuan.labs.netty.handler;

import com.smallyuan.labs.netty.protocol.request.ListGroupMembersRequestPacket;
import com.smallyuan.labs.netty.protocol.response.ListGroupMembersResponsePacket;
import com.smallyuan.labs.netty.session.Session;
import com.smallyuan.labs.netty.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

import java.util.ArrayList;
import java.util.List;

@ChannelHandler.Sharable
public class ListGroupMembersRequestHandler extends SimpleChannelInboundHandler<ListGroupMembersRequestPacket> {

    public static ListGroupMembersRequestHandler INSTANCE = new ListGroupMembersRequestHandler();

    public ListGroupMembersRequestHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersRequestPacket msg) throws Exception {
        // 获取 channelGroup
        String groupId = msg.getGroupId();
        ChannelGroup channels = SessionUtil.getChannelGroup(groupId);

        // 遍历群成员获取Session
        List<Session> sessionList = new ArrayList<>();
        for (Channel channel : channels) {
            Session session = SessionUtil.getSession(channel);
            sessionList.add(session);
        }

        // 响应
        ListGroupMembersResponsePacket listGroupMembersResponsePacket = new ListGroupMembersResponsePacket();
        listGroupMembersResponsePacket.setGroupId(groupId);
        listGroupMembersResponsePacket.setSuccess(true);
        listGroupMembersResponsePacket.setSessionList(sessionList);

        ctx.channel().writeAndFlush(listGroupMembersResponsePacket);
    }
}
