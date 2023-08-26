package com.smartbot.service.Register;

import domain.Result;
import domain.User;

public interface Register {
    public Result registercheck(User user);
    public Result register(User user);
}
