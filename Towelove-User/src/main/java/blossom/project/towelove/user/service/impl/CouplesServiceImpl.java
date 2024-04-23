package blossom.project.towelove.user.service.impl;

import blossom.project.towelove.common.entity.notification.NoticeDTO;
import blossom.project.towelove.common.exception.ServerException;
import blossom.project.towelove.common.exception.ServiceException;
import blossom.project.towelove.common.exception.errorcode.BaseErrorCode;
import blossom.project.towelove.common.request.CouplesInvitedRequest;
import blossom.project.towelove.common.request.user.CouplesCreateRequest;
import blossom.project.towelove.common.request.user.CouplesUpdateRequest;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.common.response.user.CouplesRespDTO;
import blossom.project.towelove.common.response.user.CouplesTogetherDto;
import blossom.project.towelove.common.response.user.CouplesTogetherResponse;
import blossom.project.towelove.common.response.user.SysUserVo;
import blossom.project.towelove.framework.redis.util.UserNotifyProduction;
import blossom.project.towelove.framework.user.core.UserInfoContextHolder;
import blossom.project.towelove.user.entity.SysUser;
import blossom.project.towelove.user.service.SysUserService;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import blossom.project.towelove.user.entity.Couples;
import blossom.project.towelove.user.mapper.CouplesMapper;
import blossom.project.towelove.user.service.CouplesService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;


/**
 * @author: ZhangBlossom
 * @date: 2024-01-17 13:36:03
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * @description:
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CouplesServiceImpl extends ServiceImpl<CouplesMapper, Couples> implements CouplesService {

    private final CouplesMapper couplesMapper;

    private final SysUserService sysUserService;

    private final UserNotifyProduction userNotifyProduction;


    @Override
    public CouplesRespDTO getCouplesById(Long CouplesId) {
        return null;
    }

    @Override
    public CouplesRespDTO updateCouples(CouplesUpdateRequest updateRequest) {
        return null;
    }

    @Override
    public Boolean deleteCouplesById(Long CouplesId) {
        return null;
    }

    @Override
    public Boolean batchDeleteCouples(List<Long> ids) {
        return null;
    }

    @Override
    public CouplesRespDTO createCouples(CouplesCreateRequest createRequest) {
        return null;
    }

    @Override
    public Result<String> binding(CouplesInvitedRequest couplesInvitedRequest) {
        //邀请方的用户id
        long invitedUserId = Long.parseLong(couplesInvitedRequest.getInvitedCode(), 36);
        //先查询邀请方是否存在
        SysUserVo sysUserVo = sysUserService.selectByUserId(invitedUserId);
        Couples couples = getCouples(sysUserVo, invitedUserId);
        //查看couples表中是否有这两个人
        if (!couplesMapper.selectAllByBoyIdAndGirlIdLongs(couples.getBoyId(), couples.getGirlId()).isEmpty()) {
            throw new ServiceException("双方已经有绑定的情侣关系，请解绑后重新操作");
        }
        if (couplesMapper.insert(couples) < 1) {
            throw new ServiceException("绑定情侣关系失败");
        }
        //TODO:发送消息告诉另外一方
        userNotifyProduction.sendNotifyMessage(new NoticeDTO(invitedUserId, "【新通知，我们已经成为情侣啦】"));
        return Result.ok("绑定情侣关系成功");
    }

    private static Couples getCouples(SysUserVo sysUserVo, long invitedUserId) {
        if (Objects.isNull(sysUserVo)) {
            throw new ServiceException("绑定请求非法，邀请方不存在");
        }
        Long userId = UserInfoContextHolder.getUserId();
        if (userId.equals(invitedUserId)) {
            throw new ServiceException("绑定请求非法，不能自己与自己绑定为情侣关系");
        }
        Couples couples = new Couples();
        if ("男".equals(UserInfoContextHolder.getSex())) {
            couples.setBoyId(userId);
            couples.setGirlId(invitedUserId);
        } else {
            couples.setBoyId(invitedUserId);
            couples.setGirlId(userId);
        }
        return couples;
    }

    /**
     * 解绑方法
     *
     * @param coupleId
     * @return
     */
    @Override
    public Result<String> unbinding(Long coupleId) {
        return null;
    }

    @Override
    public Long getTogetherCouples() {
        //判断是否有情侣
        String sex = UserInfoContextHolder.getSex();
        if (Objects.isNull(UserInfoContextHolder.getCoupleId()) || StrUtil.isBlank(sex)) {
            throw new ServerException("你还没有情侣，请先绑定情侣", BaseErrorCode.COUPLEID_EMPTY_ERROR);
        }
//        查询最早绑定日期
        LambdaQueryWrapper<Couples> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Couples::getCreateTime)
                        .eq(Couples::getId,UserInfoContextHolder.getCoupleId());
        Couples couples = couplesMapper.selectOne(queryWrapper);
        if (Objects.isNull(couples) || Objects.isNull(couples.getCreateTime())){
            throw new ServerException("查询情侣信息失败", BaseErrorCode.COUPLEID_VALIDATE_ERROR);
        }
//        CouplesTogetherDto couplesTogetherDto = couplesMapper.selectTogetherCouples(UserInfoContextHolder.getCoupleId(), sex);
        LocalDateTime firstBindingTime = couples.getCreateTime();
        LocalDateTime now = LocalDateTime.now();
        //计算两个时间的差值
        long between = ChronoUnit.DAYS.between(firstBindingTime, now);
        return between;
    }
}


