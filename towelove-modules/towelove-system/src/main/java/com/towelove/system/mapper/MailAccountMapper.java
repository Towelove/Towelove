package com.towelove.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.towelove.system.domain.PageResult;
import com.towelove.system.domain.mail.MailAccountDO;
import com.towelove.system.domain.mail.vo.MailAccountPageReqVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MailAccountMapper extends BaseMapper<MailAccountDO> {



}
