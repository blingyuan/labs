package com.smallyuan.labs.netty.protocol.response;

import com.smallyuan.labs.netty.protocol.Packet;

import java.util.ArrayList;
import java.util.List;

import static com.smallyuan.labs.netty.protocol.Command.CREATE_GROUP_RESPONSE;

public class CreateGroupResponsePacket extends Packet {

    private List<String> userNameList = new ArrayList<>();

    private String groupId;

    private boolean success;

    @Override
    public Byte getCommand() {
        return CREATE_GROUP_RESPONSE;
    }

    public List<String> getUserNameList() {
        return userNameList;
    }

    public void setUserNameList(List<String> userNameList) {
        this.userNameList = userNameList;
    }


    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
