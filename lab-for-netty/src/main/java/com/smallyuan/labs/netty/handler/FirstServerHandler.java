package com.smallyuan.labs.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

public class FirstServerHandler extends ChannelInboundHandlerAdapter {

    // 客户端连接成功后，服务端主动发送消息过去。
//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        System.out.println(new Date() + ":服务端推送数据！");
//        // 获取数据
//        ByteBuf buffer = getByteBuf(ctx,"welcome...");
//        // 写数据
//        ctx.channel().writeAndFlush(buffer);
//    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf buffer = (ByteBuf) msg;
        System.out.println(new Date() + ": 服务端读到数据 -> " + buffer.toString(Charset.forName("utf-8")));

        System.out.println(new Date() + ": 服务端写出数据 ");
//        ByteBuf out = getByteBuf(ctx,"hello,too!!!");
//
//        ctx.channel().writeAndFlush(out);

    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx,String context) {
        // 获取二进制抽象 ByteBuf
        ByteBuf buffer = ctx.alloc().buffer();
        // 准备写入的数据
        byte[] bytes = context.getBytes(Charset.forName("utf-8"));
        // 填充数据到ByteBuf
        buffer.writeBytes(bytes);
        return buffer;

    }
}
