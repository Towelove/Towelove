package com.towelove.file.service;


import com.towelove.common.core.domain.PageResult;
import com.towelove.file.domain.LoveAlbum;
import com.towelove.file.domain.LoveLogs;
import com.towelove.file.domain.vo.LoveLogsPageReqVO;

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
    default List<LoveLogs> selectList(){
        return null;
    }
    default PageResult<LoveLogs> selectPage(LoveLogsPageReqVO pageReqVO){
        return null;
    }

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
    long insertLoveLogs(LoveLogs loveLogs);

    /**
     * 修改恋爱日志表
     *
     * @param loveLogs 恋爱日志表
     * @return 结果返回是否插入成功
     */
    boolean updateLoveLogs(LoveLogs loveLogs);

    /**
     * 删除恋爱日志表
     *
     * @param loveLogsIds 恋爱日志表
     * @return 结果返回是否删除成功
     */
    boolean deleteLoveLogs(ArrayList<Long> loveLogsIds);


}

