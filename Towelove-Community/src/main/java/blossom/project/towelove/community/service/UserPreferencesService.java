package blossom.project.towelove.community.service;


import blossom.project.towelove.common.page.PageResponse;
import com.baomidou.mybatisplus.extension.service.IService;
import blossom.project.towelove.community.entity.UserPreferences;
import blossom.project.towelove.community.dto.UserPreferencesRespDTO;
import blossom.project.towelove.community.req.UserPreferencesCreateRequest;
import blossom.project.towelove.community.req.UserPreferencesPageRequest;
import blossom.project.towelove.community.req.UserPreferencesUpdateRequest;

import java.util.List;


/**
 * @author: ZhangBlossom
 * @date: 2024-06-09 00:08:01
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * @description: 
 */

public interface UserPreferencesService extends IService<UserPreferences> {
    
    UserPreferencesRespDTO getUserPreferencesById(Long UserPreferencesId);
    
    UserPreferencesRespDTO getUserPreferencesDetailById(Long userPreferencesId);

    PageResponse<UserPreferencesRespDTO> pageQueryUserPreferences(UserPreferencesPageRequest requestParam);

    UserPreferencesRespDTO updateUserPreferences(UserPreferencesUpdateRequest updateRequest);

    Boolean deleteUserPreferencesById(Long UserPreferencesId);

    Boolean batchDeleteUserPreferences(List<Long> ids);

    UserPreferencesRespDTO createUserPreferences(UserPreferencesCreateRequest createRequest);
}



