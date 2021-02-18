package com.smallyuan.labs.netty.attribute;

import com.smallyuan.labs.netty.session.Session;
import io.netty.util.AttributeKey;

public interface Attributes {

    // 标志登录状态
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");

    // 标志用户信息
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
