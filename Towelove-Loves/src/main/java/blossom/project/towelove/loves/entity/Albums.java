package blossom.project.towelove.loves.entity;

import java.time.LocalDateTime;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
/**
 * @author: ZhangBlossom
 * @date: 2024/1/17 13:06
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * OssController类
 * 当前类用于提供给项目统一的进行文件上传操作
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(autoResultMap = true,value = "albums")
public class Albums  {
    //主键, 相册id

    @TableId
    private Long id;

    //伴侣id
    private Long coupleId;

    //相册标题
    private String title;

    //封面图片URL
    private String coverUrl;

    //相册照片url
    private String photoUrls;

    //图片数量
    private Long photoNums = 0L;


    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField("deleted")
    @TableLogic(value = "0", delval = "1")
    private int deleted;

    @TableField(value = "remark",fill = FieldFill.INSERT)
    private String remark;

    @TableField(value = "status",fill = FieldFill.INSERT)
    private Integer status;

    //json集合，存储额外数据
    @TableField(value = "json_map",typeHandler = JacksonTypeHandler.class)
    private Map<String,Object> jsonMap;

}



