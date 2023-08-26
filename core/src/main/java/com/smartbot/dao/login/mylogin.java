package com.smartbot.dao.login;
import domain.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface mylogin {
    @Select("select * from login where account=#{account}")
    public User checklogin(User user);
}
