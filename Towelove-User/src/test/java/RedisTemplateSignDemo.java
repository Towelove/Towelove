
import blossom.project.towelove.user.ToweloveUserApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.TestPropertySource;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用redis（ RedisTemplate ）中的bitMap 记录用户签到情况
 */

public class RedisTemplateSignDemo {


    @Resource
    RedisTemplate redisTemplate;


    @Test
    public void mainTest() {
        Long initId = 10001234L;
        LocalDate today = LocalDate.now();
        DateTimeFormatter yyyyMMDft = DateTimeFormatter.ofPattern("yyyyMMdd");
        String dateMonthIndexStr = today.format(yyyyMMDft);
        String memberSignCacheKey = "member_sign:" + dateMonthIndexStr.substring(0, dateMonthIndexStr.length() - 2) + ":" + initId;
        List<String> signInfoList = getSignInfo(memberSignCacheKey, today);
        System.out.println("本月该ID签到情况:"+ signInfoList.toString());
        System.out.println("是否签到成功" + !doSign(memberSignCacheKey, today));
        System.out.println("用户:" + initId + "今天" + (checkSign(memberSignCacheKey, today) ? "已经" : "还未") + "签到");
        System.out.println("用户:" + initId + "当前签到了" + getSignCount(memberSignCacheKey) + "次");
        System.out.println("用户:" + initId + "本月连续签到了" + getContinuousSignCount( memberSignCacheKey,  today) + "天");
        System.out.println("用户:" + initId + "本月第一次签到日期为 " + getFirstSignDate( memberSignCacheKey,  today));
    }


    /**
     * 查询当天是否有签到
     *
     * @param cacheKey
     * @param localDate
     * @return
     */
    public boolean checkSign(String cacheKey, LocalDate localDate) {
        return redisTemplate.opsForValue().getBit(cacheKey, localDate.getDayOfMonth() - 1);
    }

    /**
     * 获取用户签到次数
     */
    public long getSignCount(String cacheKey) {
        Long bitCount = (Long) redisTemplate.execute((RedisCallback) cbk -> cbk.bitCount(cacheKey.getBytes()));
        return bitCount;
    }

    /**
     * 获取本月签到信息
     * @param cacheKey
     * @param localDate
     * @return
     */
    @Test
    public List<String> getSignInfo(String cacheKey, LocalDate localDate) {
        List<String> resultList = new ArrayList<>();
        int lengthOfMonth = localDate.lengthOfMonth();
        List<Long> bitFieldList = (List<Long>) redisTemplate.execute((RedisCallback<List<Long>>) cbk
                -> cbk.bitField(cacheKey.getBytes(), BitFieldSubCommands.create().get(BitFieldSubCommands.BitFieldType.unsigned(lengthOfMonth)).valueAt(0)));
        if (bitFieldList != null && bitFieldList.size() > 0) {
            long valueDec = bitFieldList.get(0) != null ? bitFieldList.get(0) : 0;
//            System.out.println("valueDec" + valueDec);
            for (int i = lengthOfMonth; i > 0; i--) {
                LocalDate tempDayOfMonth = LocalDate.now().withDayOfMonth(i);
                if (valueDec >> 1 << 1 != valueDec) {
                    resultList.add(tempDayOfMonth.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                }
                valueDec >>= 1;
            }
        }
        return resultList;
    }


    /**
     * 签到操作
     */
    @Test
    public boolean doSign(String cacheKey, LocalDate localDate) {
        if (localDate == null) {
            localDate = LocalDate.now();
        }
        //localDate 是 本月的第 dayOfMonth 天
        int currOffset = localDate.get(ChronoField.DAY_OF_MONTH) - 1;
        return redisTemplate.opsForValue().setBit(cacheKey, currOffset, true);
    }


    /**
     * 获取当月连续签到次数
     * @param cacheKey
     * @param localDate
     * @return
     */
    public long getContinuousSignCount(String cacheKey, LocalDate localDate) {
        long signCount = 0;
        List<Long> list = redisTemplate.opsForValue().bitField(cacheKey, BitFieldSubCommands.create().get(BitFieldSubCommands.BitFieldType
                .unsigned(localDate.getDayOfMonth())).valueAt(0));
        if(list != null && list.size() > 0){
            long valueDec = list.get(0) != null ? list.get(0) : 0 ;
//            System.out.println("valueDec..." + valueDec);
            for(int i = 0; i< localDate.getDayOfMonth() ; i++){
                if(valueDec >> 1 << 1 == valueDec){
                    if(i > 0){ break; }
                }else{
                    signCount += 1;
                }
                valueDec >>= 1;
            }
        }
        return signCount;
    }

    /**
     * 获取当月首次签到日期
     */
    public LocalDate getFirstSignDate(String cacheKey, LocalDate localDate) {
            long bitPosition = (Long) redisTemplate.execute((RedisCallback) cbk -> cbk.bitPos(cacheKey.getBytes(), true));
            return bitPosition < 0 ? null : localDate.withDayOfMonth((int) bitPosition + 1);
        }
}
