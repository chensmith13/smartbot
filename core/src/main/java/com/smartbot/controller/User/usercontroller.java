package com.smartbot.controller.User;

import com.smartbot.service.User.Usercommon;
import domain.Result;
import domain.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class usercontroller {
    private Usercommon usercommon;

    public usercontroller(Usercommon user) {
        this.usercommon = user;
    }
    @GetMapping("/avatar")
    public Result getavatar(HttpSession session)
    {
        User user=(User) session.getAttribute("userinfo");
        return usercommon.getavatar(user);
    }
}
