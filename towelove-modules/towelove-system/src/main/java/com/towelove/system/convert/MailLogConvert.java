package com.towelove.system.convert;

import com.towelove.common.core.domain.PageResult;
import com.towelove.system.domain.mail.MailLogDO;
import com.towelove.system.domain.mail.vo.log.MailLogRespVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
/**
 * @author: 张锦标
 * @date: 2023/3/7 12:58
 * 使用mapstruct进行DO到DTO或者VO的数据类型转换
 */
@Mapper(componentModel = "spring")
public interface MailLogConvert {

    MailLogConvert INSTANCE = Mappers.getMapper(MailLogConvert.class);

    PageResult<MailLogRespVO> convertPage(PageResult<MailLogDO> pageResult);

    MailLogRespVO convert(MailLogDO bean);

}
