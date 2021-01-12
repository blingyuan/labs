package com.smallyuan.labs.validation.web;

import com.smallyuan.labs.validation.constant.ServiceExceptionEnum;
import com.smallyuan.labs.validation.exception.ServiceException;
import com.smallyuan.labs.validation.vo.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * 全局异常处理
 */
@ControllerAdvice(basePackages = "com.smallyuan.labs.validation.controller")
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 处理 ServiceException 异常
     * @param req
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = ServiceException.class)
    public CommonResult serviceExceptionHandler(HttpServletRequest req, ServiceException ex) {
        logger.error("[serviceExceptionHandler]",ex);
        // 包装 CommonResult 结果
        return CommonResult.error(ex.getCode(),ex.getMessage());
    }

    /**
     * 处理 MissingServletRequestParameterException [参数不正确] 异常
     * @param req
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public CommonResult missingServletRequestParameterExceptionHandler(HttpServletRequest req, MissingServletRequestParameterException ex) {
        logger.error("[missingServletRequestParameterExceptionHandler]",ex);
        // 包装 CommonResult 结果
        return CommonResult.error(ServiceExceptionEnum.MISSING_REQUEST_PARAM_ERROR.getCode(),ServiceExceptionEnum.MISSING_REQUEST_PARAM_ERROR.getMessage());
    }

    /**
     * 处理其它 Exception 异常
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public CommonResult exceptionHandler(HttpServletRequest req, Exception e) {
        // 记录异常日志
        logger.error("[exceptionHandler]", e);
        // 返回 ERROR CommonResult
        return CommonResult.error(ServiceExceptionEnum.SYS_ERROR.getCode(),
                ServiceExceptionEnum.SYS_ERROR.getMessage());
    }

    /**
     * 处理 ConstraintViolationException 异常
     */
    @ResponseBody
    @ExceptionHandler(value = ConstraintViolationException.class)
    public CommonResult constraintViolationExceptionHandler(ConstraintViolationException ex) {
        logger.error("[constraintViolationExceptionHandler]",ex);
        StringBuilder detailMessage = new StringBuilder();
        for (ConstraintViolation<?> constraintViolation  : ex.getConstraintViolations()) {
            if (detailMessage.length() > 0) {
                detailMessage.append(";");
            }
            detailMessage.append(constraintViolation.getMessage());
        }

        // 包装 CommonResult 结果
        return CommonResult.error(ServiceExceptionEnum.INVALID_REQUEST_PARAM_ERROR.getCode(),
                ServiceExceptionEnum.INVALID_REQUEST_PARAM_ERROR.getMessage()+":"+detailMessage.toString());
    }

    /**
     * 处理 ConstraintViolationException 异常
     */
    @ResponseBody
    @ExceptionHandler(value = BindException.class)
    public CommonResult bindExceptionHandler(BindException ex) {
        logger.error("[bindExceptionHandler]",ex);
        StringBuilder detailMessage = new StringBuilder();
        for (ObjectError objectError : ex.getAllErrors()) {
            if (detailMessage.length() > 0) {
                detailMessage.append(";");
            }
            detailMessage.append(objectError.getDefaultMessage());
        }

        // 包装 CommonResult 结果
        return CommonResult.error(ServiceExceptionEnum.INVALID_REQUEST_PARAM_ERROR.getCode(),
                ServiceExceptionEnum.INVALID_REQUEST_PARAM_ERROR.getMessage()+":"+detailMessage.toString());
    }

}
