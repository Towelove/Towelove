package com.towelove.system.service.user;

import com.towelove.common.core.mybatis.LambdaQueryWrapperX;
import com.towelove.common.minio.MinioService;
import com.towelove.system.api.domain.SysUser;
import com.towelove.system.convert.user.UserInfoConvert;
import com.towelove.system.domain.user.UserInfoBaseVO;
import com.towelove.system.mapper.user.SysUserMapper;
import io.minio.GetObjectResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Objects;

/**
 * @author: 张锦标
 * @date: 2023/4/9 20:41
 * UserInfoServiceImpl类
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private MinioService minioService;
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser uploadAvatar(Long userId, MultipartFile file) {
        try {
            String url = minioService.uploadFile(file);
            int i = sysUserMapper.uploadAvatar(userId, url);
            if (!(i > 0)) {
                throw new RuntimeException("上传用户头像失败。。。");
            }
            SysUser sysUser = getSysUserById(userId);
            return sysUser;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SysUser updateUserInfo(UserInfoBaseVO baseVO) {
        SysUser sysUser =  new SysUser();
        sysUser = UserInfoConvert.INSTANCE.convert(baseVO);
        System.out.println(sysUser);
        //BeanUtils.copyProperties(baseVO,sysUser);
        int i = sysUserMapper.updateById(sysUser);
        if (!(i > 0)) {
            throw new RuntimeException("更新用户数据失败");
        }
        return getSysUserById(baseVO.getUserId());
    }


    @Override
    public GetObjectResponse downloadAvatar(Long userId) {
        try {
            SysUser sysUser = getSysUserById(userId);
            GetObjectResponse file = minioService.getFile(sysUser.getAvatar());
            return file;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private SysUser getSysUserById(Long userId) {
        LambdaQueryWrapperX<SysUser> lqw = new LambdaQueryWrapperX<>();
        lqw.eq(SysUser::getUserId, userId);
        SysUser sysUser = sysUserMapper.selectOne(lqw);
        if (Objects.isNull(sysUser)) {
            throw new NullPointerException("当前用户角色为空！！！，不应该查询不到这个对象");
        }
        return sysUser;
    }
}
