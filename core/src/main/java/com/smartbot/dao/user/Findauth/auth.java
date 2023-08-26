package com.smartbot.dao.user.Findauth;

import domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface auth {
    @Select("select count(*) from reply where account=#{account}")
    int findallnum(User user);
    @Select("select count(*) from reply where rrank>100 and account=#{account}")
    int findnum(User user);
    @Select("select isaccepted from application where account=#{account}")
    Integer findisaccepted(User user);
    @Update("update application set isaccepted=1,num=#{num} where account=#{user.account}")
    Integer updateapply(User user,int num);
    @Insert("insert into application values(#{user.account},1,#{num})")
    Integer insertapply(User user,int num);
    @Select("select count(*) from reply where account=#{account} and rrank>=100")
    int findmynum(User user);
}
