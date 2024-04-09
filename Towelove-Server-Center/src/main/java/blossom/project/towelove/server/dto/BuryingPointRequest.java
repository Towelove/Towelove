package blossom.project.towelove.server.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.server.dto
 * @className: BuryingPointRequest
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/4/8 14:07
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuryingPointRequest {
    /**
     * 事件id
     * 1.统计用户点击事件
     * 2.统计用户停留时间事件
     * 3.统计用户来源事件
     */
    @NotNull(message = "事件id不得为空")
    @Min(value = 1,message = "事件id违法")
    @Max(value = 3,message = "事件id违法")
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

}
