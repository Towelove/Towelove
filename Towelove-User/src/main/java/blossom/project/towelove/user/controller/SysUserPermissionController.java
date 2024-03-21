package blossom.project.towelove.user.controller;

import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.common.response.user.SysUserPermissionDto;
import blossom.project.towelove.user.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.user.controller
 * @className: SysUserPermissionController
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/3/21 20:25
 * @version: 1.0
 */
@RestController
@RequiredArgsConstructor
public class SysUserPermissionController {

    private final SysUserService sysUserService;
    /**
     * 获得用户权限
     * @param userId
     * @return
     */
    @GetMapping("/sys/permission")
    public Result<SysUserPermissionDto> getUserPermissionByUserId(@RequestParam("userId") Long userId){
        return Result.ok(sysUserService.getPermissionByUserId(userId));
    }
}
