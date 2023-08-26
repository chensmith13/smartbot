package com.smartbot.dao.user.Changeavatar;

import domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface changeavatar {
    @Select("select * from userimage where account=#{account}")
    public User findnum(User user);
    @Insert("insert into userimage values(#{account},#{address})")
    public Integer insert_avatar(User user);
    @Update("update userimage set address=#{address} where account=#{account}")
    public  Integer update_avatar(User user);
}
