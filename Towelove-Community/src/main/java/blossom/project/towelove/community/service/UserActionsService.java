package blossom.project.towelove.community.service;


import blossom.project.towelove.common.page.PageResponse;
import com.baomidou.mybatisplus.extension.service.IService;
import blossom.project.towelove.community.entity.UserActions;
import blossom.project.towelove.community.dto.UserActionsRespDTO;
import blossom.project.towelove.community.req.UserActionsCreateRequest;
import blossom.project.towelove.community.req.UserActionsPageRequest;
import blossom.project.towelove.community.req.UserActionsUpdateRequest;

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

public interface UserActionsService extends IService<UserActions> {
    
    UserActionsRespDTO getUserActionsById(Long UserActionsId);
    
    UserActionsRespDTO getUserActionsDetailById(Long userActionsId);

    PageResponse<UserActionsRespDTO> pageQueryUserActions(UserActionsPageRequest requestParam);

    UserActionsRespDTO updateUserActions(UserActionsUpdateRequest updateRequest);

    Boolean deleteUserActionsById(Long UserActionsId);

    Boolean batchDeleteUserActions(List<Long> ids);

    UserActionsRespDTO createUserActions(UserActionsCreateRequest createRequest);
}



