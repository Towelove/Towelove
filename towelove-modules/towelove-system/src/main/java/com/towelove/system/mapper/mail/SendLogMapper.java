package com.towelove.system.mapper.mail;

import com.towelove.common.core.mybatis.BaseMapperX;
import com.towelove.system.domain.mail.SendLogDo;
import com.towelove.system.service.mail.SendLogService;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 季台星
 * @Date 2023 03 18 11 34
 */
@Mapper
public interface SendLogMapper extends BaseMapperX<SendLogDo> {
}
