package blossom.project.towelove.server.service;

import blossom.project.towelove.common.response.Result;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.server.service
 * @className: SensitiveWordService
 * @author: Link Ji
 * @description: GOGO
 * @date: 2023/12/17 14:08
 * @version: 1.0
 */
public interface SensitiveWordService {
    Result<String> addSensitiveWord(String word);

    Result<String> match(String match);
}
