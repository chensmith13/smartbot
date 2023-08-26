package com.smartbot.controller.User;

import com.smartbot.service.User.Auth.Userauth;
import domain.Auth;
import domain.Result;
import domain.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userauth")
public class userauthcontroller {
    Userauth userauth;

    public userauthcontroller(Userauth userauth) {
        this.userauth = userauth;
    }

    @GetMapping("/findprogress")
    Result findprogress(HttpSession session)
    {
        User user=(User) session.getAttribute("userinfo");
        return userauth.findprogress(user);
    }
    @PutMapping("/apply")
    Result apply(@RequestBody Auth auth, HttpSession session)
    {
        User user=(User) session.getAttribute("userinfo");
       return userauth.apply(user,auth);
    }
}
