package blossom.project.towelove.user.interceptor;

import blossom.project.towelove.common.constant.TokenConstant;
import blossom.project.towelove.framework.redis.service.RedisService;
import blossom.project.towelove.user.entity.SysUser;
import blossom.project.towelove.user.mapper.SysUserMapper;
import cn.hutool.core.util.StrUtil;
import com.towelove.common.core.constant.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.user
 * @className: inteceptor
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/1/19 14:36
 * @version: 1.0
 */

public class UserInfoInterceptor implements HandlerInterceptor {
    Logger log = LoggerFactory.getLogger(UserInfoInterceptor.class);
    private final RedisService redisService;

    private final SysUserMapper sysUserMapper;

    public UserInfoInterceptor(RedisService redisService,SysUserMapper sysUserMapper) {
        this.redisService = redisService;
        this.sysUserMapper = sysUserMapper;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (StrUtil.isNotBlank(request.getHeader("invoke"))){
            return true;
        }
        String userId = request.getHeader(TokenConstant.USER_ID_HEADER);
        if (StrUtil.isBlank(userId)){
            log.info("请检查网关，用户服务请求头缺少X-User-Id");
            response.setStatus(HttpStatus.FORBIDDEN);
            response.getOutputStream().print("access error!!! userInfo can not be null");
            return false;
        }
        //查询用户信息
        SysUser cacheObject = redisService.getCacheObject(TokenConstant.USER_ID_HEADER + userId);
        if (Objects.isNull(cacheObject)) {
            cacheObject = sysUserMapper.selectById(userId);
            redisService.setCacheObject(TokenConstant.USER_ID_HEADER + userId,cacheObject);
        }
        UserInfoContextHolder.setUserInfo(cacheObject);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        UserInfoContextHolder.clean();
    }
}
