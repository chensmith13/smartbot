package com.smartbot.starter;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.crypto.Data;
import java.util.Date;

@Configuration
public class MybatisPlusConfiguration {
    @Bean
    public TimeMetaObjectHandler timeMetaObjectHandler(){
        return new TimeMetaObjectHandler();
    }

        static class TimeMetaObjectHandler implements MetaObjectHandler
        {
            @Override
            public void insertFill(MetaObject metaObject) {
                Date date=new Date();
                this.setFieldValByName("createTime",date,metaObject);
                this.setFieldValByName("updateTime",date,metaObject);
            }

            @Override
            public void updateFill(MetaObject metaObject) {
                Date date=new Date();
                this.setFieldValByName("updateTime",date,metaObject);
            }
        }
}
