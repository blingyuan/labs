package com.smallyuan.labs.netty.protocol;

import com.smallyuan.labs.netty.protocol.request.ListGroupMembersRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class ListGroupMembersConsoleCommand  implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("输入 groupId, 获取群聊成员列表：");
        String groupId = scanner.nextLine();

        ListGroupMembersRequestPacket listGroupMembersRequestPacket = new ListGroupMembersRequestPacket();
        listGroupMembersRequestPacket.setGroupId(groupId);

        channel.writeAndFlush(listGroupMembersRequestPacket);
    }
}