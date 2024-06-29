package blossom.project.towelove.comment.entity;

import blossom.project.towelove.common.entity.inner.PictureInfo;
import blossom.project.towelove.common.entity.inner.UserInfo;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "comments", autoResultMap = true)
public class Comments {
    // 评论ID
    @TableId
    private Long id;

    // 用户ID
    private Long userId;

    // 文章ID
    private Long postId;

    //评论用户信息
    // 用户信息，存储为JSON
    @TableField(value = "user_info", typeHandler = JacksonTypeHandler.class)
    private UserInfo userInfo;

    // 评论内容
    private String content;

    // 父评论ID，用于支持评论的回复
    private Long parentId;

    // 评论时间
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    // 点赞数量
    @TableField(value = "likes_num")
    private Integer likesNum;

    //是否点赞了
    @TableField(exist = false)
    private Boolean liked;

    // 评论标签（是否 博主自己的评论、是否置顶等）
    @TableField(value = "show_tags", typeHandler = JacksonTypeHandler.class)
    private List<String> showTags;

    // 子评论列表
    @TableField(exist = false)
    private List<Comments> subComments;

    //被@的用户
    @TableField(value = "at_users", typeHandler = JacksonTypeHandler.class)
    private List<UserInfo> atUsers;

    //图片信息集合 回复的评论可以带有图片信息集合
    @TableField(value = "picture_infos", typeHandler = JacksonTypeHandler.class)
    private List<PictureInfo> pictureInfos;

    //当前评论是否有更多的子评论可以展开？
    @TableField(value = "sub_comment_has_more")
    private Boolean subCommentHasMore;

    //子评论数量
    @TableField(value = "sub_comment_count")
    private Integer subCommentCount;


}

