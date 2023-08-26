package com.smartbot.service.User.Avatar;

import com.smartbot.dao.login.mylogin;
import com.smartbot.dao.user.Changeavatar.changeavatar;
import domain.Code;
import domain.Result;
import domain.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class Avatarimp implements Avatar {

    static String path;
    changeavatar change_avatar;

    static {
        path=Avatarimp.class.getClassLoader().getResource("upload/t.file").getPath();
        path=path.substring(1,path.length()-7);
    }
    public Avatarimp(changeavatar change_avatar) {
        this.change_avatar = change_avatar;
    }
    public Result upload(MultipartFile multipartFile, User user) {
            String filename;
            filename= UUID.randomUUID().toString();
            try {
                filename=filename+multipartFile.getOriginalFilename();
                Path filepath=Paths.get(path,filename);
                Files.copy(multipartFile.getInputStream(),filepath);
                //创建文件夹
               // Files.createDirectories(path.getParent());
                //复制文件到指定文件夹下面
                user.setAddress(filename);
                User num=change_avatar.findnum(user);
                //如果找到了
                if(num!=null)
                {
                    change_avatar.update_avatar(user);
                }
                //如果没有就插入并删除原本的数据
                else
                    change_avatar.insert_avatar(user);
                return new Result(Code.SAVE_OK,"上传成功");
            } catch (IOException e) {
               return new Result(Code.SAVE_ERROR,"","上传失败");
            }
    }
}
