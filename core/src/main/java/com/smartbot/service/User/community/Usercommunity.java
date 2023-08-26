package com.smartbot.service.User.community;

import domain.*;

public interface Usercommunity {
    public Result querycommunity(Pagebean pagebean, User user);
    public Result querydetail(int mid,User user);
    public Result good(int rid,User user,int isevaluated);
    public Result bad(int rid,User user,int isevaluated);
    public Result newreply(Answer answer);
    public Result updatereply(Answer answer);
    public Result deletereply(Answer answer);
    public Result historygood(Answer answer,User user);
}
