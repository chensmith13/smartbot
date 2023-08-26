package com.smartbot.controller.User;

import com.smartbot.service.User.Avatar.Avatar;
import domain.Result;
import domain.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@RestController
@RequestMapping("/avatar")
public class useravatarcontroller {
    Avatar avatar;

    public useravatarcontroller(Avatar avatar) {
        this.avatar = avatar;
    }

    @PostMapping("/add_avatar")
    public Result addavatar(@RequestParam("File") MultipartFile File, HttpSession session)
    {
        User user=(User) session.getAttribute("userinfo");
        return avatar.upload(File,user);
    }
}
