package blossom.project.towelove.server.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.server.entity
 * @className: BuryingPoint
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/4/8 14:41
 * @version: 1.0
 */
@Data
@TableName("burying_point")
@AllArgsConstructor
@NoArgsConstructor
public class BuryingPoint {

    private Long id;

    /**
     * 事件id
     * 1.统计用户点击事件
     * 2.统计用户停留时间事件
     * 3.统计用户来源事件
     */
    private Integer eventId;

    /**
     * 点击事件使用
     * 元素Id 前端自规定
     */
    private String elementId;

    /**
     * 页面停留时间
     * 页面id，可以使用url
     */
    private String pageId;
    /**
     * 页面停留时间
     */
    private Long stayTime;

    /**
     * 用户来源：二维码扫码，介绍页，输入url链接
     */
    private Integer source;

    /**
     * 浏览器指纹
     */
    private String fingerprint;


    @TableField(value = "create_time",fill = FieldFill.INSERT)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;

}
