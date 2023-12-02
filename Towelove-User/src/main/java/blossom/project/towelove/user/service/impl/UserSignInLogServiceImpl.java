package blossom.project.towelove.user.service.impl;

import blossom.project.towelove.user.domain.UserSignInLog;
import blossom.project.towelove.user.mapper.UserSignInLogMapper;
import blossom.project.towelove.user.service.UserSignLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.user.service.impl
 * @className: UserSignInLogServiceImpl
 * @author: Link Ji
 * @description:
 * @date: 2023/11/28 20:31
 * @version: 1.0
 */
@Service
public class UserSignInLogServiceImpl extends ServiceImpl<UserSignInLogMapper, UserSignInLog> implements UserSignLogService {
}
