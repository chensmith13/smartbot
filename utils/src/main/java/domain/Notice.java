package domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class Notice {
    private Date createTime;
    private Date updateTime;
    @TableId(type = IdType.AUTO)
    private int id;
    private String info;
    private  Boolean deleteFlag;
}
