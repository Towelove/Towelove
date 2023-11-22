import blossom.project.towelove.common.exception.ServiceException;
import blossom.project.towelove.user.ToweloveUserApplication;
import blossom.project.towelove.user.domain.SysUser;
import blossom.project.towelove.user.mapper.SysUserMapper;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest(classes = ToweloveUserApplication.class)
public class UserTest {
    @Resource
    private SysUserMapper userMapper;
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
}
