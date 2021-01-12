package com.smallyuan.labs.validation.constant;

import com.smallyuan.labs.validation.validator.IntArrayValuable;

import java.util.Arrays;

public enum GenderEnum implements IntArrayValuable {
    MALE(1,"男"),
    FEMALE(2,"女");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(GenderEnum::getValue).toArray();

    private final Integer value;

    private final String name;

    GenderEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    @Override
    public int[] array() {
        return ARRAYS;
    }
}
