package com.smallyuan.labs.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

public class FirstClientHandler extends ChannelInboundHandlerAdapter {
    // 客户端连接建立成功之后被调用
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + ":客户端写出数据！");
        for (int i = 0; i < 1000; i++) {
            // 获取数据
            ByteBuf buffer = getByteBuf(ctx);
            // 写数据
            ctx.channel().writeAndFlush(buffer);
        }
//        // 获取数据
//        ByteBuf buffer = getByteBuf(ctx);
//        // 写数据
//        ctx.channel().writeAndFlush(buffer);
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        // 获取二进制抽象 ByteBuf
        ByteBuf buffer = ctx.alloc().buffer();
        String context = "hello, world!仿写微信 IM 即时通讯系统!!!!!!!!!!!!!!!!!!!!!!!!!!!!";
        // 准备写入的数据
        byte[] bytes = context.getBytes(Charset.forName("utf-8"));
        // 填充数据到ByteBuf
        buffer.writeBytes(bytes);
        return buffer;

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf buffer = (ByteBuf) msg;
        System.out.println(new Date() + ": 客户端读到数据 -> " + buffer.toString(Charset.forName("utf-8")));

    }
}
