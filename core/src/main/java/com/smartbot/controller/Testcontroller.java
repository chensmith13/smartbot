package com.smartbot.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Random;

@RestController
public class Testcontroller {
    @RequestMapping("/test")
    public String test(@RequestBody List<String> name)
    {
        System.out.println("1111"+name);
        return "{name}"+name;
    }
    @PostMapping("/register")
    public String register(@RequestBody String a)
    {
        System.out.println(a);
        return a;
    }
}
