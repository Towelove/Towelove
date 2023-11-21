package blossom.project.towelove.gateway.filter;

import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.core.collection.CollUtil;
import com.titi.feign.client.UserServiceClient;
import com.titi.titicommon.DTO.UserPermissionDto;
import com.titi.titicommon.result.ResponseResult;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义权限验证接口扩展 
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        //从user模块获得用户权限信息
        return Arrays.asList("user","admin");
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
