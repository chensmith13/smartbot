package com.smartbot.service.Admin.Manageapply;

import domain.Pagebean;
import domain.Result;
import domain.User;

public interface Adminapply {
    public Result findappies(Pagebean pagebean);
    public Result accept(User user);
    public Result refuse(User user);
}
