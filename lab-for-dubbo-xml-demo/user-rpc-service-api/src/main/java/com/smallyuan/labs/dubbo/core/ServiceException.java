package com.smallyuan.labs.dubbo.core;

public class ServiceException extends RuntimeException {

    // 错误码
    private Integer code;

    /**
     * // 创建默认构造方法，用于反序列化的场景。
     */
    public ServiceException() {

    }

    public ServiceException(ServiceExceptionEnum serviceExceptionEnum) {
        super(serviceExceptionEnum.getMessage());
        this.code = serviceExceptionEnum.getCode();
    }

    public ServiceException(ServiceExceptionEnum serviceExceptionEnum,String message) {
        super(message);
        this.code = serviceExceptionEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }
}
