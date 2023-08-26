package com.smartbot.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smartbot.service.NoticeService;
import domain.Notice;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("Notice")
public class NoticeController {
    @Autowired
    NoticeService noticeService;
    @PostMapping
    public int insert(String info)
    {
        return noticeService.insert(info);
    }
    @GetMapping
    public List<Notice> select()
    {
        LambdaQueryWrapper<Notice> lambdaQueryWrapper=new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(Notice::getDeleteFlag,0);
        List<Notice> list= noticeService.mapper().selectList(lambdaQueryWrapper);
        list.sort(new Comparator<Notice>() {
            @Override
            public int compare(Notice o1, Notice o2) {
                Date time1=o1.getUpdateTime();
                Date time2=o2.getUpdateTime();
                return time2.compareTo(time1);
            }
        });
        return list;
    }
    @PutMapping
    public int update(String info)
    {
        return noticeService.update(info);
    }

}
