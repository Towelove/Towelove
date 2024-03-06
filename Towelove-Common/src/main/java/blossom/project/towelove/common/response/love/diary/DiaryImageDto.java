package blossom.project.towelove.common.response.love.diary;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.common.response.love.diary
 * @className: DiaryImageDto
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/2/23 15:02
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiaryImageDto {
    private Long id;

    private String url;


    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
