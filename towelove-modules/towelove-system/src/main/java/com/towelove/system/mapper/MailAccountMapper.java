package com.towelove.system.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.towelove.common.core.domain.PageParam;
import com.towelove.common.core.domain.PageResult;
import com.towelove.common.core.mybatis.BaseMapperX;
import com.towelove.common.core.mybatis.LambdaQueryWrapperX;
import com.towelove.common.core.utils.MyBatisUtils;
import com.towelove.common.core.utils.StringUtils;
import com.towelove.system.domain.mail.MailAccount;
import com.towelove.system.domain.mail.vo.MailAccountPageReqVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

@Mapper
public interface MailAccountMapper extends BaseMapperX<MailAccount> {
    default List<MailAccount> selectList() {
        return selectList(new QueryWrapper<>());
    }

    //TODO 编写一个分页转换类
    default PageResult<MailAccount> selectPage(MailAccountPageReqVO pageReqVO) {
        return selectPage(pageReqVO, new LambdaQueryWrapperX<MailAccount>()
                .likeIfPresent(MailAccount::getMail, pageReqVO.getMail())
                .likeIfPresent(MailAccount::getUsername , pageReqVO.getUsername()));
    }
}
