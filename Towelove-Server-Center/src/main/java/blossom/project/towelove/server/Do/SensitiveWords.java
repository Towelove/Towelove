package blossom.project.towelove.server.Do;

import blossom.project.towelove.framework.mysql.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.server.Do
 * @className: SensitiveWords
 * @author: Link Ji
 * @description: GOGO
 * @date: 2023/12/18 1:46
 * @version: 1.0
 */
@TableName("sensitive_words")
@Data
public class SensitiveWords extends BaseEntity {
    private Long id;

    private String word;
}
