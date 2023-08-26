package com.smartbot.service.User.Userhome;

import domain.*;
import org.springframework.web.multipart.MultipartFile;

public interface Userhome {
    public Result hotest();
    public Result query(Question question);
    public Result submitcommunity(QA qa);
    public Result satisfied(QA answer);
    public Result unsatisfied(Answer answer);
    public Result getavatar(User user);
    public Result addhot(Question question);
}
