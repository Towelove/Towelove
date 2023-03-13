package com.towelove.system.controller.mail;


import com.towelove.common.core.domain.PageResult;
import com.towelove.common.core.domain.R;
import com.towelove.system.convert.mail.MailTemplateConvert;
import com.towelove.system.domain.mail.MailTemplateDO;
import com.towelove.system.domain.mail.vo.template.*;
import com.towelove.system.service.mail.MailSendService;
import com.towelove.system.service.mail.MailTemplateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author: 张锦标
 * @date: 2023/3/7 12:58
 * 邮件模板管理控制层
 */

@Tag(name =  "管理后台 - 邮件模版")
@RestController
@RequestMapping("/sys/mail-template")
public class MailTemplateController {

    @Resource
    private MailTemplateService mailTempleService;
    @Resource
    private MailSendService mailSendService;

    /**
     * 创建邮件模板信息
     * @param createReqVO 邮件模板信息
     * @return 返回邮件模板id
     */
    @PostMapping("/create")
    @Operation(summary = "创建邮件模版")
    public R<Long> createMailTemplate(@Valid @RequestBody MailTemplateCreateReqVO createReqVO){
        return R.ok(mailTempleService.createMailTemplate(createReqVO));
    }

    /**
     * 修改邮件模板
     * @param updateReqVO 新邮件模板信息
     * @return 返回是否修改成功
     */
    @PutMapping("/update")
    @Operation(summary = "修改邮件模版")
    public R<Boolean> updateMailTemplate(@Valid @RequestBody MailTemplateUpdateReqVO updateReqVO){
        mailTempleService.updateMailTemplate(updateReqVO);
        return R.ok(true);
    }

    /**
     * 根据id删除邮件模板
     * @param id  要删除的邮件模板id
     * @return 是否删除成功
     */
    @DeleteMapping("/delete")
    @Operation(summary = "删除邮件模版")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")

    public R<Boolean> deleteMailTemplate(@RequestParam("id") Long id) {
        mailTempleService.deleteMailTemplate(id);
        return R.ok(true);
    }

    /**
     * 根据id获取邮件模板
     * @param id 要获取的邮件模板的id
     * @return 返回邮件模板信息
     */
    @GetMapping("/get")
    @Operation(summary = "获得邮件模版")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public R<MailTemplateRespVO> getMailTemplate(@RequestParam("id") Long id) {
        MailTemplateDO mailTemplateDO = mailTempleService.getMailTemplate(id);
        return R.ok(MailTemplateConvert.INSTANCE.convert(mailTemplateDO));
    }

    /**
     * 分页查询邮件模板
     * @param pageReqVO 分页查询数据
     * @return 返回分页查询的邮件模板信息
     */
    @GetMapping("/page")
    @Operation(summary = "获得邮件模版分页")
    public R<PageResult<MailTemplateRespVO>> getMailTemplatePage(@Valid MailTemplatePageReqVO pageReqVO) {
        PageResult<MailTemplateDO> pageResult = mailTempleService.getMailTemplatePage(pageReqVO);
        return R.ok(MailTemplateConvert.INSTANCE.convertPage(pageResult));
    }

    /**
     * 获取邮件模板精简信息
     * @return 返回邮件模板精简信息
     */
    @GetMapping("/list-all-simple")
    @Operation(summary = "获得邮件模版精简列表")
    public R<List<MailTemplateSimpleRespVO>> getSimpleTemplateList() {
        List<MailTemplateDO> list = mailTempleService.getMailTemplateList();
        return R.ok(MailTemplateConvert.INSTANCE.convertList02(list));
    }
    /**
     * 管理员发送短信 暂未完成
     * @param sendReqVO
     * @return
     */
    @PostMapping("/send-mail")
    @Operation(summary = "发送短信")
    public R<Long> sendMail(@Valid @RequestBody MailTemplateSendReqVO sendReqVO) {
        return R.ok(mailSendService.sendSingleMailToAdmin(sendReqVO.getMail(), null,
                sendReqVO.getTemplateCode(), sendReqVO.getTemplateParams()));
    }

}