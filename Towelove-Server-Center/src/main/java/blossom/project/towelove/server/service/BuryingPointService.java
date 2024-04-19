package blossom.project.towelove.server.service;

import blossom.project.towelove.server.dto.BuryingPointRequest;
import blossom.project.towelove.server.entity.BuryingPoint;

import java.util.List;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.server.service
 * @className: BuryingPointService
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/4/8 14:41
 * @version: 1.0
 */
public interface BuryingPointService {
    void saveBpData(BuryingPointRequest buryingPointRequest);

    List<BuryingPoint> getBpDataTotal();

}
