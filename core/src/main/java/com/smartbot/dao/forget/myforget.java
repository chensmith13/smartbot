package com.smartbot.dao.forget;

import domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
@Mapper
public interface myforget {
    @Select("select * from login where account=#{account} and email=#{email}")
    public User querypassword(User user);
}
