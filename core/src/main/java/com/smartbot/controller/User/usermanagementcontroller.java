package com.smartbot.controller.User;

import com.smartbot.service.User.Management.Usermanagement;
import domain.Result;
import domain.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usermanagement")
public class usermanagementcontroller {

    Usermanagement usermanagement;

    public usermanagementcontroller(Usermanagement usermanagement) {
        this.usermanagement = usermanagement;
    }

    @PostMapping("/change")
    Result changepassword(@RequestBody User user, HttpSession session)
    {
        User myuser=(User) session.getAttribute("userinfo");
        user.setAccount(myuser.getAccount());
        return  usermanagement.changepassword(user);
    }
}
