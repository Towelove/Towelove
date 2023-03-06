package com.towelove.system.convert;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.mail.MailAccount;
import com.towelove.common.core.domain.PageResult;
import com.towelove.system.domain.mail.vo.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MailAccountConvert {

    MailAccountConvert INSTANCE = Mappers.getMapper(MailAccountConvert.class);

    MailAccount convert(MailAccountCreateReqVO bean);

    MailAccount convert(MailAccountUpdateReqVO bean);

    MailAccountRespVO convert(MailAccount bean);

    PageResult<MailAccountBaseVO> convertPage(PageResult<MailAccount> pageResult);

    List<MailAccountSimpleRespVO> convertList02(List<MailAccount> list);

    default MailAccount convert(com.towelove.system.domain.mail.MailAccount account, String nickname) {
        String from = StrUtil.isNotEmpty(nickname) ? nickname + " <" + account.getMail() + ">" : account.getMail();
        return new MailAccount().setFrom(from).setAuth(true)
                .setUser(account.getUsername()).setPass(account.getPassword())
                .setHost(account.getHost()).setPort(account.getPort()).setSslEnable(account.getSslEnable());
    }

}
