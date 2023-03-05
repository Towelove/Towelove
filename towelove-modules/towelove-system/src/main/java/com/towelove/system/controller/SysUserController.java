package com.towelove.system.controller;

import com.towelove.common.core.constant.UserConstants;
import com.towelove.common.core.domain.R;
import com.towelove.common.core.exception.auth.NotLoginException;
import com.towelove.common.core.utils.StringUtils;
import com.towelove.common.core.web.domain.AjaxResult;
import com.towelove.common.core.web.page.TableDataInfo;
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
import java.util.Objects;
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
 *
 * 当前控制器为管理员以及普通用户提供服务
 * /admin/xxx代表当前请求为管理员服务
 * /xxx则代表这就是一个普通的用户请求
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends BaseController{
    @Autowired
    private ISysUserService userService;

    /**
     * 获取用户列表
     */
    //@RequiresPermissions("system:user:list")
    @GetMapping("/list")
    public TableDataInfo list(@RequestBody SysUser user)
    {
        //下一次的查询将被分页
        startPage();
        List<SysUser> list = userService.selectUserList(user);
        return getDataTable(list);
    }
    /**
     * 获取当前用户信息
     * 当前方法用于提供远程调用服务
     */
    @InnerAuth
    @GetMapping("/info/{username}")
    public R<LoginUser> info(@PathVariable("username") String username)
    {
        SysUser sysUser = userService.selectUserByUserName(username);
        if (Objects.isNull(sysUser))
        {
            return R.fail("用户名不存在！");
        }
        // 角色集合
        //Set<String> roles = permissionService.getRolePermission(sysUser);
        //// 权限集合
        //Set<String> permissions = permissionService.getMenuPermission(sysUser);
        LoginUser loginUser = new LoginUser();
        loginUser.setSysUser(sysUser);
        //sysUserVo.setRoles(roles);
        //sysUserVo.setPermissions(permissions);
        return R.ok(loginUser);
    }

    /**
     * 用户注册用户信息
     */
    @PostMapping("/register")
    public R<Boolean> register(@RequestBody SysUser sysUser)
    {
        String username = sysUser.getUserName();
        if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(sysUser)))
        {
            return R.fail("保存用户'" + username + "'失败，用户名已存在");
        }
        return R.ok(userService.registerUser(sysUser));
    }

    /**
     * 用户获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("/getInfo/{username}")
    public AjaxResult getInfo(@PathVariable("username")String username)
    {
        //SysUser user = userService.selectUserById(SecurityUtils.getUserId());
        //if (Objects.isNull(user)){
        //    throw new NotLoginException("登录信息已经过期，请重新登陆");
        //}
        SysUser user = userService.selectUserByUserName(username);
        // 角色集合
        //Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        //Set<String> permissions = permissionService.getMenuPermission(user);
        //user.setPassword("密码不可见哦");
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        //ajax.put("roles", roles);
        //ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 根据用户编号获取详细信息
     */
    //@RequiresPermissions("system:user:query")
    @GetMapping(value = { "/admin", "/admin/{userId}" })
    public AjaxResult getInfo(@PathVariable(value = "userId", required = false) Long userId)
    {
        //userService.checkUserDataScope(userId);
        AjaxResult ajax = AjaxResult.success();
        //List<SysRole> roles = roleService.selectRoleAll();
        //ajax.put("roles", SysUser.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
        //ajax.put("posts", postService.selectPostAll());
        if (StringUtils.isNotNull(userId))
        {
            SysUser sysUser = userService.selectUserById(userId);
            ajax.put(AjaxResult.DATA_TAG, sysUser);
            //ajax.put("postIds", postService.selectPostListByUserId(userId));
            //ajax.put("roleIds", sysUser.getRoles().stream().map(SysRole::getRoleId).collect(Collectors.toList()));
        }else{
            List<SysUser> sysUserList = userService.list();
            ajax.put(AjaxResult.DATA_TAG,sysUserList);
        }
        return ajax;
    }

    /**
     * 管理员添加用户方法
     * @param user 添加的用户信息
     * @return 结果返回受影响的行数 >0 success <=0 fail
     */
    //@RequiresPermissions("system:user:add")
    //@Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
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

    /**
     * 修改用户
     */
    //@RequiresPermissions("system:user:edit")
    //@Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public AjaxResult edit(@Validated @RequestBody SysUser user)
    {
        //userService.checkUserDataScope(user.getUserId());
        if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(user)))
        {
            return error("修改用户'" + user.getUserName() + "'失败，登录账号已存在");
        }
        else if (StringUtils.isNotEmpty(user.getPhonenumber())
                && UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user)))
        {
            return error("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
        }
        else if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user)))
        {
            return error("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        user.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(userService.updateUser(user));
    }

    /**
     * 管理员批量删除用户
     * 此时应该给被删除的用户的邮箱发送一条消息
     */
    //@RequiresPermissions("system:user:remove")
    //@Log(title = "用户管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds)
    {
        //TODO 可以添加一个当前在线用户 不能删除当前在线的用户
        //TODO 在短信模块完成后 应该发送短信告诉被删除的用户
        return toAjax(userService.deleteUserByIds(userIds));
    }

    /**
     * 用户重置密码
     */
    //@RequiresPermissions("system:user:edit")
    //@Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @InnerAuth
    @PutMapping("/resetPwd")
    public AjaxResult resetPwd(@RequestBody SysUser user)
    {
        //密码的合法性检查由前端进行即可
        user.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(userService.resetPwd(user));
    }

    /**
     * 状态修改
     * 管理员修改则为停用当前用户
     */
    //@RequiresPermissions("system:user:edit")
    //@Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody SysUser user)
    {
        user.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(userService.updateUserStatus(user));
    }

    /**
     * 根据用户编号获取授权角色
     * 例如普通用户变为会员
     */
    //@RequiresPermissions("system:user:query")
    @GetMapping("/authRole/{userId}")
    public AjaxResult authRole(@PathVariable("userId") Long userId)
    {
        AjaxResult ajax = AjaxResult.success();
        SysUser user = userService.selectUserById(userId);
        //List<SysRole> roles = roleService.selectRolesByUserId(userId);
        ajax.put("user", user);
        //当前为普通用户 变为会员
        if (user.getRoleId()==2){
            user.setRoleId(3L);
        }else{
            //会员变为普通用户
            user.setRoleId(2L);
        }
        userService.updateUser(user);
        //ajax.put("roles", SysUser.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
        return ajax;
    }

    /**
     * 用户授权角色
     * 将当前角色设定为超级管理员
     * 代表当前用户拥有内测资格 为内部人员
     */
    //@RequiresPermissions("system:user:edit")
    //@Log(title = "用户管理", businessType = BusinessType.GRANT)
    @PutMapping("/authRole")
    public AjaxResult insertAuthRole(Long userId)
    {
        return toAjax(userService.authRole(userId));
    }

}
