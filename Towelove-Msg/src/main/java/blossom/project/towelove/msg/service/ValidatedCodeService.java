package blossom.project.towelove.msg.service;

import blossom.project.towelove.common.request.msg.ValidateCodeRequest;

import java.util.List;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.msg.service
 * @className: ValidatedCodeService
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/3/1 21:50
 * @version: 1.0
 */
public interface ValidatedCodeService {
    String validate(ValidateCodeRequest validateCodeRequest);

    String validateMulti(List<ValidateCodeRequest> validateCodeRequests);

}
