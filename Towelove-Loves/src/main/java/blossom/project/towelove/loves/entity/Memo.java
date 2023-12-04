package blossom.project.towelove.loves.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author: ZhangBlossom
 * @date: 2023/12/4 15:09
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * Memo类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Memo {
    //便签内容
    private String content;

    //便签类型 0：xxx 1：xxx
    private Integer status;

    //便签创建时间
    private LocalDateTime createTime;
}
