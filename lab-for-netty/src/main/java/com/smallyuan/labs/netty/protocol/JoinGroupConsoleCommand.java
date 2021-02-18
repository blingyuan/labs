package com.smallyuan.labs.netty.protocol;

import com.smallyuan.labs.netty.protocol.request.JoinGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class JoinGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("输入 groupId, 加入群聊：");
        String groupId = scanner.nextLine();

        JoinGroupRequestPacket joinGroupRequestPacket = new JoinGroupRequestPacket();
        joinGroupRequestPacket.setGroupId(groupId);

        channel.writeAndFlush(joinGroupRequestPacket);
    }
}
