package com.smartbot.service.User.Manageemail;


import domain.Result;
import domain.User;

public interface Useremail {

    public Result checkemail(User user);
    public Result changeemail(User user);
}
