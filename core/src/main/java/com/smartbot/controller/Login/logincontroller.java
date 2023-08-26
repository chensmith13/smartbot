package com.smartbot.controller.Login;

import domain.Code;
import domain.Result;
import domain.User;
import com.smartbot.service.Login.Login;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@RequestMapping("/login")
@RestController
public class logincontroller {
    @Autowired
    Login login;
    @PostMapping("/logincheck")
    public Result logincheck( HttpSession session,@RequestBody User user)
    {

        String checkcode=(String) session.getAttribute("checkcode");
        Result result= login.checklogin(user,checkcode);
        if(result.getCode()== Code.Get_OK)
        {
            session.setAttribute("userinfo",result.getData());
        }
        return result;
    }
    @RequestMapping("/checkcode")
    public String checkcode(HttpSession session,HttpServletResponse response) throws IOException {
        int width=150;int height=40;
        BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics g= image.getGraphics();
        g.setColor(Color.GRAY);
        g.fillRect(0,0,width,height);
        g.setColor(Color.YELLOW);
        g.drawRect(0,0,width-1,height-1);
        String s="qwertyuiopasdfghjklzxcvbnm1234567890QWERTYUIOPASDFGHJKLZXCVBNM";
        Random ran=new Random();
        StringBuilder sb=new StringBuilder();
        g.setFont(new Font("Arial",Font.BOLD,30));
        for(int i=0;i<4;++i)
        {
            int index=ran.nextInt(s.length());
            char ch=s.charAt(index);
            sb.append(ch);
        }
        g.drawString(String.valueOf(sb),40,25);
        session.setAttribute("checkcode",String.valueOf(sb));
        ImageIO.write(image,"jpg",response.getOutputStream());
        return "success";
    }
}
