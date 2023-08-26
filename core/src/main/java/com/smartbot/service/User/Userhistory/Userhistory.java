package com.smartbot.service.User.Userhistory;

import domain.Pagebean;
import domain.Result;
import domain.User;

public interface Userhistory {
    Result findsolved(Pagebean pagebean, User user);
    Result findunsolved(Pagebean pagebean,User user);
}
