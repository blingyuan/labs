package com.smallyuan.labs.validation.vo;

import javax.validation.constraints.NotNull;

public class UserUpdateDTO {

    /**
     * 用户编号
     */
    @NotNull(message = "{UserUpdateDTO.id.NotNull}")
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}