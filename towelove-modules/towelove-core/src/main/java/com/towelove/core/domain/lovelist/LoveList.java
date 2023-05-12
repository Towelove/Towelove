package com.towelove.core.domain.lovelist;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;


/**
 * 代办列表(LoveList) 实体类
 *
 * @author 张锦标
 * @since 2023-05-12 19:33:57
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("love_list")
public class LoveList {
    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 恋爱相册的id
     */
    private Long loveAlbumId;
    /**
     * 标题
     */
    @NotNull
    private String title;
    /**
     * 是否完成
     */
    private String done;
    /**
     * 完成的合照
     */
    private String photo;
    /**
     * 完成的日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date finishTime;
    /**
     * 是否删除
     */
    @TableLogic(value = "0",delval = "1")
    private Integer deleted;

}

