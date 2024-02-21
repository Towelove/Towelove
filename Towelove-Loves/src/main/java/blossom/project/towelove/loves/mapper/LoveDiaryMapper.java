package blossom.project.towelove.loves.mapper;

import blossom.project.towelove.loves.entity.LoveDiary;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.loves.mapper
 * @className: LoveDiaryMapper
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/2/20 18:01
 * @version: 1.0
 */
@Mapper
public interface LoveDiaryMapper extends BaseMapper<LoveDiary> {
    List<String> getDiaryByCollectionId(@Param("collectionId") Long collectionId);
}
