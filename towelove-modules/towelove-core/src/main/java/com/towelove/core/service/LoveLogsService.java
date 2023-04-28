package com.towelove.core.service;


import com.github.pagehelper.PageHelper;
import com.towelove.common.core.domain.PageResult;
import com.towelove.core.domain.lovelogs.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * 恋爱日志表(LoveLogs) 服务接口
 *
 * @author 张锦标
 * @since 2023-03-26 20:40:07
 */
public interface LoveLogsService {
    /**
     * 根据分页条件和恋爱日志表信息查询恋爱日志表数据
     *
     * @return 分页数据对象 (TableDataInfo)
     */
    default List<LoveLogs> selectList() {
        return null;
    }

    PageResult<LoveLogsBaseVO> selectPage(LoveLogsPageReqVO pageReqVO);

    /**
     * 根据ID查询恋爱日志表详情
     *
     * @param loveLogsId 恋爱日志表ID
     * @return 结果返回查询结果
     */
    LoveLogs selectLoveLogsById(Long loveLogsId);

    /**
     * 新增恋爱日志表
     *
     * @param loveLogs 恋爱日志表
     * @return 结果返回生成的id
     */
    long insertLoveLogs(LoveLogsCreateReqVO createReqVO);

    /**
     * 修改恋爱日志表
     *
     * @param loveLogs 恋爱日志表
     * @return 结果返回是否插入成功
     */
    boolean updateLoveLogs(LoveLogsUpdateReqVO updateReqVO);

    /**
     * 删除恋爱日志表
     *
     * @param loveLogsIds 恋爱日志表
     * @return 结果返回是否删除成功
     */
    boolean deleteLoveLogs(ArrayList<Long> loveLogsIds);


}

