package com.smartbot.service.User.Userhistory;

import com.smartbot.dao.user.History.history;
import domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Userhistoryimp implements Userhistory{
    history his;

    public Userhistoryimp(history his) {
        this.his = his;
    }

    @Override
    public Result findunsolved(Pagebean pagebean, User user) {
        int num=his.findunsolvednum(user);
        pagebean.setTotalcount(num);
        int row= pagebean.getRow();
        int pages=0;
        if(num!=0&&row!=0)
            pages=num%row==0?num/row:num/row+1;
        pagebean.setTotalpage(pages);
        int over=(pagebean.getCurrentpage()-1)*pagebean.getRow();
       List<Question> list=his.findunsolved(user,over,row);
       pagebean.setList(list);
       return new Result(Code.Get_OK,pagebean,"success");
    }

    @Override
    public Result findsolved(Pagebean pagebean, User user) {
        int row=pagebean.getRow();
        int over=(pagebean.getCurrentpage()-1)*pagebean.getRow();
        int pages=0;
        int num=his.findnum(user);
        pagebean.setTotalcount(num);
        if(num!=0&&row!=0)
            pages=num%row==0?num/row:num/row+1;
        pagebean.setTotalpage(pages);
        List<History> list=his.findsolved(user,over,row);
        pagebean.setList(list);
        return new Result(Code.Get_OK,pagebean,"success");
    }
}
