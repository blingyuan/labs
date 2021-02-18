package com.smallyuan.labs.netty.protocol;

import com.smallyuan.labs.netty.protocol.request.GroupMessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class SendToGroupConsoleCommand implements ConsoleCommand {

    GroupMessageRequestPacket groupMessageRequestPacket = new GroupMessageRequestPacket();

    @Override
    public void exec(Scanner scanner, Channel channel) {

        System.out.println("输入groupId：");
        String toGroupId = scanner.nextLine();
        System.out.println("输入需要发送的消息：");
        String message = scanner.nextLine();
        groupMessageRequestPacket.setToGroupId(toGroupId);
        groupMessageRequestPacket.setMessage(message);
        channel.writeAndFlush(groupMessageRequestPacket);
    }
}
