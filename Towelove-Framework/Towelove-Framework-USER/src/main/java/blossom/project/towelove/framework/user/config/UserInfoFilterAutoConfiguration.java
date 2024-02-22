package blossom.project.towelove.framework.user.config;

import blossom.project.towelove.framework.user.core.UserInfoFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.framework.log.config
 * @className: UserInfoFilterAutoConfiguration
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/2/22 14:28
 * @version: 1.0
 */
@ConditionalOnWebApplication
public class UserInfoFilterAutoConfiguration {
    Logger log = LoggerFactory.getLogger(UserInfoFilterAutoConfiguration.class);


    @Bean
    public FilterRegistrationBean<UserInfoFilter> userInfoFilter(){
        FilterRegistrationBean<UserInfoFilter> registrationBean = new FilterRegistrationBean<>();
        UserInfoFilter filter = new UserInfoFilter();
        registrationBean.setFilter(filter);
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(100);
        log.info("[ToweLove-USER] UserInfo Filter Register Successfully!!!");
        return registrationBean;
    }
}
