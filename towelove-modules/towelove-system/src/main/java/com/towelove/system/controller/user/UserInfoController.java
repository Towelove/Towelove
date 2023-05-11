package com.towelove.system.controller.user;

import cn.hutool.Hutool;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailException;
import cn.hutool.extra.mail.MailUtil;
import com.google.common.base.Strings;
import com.towelove.common.core.constant.InvitedMessageConstant;
import com.towelove.common.core.constant.UserConstants;
import com.towelove.common.core.domain.OfficialMailInfo;
import com.towelove.common.core.domain.R;
import com.towelove.common.core.utils.InvitationCodeUtils;
import com.towelove.common.core.utils.JwtUtils;
import com.towelove.common.core.utils.StringUtils;
import com.towelove.common.minio.MinioService;
import com.towelove.common.redis.service.RedisService;
import com.towelove.core.api.RemoteCoreService;
import com.towelove.core.api.model.vo.LoveAlbumCreateReqVO;
import com.towelove.system.api.domain.SysUser;
import com.towelove.system.api.model.SysUserRespVO;
import com.towelove.system.convert.mail.MailAccountConvert;
import com.towelove.system.domain.mail.MailAccountDO;
import com.towelove.system.domain.user.UserInfoBaseVO;
import com.towelove.system.service.user.ISysUserService;
import com.towelove.system.service.user.UserInfoService;
import io.jsonwebtoken.Claims;
import io.minio.GetObjectResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author: 张锦标
 * @date: 2023/4/9 20:34
 * UserInfoController类
 * 当前类用于提供用户当前账号自己信息的
 * 增删改查功能
 */
