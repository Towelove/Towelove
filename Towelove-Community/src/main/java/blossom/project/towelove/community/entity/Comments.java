package blossom.project.towelove.community.entity;

import java.util.Date;

import java.io.Serializable;

import blossom.project.towelove.framework.mysql.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

/**
 * @author: ZhangBlossom
 * @date: 2024-06-08 19:42:04
 * @contact: QQ:4602197553
 * @contact: WX:zhangblossom0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * @description:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "comments", autoResultMap = true)
public class Comments  {
    //评论ID@TableId
    private Long id;

    //用户ID
    private Long userId;
    //文章ID
    private Long postId;
    //评论内容
    private String content;
    //父评论ID，用于支持评论的回复
    private Long parentId;
    //评论时间
    private Date createTime;
    //评论更新时间
    private Date updateTime;
    //删除标志（0代表未删除，1代表已删除）
    private Integer deleted;
    //备注
    private String remark;
    //json集合，存储额外数据
    private String jsonMap;

}




