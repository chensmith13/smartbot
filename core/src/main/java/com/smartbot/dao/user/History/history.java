package com.smartbot.dao.user.History;

import domain.History;
import domain.Question;
import domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface history {
    @Select("select count(*) from message,reply,history where history.account=#{account} and message.mid=history.mid and reply.rid= history.rid")
    int findnum(User user);
    @Select("select mcontent,rcontent,hid,htime from message,reply,history where history.account=#{user.account} and message.mid=history.mid and reply.rid= history.rid order by htime desc limit #{over},#{row}")
    List<History> findsolved(User user, int over, int row);
    @Select("select count(*) from message where message.account =#{account} and message.isreply=0")
    int findunsolvednum(User user);
    @Select("select mid,mcontent,mtime from message where message.account =#{user.account} and message.isreply=0 order by mtime limit #{over},#{row}")
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
    List<Question> findunsolved(User user, int over, int row);
}
