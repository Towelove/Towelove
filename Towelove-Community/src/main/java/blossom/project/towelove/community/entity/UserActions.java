package blossom.project.towelove.community.entity;

import java.time.LocalDateTime;
import java.util.Date;

import blossom.project.towelove.community.enums.ActionType;
import blossom.project.towelove.framework.mysql.domain.BaseEntity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import org.apache.ibatis.type.EnumTypeHandler;

/**
 * @author: ZhangBlossom
 * @date: 2024-06-09 00:08:01
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
@TableName(value = "user_actions", autoResultMap = true)
public class UserActions{
    //行为ID
    @TableId
    private Long id;

    //用户ID
    private Long userId;

    //文章ID
    private Long postId;

    @TableField(value = "action_type", typeHandler = EnumTypeHandler.class)
    private ActionType actionType;

    //行为时间
    private LocalDateTime actionTime;

    //用户行为状态（0，已计算数据，1未计算数据）
    private Integer status;

    //删除标志（0代表未删除，1代表已删除）
    @TableLogic(value = "0",delval = "1")
    private Integer deleted;

}




