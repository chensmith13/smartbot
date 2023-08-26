package com.smartbot.dao.admin.Findcommunity;

import domain.Question;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface findcommunity {
    @Delete("delete from message where mid=#{mid}")
    Integer deletemessage(Question question);
}
