package com.smartbot.dao.user.Usercommunity;

import domain.Answer;
import domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface findreply {
    @Select("select * from reply where mid=#{mid} order by rrank desc limit 1")
    List<Answer> findonereply();
    @Select("select count(*) from reply where mid=#{mid}")
    int findnum(int mid);
    @Select("select mid,login.account,name,status,rcontent,rtime,rrank,rid from login,reply where mid=#{mid} and login.account=reply.account order by rrank desc ")
    @Results(
            value = {
                    @Result(id = true,property ="account",column = "account"),
                    @Result(property = "user.name",column = "name"),
                    @Result(property = "user.status",column = "status"),
                    @Result(property = "rcontent",column = "rcontent"),
                    @Result(property = "rtime",column = "rtime"),
                    @Result(property = "rrank",column = "rrank"),
                    @Result(property = "rid",column = "rid"),
                    @Result(property = "mid",column = "mid"),
                    @Result(property = "user",column = "account",
                    one = @One(select = "com.smartbot.dao.user.Usercommunity.findreply.findavatar")
                    )
            }
    )
    List<Answer> findreplys(int mid, User user);
    @Select("select isevaluated from evaluate where rid=#{rid} and account=#{user.account}")
    Integer findisevaluated(Integer rid,User user);
    @Select("select address from userimage where account=#{account}")
    User findavatar(String account);
}
