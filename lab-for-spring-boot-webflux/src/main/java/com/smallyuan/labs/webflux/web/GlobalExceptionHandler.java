package com.smallyuan.labs.webflux.web;


import com.smallyuan.labs.webflux.constants.ServiceExceptionEnum;
import com.smallyuan.labs.webflux.exception.ServiceException;
import com.smallyuan.labs.webflux.vo.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ServerWebInputException;


@ControllerAdvice(basePackages = "com.smallyuan.labs.springmvc.controller")
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 处理 ServiceException 异常
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = ServiceException.class)
    public CommonResult serviceExceptionHandler(ServiceException ex) {
        logger.error("[serviceExceptionHandler]",ex);
        // 包装 CommonResult 结果
        return CommonResult.error(ex.getCode(),ex.getMessage());
    }

    /**
     * 处理 serverWebInputExceptionHandler [参数不正确] 异常
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = ServerWebInputException.class)
    public CommonResult serverWebInputExceptionHandler(ServerWebInputException ex) {
        logger.error("[serverWebInputExceptionHandler]",ex);
        // 包装 CommonResult 结果
        return CommonResult.error(ServiceExceptionEnum.MISSING_REQUEST_PARAM_ERROR.getCode(),ServiceExceptionEnum.MISSING_REQUEST_PARAM_ERROR.getMessage());
    }

    /**
     * 处理其它 Exception 异常
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public CommonResult exceptionHandler( Exception e) {
        // 记录异常日志
        logger.error("[exceptionHandler]", e);
        // 返回 ERROR CommonResult
        return CommonResult.error(ServiceExceptionEnum.SYS_ERROR.getCode(),
                ServiceExceptionEnum.SYS_ERROR.getMessage());
    }
}
