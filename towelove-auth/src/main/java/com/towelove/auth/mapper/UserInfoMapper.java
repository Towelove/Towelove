package com.towelove.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.towelove.auth.form.UserInfo;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface UserInfoMapper extends BaseMapper<UserInfo> {
}
