package com.smartbot.service.Register;

import com.smartbot.dao.register.myregister;
import domain.Code;
import domain.Result;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Registerimp implements Register {
    @Autowired
    myregister reg;
    @Override
    public Result registercheck(User user) {
        User newuser=reg.registercheck(user);
        if(newuser==null)
        {
            return new Result(Code.Get_ERROR);
        }
        else {
            return new Result(Code.Get_OK);
        }
    }

    @Override
    public Result register(User user) {
        user.setStatus("user");
        boolean flag=reg.register(user);
        if(flag)
            return new Result(Code.INSERT_OK,"注册成功");
        else
            return new Result(Code.INSERT_ERROR);
    }
}
