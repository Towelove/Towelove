package com.towelove.system.service.user;

import com.towelove.system.api.domain.SysUser;
import com.towelove.system.api.model.LoginUser;
import com.towelove.system.domain.user.UserInfoBaseVO;
import io.minio.GetObjectResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: 张锦标
 * @date: 2023/4/9 20:35
 * UserInfoService接口
 */
public interface UserInfoService {
    SysUser uploadAvatar(Long userId,MultipartFile multipartFile);

    SysUser updateUserInfo(UserInfoBaseVO baseVO);
    GetObjectResponse downloadAvatar(Long userId);

}
