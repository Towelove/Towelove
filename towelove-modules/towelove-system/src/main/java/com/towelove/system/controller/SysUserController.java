package com.towelove.system.controller;

import com.towelove.common.core.constant.UserConstants;
import com.towelove.common.core.domain.R;
import com.towelove.common.core.utils.StringUtils;
import com.towelove.common.core.web.domain.AjaxResult;
import com.towelove.common.core.web.page.TableDataInfo;
import com.towelove.common.log.annotation.Log;
import com.towelove.common.log.enums.BusinessType;
import com.towelove.common.security.annotation.InnerAuth;
import com.towelove.common.security.annotation.RequiresPermissions;
import com.towelove.common.security.utils.SecurityUtils;
import com.towelove.system.api.domain.SysRole;
import com.towelove.system.api.domain.SysUser;
import com.towelove.system.api.model.LoginUser;
import com.towelove.system.service.ISysUserService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Value("${spring.mail.port}")
    private String port;
    /**
     * 获取用户列表
     */
    @RequiresPermissions("system:user:list")
    @GetMapping("/list")
    public TableDataInfo list(SysUser user)
    {
        //下一次的查询将被分页
        startPage();
        List<SysUser> list = userService.selectUserList(user);
        return getDataTable(list);
    }
    @GetMapping("/test")
    public String test()
    {
        System.out.println(port);
        return port;
    }
    /**
     * 获取当前用户信息
     */
    @InnerAuth
    @GetMapping("/info/{username}")
    public R<LoginUser> info(@PathVariable("username") String username)
    {
        SysUser sysUser = userService.selectUserByUserName(username);
        if (StringUtils.isNull(sysUser))
        {
            return R.fail("用户名或密码错误");
        }
        // 角色集合
        //Set<String> roles = permissionService.getRolePermission(sysUser);
        //// 权限集合
        //Set<String> permissions = permissionService.getMenuPermission(sysUser);
        LoginUser sysUserVo = new LoginUser();
        sysUserVo.setSysUser(sysUser);
        //sysUserVo.setRoles(roles);
        //sysUserVo.setPermissions(permissions);
        return R.ok(sysUserVo);
    }

    /**
     * 注册用户信息
     */
    @InnerAuth
    @PostMapping("/register")
    public R<Boolean> register(@RequestBody SysUser sysUser)
    {
        String username = sysUser.getUserName();
        //if (!("true".equals(configService.selectConfigByKey("sys.account.registerUser"))))
        //{
        //    return R.fail("当前系统没有开启注册功能！");
        //}
        if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(sysUser)))
        {
            return R.fail("保存用户'" + username + "'失败，注册账号已存在");
        }
        return R.ok(userService.registerUser(sysUser));
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo()
    {
        SysUser user = userService.selectUserById(SecurityUtils.getUserId());
        // 角色集合
        //Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        //Set<String> permissions = permissionService.getMenuPermission(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        //ajax.put("roles", roles);
        //ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 根据用户编号获取详细信息
     */
    @RequiresPermissions("system:user:query")
    @GetMapping(value = { "/", "/{userId}" })
    public AjaxResult getInfo(@PathVariable(value = "userId", required = false) Long userId)
    {
        userService.checkUserDataScope(userId);
        AjaxResult ajax = AjaxResult.success();
        //List<SysRole> roles = roleService.selectRoleAll();
        //ajax.put("roles", SysUser.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
        //ajax.put("posts", postService.selectPostAll());
        //if (StringUtils.isNotNull(userId))
        //{
        //    SysUser sysUser = userService.selectUserById(userId);
        //    ajax.put(AjaxResult.DATA_TAG, sysUser);
        //    ajax.put("postIds", postService.selectPostListByUserId(userId));
        //    ajax.put("roleIds", sysUser.getRoles().stream().map(SysRole::getRoleId).collect(Collectors.toList()));
        //}
        return ajax;
    }

    /**
     * 新增用户
     */
    @RequiresPermissions("system:user:add")
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysUser user)
    {
        if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(user)))
        {
            return error("新增用户'" + user.getUserName() + "'失败，登录账号已存在");
        }
        else if (StringUtils.isNotEmpty(user.getPhonenumber())
                && UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user)))
        {
            return error("新增用户'" + user.getUserName() + "'失败，手机号码已存在");
        }
        else if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user)))
        {
            return error("新增用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        user.setCreateBy(SecurityUtils.getUsername());
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        return toAjax(userService.insertUser(user));
    }
    //
    ///**
    // * 修改用户
    // */
    //@RequiresPermissions("system:user:edit")
    //@Log(title = "用户管理", businessType = BusinessType.UPDATE)
    //@PutMapping
    //public AjaxResult edit(@Validated @RequestBody SysUser user)
    //{
    //    userService.checkUserAllowed(user);
    //    userService.checkUserDataScope(user.getUserId());
    //    if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(user)))
    //    {
    //        return error("修改用户'" + user.getUserName() + "'失败，登录账号已存在");
    //    }
    //    else if (StringUtils.isNotEmpty(user.getPhonenumber())
    //            && UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user)))
    //    {
    //        return error("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
    //    }
    //    else if (StringUtils.isNotEmpty(user.getEmail())
    //            && UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user)))
    //    {
    //        return error("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
    //    }
    //    user.setUpdateBy(SecurityUtils.getUsername());
    //    return toAjax(userService.updateUser(user));
    //}
    //
    ///**
    // * 删除用户
    // */
    //@RequiresPermissions("system:user:remove")
    //@Log(title = "用户管理", businessType = BusinessType.DELETE)
    //@DeleteMapping("/{userIds}")
    //public AjaxResult remove(@PathVariable Long[] userIds)
    //{
    //    if (ArrayUtils.contains(userIds, SecurityUtils.getUserId()))
    //    {
    //        return error("当前用户不能删除");
    //    }
    //    return toAjax(userService.deleteUserByIds(userIds));
    //}
    //
    ///**
    // * 重置密码
    // */
    //@RequiresPermissions("system:user:edit")
    //@Log(title = "用户管理", businessType = BusinessType.UPDATE)
    //@PutMapping("/resetPwd")
    //public AjaxResult resetPwd(@RequestBody SysUser user)
    //{
    //    userService.checkUserAllowed(user);
    //    userService.checkUserDataScope(user.getUserId());
    //    user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
    //    user.setUpdateBy(SecurityUtils.getUsername());
    //    return toAjax(userService.resetPwd(user));
    //}
    //
    ///**
    // * 状态修改
    // */
    //@RequiresPermissions("system:user:edit")
    //@Log(title = "用户管理", businessType = BusinessType.UPDATE)
    //@PutMapping("/changeStatus")
    //public AjaxResult changeStatus(@RequestBody SysUser user)
    //{
    //    userService.checkUserAllowed(user);
    //    userService.checkUserDataScope(user.getUserId());
    //    user.setUpdateBy(SecurityUtils.getUsername());
    //    return toAjax(userService.updateUserStatus(user));
    //}
    //
    ///**
    // * 根据用户编号获取授权角色
    // */
    //@RequiresPermissions("system:user:query")
    //@GetMapping("/authRole/{userId}")
    //public AjaxResult authRole(@PathVariable("userId") Long userId)
    //{
    //    AjaxResult ajax = AjaxResult.success();
    //    SysUser user = userService.selectUserById(userId);
    //    List<SysRole> roles = roleService.selectRolesByUserId(userId);
    //    ajax.put("user", user);
    //    ajax.put("roles", SysUser.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
    //    return ajax;
    //}
    //
    ///**
    // * 用户授权角色
    // */
    //@RequiresPermissions("system:user:edit")
    //@Log(title = "用户管理", businessType = BusinessType.GRANT)
    //@PutMapping("/authRole")
    //public AjaxResult insertAuthRole(Long userId, Long[] roleIds)
    //{
    //    userService.checkUserDataScope(userId);
    //    userService.insertUserAuth(userId, roleIds);
    //    return success();
    //}

}
