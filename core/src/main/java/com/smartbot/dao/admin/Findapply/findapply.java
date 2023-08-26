package com.smartbot.dao.admin.Findapply;

import domain.Application;
import domain.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface findapply {
    @Select("select count(*) from application where isaccepted=1")
    Integer findnum();
    @Select("select num,name,login.account from login,application where login.account=application.account and isaccepted=1 order by num desc limit #{over},#{row}")
   List<Application> findapplies(int over, int row);
    @Update("update login set status='expert' where account=#{account}")
    Integer updatestatus(User user);
    @Delete("delete from application where account=#{account}")
    Integer deleteapplication(User user);
    @Update("update application set isevaluated=2 where account=#{account}")
    Integer torefuse(User user);

}
