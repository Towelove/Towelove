package blossom.project.towelove.server.service.impl;

import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.server.ac.AcMatcher;
import blossom.project.towelove.server.service.SensitiveWordService;
import com.alibaba.fastjson.JSON;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.server.service
 * @className: SensitiveWordService
 * @author: Link Ji
 * @description: GOGO
 * @date: 2023/12/17 14:08
 * @version: 1.0
 */
@Service
@RequiredArgsConstructor
public class SensitiveWordServiceImpl implements SensitiveWordService {
    private AcMatcher acMatcher;
    @Override
    public Result<String> addSensitiveWord(String word) {
        return null;
    }

    @Override
    public Result<String> match(String match) {
        //判断是否初始化
        if (!acMatcher.isFetchInitFailurePoints()){
            acMatcher.constructorFailurePoints();
        }
        Set<String> search = acMatcher.search(match);
        return search.isEmpty() ? Result.ok("无敏感词") : Result.fail("敏感词出现", JSON.toJSONString(search));
    }
}
