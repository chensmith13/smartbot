package com.smartbot.controller.User;

import com.smartbot.service.User.Userhome.Userhome;
import com.smartbot.service.User.Userhome.Userhomeimp;
import domain.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/userhome")
@RestController
public class userhomecontroller {

    private Userhome userhome;

    public userhomecontroller(Userhomeimp user) {
        this.userhome = user;
    }

    @GetMapping("/hotest")
    public Result findhotest()
    {
        return userhome.hotest();
    }
    @PostMapping("/query")
    public Result query(@RequestBody Question question)
    {
       question.setMcontent(question.getMcontent().replaceAll("<br>",""));
        return  userhome.query(question);
    }
    @PutMapping("/submit")
    public Result submit(@RequestBody QA qa, HttpSession session)
    {
        qa.setUser((User) session.getAttribute("userinfo"));
        return userhome.submitcommunity(qa);
    }
    @PatchMapping("/satisfied")
    public Result satisfied(@RequestBody QA qa,HttpSession session)
    {
        qa.setUser((User) session.getAttribute("userinfo"));
        return userhome.satisfied(qa);
    }
    @PatchMapping("/unsatisfied")
    public Result unsatisfied(@RequestBody Answer answer)
    {
        return userhome.unsatisfied(answer);
    }
    @PatchMapping("/addhot")
    public Result addhot(@RequestBody Question question)
    {
        return  userhome.addhot(question);
    }

}
