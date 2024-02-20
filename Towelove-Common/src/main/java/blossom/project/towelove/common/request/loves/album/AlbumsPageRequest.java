package blossom.project.towelove.common.request.loves.album;

import blossom.project.towelove.common.page.PageRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author: ZhangBlossom
 * @date: 2024/1/17 14:07
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlbumsPageRequest extends PageRequest {

    @NotNull(message = "coupleId can not be null!!!")
    private Long coupleId;

    private Long pageSize = 14L;

}
