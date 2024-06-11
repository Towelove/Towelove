package blossom.project.towelove.community.req;

import java.util.List;
import java.util.Map;

import blossom.project.towelove.community.entity.inner.InteractInfo;
import blossom.project.towelove.community.entity.inner.UserInfo;
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

    @NotBlank(message = "文章标题不能为空")
    private String title;

    @NotBlank(message = "文章内容不能为空")
    private String content;

    @NotNull(message = "用户信息不能为空")
    private UserInfo userInfo;

    private InteractInfo interactInfo;

    // 文章图片信息，以List<String>格式存储
    private List<String> imageList;

    // 文章标签信息，以List<String>格式存储
    private List<String> tagList;

    private Map<String, Object> jsonMap;
}