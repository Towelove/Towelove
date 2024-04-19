package blossom.project.towelove.framework.flower.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: 张锦标
 * @date: 2024/4/19 11:35 AM
 * ToweloveAlbumRequest类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToweloveAlbumRequest extends BaseRequest{
    private Long id;

    private String photoUrls;

    private String description;
}
