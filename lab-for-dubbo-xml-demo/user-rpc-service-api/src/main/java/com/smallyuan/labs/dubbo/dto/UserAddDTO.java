package com.smallyuan.labs.dubbo.dto;


import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserAddDTO {

    /**
     * 昵称
     */
    @NotEmpty(message = "昵称不能为空")
    @Length(min = 5, max = 16, message = "账号长度为 5-16 位")
    private String name;
    /**
     * 性别
     */
    @NotNull(message = "性别不能为空")
    private Integer gender;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }
}
