package com.towelove.system.convert;

import com.towelove.common.core.domain.PageResult;
import com.towelove.system.domain.mail.MailTemplate;
import com.towelove.system.domain.mail.vo.MailTemplateCreateReqVO;
import com.towelove.system.domain.mail.vo.MailTemplateRespVO;
import com.towelove.system.domain.mail.vo.MailTemplateSimpleRespVO;
import com.towelove.system.domain.mail.vo.MailTemplateUpdateReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MailTemplateConvert {

    MailTemplateConvert INSTANCE = Mappers.getMapper(MailTemplateConvert.class);

    MailTemplate convert(MailTemplateUpdateReqVO bean);

    MailTemplate convert(MailTemplateCreateReqVO bean);

    MailTemplateRespVO convert(MailTemplate bean);

    PageResult<MailTemplateRespVO> convertPage(PageResult<MailTemplate> pageResult);

    List<MailTemplateSimpleRespVO> convertList02(List<MailTemplate> list);

}
