package com.smallyuan.labs.netty.protocol.request;

import com.smallyuan.labs.netty.protocol.Packet;

import java.util.ArrayList;
import java.util.List;

import static com.smallyuan.labs.netty.protocol.Command.CREATE_GROUP_REQUEST;

public class CreateGroupRequestPacket extends Packet {

    private List<String> userIdList = new ArrayList<>();

    @Override
    public Byte getCommand() {
        return CREATE_GROUP_REQUEST;
    }

    public List<String> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<String> userIdList) {
        this.userIdList = userIdList;
    }
}
