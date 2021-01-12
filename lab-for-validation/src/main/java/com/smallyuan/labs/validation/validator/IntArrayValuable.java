package com.smallyuan.labs.validation.validator;

/**
 * 因为对于一个枚举类来说，我们无法获得它具体有那些值。所以，我们会要求这个枚举类实现该接口，返回它拥有的所有枚举值。
 */
public interface IntArrayValuable {

    int[] array();

}
