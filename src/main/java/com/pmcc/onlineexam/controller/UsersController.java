package com.pmcc.onlineexam.controller;

import com.pmcc.onlineexam.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exam/users")
public class UsersController {
    @Autowired
    UsersService users;

    @RequestMapping("/get-tree-all")
    public String gettree() {
        String data = users.gettree();
        return data;
    }
}
