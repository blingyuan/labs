package com.smallyuan.labs.springmvc.web;


import com.smallyuan.labs.springmvc.vo.CommonResult;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 全局统一返回的示例
 * 实现 ResponseBodyAdvice 接口，并添加 @ControllerAdvice 注解，拦截指定包下的返回结果
 *
 * 实际项目中是否需要用AOP，修改一个方法的返回结果类型，可以用，没问题。但是没必要
 * 强制要求controller的返回结果类型都是CommonResult就行。
 */
@ControllerAdvice(basePackages = "com.smallyuan.labs.springmvc.controller")
public class GlobalResponseBodyHandler implements ResponseBodyAdvice {

    /**
     * 判断拦截哪些api接口的返回结果
     * 这里表示拦截指定包下所有api接口的返回结果
     * @param methodParameter
     * @param aClass
     * @return
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    /**
     * 拦截到的返回结果，如何进行改写
     * @param body
     * @param methodParameter
     * @param mediaType
     * @param aClass
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @return
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        // 如果已经是 CommonResult 类型，则直接返回
        if (body instanceof CommonResult) {
            return body;
        }
        // 如果不是，包装成CommonResult 类型。（这里如果能被拦截到，约定就是成功的返回，对于异常的我们会进行全局异常处理）
        return CommonResult.success(body);
    }
}
