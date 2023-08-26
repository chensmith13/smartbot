package com.smartbot.starter;

import com.smartbot.dao.login.mylogin;
import com.smartbot.dao.user.Usercommunity.community;
import com.smartbot.dao.user.Usercommunity.findreply;
import com.smartbot.dao.user.Userhome.myuserhome;
import domain.Answer;
import domain.Question;
import domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartbotApplication.class)
public class test {
    @Autowired
    myuserhome my1;
    @Autowired
    findreply us;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Test
    public void tet()
    {
      List<String>list=stringRedisTemplate.opsForList().range("hot",0,7);
        System.out.println(list.size());
        List<Question> mylist=new ArrayList<>();
        for (String s : list) {
            Question question=new Question();
            question.setMid(Integer.valueOf(s));
            question.setMcontent((String) stringRedisTemplate.opsForHash().get(s,"mcontent"));
            question.setHot(Integer.valueOf((String) stringRedisTemplate.opsForHash().get(s,"hot") ));
           mylist.add(question);
        }
    }
    @Test
    public void updateredis()
    {
        List<String>list=stringRedisTemplate.opsForList().leftPop("hot",5);
        for (String s : list) {
            stringRedisTemplate.opsForHash().delete(s,"hot");
            stringRedisTemplate.opsForHash().delete(s,"mcontent");
        }
        List<Question> list1=my1.hotest();
        for (Question question : list1) {
            String mid=String.valueOf(question.getMid());
            stringRedisTemplate.opsForList().rightPush("hot",mid);
            stringRedisTemplate.opsForHash().put(mid,"hot",String.valueOf(question.getHot()));
            stringRedisTemplate.opsForHash().put(mid,"mcontent",question.getMcontent());
        }
        System.out.println(stringRedisTemplate.opsForList().size("hot"));
    }
}
