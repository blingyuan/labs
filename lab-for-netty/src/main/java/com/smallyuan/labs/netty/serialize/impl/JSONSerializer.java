package com.smallyuan.labs.netty.serialize.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smallyuan.labs.netty.serialize.Serializer;
import com.smallyuan.labs.netty.serialize.SerializerAlgorithm;


public class JSONSerializer implements Serializer {
    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object o) {
        return JSON.toJSONBytes(o);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSONObject.parseObject(bytes,clazz);
    }
}
