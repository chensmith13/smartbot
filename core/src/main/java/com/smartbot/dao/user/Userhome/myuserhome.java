package com.smartbot.dao.user.Userhome;

import domain.Answer;
import domain.QA;
import domain.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface myuserhome {
    @Select("select  * from message order by hot desc limit 5")
    public List<Question> hotest();
    @Select("select * from message where mcontent like '%${question}%'")
    public List<Question> findmsg(String question);
    @Select("select  * from reply where mid =#{mid} order by rrank desc limit 1")
    public Answer findtopans(Question question);
    @Select("select mid from message where mcontent=#{mcontent}")
    public Integer findmsgid(Question question);
    @Insert("insert into message(account,mcontent,mtime,isreply) values (#{user.account},#{question.mcontent},#{question.mtime},#{question.isreply})")
    public int insert_message(QA qa);
    @Select("select count(*) from message where mid=#{question.mid} and isreply=0")
    public int findusermsg(QA qa);
    @Update("update message set account=#{user.account},mtime=#{question.mtime},isreply=0 where mid=#{question.mid}")
    public int update_message_unsolved(QA qa);
    @Update("update message set hot=hot+1 where mcontent=#{mcontent}")
    public int update_message_hot(Question question);
    @Update("update reply set rrank=rrank-1 where rid=#{rid}")
    public int reduce_reply_rank(Answer answer);
    @Update("update reply set rrank=rrank+1 where rid=#{rid}")
    public int add_reply_rank(Answer answer);
    @Select("select rid from reply where rcontent=#{rcontent}")
    public Integer find_reply(Answer answer);
    @Select("select mid from message where mcontent=#{mcontent}")
    public Integer find_message(Question question);
    @Insert("insert into reply(account,rcontent,rtime,rrank,mid) values('-1',#{answer.rcontent},#{answer.rtime},1,#{question.mid})")
    public Integer insert_reply(QA qa);
    @Insert("insert into history(account,mid,rid,htime) values(#{qa.user.account},#{qa.question.mid},#{qa.answer.rid},#{htime}")
    Integer insert_history(QA qa,String htime);
    @Select("select rid,reply.account,rcontent,rtime,mid,rrank from reply,login where mid =#{mid} and login.account=reply.account and status='expert' order by rrank desc limit 1")
    public Answer findexpertpans(Question question);
    @Select("select rid,reply.account,rcontent,rtime,mid,rrank from reply,login where mid =#{mid} and login.account=reply.account and status<> 'expert' order by rrank desc limit 1")
    public Answer findotherpans(Question question);
}
