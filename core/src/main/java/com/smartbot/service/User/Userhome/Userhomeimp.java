package com.smartbot.service.User.Userhome;

import com.smartbot.dao.login.mylogin;
import com.smartbot.dao.user.Userhome.myuserhome;
import domain.*;
import find.findutil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class Userhomeimp implements Userhome {

    static Long date=System.currentTimeMillis()/1000;
    myuserhome user;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    public Userhomeimp(myuserhome u) {
        this.user=u;
    }
    @Override
    public Result hotest() {

        //先比较时间过去了多久
        Long now=System.currentTimeMillis()/1000;
        List<Question>list;
        //如果过去了6小时就更新redis
        if(now-date>3600*6)
        {
            list=user.hotest();
            if(list==null)
            {
                list=new ArrayList<Question>();
            }
            //先删除原有数据然后进行更新操作
            List<String>mylist=stringRedisTemplate.opsForList().leftPop("hot",5);
            for (String s : mylist) {
                stringRedisTemplate.opsForHash().delete(s,"hot");
                stringRedisTemplate.opsForHash().delete(s,"mcontent");
            }
            for (Question question : list) {
                String mid=String.valueOf(question.getMid());
                stringRedisTemplate.opsForList().rightPush("hot",mid);
                stringRedisTemplate.opsForHash().put(mid,"hot",String.valueOf(question.getHot()));
                stringRedisTemplate.opsForHash().put(mid,"mcontent",question.getMcontent());
            }
            date=now;
        }
        List<String>list1=stringRedisTemplate.opsForList().range("hot",0,7);
        //先查redis看redis是否有数据
        if(list1.size()>0) {
            list = new ArrayList<>();
            for (String s : list1) {
                Question question = new Question();
                question.setMid(Integer.valueOf(s));
                question.setMcontent((String) stringRedisTemplate.opsForHash().get(s, "mcontent"));
                question.setHot(Integer.valueOf((String) stringRedisTemplate.opsForHash().get(s, "hot")));
                list.add(question);
            }
            while (list.size()<5)
            {
                list.add(new Question());
            }
        }//没有就查mysql并更新redis
        else {
            list=user.hotest();
            if(list==null)
            {
                list=new ArrayList<Question>();
            }
            //先删除原有数据然后进行更新操作
            List<String>mylist=stringRedisTemplate.opsForList().leftPop("hot",5);
            for (String s : mylist) {
                stringRedisTemplate.opsForHash().delete(s,"hot");
                stringRedisTemplate.opsForHash().delete(s,"mcontent");
            }
            for (Question question : list) {
                String mid=String.valueOf(question.getMid());
                stringRedisTemplate.opsForList().rightPush("hot",mid);
                stringRedisTemplate.opsForHash().put(mid,"hot",String.valueOf(question.getHot()));
                stringRedisTemplate.opsForHash().put(mid,"mcontent",question.getMcontent());
            }
            while (list.size()<5)
            {
                list.add(new Question());
            }

        }
        return new Result(Code.Get_OK,list);
    }

    @Override
    public Result query(Question question) {
        String status=question.getStatus();
        //如果通过数据库查询
        if("sql".equals(status))
        {
            String keyword=findutil.findkeywords(question.getMcontent());
            List<Question> list=user.findmsg(keyword);

            //如果数据库中有此问题
            if(list.size()>0)
            {
                int index=findutil.findbestmatch(question.getMcontent(),list);
                //如果有匹配项
                if(index>=0)
                {
                    Answer answer;
                    Question question1=list.get(index);
                    Answer expertanswer=user.findexpertpans(question1);
                    Answer otheranswer=user.findotherpans(question1);
                    if(expertanswer==null)
                    {
                        answer=otheranswer;
                    }
                    else if(otheranswer==null)
                        answer=expertanswer;
                    else {
                        answer=expertanswer.getRrank()*2.5>=otheranswer.getRrank()?expertanswer:otheranswer;
                    }
                    //如果有答案
                    if(answer!=null)
                    answer.setStatus("spider");
                    //如果没有答案
                    else
                    {
                        String ans=findutil.findbyzhidao(question.getMcontent());
                        answer=new Answer();
                        answer.setRcontent(ans);
                        answer.setStatus("gpt");
                    }
                    return new Result(Code.Get_OK,answer,"");
                }
                //如果没有匹配项则爬虫
                else
                {
                    String ans=findutil.findbyzhidao(question.getMcontent());
                    Answer answer=new Answer();
                    answer.setRcontent(ans);
                    answer.setStatus("gpt");
                    return new Result(Code.Get_OK,answer,"");
                }
            }
            //如果没有答案则通过爬虫
            else
            {
                String ans=findutil.findbyzhidao(question.getMcontent());
                Answer answer=new Answer();
                answer.setRcontent(ans);
                answer.setStatus("gpt");
                return new Result(Code.Get_OK,answer,"");
            }
        }
        //如果通过爬虫查询
        else
        {
            String ans=findutil.findbyzhidao(question.getMcontent());
            Answer answer=new Answer();
            answer.setRcontent(ans);
            answer.setStatus("gpt");
            return new Result(Code.Get_OK,answer,"");
        }
    }

    @Override
    public Result addhot(Question question) {
        user.update_message_hot(question);
        return null;
    }

    @Override
    public Result submitcommunity(QA qa) {
        Integer id=user.findmsgid(qa.getQuestion());
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        String msg_time=simpleDateFormat.format(new Date());
        qa.getQuestion().setMtime(msg_time);
        //如果数据库中没有则直接插入
        if(id==null)
        {
            qa.getQuestion().setIsreply(0);
            int flag=user.insert_message(qa);
            if(flag>0)
                return new Result(Code.INSERT_OK,"已将该问题发布至社区，您可去社区查看");
            else
                return new Result(Code.INSERT_ERROR,"问题求助失败！请联系管理员");
        }
        //有则进行状态更新
        else
        {
            qa.getQuestion().setMid(id);
            int msgnum=user.findusermsg(qa);
            //如果社区已经有了这个问题
            if(msgnum>0)
            {
                return new Result(Code.UPDATE_ERROR,"社区已有该问题请去社区查看！");
            }
            //如果社区没有这个问题
            else
            {
               int flag= user.update_message_unsolved(qa);
               if(flag<1)
                   return new Result(Code.UPDATE_ERROR,"问题求助失败！请联系管理员");
               else if(flag==1)
                   return new Result(Code.UPDATE_OK,"已将该问题发布至社区，您可去社区查看");

            }
        }
        return null;
    }

    @Override
    public Result satisfied(QA qa) {
        Answer answer=qa.getAnswer();
        //如果通过数据库查询
        if(answer.getRid()!=-1)
        {
            user.add_reply_rank(answer);
        }
        //如果通过爬虫等方式
        else {
            Integer id=user.find_reply(answer);
            //如果数据库有了一样的回答了
            if(id!=null)
            {
                answer.setRid(id);
                user.add_reply_rank(answer);
            }
            //如果数据库没有一样的回答则插入
            else {
                Integer mid=user.find_message(qa.getQuestion());
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
                String msg_time=simpleDateFormat.format(new Date());
                qa.getQuestion().setMtime(msg_time);
                qa.getAnswer().setRtime(msg_time);
                //如果有了该问题则只创建回答
                if(mid!=null)
                {
                }
                else //如果没有该问题则先创建问题
                {
                    qa.getQuestion().setIsreply(1);
                    user.insert_message(qa);
                }
                //创建回答
                mid=user.find_message(qa.getQuestion());
                qa.getQuestion().setMid(mid);
                user.insert_reply(qa);
            }
        }
        SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        String time=simpleDateFormat1.format(new Date());
        int mymid=user.find_message(qa.getQuestion());
        qa.getQuestion().setMid(mymid);
        user.insert_history(qa,time);
        return null;
    }
    @Override
    public Result unsatisfied(Answer answer)
    {

        user.reduce_reply_rank(answer);
        return  null;
    }

    @Override
    public Result getavatar(User user) {
        return null;
    }
}
