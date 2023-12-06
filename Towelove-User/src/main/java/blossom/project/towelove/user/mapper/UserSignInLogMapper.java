package blossom.project.towelove.user.mapper;

import blossom.project.towelove.user.domain.UserSignInLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.user.mapper
 * @className: UserSignInLogMapper
 * @author: Link Ji
 * @description:
 * @date: 2023/11/28 20:30
 * @version: 1.0
 */
@Mapper
public interface UserSignInLogMapper extends BaseMapper<UserSignInLog> {
}
