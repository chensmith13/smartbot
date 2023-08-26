package com.smartbot.service.Admin.Managecommunity;

import com.smartbot.dao.admin.Findcommunity.findcommunity;
import domain.Code;
import domain.Question;
import domain.Result;
import org.springframework.stereotype.Service;

@Service
public class Adminmanagementimp implements Adminmanagement{
    findcommunity find;

    public Adminmanagementimp(findcommunity find) {
        this.find = find;
    }

    @Override
    public Result deletemessage(Question question) {
        find.deletemessage(question);
        return new Result(Code.DELETE_OK,"success");
    }
}
