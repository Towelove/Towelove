package blossom.project.towelove.community.req;

import java.util.Date;

import java.io.Serializable;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author: ZhangBlossom
 * @date: 2024-06-08 22:44:29
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
public class PostsUpdateRequest  {
    
    @NotNull
    private Long id;

    @NotBlank(message = "帖子标题不能为空！！！")
    private String title;

    @NotBlank(message = "帖子内容不能为空！！！")
    private String content;

    private String remark;

    private Map<String,Object> jsonMap;
}


