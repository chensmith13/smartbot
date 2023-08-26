package com.smartbot.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smartbot.dao.NoticeMapper;
import domain.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeService {
    @Autowired
    private NoticeMapper noticeMapper;

    public BaseMapper<Notice> mapper()
    {
        return  noticeMapper;
    }
    public int insert(String notice)
    {
        Notice notice1=new Notice();
        notice1.setInfo(notice);
        notice1.setDeleteFlag(false);
        return noticeMapper.insert(notice1);
    }
    public List<Notice> select()
    {
        return noticeMapper.selectList(null);
    }
    public int update(String info)
    {
        Notice notice=new Notice();
        notice.setId(3);
        notice.setInfo(info);
        return noticeMapper.updateById(notice);
    }
}
