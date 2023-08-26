package com.smartbot.dao.user.Email;

import domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface email {
    @Select("select * from login where account=#{account}")
    User finduserinfo(User user);
    @Update("update login set email=#{email} where account=#{account}")
    Integer changeemail(User user);
}
