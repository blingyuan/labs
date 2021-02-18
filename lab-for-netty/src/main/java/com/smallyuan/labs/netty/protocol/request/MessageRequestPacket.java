package com.smallyuan.labs.netty.protocol.request;

import com.smallyuan.labs.netty.protocol.Packet;

import static com.smallyuan.labs.netty.protocol.Command.MESSAGE_REQUEST;

public class MessageRequestPacket extends Packet {

    private String toUserId;

    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
