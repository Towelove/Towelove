package blossom.project.towelove.community.req;

import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import javax.validation.constraints.NotNull;

/**
 * @author: ZhangBlossom
 * @date: 2024-06-08 22:47:19
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
public class CommentLikesUpdateRequest  {
    
    @NotNull
    private Long id;
}


