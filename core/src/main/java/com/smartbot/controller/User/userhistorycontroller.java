package com.smartbot.controller.User;

import com.smartbot.service.User.Userhistory.Userhistory;
import domain.Pagebean;
import domain.Result;
import domain.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("userhistory")
public class userhistorycontroller {
    Userhistory userhistory;

    public userhistorycontroller(Userhistory userhistory) {
        this.userhistory = userhistory;
    }

    @PostMapping("/solved")
    public Result solved(@RequestBody Pagebean pagebean, HttpSession session)
    {
        User user=(User) session.getAttribute("userinfo");
        return userhistory.findsolved(pagebean,user);
    }
    @PostMapping("/unsolved")
    public Result unsolved(@RequestBody Pagebean pagebean, HttpSession session)
    {
        User user=(User) session.getAttribute("userinfo");
        return userhistory.findunsolved(pagebean,user);
    }
}
