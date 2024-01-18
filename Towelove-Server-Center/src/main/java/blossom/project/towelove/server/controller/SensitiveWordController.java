package blossom.project.towelove.server.controller;

import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.server.service.SensitiveWordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.server.controller
 * @className: SensitiveWordController
 * @author: Link Ji
 * @description: GOGO
 * @date: 2023/12/17 14:07
 * @version: 1.0
 */
@RestController
@RequiredArgsConstructor
public class SensitiveWordController {
    private SensitiveWordService sensitiveWordService;

    /**
     * 添加敏感词
     * @param word
     * @return
     */
    @PostMapping("/sensitive")
    public Result<String> addSensitiveWord(@RequestParam String word){
        return sensitiveWordService.addSensitiveWord(word);
    }

    @GetMapping("/match")
    public Result<String> matchSensitiveWord(@RequestParam String match){
        return sensitiveWordService.match(match);
    }
}