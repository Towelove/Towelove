package blossom.project.towelove.gateway.filter.satoken;

import blossom.project.towelove.client.serivce.user.RemoteUserService;
import blossom.project.towelove.common.constant.SecurityConstant;
import blossom.project.towelove.common.domain.dto.SysUser;
import blossom.project.towelove.common.exception.ServiceException;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.common.response.user.LoginUserResponse;
import blossom.project.towelove.common.response.user.SysUserPermissionDto;
import blossom.project.towelove.gateway.config.UserContextHolder;
import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.exception.BackResultException;
import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson2.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 自定义权限验证接口扩展 
 */
@Component
public class StpInterfaceImpl implements StpInterface {
    Logger log = LoggerFactory.getLogger(StpInterfaceImpl.class);


    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
////        WebFlux调用Feign必须使用线程池
////        从user模块获得用户权限信息
//        String loginIdAsString = StpUtil.getLoginIdAsString();
//        SysUser sysUser = JSON.parseObject(loginIdAsString, SysUser.class);
//        Future< Result<SysUserPermissionDto>> future = singleThreadPool.submit(() ->
//                userService.getUserPermissionByUserId(sysUser.getId())
//        );
//        Result<SysUserPermissionDto> result = null;
//        try {
//             result =  future.get(5,TimeUnit.SECONDS);
//            if (Objects.isNull(result) || Objects.isNull(result.getData())){
//                log.info("[{}]用户查询权限为空",sysUser.getId());
//                //需要自行设置响应头
//                SaHolder.getResponse().setHeader("Content-Type", "application/json;charset=UTF-8");
//                throw new BackResultException(JSON.toJSONString(Result.fail(HttpStatus.FORBIDDEN.getReasonPhrase(),HttpStatus.FORBIDDEN.value(),"无权限",SecurityConstant.REQUEST_ID)));
//            }
//        } catch (InterruptedException | ExecutionException | TimeoutException e) {

//        }
        LoginUserResponse loginUserResponse = JSON.parseObject(loginId.toString(),LoginUserResponse.class);
        SaHolder.getStorage().set("user",loginUserResponse);
        List<String> userPermission = loginUserResponse.getUserPermission();
        if (Objects.isNull(userPermission) || userPermission.isEmpty()){
            SaHolder.getResponse().setHeader("Content-Type", "application/json;charset=UTF-8");
            throw new BackResultException(JSON.toJSONString(Result.fail(HttpStatus.FORBIDDEN.getReasonPhrase(),HttpStatus.FORBIDDEN.value(),"无权限",SecurityConstant.REQUEST_ID)));
        }
        return userPermission;
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
