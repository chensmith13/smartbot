package com.smartbot.controller.User;

import com.smartbot.service.User.community.Usercommunity;
import domain.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usercommunity")
public class usercommunity {
    private Usercommunity usercommunity;

    public usercommunity(Usercommunity usercommunity) {
        this.usercommunity = usercommunity;
    }

    @PostMapping("/query")
    public Result query(@RequestBody Pagebean pagebean, HttpSession session)
    {
        User user=(User) session.getAttribute("userinfo");
     return usercommunity.querycommunity(pagebean,user);
    }
    @PostMapping("/querydetail")
    public Result querydetail(@RequestBody Question question, HttpSession session)
    {
        User user=(User) session.getAttribute("userinfo");
       return usercommunity.querydetail(question.getMid(),user);
    }
    @PostMapping("/mygood")
    public Result mygood(@RequestBody Answer answer,HttpSession session)
    {
        User user=(User) session.getAttribute("userinfo");
        return usercommunity.good(answer.getRid(), user,answer.getIsevaluated());
    }
    @PostMapping("/mybad")
    public Result mybad(@RequestBody Answer answer,HttpSession session)
    {
        User user=(User) session.getAttribute("userinfo");
        return usercommunity.bad(answer.getRid(), user,answer.getIsevaluated());
    }
    @PostMapping("/newreply")
    public Result newreply(@RequestBody Answer answer,HttpSession session)
    {
        User user=(User) session.getAttribute("userinfo");
        answer.setUser(user);
       return usercommunity.newreply(answer);
    }
    @PostMapping("/updatereply")
    public Result update(@RequestBody Answer answer,HttpSession session)
    {
        User user=(User) session.getAttribute("userinfo");
        answer.setUser(user);
        return usercommunity.updatereply(answer);
    }
    @PostMapping("/delete")
    public Result delete(@RequestBody Answer answer)
    {
        return usercommunity.deletereply(answer);
    }
    @PostMapping("/historygood")
    public Result historygood(@RequestBody Answer answer,HttpSession session)
    {
        User user=(User) session.getAttribute("userinfo");
        return usercommunity.historygood(answer,user);
    }
}
