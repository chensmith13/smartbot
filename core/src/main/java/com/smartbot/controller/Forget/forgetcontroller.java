package com.smartbot.controller.Forget;

import domain.Result;
import domain.User;
import com.smartbot.service.Forget.Forget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/forget")
@RestController
public class forgetcontroller {
    @Autowired
    Forget forget;
    @RequestMapping("/querypassword")
    public Result querypassword(@RequestBody User user)
    {
        return forget.sendpassword(user);
    }
}

