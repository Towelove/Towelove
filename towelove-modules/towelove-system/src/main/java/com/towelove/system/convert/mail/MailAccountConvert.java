package com.towelove.system.convert.mail;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.mail.Mail;
import cn.hutool.extra.mail.MailAccount;
import com.towelove.common.core.domain.PageResult;
import com.towelove.system.api.domain.SysMailAccount;
import com.towelove.system.domain.mail.MailAccountDO;
import com.towelove.system.domain.mail.MailTemplateDO;
import com.towelove.system.domain.mail.vo.account.*;
import com.towelove.system.domain.mail.vo.template.MailTemplateSimpleRespVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
/**
 * @author: 张锦标
 * @date: 2023/3/7 12:58
 * 使用mapstruct进行DO到DTO或者VO的数据类型转换
 * 关于lombok和mapstruct的版本兼容问题多说几句，
 * maven插件要使用3.6.0版本以上、
 * lombok使用1.16.16版本以上，
 * 另外编译的lombok mapstruct的插件不要忘了加上。
 * 否则会出现下面的错误：No
 * property named "aaa" exists in source parameter(s).
 * Did you mean "null"?
 *
 * 这种异常就是lombok编译异常导致缺少get setter方法造成的
 * 还有就是缺少构造函数也会抛异常。
 */
@Mapper(componentModel = "spring")
public interface MailAccountConvert {

    MailAccountConvert INSTANCE = Mappers.getMapper(MailAccountConvert.class);
    @Mapping(source = "username",target = "username")
    MailAccountDO convert(MailAccountCreateReqVO bean);

    MailAccountDO convert(MailAccountUpdateReqVO bean);

    MailAccountRespVO convert(MailAccountDO bean);
    SysMailAccount convertToSysMailAccount(MailAccountDO bean);
    PageResult<MailAccountBaseVO> convertPage(PageResult<MailAccountDO> pageResult);

    List<MailAccountSimpleRespVO> convertList02(List<MailAccountDO> list);
    MailAccountSimpleRespVO map(MailAccountDO value);
    MailTemplateSimpleRespVO map(MailTemplateDO value);
    @Mapping(source = "mailAccountDO.id",target = "id")
    @Mapping(source = "mailTemplateDO.createTime",target = "createTime",
            dateFormat = "yyyy-MM-dd HH:mm:ss")
    MailAccountRespVO map(MailTemplateDO mailTemplateDO,
                          MailAccountDO mailAccountDO);

    default MailAccount convert(MailAccountDO account,
                                String nickname) {
        String from = StrUtil.isNotEmpty(nickname) ?
                nickname + " <" + account.getMail() + ">" : account.getMail();
        return new MailAccount().setFrom(from).setAuth(true)
                .setUser(account.getUsername()).setPass(account.getPassword())
                .setHost(account.getHost()).setPort(account.getPort())
                .setSslEnable(account.getSslEnable());
    }

}
