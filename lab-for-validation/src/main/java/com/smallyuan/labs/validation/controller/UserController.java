package com.smallyuan.labs.validation.controller;

import com.smallyuan.labs.validation.vo.UserUpdateDTO;
import com.smallyuan.labs.validation.vo.UserUpdateGenderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/updateGender")
    public void updateGender(@Valid UserUpdateGenderDTO updateGenderDTO) {
        logger.info("[updateGender][updateGenderDTO: {}]", updateGenderDTO);
    }

    @PostMapping("/update")
    public void update(@Valid UserUpdateDTO updateDTO) {
        logger.info("[update][UserUpdateDTO: {}]", updateDTO);
    }

}