@RestController
@RequestMapping("/sys/user/info")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private MinioService minioService;

    @Autowired
    private RedisService redisService;
    @Autowired
    private OfficialMailInfo officialMailInfo;
    @Autowired
    private RemoteCoreService remoteCoreService;




    /**
     * 用户注册用户信息
     */
    @PostMapping("/register")
    //这里重写最好
    public R register(@RequestBody SysUser sysUser) {
        String username = sysUser.getUserName();
        if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(sysUser))) {
            return R.fail("保存用户'" + username + "'失败，用户名已存在");
        }
        String invitedCode = sysUser.getInvitedCode();
        //判断传递来的数据中是否有邀请码数据
        if (!Strings.isNullOrEmpty(invitedCode)) {
            //该用户是被邀请注册，校验存储在redis中的邀请码
            //解析邀请码信息得到邀请人的用户Id
            //Long invitedId = InvitationCodeUtils
            //        .parseInvitationCode(sysUser.getInvitedCode());
            //从redis中得到匹配邀请码
            Integer invitedId = redisService
                    .getCacheObject("InvitationCode:user:" + invitedCode);
            if (!Objects.isNull(invitedId)) {
                //将女方信息存入数据库中
                Boolean aBoolean = userService.registerUser(sysUser);
                //校验成功绑定数据到相册表中，开启相册功能
                if (aBoolean) {
                    //TODO 将男女方信息存入相册表中
                    //判断当前注册用户的性别
                    Long registerId = sysUser.getUserId();
                    LoveAlbumCreateReqVO loveAlbumCreateReqVO = new LoveAlbumCreateReqVO();
                    if (sysUser.getSex().equals("0")) {//当前被邀请的是男生
                        loveAlbumCreateReqVO.setGirlId(Long.valueOf(invitedId));
                        loveAlbumCreateReqVO.setBoyId(registerId);
                    } else {
                        loveAlbumCreateReqVO.setGirlId(registerId);
                        loveAlbumCreateReqVO.setBoyId(Long.valueOf(invitedId));
                    }
                    loveAlbumCreateReqVO.setDaysInLove(new Date());
                    loveAlbumCreateReqVO.setTitle("等待设置相册标题");
                    remoteCoreService.add(loveAlbumCreateReqVO);
                    redisService.deleteObject("InvitationCode:user:" + invitedCode);
                }
            }

        }
        userService.registerUser(sysUser);

        return R.ok(sysUser.getUserId(), "用户注册成功");
    }

    /**
     * 向另一半发送绑定恋爱相册的邀请
     *
     * @param email    另一半的邮箱
     * @param username 对方的用户名
     * @param userId   发送当前邀请请求的userId
     * @return
     */
    @GetMapping("/inviteLover")
    public R bingdingRelationShip(@RequestParam(value = "email", required = false) String email,
                                  @RequestParam(value = "username", required = false) String username,
                                  @RequestParam("userId") Long userId) {

        if (Strings.isNullOrEmpty(username) && Strings.isNullOrEmpty(email)) {
            return R.fail("接收人的邮箱和用户名不能同时为空");
        }
        //根据userId生成用户邀请码
        String invitationCode = InvitationCodeUtils.getInvitationCode(userId);
        //将邀请码保存到redis中并且设置1天过期
        redisService.setCacheObject("InvitationCode:user:" + invitationCode,
                userId, 7L, TimeUnit.DAYS);
        //根据邮箱发送
        if (!Strings.isNullOrEmpty(email)) {
            sendInvitedMessageToUser(email, invitationCode, userId);
        } else {//根据用户名发送
            sendInvitedMessageToUserByUsername(username, invitationCode, userId);
        }
        return R.ok("向目标用户发送电子邮件成功");
    }

    private void sendInvitedMessageToUserByUsername(String username, String invitationCode, Long userId) {
        SysUser sysUser1 = userService.selectUserByUserName(username);
        String email = sysUser1.getEmail();
        //TODO 将邀请码发送到指定email即可
        // 1. 创建发送账号
        //TODO 真正的发送消息并且记录日志
        //配置MailAccount对象 hutool提供的
        MailAccount mailAccount =
                new MailAccount().setFrom("Towelove官方<460219753@qq.com>")
                        .setAuth(true).setUser(officialMailInfo.getUsername())
                        .setPass(officialMailInfo.getPassword())
                        .setHost(officialMailInfo.getHost())
                        .setPort(officialMailInfo.getPort()).setSslEnable(true);
        //发送邮件 msgIG为邮件id
        String msgId = null;

        SysUser sysUser = userService.selectUserById(userId);
        String invitedName = sysUser.getUserName();
        //TODO 这里需要完成消息的延时发送
        msgId = MailUtil.send(mailAccount, email,
                invitedName + "邀请您绑定情侣相册",
                invitedName + InvitedMessageConstant.INVITED_CONTENT +
                        invitationCode + "项目地址为:"
                        + InvitedMessageConstant.TOWELOVE_URL, true);
    }

    @Async
    public void sendInvitedMessageToUser(String email, String invitedCode, Long userId) {
        //TODO 将邀请码发送到指定email即可
        // 1. 创建发送账号
        //TODO 真正的发送消息并且记录日志
        //配置MailAccount对象 hutool提供的
        MailAccount mailAccount =
                new MailAccount().setFrom("Towelove官方<460219753@qq.com>")
                        .setAuth(true).setUser(officialMailInfo.getUsername())
                        .setPass(officialMailInfo.getPassword())
                        .setHost(officialMailInfo.getHost())
                        .setPort(officialMailInfo.getPort()).setSslEnable(true);
        //发送邮件 msgIG为邮件id
        String msgId = null;

        SysUser sysUser = userService.selectUserById(userId);
        String invitedName = sysUser.getUserName();
        //TODO 这里需要完成消息的延时发送
        msgId = MailUtil.send(mailAccount, email,
                invitedName + "邀请您绑定情侣相册",
                invitedName + InvitedMessageConstant.INVITED_CONTENT +
                        invitedCode + "项目地址为:"
                        + InvitedMessageConstant.TOWELOVE_URL, true);
    }


    /**
     * 判断当前用户输入的旧密码是否正确
     *
     * @param username    用户名
     * @param oldPassword 用户旧密码
     * @return 返回是否正确 true为正确
     */
    @GetMapping("/compare/pwd")
    public R<Boolean> comparePwd(@RequestParam("username") String username,
                                 @RequestParam("oldPassword") String oldPassword) {
        return R.ok(userService.comparePwd(username, oldPassword));
    }

    /**
     * 当前方法用于用户上传头像
     *
     * @param userId
     * @param file
     * @return
     */
    @PostMapping("/upload/avatar/{userId}")
    public R<SysUserRespVO> uploadAvatar(@PathVariable("userId") Long userId, MultipartFile file) {
        SysUser sysUser = userInfoService.uploadAvatar(userId, file);
        SysUserRespVO respVO = new SysUserRespVO();
        BeanUtils.copyProperties(sysUser, respVO);
        return R.ok(respVO);
    }

    /**
     * 当前方法用于完成当前用户的头像的获取
     *
     * @param userId   用户id
     * @param response
     */
    @GetMapping("/download/avatar/{userId}")
    public void downloadAvatar(@PathVariable("userId") Long userId, HttpServletResponse response) {
        try (ServletOutputStream os = response.getOutputStream()) {
            response.setContentType("image/jpeg");
            GetObjectResponse file = userInfoService.downloadAvatar(userId);
            int len = 0;
            byte[] buffre = new byte[1024 * 10];
            while ((len = file.read(buffre)) != -1) {
                os.write(buffre, 0, len);
                os.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 用户修改用户信息
     *
     * @param baseVO 表单提交过来的用户信息
     * @return 返回修改后的用户信息
     */
    @PutMapping("/edit")
    public R<SysUserRespVO> edit(@RequestBody UserInfoBaseVO baseVO) {
        SysUser sysUser = userInfoService.updateUserInfo(baseVO);
        SysUserRespVO respVO = new SysUserRespVO();
        BeanUtils.copyProperties(sysUser, respVO);
        return R.ok(respVO);
    }

    /**
     * 得到tokne进行解析
     * @param request
     * @return
     */
    @GetMapping("/parseToken")
    public R parseToken(HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        Long userId = Long.valueOf(JwtUtils.getUserId(authorization));
        SysUser sysUser = userService.selectUserById(userId);
        SysUserRespVO respVO = new SysUserRespVO();
        BeanUtils.copyProperties(sysUser,respVO);
        return R.ok(respVO);
    }
}
