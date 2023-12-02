package blossom.project.towelove.shortUrl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.shortUrl.dto
 * @className: ShortResponse
 * @author: Link Ji
 * @description: TODO
 * @date: 2023/12/2 17:47
 * @version: 1.0
 */
@Data
@AllArgsConstructor
public class ShortResponse {
    private int index;

    private String shortUrl;
}
