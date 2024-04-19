package blossom.project.towelove.server.controller;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.server.controller
 * @className: BuryingPointController
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/4/8 14:04
 * @version: 1.0
 */

import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.server.dto.BuryingPointRequest;
import blossom.project.towelove.server.entity.BuryingPoint;
import blossom.project.towelove.server.service.BuryingPointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 网站埋点
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/collect")
public class BuryingPointController {

    private final BuryingPointService buryingPointService;
    /**
     * 上报埋点数据
     */
    @PostMapping("")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void collect(@RequestBody @Validated BuryingPointRequest buryingPointRequest) {
        buryingPointService.saveBpData(buryingPointRequest);
    }

    /**
     * 获取全量埋点数据
     * @return
     */
    @GetMapping("")
    public Result<List<BuryingPoint>> getBpDataTotal(){
        return Result.ok(buryingPointService.getBpDataTotal());
    }

}
