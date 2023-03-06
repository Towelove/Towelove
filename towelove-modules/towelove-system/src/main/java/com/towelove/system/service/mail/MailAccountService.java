package com.towelove.system.service.mail;


import com.baomidou.mybatisplus.extension.service.IService;
import com.towelove.common.core.domain.PageResult;
import com.towelove.common.core.web.page.TableDataInfo;

import com.towelove.system.domain.mail.MailAccount;
import com.towelove.system.domain.mail.vo.MailAccountCreateReqVO;
import com.towelove.system.domain.mail.vo.MailAccountPageReqVO;
import com.towelove.system.domain.mail.vo.MailAccountUpdateReqVO;

import javax.validation.Valid;
import java.util.List;

/**
 * 邮箱账号 Service 接口
 *
 * @author: 张锦标
 * @since 2022-03-21
 */
public interface MailAccountService {

    /**
     * 初始化邮箱账号的本地缓存
     */
    void initLocalCache();

    /**
     * 从缓存中获取邮箱账号
     *
     * @param id 编号
     * @return 邮箱账号
     */
    MailAccount getMailAccountFromCache(Long id);

    /**
     * 创建邮箱账号
     *
     * @param account 邮箱账号信息
     * @return 编号
     */
    Long createMailAccount(@Valid MailAccount account);

    /**
     * 修改邮箱账号
     *
     * @param account 邮箱账号信息
     */
    void updateMailAccount(@Valid MailAccount account);

    /**
     * 删除邮箱账号
     *
     * @param id 编号
     */
    void deleteMailAccount(Long id);

    /**
     * 获取邮箱账号信息
     *
     * @param id 编号
     * @return 邮箱账号信息
     */
    MailAccount getMailAccount(Long id);

    /**
     * 获取邮箱账号分页信息
     *
     * @param pageReqVO 邮箱账号分页参数
     * @return 邮箱账号分页信息
     */
    PageResult<MailAccount> getMailAccountPage(MailAccountPageReqVO pageReqVO);

    /**
     * 获取邮箱数组信息
     *
     * @return 邮箱账号信息数组
     */
    List<MailAccount> getMailAccountList();

}
