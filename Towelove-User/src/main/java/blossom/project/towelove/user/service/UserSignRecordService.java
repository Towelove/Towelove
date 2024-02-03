package blossom.project.towelove.user.service;

import blossom.project.towelove.user.domain.UserSignInRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.user.service
 * @className: UserSignRecordService
 * @author: Link Ji
 * @description:
 * @date: 2023/11/27 23:29
 * @version: 1.0
 */
public interface UserSignRecordService extends IService<UserSignInRecord> {
    Long getSignInByMouthTotally(Long userId);

    Long getSignInTotally(Long userId);

    String singnInByUserId(Long userId);

    Object getSignInMonth();
}
