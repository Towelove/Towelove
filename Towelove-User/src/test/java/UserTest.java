import blossom.project.towelove.common.constant.UserConstants;
import blossom.project.towelove.common.response.user.SysUserPermissionDto;
import blossom.project.towelove.framework.redis.service.RedisService;
import blossom.project.towelove.user.ToweloveUserApplication;
import blossom.project.towelove.user.entity.SysUser;
import blossom.project.towelove.user.mapper.SysPermissionMapper;
import blossom.project.towelove.user.mapper.SysUserMapper;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest(classes = ToweloveUserApplication.class)
//@TestPropertySource("classpath: config/bootstrap.yml")
//@TestPropertySource("classpath:bootstrap.yml")
public class UserTest {
    @Resource
    private SysUserMapper userMapper;

    @Resource
    private SysPermissionMapper sysPermissionMapper;
    @Test
    public void test(){
        List<SysUser> sysUsers = userMapper.selectList(null);
        System.out.println(sysUsers);
    }

    @Test
    void test02(){
        LambdaQueryWrapper<SysUser> lqw = new LambdaQueryWrapper<>();
        lqw.eq(StrUtil.isNotBlank("78"),SysUser::getPhoneNumber,"78");
        lqw.eq(StrUtil.isNotBlank("j.unfw@qq.com"),SysUser::getEmail,"j.unfw@qq.com");
        List<SysUser> sysUsers = userMapper.selectList(null);
        List<SysUser> sysUsers1 = userMapper.selectList(lqw);
        sysUsers.forEach(System.out::println);
    }

    @Test
    void test03(){
        SysUser sysUser = userMapper.selectByPhoneNumberOrEmail("78",null);
    }

    @Test
    void test04(){
        List<SysUserPermissionDto> sysUserPermissionDtos = sysPermissionMapper.selectUserPermissionByUserId(1727556093546999809L);
        System.out.println(sysUserPermissionDtos);
    }
    @Resource
    private RedisService redisService;
    @Test
    void test05() {
        redisService.setBit("user_signIn_2023:1727582102832959490", 140L, true);
    }
    @Test
    void test06() {
        int begin = 0;
        int end = 0;
        for (int i = 0; i < 1100; i++) {
            Long bitByRange = redisService.getBitByRange("user_signIn_2023:1727582102832959490", 0, i);
            if (bitByRange != 0){
                System.out.println(i);
                begin = i;
                break;
            }
        }
        for (int i = begin; i < 9999; i++) {
            Long bitByRange = redisService.getBitByRange("user_signIn_2023:1727582102832959490", begin, i);
            if (bitByRange == 3){
                System.out.println(i);
                end = i;
                break;
            }
        }
    }
    @Test
    void  test000(){
        DateTime currentTime = DateTime.now();
        Long result = 0L;
        for (int i = 0; i < 12; i++) {
            Long l = redisService.bitCount(String.format(UserConstants.USER_SIGN_IN_KEY, currentTime.get(DateTimeFieldType.year()), currentTime.get(DateTimeFieldType.monthOfYear()), 1727582102832959490L));
            result += l;
        }
        System.out.println(result);
    }

    @Test
    void test111(){
        DateTime currentTime = DateTime.now();
         String key = String.format(UserConstants.USER_SIGN_IN_KEY
                 ,currentTime.get(DateTimeFieldType.year())
                 ,"*"
                 ,1727552724501671937L);
        //redis脚本
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setResultType(Long.class);
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/user_sign_in_month_totally.lua")));
        Object execute = redisService.redisTemplate.execute(redisScript, List.of(key));
        if (execute instanceof Long){
            System.out.println((Long) execute);
        }
    }
}
class SingleTon{
    private volatile  static SingleTon singleTon;

    private SingleTon(){}

    public static SingleTon getInstance(){
        if (singleTon == null){
            synchronized (SingleTon.class){
                if (singleTon == null){
                    singleTon = new SingleTon();
                }
            }
        }
        return singleTon;
    }
}
