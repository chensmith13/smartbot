package com.smartbot.service.User.Avatar;

import domain.Result;
import domain.User;
import org.springframework.web.multipart.MultipartFile;

public interface Avatar {
    public Result upload(MultipartFile multipartFile, User user);
}
