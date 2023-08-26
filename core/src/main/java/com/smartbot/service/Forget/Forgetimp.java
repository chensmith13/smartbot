package com.smartbot.service.Forget;
import com.smartbot.dao.forget.myforget;
import domain.Code;
import domain.Result;
import domain.User;
import mail.mailutil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Forgetimp implements Forget{
    @Autowired
    myforget forget;
    @Override
    public Result sendpassword(User user) {
       User newuser= forget.querypassword(user);
       if(newuser==null)
           return new Result(Code.Get_ERROR,"输入的用户名或邮箱有误");
       else {
           try {
               mailutil.send(newuser);
           } catch (Exception e) {
               return new Result(Code.Get_ERROR,"发送失败");
           }
           return new Result(Code.Get_OK,"密码已发送至邮箱请注意查收");
       }
    }
}
