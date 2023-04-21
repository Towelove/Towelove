package com.towelove.system.api.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.towelove.common.core.web.domain.BaseEntity;
import lombok.Data;

@Data
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
    private String host = "smtp.qq.com";
    /**
     * SMTP 服务器端口
     */
    private Integer port = 465;
    /**
     * 是否开启 SSL
     */
    private Boolean sslEnable = true;

}
