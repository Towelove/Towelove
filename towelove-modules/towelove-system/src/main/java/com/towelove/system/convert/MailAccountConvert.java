package com.towelove.system.convert;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.mail.MailAccount;
import com.towelove.common.core.domain.PageResult;
import com.towelove.system.domain.mail.MailAccountDO;
import com.towelove.system.domain.mail.vo.account.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
/**
 * @author: 张锦标
 * @date: 2023/3/7 12:58
 * 使用mapstruct进行DO到DTO或者VO的数据类型转换
 */
@Mapper
public interface MailAccountConvert {

    MailAccountConvert INSTANCE = Mappers.getMapper(MailAccountConvert.class);

    MailAccountDO convert(MailAccountCreateReqVO bean);

    MailAccountDO convert(MailAccountUpdateReqVO bean);

    MailAccountRespVO convert(MailAccountDO bean);

    PageResult<MailAccountBaseVO> convertPage(PageResult<MailAccountDO> pageResult);

    List<MailAccountSimpleRespVO> convertList02(List<MailAccountDO> list);

    default MailAccount convert(MailAccountDO account,
                                String nickname) {
        String from = StrUtil.isNotEmpty(nickname) ? nickname + " <" + account.getMail() + ">" : account.getMail();
        return new MailAccount().setFrom(from).setAuth(true)
                .setUser(account.getUsername()).setPass(account.getPassword())
                .setHost(account.getHost()).setPort(account.getPort()).setSslEnable(account.getSslEnable());
    }

}
