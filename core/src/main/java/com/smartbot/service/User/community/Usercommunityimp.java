package com.smartbot.service.User.community;

import com.smartbot.dao.user.Usercommunity.community;
import com.smartbot.dao.user.Usercommunity.findreply;
import domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class Usercommunityimp implements Usercommunity{
    community comm;
    findreply find;

    public Usercommunityimp(community comm, findreply find) {
        this.comm = comm;
        this.find = find;
    }

    @Override
    public Result querycommunity(Pagebean pagebean, User user){
        int num=comm.findnum(user);
        if(num>0)
        {
            int row=pagebean.getRow();
            int over=(pagebean.getCurrentpage()-1)*pagebean.getRow();
           List<Question> list= comm.findmsg(user,over,row);
           for(int i=0;i<list.size();++i)
           {
               int length=list.get(i).getAnswers().size();
               if(length>0)
               {
                   int length2=list.get(i).getAnswers().get(0).getRcontent().length();
                   if(length2>200)
                   {
                       list.get(i).getAnswers().get(0).setRcontent(list.get(i).getAnswers().get(0).getRcontent().substring(0,200)+"...");
                   }
               }
           }
           pagebean.setList(list);
           pagebean.setTotalcount(num);
           int pages=num%row==0?num/row:num/row+1;
           pagebean.setTotalpage(pages);
           return new Result(Code.Get_OK,pagebean);
        }
        else
        {
            return new Result(Code.Get_ERROR,"社区中暂无待解答的问题");
        }
    }

    @Override
    public Result good(int rid, User user,int isevaluated) {
        int num=comm.findevnum(rid,user.getAccount());
        if (num>0)
        {
            comm.update_evaluate(rid,user.getAccount(),isevaluated);
        }
        else
            comm.insert_evaluate(rid,user.getAccount(),isevaluated);
        comm.updategood(rid);
        return new Result(Code.UPDATE_OK,"");
    }

    @Override
    public Result bad(int rid, User user,int isevaluated) {
        int num=comm.findevnum(rid,user.getAccount());
        if (num>0)
        {
            comm.update_evaluate(rid,user.getAccount(),isevaluated);
        }
        else
            comm.insert_evaluate(rid,user.getAccount(),isevaluated);
        comm.updatebad(rid);
        return new Result(Code.UPDATE_OK,"");
    }

    @Override
    public Result newreply(Answer answer) {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        String msg_time=simpleDateFormat.format(new Date());
        answer.setRtime(msg_time);
        comm.newreply(answer);
        return new Result(Code.INSERT_OK,"success");
    }

    @Override
    public Result deletereply(Answer answer) {
       comm.deletereply(answer);
       return new Result(Code.DELETE_OK,"success");
    }

    @Override
    public Result historygood(Answer answer,User user) {
        int mid=comm.findonemsg(answer);
        int num=comm.findevnum(answer.getRid(),user.getAccount());
        if (num>0)
        {
            comm.update_evaluate(answer.getRid(),user.getAccount(),1);
        }
        else
            comm.insert_evaluate(answer.getRid(),user.getAccount(),1);
        comm.update_msg(mid);
        return new Result(Code.UPDATE_OK,"已将该问题移出社区");
    }

    @Override
    public Result updatereply(Answer answer) {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        String msg_time=simpleDateFormat.format(new Date());
        answer.setRtime(msg_time);
        comm.updatereply(answer);
        return new Result(Code.UPDATE_OK,"success");
    }

    @Override
    public Result querydetail(int mid,User user) {
        Detailedquestion detailedquestion=new Detailedquestion();
        detailedquestion.setMid(mid);
        String address=comm.findmsgavatar(mid);
        if(address==null)
            address="none";
        detailedquestion.setAddress(address);
        Answer answer1=new Answer();
        List<Answer> list=find.findreplys(mid,user);
        if(list.size()>0)
        {
            boolean flag=false;
            for (int i=0;i<list.size();++i)
            {
                Answer answer=list.get(i);
                int rid=answer.getRid();
                Integer isevaluated=find.findisevaluated(rid,user);
                if(isevaluated==null)
                    isevaluated=0;
                answer.setIsevaluated(isevaluated);
                if(user.getAccount().equals(answer.getAccount()))
                {
                    answer1=answer;
                    list.remove(i);
                    flag=true;
                    break;
                }
                else if (answer.getUser()==null)
                {
                    User user1=new User();
                    answer.setUser(user1);
                }
            }
            if(!flag)
            {
                answer1.setRid(-1);
            }
        }
        else{
            Answer answer= new Answer();
            answer.setStatus("none");
            answer.setRcontent("暂无回答");
            list.add(answer);
            answer1.setRid(-1);
        }
        detailedquestion.setMyanswer(answer1);
        detailedquestion.setList(list);
        return new Result(Code.Get_OK,detailedquestion);
    }
}
