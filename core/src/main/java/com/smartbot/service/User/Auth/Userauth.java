package com.smartbot.service.User.Auth;

import domain.Auth;
import domain.Result;
import domain.User;

public interface Userauth {
    public Result findprogress(User user);
    public Result apply(User user, Auth auth);
}
