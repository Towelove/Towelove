package com.towelove.system.controller;

import com.towelove.common.core.web.page.TableDataInfo;
import com.towelove.common.security.annotation.RequiresPermissions;
import com.towelove.system.api.domain.SysUser;
import com.towelove.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import com.towelove.common.core.web.controller.BaseController;
/**
 * @author: 张锦标
 * @date: 2023/2/24 12:58
 * Description:
 * 用户数据信息控制层
 * 这里需要考虑到auth模块需要使用到用户的登录信息
 * 并且为了使得auth模块不再需要导入数据库
 * 因此需要使用Feign远程调用
 * 因此为了防止重复对象的创建
 * 这里的SysUser对象直接创建在system-api中即可
 */
@RestController
@RequestMapping("/user")
public class SysUserController extends BaseController{
    @Autowired
    private ISysUserService userService;
    /**
     * 获取用户列表
     */
    @RequiresPermissions("system:user:list")
    @GetMapping("/list")
    public TableDataInfo list(SysUser user)
    {
        startPage();
        List<SysUser> list = userService.selectUserList(user);
        return getDataTable(list);
    }

}
