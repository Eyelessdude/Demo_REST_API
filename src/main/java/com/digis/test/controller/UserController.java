package com.digis.test.controller;

import com.digis.test.dto.UserDto;
import com.digis.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = {"/{login}"})
    public UserDto getUserByLogin(@PathVariable("login") String login) {

        return userService.getUserByLogin(login);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createUser(@RequestBody UserDto userDto) {

        userService.createUser(userDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping
    public void updateUser(@RequestBody UserDto userDto) {

        userService.updateUser(userDto);
    }
}
