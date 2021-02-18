package com.smallyuan.labs.netty.protocol.response;

import com.smallyuan.labs.netty.protocol.Packet;
import com.smallyuan.labs.netty.session.Session;

import static com.smallyuan.labs.netty.protocol.Command.GROUP_MESSAGE_REQUEST;

public class GroupMessageResponsePacket extends Packet {

    private String fromGroupId;

    private Session fromUser;

    private String message;

    @Override
    public Byte getCommand() {
        return GROUP_MESSAGE_REQUEST;
    }

    public String getFromGroupId() {
        return fromGroupId;
    }

    public void setFromGroupId(String fromGroupId) {
        this.fromGroupId = fromGroupId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Session getFromUser() {
        return fromUser;
    }

    public void setFromUser(Session fromUser) {
        this.fromUser = fromUser;
    }
}
