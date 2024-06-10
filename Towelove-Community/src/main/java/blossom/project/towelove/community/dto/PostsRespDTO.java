package blossom.project.towelove.community.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import blossom.project.towelove.community.entity.posts.InteractInfo;
import blossom.project.towelove.community.entity.posts.UserInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author: ZhangBlossom
 * @date: 2024-06-08 22:44:29
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
public class PostsRespDTO {

    private Long id;

    private String title;

    private String content;

    // JSON 转换后的用户信息
    private UserInfo userInfo;

    // JSON 转换后的图片列表
    private List<String> imageList;

    // JSON 转换后的标签列表
    private List<String> tagList;

    // JSON 转换后的交互信息
    private InteractInfo interactInfo;

    private Integer status;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    private Integer deleted;

    private Map<String, Object> jsonMap;

    private Integer pv;


}
