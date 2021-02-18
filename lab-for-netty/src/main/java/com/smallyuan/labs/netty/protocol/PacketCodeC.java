package com.smallyuan.labs.netty.protocol;

import com.smallyuan.labs.netty.protocol.request.*;
import com.smallyuan.labs.netty.protocol.response.*;
import com.smallyuan.labs.netty.serialize.Serializer;
import com.smallyuan.labs.netty.serialize.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;

import static com.smallyuan.labs.netty.protocol.Command.*;

/**
 * 这个是我们制定的一个协议
 * 魔数（4字节） + 版本号（1字节） + 序列化算法（1字节） + 指令（1字节） + 数据长度（4字节） + 数据（变长）
 */
public class PacketCodeC {

    public static final int MAGIC_NUMBER = 0x12345678;
    private static final Map<Byte,Class<? extends Packet>> packetTypeMap;
    private static final Map<Byte,Serializer> serializeMap;

    public static final PacketCodeC INSTANCE = new PacketCodeC();

    static {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(LOGIN_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(MESSAGE_REQUEST, MessageRequestPacket.class);
        packetTypeMap.put(MESSAGE_RESPONSE, MessageResponsePacket.class);
        packetTypeMap.put(LOGOUT_REQUEST, LogoutRequestPacket.class);
        packetTypeMap.put(LOGOUT_RESPONSE, LogoutResponsePacket.class);
        packetTypeMap.put(CREATE_GROUP_REQUEST, CreateGroupRequestPacket.class);
        packetTypeMap.put(CREATE_GROUP_RESPONSE, CreateGroupResponsePacket.class);
        packetTypeMap.put(JOIN_GROUP_REQUEST, JoinGroupRequestPacket.class);
        packetTypeMap.put(JOIN_GROUP_RESPONSE, JoinGroupResponsePacket.class);
        packetTypeMap.put(QUIT_GROUP_REQUEST, QuitGroupRequestPacket.class);
        packetTypeMap.put(QUIT_GROUP_RESPONSE, QuitGroupResponsePacket.class);
        packetTypeMap.put(LIST_GROUP_MEMBERS_REQUEST, ListGroupMembersRequestPacket.class);
        packetTypeMap.put(LIST_GROUP_MEMBERS_RESPONSE, ListGroupMembersResponsePacket.class);
        packetTypeMap.put(GROUP_MESSAGE_REQUEST, GroupMessageRequestPacket.class);
        packetTypeMap.put(GROUP_MESSAGE_RESPONSE, GroupMessageResponsePacket.class);

        serializeMap = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        serializeMap.put(serializer.getSerializerAlgorithm(),serializer);
    }



    public ByteBuf encode(ByteBuf byteBuf,Packet packet) {

        // 序列化java对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        // 编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

    public Packet decode(ByteBuf byteBuf) {
        // 跳过魔术字
        byteBuf.skipBytes(4);
        // 跳过版本号
        byteBuf.skipBytes(1);
        // 序列化算法标识
        byte serializeAlgorithm = byteBuf.readByte();
        // 指令
        byte command = byteBuf.readByte();
        // 数据包长度
        int length = byteBuf.readInt();
        // 数据包
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null){
            return serializer.deserialize(requestType,bytes);
        }

        return null;

    }

    private Serializer getSerializer(byte serializeAlgorithm) {
        return serializeMap.get(serializeAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command) {
        return packetTypeMap.get(command);
    }
}
