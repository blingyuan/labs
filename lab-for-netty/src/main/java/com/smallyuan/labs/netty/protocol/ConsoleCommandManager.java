package com.smallyuan.labs.netty.protocol;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleCommandManager implements ConsoleCommand {

    private Map<String,ConsoleCommand> consoleCommandMap;

    public ConsoleCommandManager() {
        consoleCommandMap = new HashMap<>();
        // 给指定userId 发消息
        consoleCommandMap.put("sendToUser",new SendToUserConsoleCommand());
        // 登出
        consoleCommandMap.put("logout",new LogoutConsoleCommand());
        // 拉群
        consoleCommandMap.put("createGroup",new CreateGroupConsoleCommand());
        // 加入群聊
        consoleCommandMap.put("joinGroup",new JoinGroupConsoleCommand());
        // 退出群聊
        consoleCommandMap.put("quitGroup",new QuitGroupConsoleCommand());
        // 获取成员列表
        consoleCommandMap.put("listGroupMembers",new ListGroupMembersConsoleCommand());
        // 给指定 groupId 发消息
        consoleCommandMap.put("sendToGroup",new SendToGroupConsoleCommand());
    }

    @Override
    public void exec(Scanner scanner, Channel channel) {
        // 获取指令
        String command = scanner.nextLine();

        ConsoleCommand consoleCommand = consoleCommandMap.get(command);

        if (consoleCommand != null) {
            consoleCommand.exec(scanner, channel);
        } else {
            System.err.println("无法识别[" + command + "]指令，请重新输入!");
        }
    }
}
