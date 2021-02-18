package com.smallyuan.labs.netty.protocol;

import com.smallyuan.labs.netty.protocol.request.MessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class SendToUserConsoleCommand implements ConsoleCommand {

    MessageRequestPacket messageRequestPacket = new MessageRequestPacket();

    @Override
    public void exec(Scanner scanner, Channel channel) {

        System.out.println("输入消息接收方 ID：");
        String toUserId = scanner.nextLine();
        System.out.println("输入需要发送的消息：");
        String message = scanner.nextLine();
        messageRequestPacket.setToUserId(toUserId);
        messageRequestPacket.setMessage(message);
        channel.writeAndFlush(messageRequestPacket);
    }
}
