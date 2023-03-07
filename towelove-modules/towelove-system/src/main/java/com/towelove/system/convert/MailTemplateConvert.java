package com.towelove.system.convert;

import com.towelove.common.core.domain.PageResult;
import com.towelove.system.domain.mail.MailTemplateDO;
import com.towelove.system.domain.mail.vo.template.MailTemplateCreateReqVO;
import com.towelove.system.domain.mail.vo.template.MailTemplateRespVO;
import com.towelove.system.domain.mail.vo.template.MailTemplateSimpleRespVO;
import com.towelove.system.domain.mail.vo.template.MailTemplateUpdateReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
/**
 * @author: 张锦标
 * @date: 2023/3/7 12:58
 * 使用mapstruct进行DO到DTO或者VO的数据类型转换
 */
@Mapper
public interface MailTemplateConvert {

    MailTemplateConvert INSTANCE = Mappers.getMapper(MailTemplateConvert.class);

    MailTemplateDO convert(MailTemplateUpdateReqVO bean);

    MailTemplateDO convert(MailTemplateCreateReqVO bean);

    MailTemplateRespVO convert(MailTemplateDO bean);

    PageResult<MailTemplateRespVO> convertPage(PageResult<MailTemplateDO> pageResult);

    List<MailTemplateSimpleRespVO> convertList02(List<MailTemplateDO> list);

}
