package blossom.project.towelove.common.response.love.diary;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
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

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.common.response.love.diary
 * @className: DiaryCollectionDTO
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/2/20 16:41
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiaryCollectionDTO {


    private Long id;


    /**
     * 用户id
     */
    private Long userId;



    /**
     * 封面
     */

    private String cover;

    /**
     * 标题
     */

    private String title;


    private Integer status;

    @TableField(value = "create_time",fill = FieldFill.INSERT)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
