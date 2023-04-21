package com.towelove.system.mapper.mail;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.towelove.common.core.domain.PageResult;
import com.towelove.common.core.mybatis.BaseMapperX;
import com.towelove.common.core.mybatis.LambdaQueryWrapperX;
import com.towelove.system.domain.mail.MailAccountDO;
import com.towelove.system.domain.mail.vo.account.MailAccountPageReqVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MailAccountMapper extends BaseMapperX<MailAccountDO> {
    default List<MailAccountDO> selectList() {
        return selectList(new QueryWrapper<>());
    }

    //TODO 编写一个分页转换类
    default PageResult<MailAccountDO> selectPage(MailAccountPageReqVO pageReqVO) {
        return selectPage(pageReqVO, new LambdaQueryWrapperX<MailAccountDO>()
                .likeIfPresent(MailAccountDO::getMail, pageReqVO.getMail())
                .likeIfPresent(MailAccountDO::getUsername , pageReqVO.getUsername()));
    }

}
