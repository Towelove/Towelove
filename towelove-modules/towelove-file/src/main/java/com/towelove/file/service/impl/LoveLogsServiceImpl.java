package com.towelove.file.service.impl;


import com.towelove.common.core.domain.PageResult;
import com.towelove.file.domain.LoveLogs;
import com.towelove.file.domain.vo.LoveLogsPageReqVO;
import com.towelove.file.mapper.LoveLogsMapper;
import com.towelove.file.service.LoveLogsService;
import io.jsonwebtoken.lang.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 恋爱日志表(LoveLogs) 服务实现类
 *
 * @author 张锦标
 * @since 2023-03-26 20:40:07
 */
@Service
public class LoveLogsServiceImpl implements LoveLogsService {

    @Autowired
    private LoveLogsMapper loveLogsMapper;


    @Override
    public List<LoveLogs> selectList() {
        return loveLogsMapper.selectList();
    }
    @Override
    public PageResult<LoveLogs> selectPage(LoveLogsPageReqVO loveLogs) {
        return loveLogsMapper.selectPage(loveLogs);
    }


    @Override
    public LoveLogs selectLoveLogsById(Long loveLogsId) {
        return loveLogsMapper.selectById(loveLogsId);
    }

    @Override
    public long insertLoveLogs(LoveLogs loveLogs) {
        int success = loveLogsMapper.insert(loveLogs);
        if (success > 0) {
            return loveLogs.getId();
        }
        throw new RuntimeException("插入LoveLogs失败");
    }

    @Override
    public boolean updateLoveLogs(LoveLogs loveLogs) {
        if (Objects.isNull(loveLogs)) {
            throw new RuntimeException("前端传来的对象为空");
        }
        int isUpdate = loveLogsMapper.updateById(loveLogs);
        if (isUpdate == 0) {
            throw new RuntimeException("修改任务失败");
        }
        return isUpdate > 0;
    }

    @Override
    public boolean deleteLoveLogs(ArrayList<Long> loveLogsIds) {
        if (Collections.isEmpty(loveLogsIds)) {
            throw new RuntimeException("id集合为空...");
        }
        for (Long id : loveLogsIds) {
            int i = loveLogsMapper.deleteById(id);
            if (i <= 0) {
                throw new RuntimeException("删除数据失败");
            }
        }
        return true;
    }
}

