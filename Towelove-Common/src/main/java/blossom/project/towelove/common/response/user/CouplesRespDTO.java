package blossom.project.towelove.common.response.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author: ZhangBlossom
 * @date: 2024-01-17 13:36:03
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
public class CouplesRespDTO {

    private Long id;

    private Long girlId;

    private Long boyId;

    private Integer status;

}


