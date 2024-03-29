package blossom.project.towelove.common.response.todoList;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author wangLele
 * @Mail 1819220754@qq.com
 * @date 下午2:57 4/12/2023
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodoListRespDTO {
    @TableId
    private Long id;

    //用户id  user_id  loves_id
    private Long coupleId;

    private Long msgTaskId;

    //标题
    private String title;

    //描述
    private String description;

    //截止日期
    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime deadline;

    //完成日期
    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime completionDate;

    /**
     * @Comment("是否提醒")
     */
    private boolean reminder;

    /**
     * @Comment("是否小組件")
     */
    private boolean widget;

    //todo实体完成状态，如果完成，为1，否则为0
    private Integer status;
}
