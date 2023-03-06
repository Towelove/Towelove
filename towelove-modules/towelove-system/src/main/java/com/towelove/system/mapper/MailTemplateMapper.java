package com.towelove.system.mapper;

import com.towelove.common.core.domain.PageResult;
import com.towelove.common.core.mybatis.BaseMapperX;
import com.towelove.common.core.mybatis.LambdaQueryWrapperX;
import com.towelove.system.domain.mail.MailTemplate;
import com.towelove.system.domain.mail.vo.MailTemplatePageReqVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MailTemplateMapper extends BaseMapperX<MailTemplate> {

    default PageResult<MailTemplate> selectPage(MailTemplatePageReqVO pageReqVO){
        return selectPage(pageReqVO , new LambdaQueryWrapperX<MailTemplate>()
                .eqIfPresent(MailTemplate::getStatus, pageReqVO.getStatus())
                .likeIfPresent(MailTemplate::getCode, pageReqVO.getCode())
                .likeIfPresent(MailTemplate::getName, pageReqVO.getName())
                .eqIfPresent(MailTemplate::getAccountId, pageReqVO.getAccountId())
                .betweenIfPresent(MailTemplate::getCreateTime, pageReqVO.getCreateTime()));
    }

    default Long selectCountByAccountId(Long accountId) {
        return selectCount(MailTemplate::getAccountId, accountId);
    }

    default MailTemplate selectByCode(String code) {
        return selectOne(MailTemplate::getCode, code);
    }

}
