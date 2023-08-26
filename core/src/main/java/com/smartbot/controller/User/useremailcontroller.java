package com.smartbot.controller.User;

import com.smartbot.service.User.Manageemail.Useremail;
import domain.Result;
import domain.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/useremail")
public class useremailcontroller {
    Useremail useremail;

    public useremailcontroller(Useremail useremail) {
        this.useremail = useremail;
    }
    @PostMapping("/check")
    public Result check(HttpSession session)
    {
        User user=(User) session.getAttribute("userinfo");
        return useremail.checkemail(user);
    }
    @PostMapping("/changeemail")
    public Result changeemail(@RequestBody User myuser, HttpSession session)
    {
        User user=(User) session.getAttribute("userinfo");
        myuser.setAccount(user.getAccount());
        return  useremail.changeemail(user);
    }
}
