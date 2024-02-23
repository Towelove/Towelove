package blossom.project.towelove.common.request.loves.diary;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.common.request.loves.diary
 * @className: DiaryCollectionCreateRequest
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/2/20 17:07
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiaryCollectionCreateRequest {


    @NotNull(message = "cover could not be null")
    private String cover;

    private String title;

}
