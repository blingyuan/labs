package com.smallyuan.labs.netty.protocol;

import com.smallyuan.labs.netty.protocol.request.QuitGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class QuitGroupConsoleCommand  implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("输入 groupId, 退出群聊：");
        String groupId = scanner.nextLine();

        QuitGroupRequestPacket quitGroupRequestPacket = new QuitGroupRequestPacket();
        quitGroupRequestPacket.setGroupId(groupId);

        channel.writeAndFlush(quitGroupRequestPacket);
    }
}
