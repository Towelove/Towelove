package com.towelove.system.controller.mail;


import com.towelove.common.core.domain.PageResult;
import com.towelove.common.core.domain.R;
import com.towelove.common.core.utils.bean.BeanUtils;
import com.towelove.system.api.model.MailAccountBaseVO;
import com.towelove.system.api.model.MailAccountRespVO;
import com.towelove.system.convert.mail.MailAccountConvert;
import com.towelove.system.domain.mail.MailAccountDO;
import com.towelove.system.domain.mail.vo.account.MailAccountCreateReqVO;
import com.towelove.system.domain.mail.vo.account.MailAccountPageReqVO;
import com.towelove.system.domain.mail.vo.account.MailAccountSimpleRespVO;
import com.towelove.system.domain.mail.vo.account.MailAccountUpdateReqVO;
import com.towelove.system.service.mail.MailAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: 张锦标
 * @date: 2023/3/7 12:58
 * 邮箱账号管理控制层
 */
@Tag(name =  "管理后台 - 邮箱账号")
@RestController
@RequestMapping("/sys/mail-account")
public class MailAccountController {

    @Resource
    private MailAccountService mailAccountService;

    /**
     * 创建邮箱账号
     * @param createReqVO 前端传来的账号信息
     * @return 创建成功返回id
     */
    @PostMapping("/create")
    @Operation(summary = "创建邮箱账号")
    public R<Long> createMailAccount(@Valid @RequestBody MailAccountCreateReqVO createReqVO) {
        return R.ok(mailAccountService.createMailAccount(createReqVO));
    }

    /**
     * 修改邮箱账号信息
     * @param updateReqVO 要修改的邮箱账号信息
     * @return boolean值 返回是否修改成功
     */
    @PutMapping("/update")
    @Operation(summary = "修改邮箱账号")
    public R<Boolean> updateMailAccount(@Valid @RequestBody MailAccountUpdateReqVO updateReqVO) {
        mailAccountService.updateMailAccount(updateReqVO);
        return R.ok(true);
    }

    /**
     * 删除邮箱账号
     * @param id 要删除的账号id
     * @return 返回修改是否成功
     */
    @DeleteMapping("/delete")
    @Operation(summary = "删除邮箱账号")
    @Parameter(name = "id", description = "编号", required = true)
    public R<Boolean> deleteMailAccount(@RequestParam Long id) {
        mailAccountService.deleteMailAccount(id);
        return R.ok(true);
    }

    /**
     * 根据id获取邮箱账号
     * @param id 要获取的邮箱账号id
     * @return 返回查询的信息
     */
    @GetMapping("/get")
    @Operation(summary = "获得邮箱账号")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public R<MailAccountRespVO> getMailAccount(@RequestParam("id") Long id) {
        MailAccountDO mailAccountDO = mailAccountService.getMailAccount(id);
        MailAccountRespVO mailAccountRespVO = new MailAccountRespVO();
        BeanUtils.copyProperties(mailAccountDO,mailAccountRespVO);
        return R.ok(mailAccountRespVO);
    }
    /**
     * 根据id获取邮箱账号
     * @param userId 要获取的邮箱账号id
     * @return 返回查询的信息
     */
    @GetMapping("/getByUserId")
    @Operation(summary = "获得邮箱账号根据userid")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public R<Long> getMailAccountByUserId(@RequestParam("userId") Long userId) {
        MailAccountDO mailAccountDO = mailAccountService.getMailAccountByUserId(userId);
        return R.ok(mailAccountDO.getId());
    }

    /**
     * 分页查询(username/email查询条件)
     *
     * @param pageReqVO 分页查询条件
     * @return 分页查询数据
     */
    @GetMapping("/page")
    @Operation(summary = "获得邮箱账号分页")
    public R<PageResult<MailAccountBaseVO>> getMailAccountPage(@Valid MailAccountPageReqVO pageReqVO) {
        PageResult<MailAccountDO> pageResult = mailAccountService.getMailAccountPage(pageReqVO);
        List<MailAccountBaseVO> voList = pageResult.getList().stream().map(x -> {
            MailAccountBaseVO baseVO = new MailAccountBaseVO();
            BeanUtils.copyProperties(x, baseVO);
            return baseVO;
        }).collect(Collectors.toList());
        PageResult<MailAccountBaseVO> pageResult1 = new PageResult<>();
        pageResult1.setList(voList);
        pageResult1.setTotal((long) voList.size());
        return R.ok(pageResult1);
    }

    /**
     * 获取邮箱账号精简对象(id--email)
     * @return 返回精简对象
     */
    @GetMapping("/list-all-simple")
    @Operation(summary = "获得邮箱账号精简列表")
    public R<List<MailAccountSimpleRespVO>> getSimpleMailAccountList() {
        List<MailAccountDO> list = mailAccountService.getMailAccountList();
        return R.ok(MailAccountConvert.INSTANCE.convertList02(list));
    }
}