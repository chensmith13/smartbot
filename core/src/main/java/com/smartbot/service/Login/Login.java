package com.smartbot.service.Login;

import domain.Result;
import domain.User;
import jakarta.servlet.http.HttpSession;


public interface Login {
    public Result checklogin(User user, String checkcode);
}
