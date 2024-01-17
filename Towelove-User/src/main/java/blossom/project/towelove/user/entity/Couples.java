package blossom.project.towelove.user.entity;

import java.util.Date;

import java.io.Serializable;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

/**
 * @author: ZhangBlossom
 * @date: 2024-01-17 13:36:03
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * @description:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "couples", autoResultMap = true)
public class Couples {
    //主键,伴侣id@TableId
    private Long id;

    //女方id
    private Long girlId;
    //男方id
    private Long boyId;
    //账号状态（0正常 1分手 ...其他扩展状态）
    private Integer status;
    //创建人的用户id
    private String createBy;
    //创建时间
    private Date createTime;
    //更新人
    private String updateBy;
    //更新时间
    private Date updateTime;
    //删除标志（0代表未删除，1代表已删除）
    private Integer deleted;
    //备注
    private String remark;
    //json集合，存储额外数据
    private Map<String, Object> jsonMap;

}



