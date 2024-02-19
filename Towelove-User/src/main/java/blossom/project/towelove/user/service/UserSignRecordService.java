package blossom.project.towelove.user.service;

import blossom.project.towelove.user.domain.UserSignInRecord;
import blossom.project.towelove.user.domain.UserSignInVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDateTime;
import java.util.Date;

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
    UserSignInVo getSignInByMouthTotally(LocalDateTime localDateTime);

    Long getSignInTotally();

    String singnInByUserId();


}
