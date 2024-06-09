package blossom.project.towelove.community.convert;


import blossom.project.towelove.community.dto.UserActionsRespDTO;
import blossom.project.towelove.community.entity.UserActions;
import blossom.project.towelove.community.req.UserActionsCreateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author: ZhangBlossom
 * @date: 2024-06-09 00:08:01
 * @contact: QQ:4602197553
 * @contact: WX:zhangblossom0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * @description: 
 */

@Mapper
public interface UserActionsConvert {
      UserActionsConvert INSTANCE = Mappers.getMapper(UserActionsConvert.class);

UserActions convert(UserActionsCreateRequest createRequest);
UserActionsRespDTO convert(UserActions lists);

List<UserActionsRespDTO> convert(List<UserActions> records);

  
}



