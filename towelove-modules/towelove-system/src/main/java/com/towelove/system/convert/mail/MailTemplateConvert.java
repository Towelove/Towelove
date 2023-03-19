package com.towelove.system.convert.mail;

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
@Mapper
public interface MailTemplateConvert {

    MailTemplateConvert INSTANCE = Mappers.getMapper(MailTemplateConvert.class);

    MailTemplateDO convert(MailTemplateUpdateReqVO bean);

    MailTemplateDO convert(MailTemplateCreateReqVO bean);

    MailTemplateRespVO convert(MailTemplateDO bean);

    PageResult<MailTemplateRespVO> convertPage(PageResult<MailTemplateDO> pageResult);

    List<MailTemplateSimpleRespVO> convertList02(List<MailTemplateDO> list);
    MailTemplateSimpleRespVO map(MailTemplateDO value);

}
