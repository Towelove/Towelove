package com.towelove.system.controller.user;

import com.towelove.common.core.domain.R;
import com.towelove.common.minio.MinioService;
import com.towelove.system.api.domain.SysUser;
import com.towelove.system.api.request.UploadImgRequest;
import com.towelove.system.mq.message.mail.MailSendMessage;
import com.towelove.system.service.mail.MailSendService;
import com.towelove.system.service.user.ISysUserService;
import com.towelove.system.service.user.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@RestController
@RequestMapping("/image")
@Slf4j
public class ImageController {

    @Autowired
    private MinioService minioService;
    @Autowired
    private MailSendService mailSendService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private ISysUserService userService;


    @PostMapping("/upload")
    public R<String> uploadImage(@RequestBody UploadImgRequest uploadImgRequest) {
        // 处理图像上传逻辑
        String imageUrl = null;
        try {
            imageUrl = minioService.uploadFile(uploadImgRequest.getFile());
        } catch (Exception e) {
            return R.fail("上传失败重试");
        }
        Optional.ofNullable(imageUrl).orElseThrow(() -> new RuntimeException("上传失败"));
        SysUser  sysUser = userService.selectUserById(uploadImgRequest.getUserId());
        if (uploadImgRequest.getAlbumId() == null) {
            // 发给自己另一半
            SysUser  halfUser =userService.selectHalfUserById(uploadImgRequest.getUserId());
            sendImageToHalf(imageUrl, halfUser.getEmail());
        }

        //保存相册
        userInfoService.saveAlbum(uploadImgRequest.getUserId(),uploadImgRequest.getAlbumId(),imageUrl);

        return R.ok();
    }

    /**
     *  发送图片给另一半
     * @param imageUrl
     * @param email
     */
    private void sendImageToHalf(String imageUrl,String email) {
        //TODO 后期改为异步发送  现在用邮件后期 做桌面小工具 用MQ 或 事件
        MailSendMessage mailSendMessage = new MailSendMessage();
        mailSendMessage.setMail(email);
        mailSendMessage.setContent("您的另一半给您发来了一张图片");
        //html 拼接img标签
        mailSendMessage.setContent("<img src=\""+imageUrl+"\"/>");
        mailSendService.doSendMail(mailSendMessage);
    }
}
