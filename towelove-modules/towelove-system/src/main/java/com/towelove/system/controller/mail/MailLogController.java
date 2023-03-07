package com.towelove.system.controller.mail;

import com.towelove.common.core.domain.PageResult;
import com.towelove.common.core.domain.R;
import com.towelove.system.convert.MailLogConvert;
import com.towelove.system.domain.mail.MailLogDO;
import com.towelove.system.domain.mail.vo.log.MailLogPageReqVO;
import com.towelove.system.domain.mail.vo.log.MailLogRespVO;
import com.towelove.system.service.mail.MailLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author: 张锦标
 * @date: 2023/3/7 12:58
 * 邮箱日志管理控制层
 */
@Tag(name =  "管理后台 - 邮件日志")
@RestController
@RequestMapping("/sys/mail-log")
public class MailLogController {

    @Resource
    private MailLogService mailLogService;

    /**
     * 分页日志查询
     * @param pageVO 日志查询条件
     * @return 返回分页数据
     */
    @GetMapping("/page")
    @Operation(summary = "获得邮箱日志分页")
    public R<PageResult<MailLogRespVO>> getMailLogPage(@Valid MailLogPageReqVO pageVO) {
        PageResult<MailLogDO> pageResult = mailLogService.getMailLogPage(pageVO);
        return R.ok(MailLogConvert.INSTANCE.convertPage(pageResult));
    }

    /**
     * 根据id获取某一特定日志
     * @param id 日志id
     * @return 日志信息
     */
    @GetMapping("/get")
    @Operation(summary = "获得邮箱日志")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public R<MailLogRespVO> getMailTemplate(@RequestParam("id") Long id) {
        MailLogDO mailLogDO = mailLogService.getMailLog(id);
        return R.ok(MailLogConvert.INSTANCE.convert(mailLogDO));
    }

}