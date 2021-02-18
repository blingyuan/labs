package com.smallyuan.labs.concurrency;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * 通过改写 interrupt 方法将非标准的取消操作封装在Thread中
 * java.io包中的同步Socket I/O。对套接字进行读取和写入会阻塞io，虽然InputStream和OutputStream中的read和write等方法都不会响应中断，
 * 可以通过关闭底层的套接字，使得被阻塞的线程抛出异常
 */
public class ReaderThread extends Thread {

    private final Socket socket;
    private final InputStream in;

    public ReaderThread(Socket socket) throws IOException {
        this.socket = socket;
        this.in = socket.getInputStream();
    }

    @Override
    public void interrupt() {
        try {
            socket.close();
        } catch (IOException ignored) {

        } finally {
            super.interrupt();
        }
    }

    @Override
    public void run() {
        try {
            byte[] buf = new byte[1024];
            while (true) {
                int count = in.read(buf);
                if (count < 0) {
                    break;
                } else if (count > 0) {

                }
            }
        } catch (IOException e) {
            // 允许线程退出
        }
        super.run();
    }
}
