package com.smartbot.service.User.Manageemail;

import com.smartbot.dao.user.Email.email;
import domain.Code;
import domain.Result;
import domain.User;
import mail.mailutil;
import org.springframework.stereotype.Service;

@Service
public class Useremailimp implements Useremail{
    public Useremailimp(email e) {
        this.e = e;
    }

    email e;
    @Override
    public Result checkemail(User user) {
       user= e.finduserinfo(user);
        boolean flag=mailutil.receive(user);
        if(flag)
        {
            return new Result(Code.Get_OK,"success");
        }
        else{
            return new Result(Code.Get_ERROR,"暂时未收到您发的消息，请检查消息格式");
        }
    }

    @Override
    public Result changeemail(User user) {
        e.changeemail(user);
        return new Result(Code.UPDATE_OK,"success");
    }
}
