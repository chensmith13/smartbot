package com.smartbot.service.Admin.Manageapply;

import com.smartbot.dao.admin.Findapply.findapply;
import domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Adminapplyimp implements Adminapply{
    findapply find;

    public Adminapplyimp(findapply find) {
        this.find = find;
    }

    @Override
    public Result accept(User user) {
        find.updatestatus(user);
        find.deleteapplication(user);
        return new Result(Code.UPDATE_OK,"success");
    }

    @Override
    public Result refuse(User user) {
        find.torefuse(user);
        return new Result(Code.UPDATE_OK,"success");
    }

    @Override
    public Result findappies(Pagebean pagebean) {
        int num=find.findnum();
        pagebean.setTotalcount(num);
        int row= pagebean.getRow();
        int pages=0;
        if(num!=0&&row!=0)
            pages=num%row==0?num/row:num/row+1;
        pagebean.setTotalpage(pages);
        int over=(pagebean.getCurrentpage()-1)*pagebean.getRow();
       List<Application>list= find.findapplies(over,row);
       pagebean.setList(list);
       return new Result(Code.Get_OK,pagebean,"success");
    }
}
