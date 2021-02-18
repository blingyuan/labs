package com.smallyuan.labs.netty.handler;

import com.smallyuan.labs.netty.protocol.request.MessageRequestPacket;
import com.smallyuan.labs.netty.protocol.response.MessageResponsePacket;
import com.smallyuan.labs.netty.session.Session;
import com.smallyuan.labs.netty.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


@ChannelHandler.Sharable
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    public static MessageRequestHandler INSTANCE = new MessageRequestHandler();

    public MessageRequestHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) throws Exception {

        Session session = SessionUtil.getSession(ctx.channel());

        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUserName(session.getUserName());
        messageResponsePacket.setMessage(messageRequestPacket.getMessage());

        // 获取消息接收方的channel
        Channel toUserChannel = SessionUtil.getChannel(messageRequestPacket.getToUserId());

        System.out.printf("收到 [ %s ] 发送给 [ %s ] 的消息：%s \n",session.getUserId(),
                 messageRequestPacket.getToUserId(),messageRequestPacket.getMessage());

        // 将消息发送给消息接收方
        if (toUserChannel != null && SessionUtil.hasLogin(toUserChannel)) {
            toUserChannel.writeAndFlush(messageResponsePacket);
        } else {
            System.err.println("[" + messageRequestPacket.getToUserId() + "] 不在线，发送失败!");
        }

    }
}
