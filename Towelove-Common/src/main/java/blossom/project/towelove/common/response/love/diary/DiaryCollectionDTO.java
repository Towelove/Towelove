package blossom.project.towelove.common.response.love.diary;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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


    private Long coupleId;

    /**
     * 封面
     */

    private String cover;

    /**
     * 标题
     */

    private String title;


    private Integer status;
}
