package com.smartbot.dao.user;

import domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface myuser {
    @Select("select * from userimage where account=#{account}")
    public User findavatar(User user);
}
