package com.smallyuan.labs.netty.protocol.request;

import com.smallyuan.labs.netty.protocol.Packet;

import static com.smallyuan.labs.netty.protocol.Command.QUIT_GROUP_REQUEST;

public class QuitGroupRequestPacket extends Packet {


    private String groupId;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public Byte getCommand() {
        return QUIT_GROUP_REQUEST;
    }
}
