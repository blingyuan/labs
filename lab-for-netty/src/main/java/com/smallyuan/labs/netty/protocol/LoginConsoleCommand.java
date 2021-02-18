package com.smallyuan.labs.netty.protocol;

import com.smallyuan.labs.netty.protocol.request.LoginRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class LoginConsoleCommand implements ConsoleCommand {

    LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("输入用户名登录：");

        String userName = scanner.nextLine();

        loginRequestPacket.setUserName(userName);
        loginRequestPacket.setPassword("pwd");

        // 登录
        channel.writeAndFlush(loginRequestPacket);

        // 等待一个登录逻辑最长处理时间
        waitForLoginResponse();
    }

    private static void waitForLoginResponse() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {

        }
    }
}
