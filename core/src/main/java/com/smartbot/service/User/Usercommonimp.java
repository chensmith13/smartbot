package com.smartbot.service.User;

import com.smartbot.service.User.Usercommon;
import domain.Code;
import domain.Result;
import domain.User;
import io.netty.handler.codec.base64.Base64Encoder;
import org.springframework.stereotype.Service;
import com.smartbot.dao.user.myuser;
@Service
public class Usercommonimp implements Usercommon {
    private myuser my;
    static String path;
    static {
        path=Usercommonimp.class.getClassLoader().getResource("upload/t.file").getPath();
        path=path.substring(1,path.length()-7);
    }
    public Usercommonimp(myuser my) {
        this.my=my;
    }

    @Override
    public Result getavatar(User user) {
        User newuser=my.findavatar(user);
        String defaultaddress="none";
        StringBuilder sb=new StringBuilder();
        if(newuser==null)
        {
            newuser=new User();
            newuser.setAddress(defaultaddress);
        }
        else
        {
            sb.append(newuser.getAddress());
            newuser.setAddress(sb.toString());
        }
       return new Result(Code.Get_OK,newuser);
    }
}
