package com.smartbot.controller.Register;

import domain.Result;
import domain.User;
import com.smartbot.service.Register.Register;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/register")
@RestController
public class registercontroller {
    @Autowired
    Register register;
    @PostMapping("/registercheck")
    public Result registercheck(@RequestBody User user)
    {
        return register.registercheck(user);
    }
    @PostMapping("/register")
    public Result register(@RequestBody User user)
    {
       return register.register(user);
    }
}
