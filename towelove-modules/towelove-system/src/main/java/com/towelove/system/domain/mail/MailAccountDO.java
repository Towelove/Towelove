package com.towelove.system.domain.mail;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.towelove.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 邮箱账号 DO
 *
 * 用途：配置发送邮箱的账号
 *
 * @author: 张锦标
 * @since 2023-03-01
 */
@TableName(value = "sys_mail_account", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class MailAccountDO extends BaseEntity {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 邮箱
     */
    private String mail;

    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * SMTP 服务器域名
     */
    private String host;
    /**
     * SMTP 服务器端口
     */
    private Integer port;
    /**
     * 是否开启 SSL
     */
    private Boolean sslEnable;

}
