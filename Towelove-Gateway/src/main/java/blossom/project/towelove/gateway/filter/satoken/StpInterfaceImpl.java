package blossom.project.towelove.gateway.filter.satoken;

import blossom.project.towelove.client.serivce.user.RemoteUserService;
import blossom.project.towelove.common.domain.dto.SysUser;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.common.response.user.SysUserPermissionDto;
import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson2.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 自定义权限验证接口扩展 
 */
@Component
public class StpInterfaceImpl implements StpInterface {
    @Resource
    private RemoteUserService userService;

    ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        //WebFlux调用Feign必须使用线程池
        //从user模块获得用户权限信息
        Future< Result<List<SysUserPermissionDto>>> future = singleThreadPool.submit(() -> {
            String loginIdAsString = StpUtil.getLoginIdAsString();
            SysUser sysUser = JSON.parseObject(loginIdAsString, SysUser.class);
            return userService.getUserPermissionByUserId(sysUser.getId());
        });
        try {
            Result<List<SysUserPermissionDto>> result =  future.get(3,TimeUnit.SECONDS);
            if (HttpStatus.OK.value() == result.getCode()){
                return result.getData().stream().map(SysUserPermissionDto::getPermission).collect(Collectors.toList());
            }
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
        return List.of();
    }

    /**
     * 暂时不做角色管理
     * @param o
     * @param s
     * @return
     */
    @Override
    public List<String> getRoleList(Object o, String s) {
        return null;
    }
}
