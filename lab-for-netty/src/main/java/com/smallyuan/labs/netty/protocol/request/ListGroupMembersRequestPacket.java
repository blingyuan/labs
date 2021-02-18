package com.smallyuan.labs.netty.protocol.request;

import com.smallyuan.labs.netty.protocol.Packet;

import static com.smallyuan.labs.netty.protocol.Command.LIST_GROUP_MEMBERS_REQUEST;

public class ListGroupMembersRequestPacket extends Packet {

    private String groupId;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public Byte getCommand() {
        return LIST_GROUP_MEMBERS_REQUEST;
    }
}