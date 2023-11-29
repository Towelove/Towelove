package blossom.project.towelove.user.service.impl;

import blossom.project.towelove.common.constant.UserConstants;
import blossom.project.towelove.common.exception.ServiceException;
import blossom.project.towelove.framework.log.annotation.LoveLog;
import blossom.project.towelove.framework.redis.service.RedisService;
import blossom.project.towelove.user.domain.UserSignInRecord;
import blossom.project.towelove.user.mapper.UserSignInRecordMapper;
import blossom.project.towelove.user.service.UserSignRecordService;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.user.service.impl
 * @className: UserSignInRecordServiceImpl
 * @author: Link Ji
 * @description: TODO
 * @date: 2023/11/27 23:29
 * @version: 1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserSignInRecordServiceImpl extends ServiceImpl<UserSignInRecordMapper, UserSignInRecord> implements UserSignRecordService {

    private final RedisService redisService;

    private final RedisTemplate<String,Object> redisTemplate;

    @Override
    public String singnInByUserId(Long userId) {
        log.info("用户：{} 发起签到请求",userId);
        DateTime dateTime = DateTime.now();
        //Key: USER_SIGN_IN_year_month:userId
        String userSignInKey = String
                .format(UserConstants.USER_SIGN_IN_KEY, dateTime.get(DateTimeFieldType.year()),dateTime.get(DateTimeFieldType.monthOfYear()), userId);
        log.info("用户签到对应签到表key为:{}",userSignInKey);
        //获取当天的偏移量
        long offset = Long
                .parseLong(LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("dd")));
        if (redisService.getBit(userSignInKey,offset)) {
            throw new ServiceException("用户已经签到了");
        }
        /**
         * TODO:考虑使用分布式锁防止并发问题
         */
        if (redisService.setBit(userSignInKey,offset,true)) {
            throw new ServiceException("用户签到失败，请联系管理员");
        }
        String userSignInTotallyOnYear = String.format(UserConstants.USER_TOTAL_SIGN_IN_KEY,dateTime.get(DateTimeFieldType.year()),userId);
        String userSignInTotallyOnMouth = String.format(UserConstants.USER_TOTAL_SIGN_IN_BY_MOUTH_KEY,dateTime.get(DateTimeFieldType.year())
                ,dateTime.get(DateTimeFieldType.monthOfYear())
                ,userId);
        if (Objects.nonNull(redisTemplate.opsForValue().get(userSignInTotallyOnYear))) {

        }
        //异步更新总签到天数和月签到天数
        return "签到成功";
    }

    @Override
    public Long getSignInTotally(Long userId) {
        DateTime dateTime = DateTime.now();
        String userTotalSignInKey = String.format(UserConstants.USER_TOTAL_SIGN_IN_KEY,dateTime.get(DateTimeFieldType.year()), userId);
        Object userSignINTotally = redisService.redisTemplate.opsForValue().get(userTotalSignInKey);
        if (Objects.nonNull(userSignINTotally)){
            return Long.parseLong(userSignINTotally.toString());
        }
        Long bitByRange = getContinuousSignCount(userId);
        redisTemplate.opsForValue().set(userTotalSignInKey,bitByRange,10, TimeUnit.HOURS);
        return bitByRange;
    }

    @Override
    public Long getSignInByMouthTotally(Long userId) {
        DateTime currentTime = DateTime.now();
        String userTotalSignInByMouthKey = String.format(UserConstants.USER_TOTAL_SIGN_IN_BY_MOUTH_KEY,
                currentTime.get(DateTimeFieldType.year()),
                currentTime.get(DateTimeFieldType.monthOfYear()), userId);
        Object userSignINTotally = redisService.redisTemplate.opsForValue().get(userTotalSignInByMouthKey);
        if (Objects.nonNull(userSignINTotally)){
            return Long.parseLong(userSignINTotally.toString());
        }
        String userSignInKey = String.format(UserConstants.USER_SIGN_IN_KEY
                , currentTime.get(DateTimeFieldType.year())
                , currentTime.get(DateTimeFieldType.monthOfYear())
                , userId);
        long bitByRange = getContinuousSignCountByMouth(userSignInKey);
        redisTemplate.opsForValue().set(userTotalSignInByMouthKey,bitByRange,10,TimeUnit.HOURS);
        //异步更新到数据库
        return bitByRange;
    }

    /**
     * 获得用户全年签到天数
     * @param userId
     * @return
     */
    public Long getContinuousSignCount(Long userId){
        Long userSignInTotallyOnYear = 0L;
        DateTime currentTime = DateTime.now();
        for (int month = 0; month < 12; month++) {
            String userSignInKey = String.format(UserConstants.USER_SIGN_IN_KEY, currentTime.get(DateTimeFieldType.year())
                    , month
                    , userId);
            long continuousSignCountByMouth = getContinuousSignCountByMouth(userSignInKey);
            userSignInTotallyOnYear += continuousSignCountByMouth;
        }
        //获得总签到天数
        return userSignInTotallyOnYear;
    }

    /**
     * 获取本月某用户签到天数
     * @param key: 用户签到BitMap的Key
     * @return 当月签到总数量
     */
    public long getContinuousSignCountByMouth(String key){
        //获得本月开始时候的偏移量
        return redisService.getBitByRange(key);
    }
}
