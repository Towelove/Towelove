package com.towelove.system.convert.user;

import com.towelove.system.api.domain.SysUser;
import com.towelove.system.convert.mail.MailAccountConvert;
import com.towelove.system.domain.user.UserInfoBaseVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author: 张锦标
 * @date: 2023/4/9 21:50
 * UserInfoConvert类
 */
@Mapper
public interface UserInfoConvert {
    UserInfoConvert INSTANCE = Mappers.getMapper(UserInfoConvert.class);
    //@Mapping(target = "deleted",ignore = true) //忽略isDelete字段
    SysUser convert(UserInfoBaseVO userInfoBaseVO);
}
