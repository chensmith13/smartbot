package com.smartbot.service.Login;
import com.smartbot.dao.login.mylogin;
import domain.Code;
import domain.Result;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Loginimp implements Login {
    @Autowired
    mylogin lo;
    @Override
    public Result checklogin(User user,String checkcode) {
        String msg="";
        if(!checkcode.equalsIgnoreCase(user.getCheckcode()))
        {
            msg="验证码输入错误！";
            return new Result(Code.Get_ERROR,user,msg);

        }
        else if(user.getAccount().isEmpty()||user.getPassword().isEmpty())
        {
            msg="用户名或密码不能为空";
            return new Result(Code.Get_ERROR,user,msg);
        }
        else
        {
            User sqluser=lo.checklogin(user);
            if (sqluser==null||!sqluser.getPassword().equals(user.getPassword()))
            {
                System.out.println("111");
                msg="用户名或密码错误";
                return new Result(Code.Get_ERROR,user,msg);
            }
            else
            {
                return new Result(Code.Get_OK,sqluser,msg);
            }
        }
    }
}
