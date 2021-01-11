package com.smallyuan.labs.webflux.controller;

import com.smallyuan.labs.webflux.vo.UserVo;
import org.reactivestreams.Publisher;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * 基于 Annotated Controller 方式实现
 */
@RestController
@RequestMapping("/users")
public class UserController {

    /**
     * 查询用户列表
     * @return
     */
    @GetMapping("/list")
    public Flux<UserVo> list() {
        List<UserVo> result = new ArrayList<>();
        UserVo vo = new UserVo();
        vo.setId(1);
        vo.setName("yuan");
        result.add(vo);
        // 返回列表
        return Flux.fromIterable(result);
    }

    /**
     * 获取指定编号的用户
     * @param id
     * @return
     */
    @GetMapping("/get")
    public Mono<UserVo> get(@RequestParam("id") Integer id) {
        UserVo vo = new UserVo();
        vo.setId(1);
        vo.setName("yuan");
        return Mono.just(vo);
    }

    /**
     * 添加用户
     * @return
     */
    @PostMapping("/add") // 添加了 @RequestBody 注解，从 request 的 Body 中读取参数,这里提交参数需要 Content-Type="application/json"
    public Mono<Integer> add(@RequestBody Publisher<UserVo> addDTO) {
        Integer returnId = 1;
        return Mono.just(returnId);
    }

    /**
     * 添加用户
     * @return
     */
    @PostMapping("/add2") // 这里通过 request 的 Form Data 或 Multipart Data 传递参数,可以使用 application/x-www-form-urlencoded 或 multipart/form-data 这两个 Content-Type 内容类型
    public Mono<Integer> add2(Mono<UserVo> addDTO) {
        Integer returnId = 1;
        return Mono.just(returnId);
    }

    /**
     * 更新
     * @param updateDTO
     * @return
     */
    @PostMapping("/update")
    public Mono<Boolean> update(@RequestBody Publisher<UserVo> updateDTO){
        return Mono.just(true);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @PostMapping("/delete")
    public Mono<Boolean> delete(@RequestParam("id") Integer id) {
        return Mono.just(true);
    }
}
