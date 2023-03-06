package com.towelove.system.service.mail;



import com.towelove.system.domain.PageResult;
import com.towelove.system.domain.mail.MailTemplateDO;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 邮件模版 Service 接口
 *
 * @author wangjingyi
 * @since 2022-03-21
 */
public interface MailTemplateService {

    /**
     * 初始化邮件模版的本地缓存
     */
    void initLocalCache();




    /**
     * 邮件模版删除
     *
     * @param id 编号
     */
    void deleteMailTemplate(Long id);

    /**
     * 获取邮件模版
     *
     * @param id 编号
     * @return 邮件模版
     */
    MailTemplateDO getMailTemplate(Long id);




    /**
     * 获取邮件模板数组
     *
     * @return 模版数组
     */
    List<MailTemplateDO> getMailTemplateList();

    /**
     * 从缓存中获取邮件模版
     *
     * @param code 模板编码
     * @return 邮件模板
     */
    MailTemplateDO getMailTemplateByCodeFromCache(String code);

    /**
     * 邮件模版内容合成
     *
     * @param content 邮件模版
     * @param params 合成参数
     * @return 格式化后的内容
     */
    String formatMailTemplateContent(String content, Map<String, Object> params);

    /**
     * 获得指定邮件账号下的邮件模板数量
     *
     * @param accountId 账号编号
     * @return 数量
     */
    long countByAccountId(Long accountId);

}
