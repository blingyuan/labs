package com.smallyuan.labs.netty.serialize;

import com.smallyuan.labs.netty.serialize.impl.JSONSerializer;

public interface Serializer {

    byte JSON_SERIALIZER = 1;
    /**
     * 默认序列化
     */
    Serializer DEFAULT = new JSONSerializer();

    /**
     * 序列化算法
     * @return
     */
    byte getSerializerAlgorithm();

    /**
     * java 对象转换成二进制
     * @param o
     * @return
     */
    byte[] serialize(Object o);

    /**
     * 二进制转换为 java 对象
     * @param <T>
     * @return
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);
}
