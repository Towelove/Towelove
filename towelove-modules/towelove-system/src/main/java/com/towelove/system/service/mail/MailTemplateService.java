package com.towelove.system.service.mail;



import com.towelove.system.domain.mail.MailTemplate;

import java.util.List;
import java.util.Map;

/**
 * 邮件模版 Service 接口
 * 模板功能暂时不使用
 * 邮件内容完全由用户自定义
 * 只有后期发送官方模板文件之后使用这个功能
 * @author: 张锦标
 * @since 2022-03-21
 */
@Deprecated
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
    MailTemplate getMailTemplate(Long id);




    /**
     * 获取邮件模板数组
     *
     * @return 模版数组
     */
    List<MailTemplate> getMailTemplateList();

    /**
     * 从缓存中获取邮件模版
     *
     * @param code 模板编码
     * @return 邮件模板
     */
    MailTemplate getMailTemplateByCodeFromCache(String code);

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
