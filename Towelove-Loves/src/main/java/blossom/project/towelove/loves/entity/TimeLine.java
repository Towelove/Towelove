package blossom.project.towelove.loves.entity;

import java.time.LocalDateTime;
import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

/**
 * (Timeline) 表实体类
 * 用于表示时间线上的一个事件，包括事件标题、描述、发生的日期等信息。
 *
 * @author SIK
 * @since 2023-12-1 17:03:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("timeline_event")
public class TimeLine {
    // 事件编号
    @TableId
    private Long id;

    // 用户ID
    private Long userId;

    // 事件标题
    private String title;

    // 事件描述，存储JSON格式的描述信息
    private String eventDesc;

    // 事件日期
    private LocalDateTime eventDate;

    // 事件封面图片URL
    private String coverUrl;

    // 与事件相关的图片URL，可能用逗号分隔存储多个URL
    private String photoUrls;

    // 事件的创建时间
    private LocalDateTime createTime;

    // 事件的更新时间
    private LocalDateTime updateTime;

    // 事件的状态，例如0为隐藏，1为公开
    private Integer status;

    // 事件的点赞数量
    private Integer likesNumber;

    // 事件的评论数量
    private Integer commentsNumber;

    // 是否删除标记，例如0为未删除，1为已删除
    @TableLogic(value = "0",delval = "1")
    private Integer deleted;

    // 事件备注
    private String remark;


}
