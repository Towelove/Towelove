package com.towelove.system.mapper;

import com.towelove.common.core.domain.PageResult;
import com.towelove.common.core.mybatis.BaseMapperX;
import com.towelove.common.core.mybatis.LambdaQueryWrapperX;
import com.towelove.system.domain.mail.MailLog;
import com.towelove.system.domain.mail.vo.MailLogPageReqVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: 张锦标
 * @date: 2023/3/6 20:10
 * Description:
 */
@Mapper
public interface MailLogMapper extends BaseMapperX<MailLog> {

    default PageResult<MailLog> selectPage(MailLogPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MailLog>()
                .eqIfPresent(MailLog::getUserId, reqVO.getUserId())
                .eqIfPresent(MailLog::getUserType, reqVO.getUserType())
                .likeIfPresent(MailLog::getToMail, reqVO.getToMail())
                .eqIfPresent(MailLog::getAccountId, reqVO.getAccountId())
                .eqIfPresent(MailLog::getTemplateId, reqVO.getTemplateId())
                .eqIfPresent(MailLog::getSendStatus, reqVO.getSendStatus())
                .betweenIfPresent(MailLog::getSendTime, reqVO.getSendTime())
                .orderByDesc(MailLog::getId));
    }

}
