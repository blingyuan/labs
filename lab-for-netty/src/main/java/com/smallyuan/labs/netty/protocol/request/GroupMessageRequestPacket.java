package com.smallyuan.labs.netty.protocol.request;

import com.smallyuan.labs.netty.protocol.Packet;

import static com.smallyuan.labs.netty.protocol.Command.GROUP_MESSAGE_REQUEST;

public class GroupMessageRequestPacket extends Packet {

    private String toGroupId;

    private String message;

    @Override
    public Byte getCommand() {
        return GROUP_MESSAGE_REQUEST;
    }


    public String getToGroupId() {
        return toGroupId;
    }

    public void setToGroupId(String toGroupId) {
        this.toGroupId = toGroupId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
