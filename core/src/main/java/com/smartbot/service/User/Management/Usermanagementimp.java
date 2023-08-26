package com.smartbot.service.User.Management;

import com.smartbot.dao.user.Manageaccount.manageaccount;
import domain.Code;
import domain.Result;
import domain.User;
import org.springframework.stereotype.Service;

@Service
public class Usermanagementimp implements Usermanagement{
    manageaccount manage;

    public Usermanagementimp(manageaccount manage) {
        this.manage = manage;
    }

    @Override
    public Result changepassword(User user) {
        manage.changepassword(user);
        return new Result(Code.UPDATE_OK,"success");
    }
}
