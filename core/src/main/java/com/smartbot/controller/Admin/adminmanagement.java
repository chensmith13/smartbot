package com.smartbot.controller.Admin;

import com.smartbot.service.Admin.Managecommunity.Adminmanagement;
import domain.Question;
import domain.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/adminmanagement")
public class adminmanagement {
    Adminmanagement adminmanagement;

    public adminmanagement(Adminmanagement adminmanagement) {
        this.adminmanagement = adminmanagement;
    }

    @PostMapping("/delete")
    public Result delete(@RequestBody Question question)
    {
        return adminmanagement.deletemessage(question);
    }
}
