package blossom.project.towelove.common.response.love.diary;

import blossom.project.towelove.common.domain.dto.BaseEntity;
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

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.common.response.love.diary
 * @className: DiaryTitleDTO
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/2/20 22:49
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiaryTitleDTO  {
    private Long id;

    private String title;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
