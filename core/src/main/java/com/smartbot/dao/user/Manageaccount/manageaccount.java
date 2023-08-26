package com.smartbot.dao.user.Manageaccount;
import domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface manageaccount {
    @Update("update login set password=#{password} where account=#{account}")
    Integer changepassword(User user);
}
