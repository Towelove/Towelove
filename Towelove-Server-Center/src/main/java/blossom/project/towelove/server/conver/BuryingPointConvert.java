package blossom.project.towelove.server.conver;

import blossom.project.towelove.server.dto.BuryingPointRequest;
import blossom.project.towelove.server.entity.BuryingPoint;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.server.conver
 * @className: BuryingPointConvert
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/4/8 14:48
 * @version: 1.0
 */
@Mapper
public interface BuryingPointConvert {
    BuryingPointConvert INSTANCE = Mappers.getMapper(BuryingPointConvert.class);

    BuryingPoint convert(BuryingPointRequest buryingPointRequest);
}
