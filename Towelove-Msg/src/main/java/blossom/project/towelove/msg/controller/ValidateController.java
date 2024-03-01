package blossom.project.towelove.msg.controller;

import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.common.request.msg.ValidateCodeRequest;
import blossom.project.towelove.msg.service.ValidatedCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.msg.controller
 * @className: ValidateController
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/3/1 21:47
 * @version: 1.0
 */
@RestController
@RequestMapping("/validate")
@RequiredArgsConstructor
public class ValidateController {

    private final ValidatedCodeService validatedCodeService;
    @PostMapping("")
    public Result<String> validate(@RequestBody @Validated ValidateCodeRequest validateCodeRequest){
        return Result.ok(validatedCodeService.validate(validateCodeRequest));
    }

    @PostMapping("/multi")
    public Result<String> multiValidate(@RequestBody @Validated List<ValidateCodeRequest> validateCodeRequests){
        return Result.ok(validatedCodeService.validateMulti(validateCodeRequests));
    }
}
