package com.smartbot.dao.register;

import domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface myregister{
    @Select("select account from login where account =#{account}")
    public User registercheck(User user);
    @Insert("insert into login values(#{account},#{password},#{status},#{name},#{email})")
    public boolean register(User user);
}
