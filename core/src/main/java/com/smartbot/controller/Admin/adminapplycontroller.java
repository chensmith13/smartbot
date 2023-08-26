package com.smartbot.controller.Admin;

import com.smartbot.service.Admin.Manageapply.Adminapply;
import domain.Pagebean;
import domain.Result;
import domain.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/adminapply")
public class adminapplycontroller {
    Adminapply adminapply;

    public adminapplycontroller(Adminapply adminapply) {
        this.adminapply = adminapply;
    }

    @PostMapping("/query")
    public Result query(@RequestBody Pagebean pagebean)
    {
        return  adminapply.findappies(pagebean);
    }
    @PostMapping("/accept")
    public Result accept(@RequestBody User user)
    {
        return adminapply.accept(user);
    }
    @PostMapping("/refuse")
    public Result refuse(@RequestBody User user)
    {
        return adminapply.refuse(user);
    }

}
