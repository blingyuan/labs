package com.smallyuan.labs.netty.handler;

import com.smallyuan.labs.netty.protocol.request.CreateGroupRequestPacket;
import com.smallyuan.labs.netty.protocol.response.CreateGroupResponsePacket;
import com.smallyuan.labs.netty.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 创建群聊的处理
 */
@ChannelHandler.Sharable
public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {

    public static CreateGroupRequestHandler INSTANCE = new CreateGroupRequestHandler();

    public CreateGroupRequestHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket msg) throws Exception {
        List<String> userIdList = msg.getUserIdList();

        List<String> userNameList = new ArrayList<>(userIdList.size());

        // 创建一个 channel 分组
        ChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());
        // 获取将群聊成员的 userName 和 channel
        for (String userId : userIdList) {
            Channel channel = SessionUtil.getChannel(userId);
            if (channel != null) {
                channelGroup.add(channel);
                userNameList.add(SessionUtil.getSession(channel).getUserName());
            }
        }

        // 创建群聊创建结果的响应
        CreateGroupResponsePacket createGroupResponsePacket = new CreateGroupResponsePacket();
        createGroupResponsePacket.setSuccess(true);
        createGroupResponsePacket.setUserNameList(userNameList);
        String groupId = UUID.randomUUID().toString().split("-")[0];
        createGroupResponsePacket.setGroupId(groupId);

        SessionUtil.addChannelGroup(groupId,channelGroup);

        // 给群聊成员发送进群消息
        channelGroup.writeAndFlush(createGroupResponsePacket);



        System.out.print("群创建成功，id 为[" + createGroupResponsePacket.getGroupId() + "], ");
        System.out.println("群里面有：" + createGroupResponsePacket.getUserNameList());
    }
}
