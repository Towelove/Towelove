package blossom.project.towelove.community.req;

import java.util.Map;

import blossom.project.towelove.community.entity.posts.UserInfo;
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
public class PostsCreateRequest  {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private String remark;

    private Map<String,Object> jsonMap;

    // 前端传递的用户信息
    @NotNull
    private UserInfo userInfo;
}