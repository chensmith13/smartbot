package com.smartbot.dao.user.Usercommunity;
import domain.Answer;
import domain.Question;
import domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface community {

    @Select("select count(*) from message where account!=#{account} and isreply=0")
    Integer findnum(User user);
    @Select("select mid,mcontent,mtime from message where message.account !=#{user.account} and message.isreply=0 order by mtime limit #{over},#{row}")
   @Results(
           value =
           {
                   @Result(id=true,property = "mid",column = "mid"),
                   @Result(property = "mcontent",column = "mcontent"),
                   @Result(property = "mtime",column = "mtime"),
                   @Result(property = "answers",column = "mid",
                    many=@Many(select="com.smartbot.dao.user.Usercommunity.findreply.findonereply")),
                   @Result(property = "ansnum",column = "mid",
                           one=@One(select="com.smartbot.dao.user.Usercommunity.findreply.findnum"))
           }
   )
    List<Question> findmsg(User user,int over,int row);
    @Select("select address from userimage,message where userimage.account=message.account and mid=#{mid}")
    String findmsgavatar(int mid);
    @Select("select count(*) from evaluate where rid=#{rid} and account=#{account}")
    int findevnum(int rid,String account);
    @Update("update reply set rrank=rrank+1 where rid=#{rid}")
    Integer updategood(int rid);
    @Update("update reply set rrank=rrank-1 where rid=#{rid}")
    Integer updatebad(int rid);
    @Insert("insert into evaluate(rid,account,isevaluated) values(#{rid},#{account},#{isevaluated})")
    Integer insert_evaluate(int rid,String account,int isevaluated);
    @Update("update evaluate set isevaluated=#{isevaluated} where rid=#{rid} and account=#{account}")
    Integer update_evaluate(int rid,String account,int isevaluated);
    @Insert("insert into reply(account,rcontent,rtime,mid) values(#{user.account},#{rcontent},#{rtime},#{mid})")
    Integer newreply(Answer answer);
    @Update("update reply set rcontent =#{rcontent},rtime=#{rtime} where rid=#{rid}")
    Integer updatereply(Answer answer);
    @Delete("delete from reply where rid=#{rid}")
    Integer deletereply(Answer answer);
    @Select("select mid from reply where rid=#{rid}")
    Integer findonemsg(Answer answer);
    @Update("update message set isreply=1 where mid=#{mid}")
    Integer update_msg(int mid);
}
