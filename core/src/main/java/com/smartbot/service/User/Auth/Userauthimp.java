package com.smartbot.service.User.Auth;

import com.smartbot.dao.user.Findauth.auth;
import domain.Auth;
import domain.Code;
import domain.Result;
import domain.User;
import org.springframework.stereotype.Service;

@Service
public class Userauthimp implements Userauth{
    auth userauth;

    public Userauthimp(auth userauth) {
        this.userauth = userauth;
    }

    @Override
    public Result findprogress(User user) {
       int allnum=userauth.findallnum(user);
       int mynum=userauth.findnum(user);
       Integer isaccepted=userauth.findisaccepted(user);
       if(isaccepted==null)
           isaccepted=-1;
        Auth auth=new Auth();
        auth.setAllnum(allnum);
        auth.setMynum(mynum);
        auth.setIsaccepted(isaccepted);
       return new Result(Code.Get_OK,auth,"");
    }

    @Override
    public Result apply(User user,Auth auth) {
        int num=userauth.findmynum(user);
        if(auth.getIsaccepted()==-1)
        {
            userauth.insertapply(user,num);
        }
        else if(auth.getIsaccepted()==2)
            userauth.updateapply(user,num);
        return new Result(Code.UPDATE_OK,"success");
    }
}
