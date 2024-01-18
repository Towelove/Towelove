package blossom.project.towelove.user.entity;

import java.util.Date;

import java.io.Serializable;
import java.util.Map;

import blossom.project.towelove.framework.mysql.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
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
public class Couples extends BaseEntity {
    //主键,伴侣id@TableId
    private Long id;

    //女方id
    private Long girlId;
    //男方id
    private Long boyId;

}



