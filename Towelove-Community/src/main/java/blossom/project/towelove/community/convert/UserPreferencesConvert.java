package blossom.project.towelove.community.convert;


import blossom.project.towelove.community.dto.UserPreferencesRespDTO;
import blossom.project.towelove.community.entity.UserPreferences;
import blossom.project.towelove.community.req.UserPreferencesCreateRequest;
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
public interface UserPreferencesConvert {
      UserPreferencesConvert INSTANCE = Mappers.getMapper(UserPreferencesConvert.class);

UserPreferences convert(UserPreferencesCreateRequest createRequest);
UserPreferencesRespDTO convert(UserPreferences lists);

List<UserPreferencesRespDTO> convert(List<UserPreferences> records);

  
}



