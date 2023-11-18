CREATE TABLE `love_album`
(
    `id`            BIGINT NOT NULL AUTO_INCREMENT COMMENT '编号',
    `boy_id`        BIGINT COMMENT '男方id',
    `girl_id`       BIGINT COMMENT '女方id',
    `title`         VARCHAR(255) COMMENT '相册标题',
    `days_in_love`  DATETIME COMMENT '相恋天数',
    `likes_number`  INT COMMENT '点赞人数',
    `views_number`  INT COMMENT '观看人数',
    `can_see`       INT COMMENT '外人是否可见',
    `status`        INT COMMENT '开启状态',
    `love_logs_ids` JSON COMMENT '对应的Lovelogs的id',
    `create_by`     VARCHAR(255) COMMENT '创建者',
    `create_time`   DATETIME COMMENT '创建时间',
    `update_by`     VARCHAR(255) COMMENT '更新者',
    `update_time`   DATETIME COMMENT '更新时间',
    `remark`        TEXT COMMENT '备注',
    `deleted`       TINYINT(1) default 0 COMMENT '是否删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;


CREATE TABLE `love_list`
(
    `id`            BIGINT       NOT NULL AUTO_INCREMENT COMMENT '编号',
    `love_album_id` BIGINT COMMENT '恋爱相册的id',
    `title`         VARCHAR(255) NOT NULL COMMENT '标题',
    `done`          VARCHAR(255) COMMENT '是否完成',
    `photo`         VARCHAR(255) COMMENT '完成的合照',
    `finish_time`   DATE COMMENT '完成的日期',
    `deleted`       tinyint(1) default 0 COMMENT '是否删除',

    -- 公共字段
    `create_by`     VARCHAR(255) COMMENT '创建者',
    `create_time`   DATETIME COMMENT '创建时间',
    `update_by`     VARCHAR(255) COMMENT '更新者',
    `update_time`   DATETIME COMMENT '更新时间',
    `remark`        TEXT COMMENT '备注',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;


CREATE TABLE `love_logs`
(
    `id`            BIGINT NOT NULL AUTO_INCREMENT COMMENT '编号',
    `title`         VARCHAR(255) COMMENT '恋爱日记的标题',
    `love_album_id` BIGINT COMMENT '恋爱相册id，用于获取当前日志属于哪一个相册',
    `description`   TEXT COMMENT '今日描述',
    `urls`          TEXT COMMENT '照片url，根据英文逗号分割',
    `status`        INT COMMENT '状态',
    `can_see`       INT COMMENT '外人是否可见',

    -- 公共字段
    `create_by`     VARCHAR(255) COMMENT '创建者',
    `create_time`   DATETIME COMMENT '创建时间',
    `update_by`     VARCHAR(255) COMMENT '更新者',
    `update_time`   DATETIME COMMENT '更新时间',
    `remark`        TEXT COMMENT '备注',
    `deleted`       TINYINT(1) default 0 COMMENT '是否删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;


CREATE TABLE `love_post_office`
(
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '编号',
    `sender_id`   BIGINT       NOT NULL COMMENT '发送者ID',
    `sender_name` VARCHAR(255) NOT NULL COMMENT '发送者姓名',
    `receiver_id` BIGINT       NOT NULL COMMENT '接收者ID',
    `content`     TEXT         NOT NULL COMMENT '内容',
    `send_time`   DATETIME COMMENT '发送时间',
    `is_read`     TINYINT(1) COMMENT '是否已读',
    `deleted`     TINYINT(1) default 0 COMMENT '是否删除',


    -- 公共字段
    `create_by`   VARCHAR(255) COMMENT '创建者',
    `create_time` DATETIME COMMENT '创建时间',
    `update_by`   VARCHAR(255) COMMENT '更新者',
    `update_time` DATETIME COMMENT '更新时间',
    `remark`      TEXT COMMENT '备注',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `time_line`
(
    `id`            BIGINT       NOT NULL AUTO_INCREMENT COMMENT '编号',
    `love_album_id` BIGINT COMMENT '恋爱相册ID',
    `title`         VARCHAR(255) NOT NULL COMMENT '标题',
    `deleted`       TINYINT(1) default 0 COMMENT '是否删除',

    -- 公共字段
    `create_by`     VARCHAR(255) COMMENT '创建者',
    `create_time`   DATETIME COMMENT '创建时间',
    `update_by`     VARCHAR(255) COMMENT '更新者',
    `update_time`   DATETIME COMMENT '更新时间',
    `remark`        TEXT COMMENT '备注',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;


CREATE TABLE `msg_task`
(
    `id`              BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`         BIGINT COMMENT '用户id',
    `account_id`      BIGINT COMMENT '邮箱账号id',
    `template_id`     BIGINT COMMENT '模板id',
    `receive_account` VARCHAR(255) COMMENT '接收消息的邮箱账号',
    `nickname`        VARCHAR(255) COMMENT '发送人名称',
    `title`           VARCHAR(255) COMMENT '标题',
    `content`         TEXT COMMENT '内容',
    `send_time`       TIME COMMENT '消息发送时间',
    `params`          JSON COMMENT '参数数组(自动根据内容生成)',
    `status`          INT COMMENT '状态',

    -- 公共字段
    `create_by`       VARCHAR(255) COMMENT '创建者',
    `create_time`     DATETIME COMMENT '创建时间',
    `update_by`       VARCHAR(255) COMMENT '更新者',
    `update_time`     DATETIME COMMENT '更新时间',
    `remark`          TEXT COMMENT '备注',
    `deleted`         TINYINT(1) COMMENT '是否删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

create table towelove.sys_oper_log
(
    oper_id        bigint auto_increment comment '日志主键'
        primary key,
    title          varchar(50)   default '' null comment '模块标题',
    business_type  int           default 0 null comment '业务类型（0其它 1新增 2修改 3删除）',
    method         varchar(100)  default '' null comment '方法名称',
    request_method varchar(10)   default '' null comment '请求方式',
    operator_type  int           default 0 null comment '操作类别（0其它 1后台用户 2手机端用户）',
    oper_name      varchar(50)   default '' null comment '操作人员',
    dept_name      varchar(50)   default '' null comment '部门名称',
    oper_url       varchar(255)  default '' null comment '请求URL',
    oper_ip        varchar(128)  default '' null comment '主机地址',
    oper_location  varchar(255)  default '' null comment '操作地点',
    oper_param     varchar(2000) default '' null comment '请求参数',
    json_result    varchar(2000) default '' null comment '返回参数',
    status         int           default 0 null comment '操作状态（0正常 1异常）',
    error_msg      varchar(2000) default '' null comment '错误消息',
    oper_time      datetime null comment '操作时间',
    -- 公共字段
    `create_by`    VARCHAR(255) COMMENT '创建者',
    `remark`       TEXT COMMENT '备注',
    `deleted`      TINYINT(1) COMMENT '是否删除'
) comment '操作日志记录' auto_increment = 99;

create table towelove.sys_user
(
    user_id     bigint auto_increment comment '主键,用户id'
        primary key,
    role_id     bigint                     not null comment '用户角色id',
    user_name   varchar(64) default 'NULL' not null comment '用户名',
    nick_name   varchar(64) default 'NULL' not null comment '昵称',
    password    varchar(64) default 'NULL' not null comment '密码',
    email       varchar(64) null comment '邮箱',
    smtp_code   varchar(64) null comment '邮箱SMTP编码',
    phonenumber varchar(32) null comment '手机号',
    sex         char null comment '用户性别（0男，1女，2未知）',
    avatar      varchar(128) null comment '头像',
    status      char        default '0' null comment '账号状态（0正常 1停用）',
    user_type   char        default '1'    not null comment '用户类型（0管理员，1普通用户）',
    login_ip    varchar(64) null comment '上次登录ip',
    create_by   varchar(64) null comment '创建人的用户id',
    create_time datetime null comment '创建时间',
    update_by   varchar(64) null comment '更新人',
    update_time datetime null comment '更新时间',
    deleted     tinyint(1) default 0 null comment '删除标志（0代表未删除，1代表已删除）',
    remark      varchar(200) null comment '备注'
) comment '系统用户表' auto_increment = 6;



create table towelove.sys_dict_type
(
    dict_id     bigint auto_increment comment '字典主键'
        primary key,
    dict_name   varchar(100) default '' null comment '字典名称',
    dict_type   varchar(100) default '' null comment '字典类型',
    status      char         default '0' null comment '状态（0正常 1停用）',
    create_by   varchar(64)  default '' null comment '创建者',
    create_time datetime null comment '创建时间',
    update_by   varchar(64)  default '' null comment '更新者',
    update_time datetime null comment '更新时间',
    remark      varchar(500) null comment '备注',
    `deleted`   tinyint(1) default 0 COMMENT '是否删除',
    constraint dict_type
        unique (dict_type)
) comment '字典类型表' auto_increment = 99;



CREATE TABLE `sys_send_log`
(
    `id`            BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`       BIGINT COMMENT '用户ID',
    `receive_email` VARCHAR(255) COMMENT '接收邮箱',
    `send_email`    VARCHAR(255) COMMENT '发送邮箱',
    `send_error`    TEXT COMMENT '发送错误信息',
    `send_status`   INT COMMENT '邮件发送状态',

    -- 公共字段
    `create_by`     VARCHAR(255) COMMENT '创建者',
    `create_time`   DATETIME COMMENT '创建时间',
    `update_by`     VARCHAR(255) COMMENT '更新者',
    `update_time`   DATETIME COMMENT '更新时间',
    `remark`        TEXT COMMENT '备注',
    `deleted`       TINYINT(1) default 0 COMMENT '是否删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;


CREATE TABLE `sys_mail_template`
(
    `id`          BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`        VARCHAR(255) COMMENT '模版名称',
    `code`        VARCHAR(255) COMMENT '模版编号',
    `account_id`  BIGINT COMMENT '发送的邮箱账号编号，对应到MailAccount的id字段',
    `nickname`    VARCHAR(255) COMMENT '发送人名称',
    `title`       VARCHAR(255) COMMENT '标题',
    `content`     TEXT COMMENT '内容',
    `status`      INT COMMENT '状态',
    `params`      JSON COMMENT '参数数组(自动根据内容生成)',

    -- 公共字段
    `create_by`   VARCHAR(255) COMMENT '创建者',
    `create_time` DATETIME COMMENT '创建时间',
    `update_by`   VARCHAR(255) COMMENT '更新者',
    `update_time` DATETIME COMMENT '更新时间',
    `remark`      TEXT COMMENT '备注',
    `deleted`     TINYINT(1) default 0 COMMENT '是否删除',

    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;


CREATE TABLE `sys_mail_log`
(
    `id`                BIGINT NOT NULL AUTO_INCREMENT COMMENT '日志编号，自增',
    `user_id`           BIGINT COMMENT '用户编码',
    `user_type`         INT COMMENT '用户类型',
    `to_mail`           VARCHAR(255) COMMENT '接收邮箱地址',
    `account_id`        BIGINT COMMENT '邮箱账号编号',
    `from_mail`         VARCHAR(255) COMMENT '发送邮箱地址',
    `template_id`       BIGINT COMMENT '模版编号',
    `template_code`     VARCHAR(255) COMMENT '模版编码',
    `template_nickname` VARCHAR(255) COMMENT '模版发送人名称',
    `template_title`    VARCHAR(255) COMMENT '模版标题',
    `template_content`  TEXT COMMENT '模版内容',
    `template_params`   JSON COMMENT '模版参数',
    `send_status`       INT COMMENT '发送状态',
    `send_time`         DATETIME COMMENT '发送时间',
    `send_message_id`   VARCHAR(255) COMMENT '发送返回的消息ID',
    `send_exception`    TEXT COMMENT '发送异常',

    -- 公共字段
    `create_by`         VARCHAR(255) COMMENT '创建者',
    `create_time`       DATETIME COMMENT '创建时间',
    `update_by`         VARCHAR(255) COMMENT '更新者',
    `update_time`       DATETIME COMMENT '更新时间',
    `remark`            TEXT COMMENT '备注',
    `deleted`           tinyint(1) default 0 COMMENT '是否删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;


CREATE TABLE `sys_mail_account`
(
    `id`          BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`     BIGINT COMMENT '用户id',
    `mail`        VARCHAR(255) COMMENT '邮箱',
    `username`    VARCHAR(255) COMMENT '用户名',
    `password`    VARCHAR(255) COMMENT '密码',
    `host`        VARCHAR(255) DEFAULT 'smtp.qq.com' COMMENT 'SMTP 服务器域名',
    `port`        INT          DEFAULT 465 COMMENT 'SMTP 服务器端口',
    `ssl_enable`  TINYINT(1) DEFAULT 1 COMMENT '是否开启 SSL',

    -- 公共字段
    `create_by`   VARCHAR(255) COMMENT '创建者',
    `create_time` DATETIME COMMENT '创建时间',
    `update_by`   VARCHAR(255) COMMENT '更新者',
    `update_time` DATETIME COMMENT '更新时间',
    `remark`      TEXT COMMENT '备注',
    `deleted`     TINYINT(1) default 0 COMMENT '是否删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

create table towelove.email_dict
(
    email_id       bigint auto_increment comment '主键,email类型id'
        primary key,
    email_type     bigint                            not null comment '邮箱类型',
    protocal_port  int         default 465           not null comment '协议端口',
    email_protocal varchar(64) default 'smtp'        not null comment '协议',
    email_host     varchar(64) default 'smtp.qq.com' not null comment '协议主机',
    status         char        default '0' null comment '账号状态（0正常 1停用）',
    create_by      varchar(64) null comment '创建人的用户id',
    create_time    datetime null comment '创建时间',
    update_by      varchar(64) null comment '更新人',
    update_time    datetime null comment '更新时间',
    deleted        tinyint(1) default 0 null comment '删除标志（0代表未删除，1代表已删除）',
    remark         varchar(200) null comment '备注'
) comment '系统用户表';

create table towelove.user_email
(
    user_email_id  bigint auto_increment comment '用户邮箱映射表id'
        primary key,
    user_id        bigint      not null comment '用户id',
    email_id       bigint      not null comment '对应的邮箱id类型',
    email          varchar(64) not null comment '邮箱',
    email_password varchar(64) not null comment 'smtp密码',
    status         char default '0' null comment '账号状态（0正常 1停用）',
    create_by      varchar(64) null comment '创建人的用户id',
    create_time    datetime null comment '创建时间',
    update_by      varchar(64) null comment '更新人',
    update_time    datetime null comment '更新时间',
    deleted        tinyint(1) default 0 null comment '删除标志（0代表未删除，1代表已删除）',
    remark         varchar(200) null comment '备注'
) comment '系统用户表' auto_increment = 2;


CREATE TABLE user_info
(
    id                BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'id',
    openid            VARCHAR(255) COMMENT '微信openid',
    nick_ame          VARCHAR(255) COMMENT '昵称',
    phone             VARCHAR(255) COMMENT '手机号',
    name              VARCHAR(255) COMMENT '用户姓名',
    certificates_type VARCHAR(255) COMMENT '证件类型',
    certificates_no   VARCHAR(255) COMMENT '证件编号',
    certificates_url  VARCHAR(255) COMMENT '证件路径',
    auth_status       INT COMMENT '认证状态（0：未认证 1：认证中 2：认证成功 -1：认证失败）',
    status            INT COMMENT '状态（0：锁定 1：正常）'
);



#
# XXL-JOB v2.4.0
# Copyright (c) 2015-present, xuxueli.

CREATE database if NOT EXISTS `xxl_job` default character set utf8mb4 collate utf8mb4_unicode_ci;
use
`xxl_job`;

SET NAMES utf8mb4;

CREATE TABLE `xxl_job_info`
(
    `id`                        int(11) NOT NULL AUTO_INCREMENT,
    `job_group`                 int(11) NOT NULL COMMENT '执行器主键ID',
    `job_desc`                  varchar(255) NOT NULL,
    `add_time`                  datetime              DEFAULT NULL,
    `update_time`               datetime              DEFAULT NULL,
    `author`                    varchar(64)           DEFAULT NULL COMMENT '作者',
    `alarm_email`               varchar(255)          DEFAULT NULL COMMENT '报警邮件',
    `schedule_type`             varchar(50)  NOT NULL DEFAULT 'NONE' COMMENT '调度类型',
    `schedule_conf`             varchar(128)          DEFAULT NULL COMMENT '调度配置，值含义取决于调度类型',
    `misfire_strategy`          varchar(50)  NOT NULL DEFAULT 'DO_NOTHING' COMMENT '调度过期策略',
    `executor_route_strategy`   varchar(50)           DEFAULT NULL COMMENT '执行器路由策略',
    `executor_handler`          varchar(255)          DEFAULT NULL COMMENT '执行器任务handler',
    `executor_param`            varchar(512)          DEFAULT NULL COMMENT '执行器任务参数',
    `executor_block_strategy`   varchar(50)           DEFAULT NULL COMMENT '阻塞处理策略',
    `executor_timeout`          int(11) NOT NULL DEFAULT '0' COMMENT '任务执行超时时间，单位秒',
    `executor_fail_retry_count` int(11) NOT NULL DEFAULT '0' COMMENT '失败重试次数',
    `glue_type`                 varchar(50)  NOT NULL COMMENT 'GLUE类型',
    `glue_source`               mediumtext COMMENT 'GLUE源代码',
    `glue_remark`               varchar(128)          DEFAULT NULL COMMENT 'GLUE备注',
    `glue_updatetime`           datetime              DEFAULT NULL COMMENT 'GLUE更新时间',
    `child_jobid`               varchar(255)          DEFAULT NULL COMMENT '子任务ID，多个逗号分隔',
    `trigger_status`            tinyint(4) NOT NULL DEFAULT '0' COMMENT '调度状态：0-停止，1-运行',
    `trigger_last_time`         bigint(13) NOT NULL DEFAULT '0' COMMENT '上次调度时间',
    `trigger_next_time`         bigint(13) NOT NULL DEFAULT '0' COMMENT '下次调度时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `xxl_job_log`
(
    `id`                        bigint(20) NOT NULL AUTO_INCREMENT,
    `job_group`                 int(11) NOT NULL COMMENT '执行器主键ID',
    `job_id`                    int(11) NOT NULL COMMENT '任务，主键ID',
    `executor_address`          varchar(255) DEFAULT NULL COMMENT '执行器地址，本次执行的地址',
    `executor_handler`          varchar(255) DEFAULT NULL COMMENT '执行器任务handler',
    `executor_param`            varchar(512) DEFAULT NULL COMMENT '执行器任务参数',
    `executor_sharding_param`   varchar(20)  DEFAULT NULL COMMENT '执行器任务分片参数，格式如 1/2',
    `executor_fail_retry_count` int(11) NOT NULL DEFAULT '0' COMMENT '失败重试次数',
    `trigger_time`              datetime     DEFAULT NULL COMMENT '调度-时间',
    `trigger_code`              int(11) NOT NULL COMMENT '调度-结果',
    `trigger_msg`               text COMMENT '调度-日志',
    `handle_time`               datetime     DEFAULT NULL COMMENT '执行-时间',
    `handle_code`               int(11) NOT NULL COMMENT '执行-状态',
    `handle_msg`                text COMMENT '执行-日志',
    `alarm_status`              tinyint(4) NOT NULL DEFAULT '0' COMMENT '告警状态：0-默认、1-无需告警、2-告警成功、3-告警失败',
    PRIMARY KEY (`id`),
    KEY                         `I_trigger_time` (`trigger_time`),
    KEY                         `I_handle_code` (`handle_code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `xxl_job_log_report`
(
    `id`            int(11) NOT NULL AUTO_INCREMENT,
    `trigger_day`   datetime DEFAULT NULL COMMENT '调度-时间',
    `running_count` int(11) NOT NULL DEFAULT '0' COMMENT '运行中-日志数量',
    `suc_count`     int(11) NOT NULL DEFAULT '0' COMMENT '执行成功-日志数量',
    `fail_count`    int(11) NOT NULL DEFAULT '0' COMMENT '执行失败-日志数量',
    `update_time`   datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `i_trigger_day` (`trigger_day`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `xxl_job_logglue`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT,
    `job_id`      int(11) NOT NULL COMMENT '任务，主键ID',
    `glue_type`   varchar(50) DEFAULT NULL COMMENT 'GLUE类型',
    `glue_source` mediumtext COMMENT 'GLUE源代码',
    `glue_remark` varchar(128) NOT NULL COMMENT 'GLUE备注',
    `add_time`    datetime    DEFAULT NULL,
    `update_time` datetime    DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `xxl_job_registry`
(
    `id`             int(11) NOT NULL AUTO_INCREMENT,
    `registry_group` varchar(50)  NOT NULL,
    `registry_key`   varchar(255) NOT NULL,
    `registry_value` varchar(255) NOT NULL,
    `update_time`    datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY              `i_g_k_v` (`registry_group`, `registry_key`, `registry_value`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `xxl_job_group`
(
    `id`           int(11) NOT NULL AUTO_INCREMENT,
    `app_name`     varchar(64) NOT NULL COMMENT '执行器AppName',
    `title`        varchar(12) NOT NULL COMMENT '执行器名称',
    `address_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '执行器地址类型：0=自动注册、1=手动录入',
    `address_list` text COMMENT '执行器地址列表，多地址逗号分隔',
    `update_time`  datetime DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `xxl_job_user`
(
    `id`         int(11) NOT NULL AUTO_INCREMENT,
    `username`   varchar(50) NOT NULL COMMENT '账号',
    `password`   varchar(50) NOT NULL COMMENT '密码',
    `role`       tinyint(4) NOT NULL COMMENT '角色：0-普通用户、1-管理员',
    `permission` varchar(255) DEFAULT NULL COMMENT '权限：执行器ID列表，多个逗号分割',
    PRIMARY KEY (`id`),
    UNIQUE KEY `i_username` (`username`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `xxl_job_lock`
(
    `lock_name` varchar(50) NOT NULL COMMENT '锁名称',
    PRIMARY KEY (`lock_name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

INSERT INTO `xxl_job_group`(`id`, `app_name`, `title`, `address_type`, `address_list`, `update_time`)
VALUES (1, 'xxl-job-executor-sample', '示例执行器', 0, NULL, '2018-11-03 22:21:31');
INSERT INTO `xxl_job_info`(`id`, `job_group`, `job_desc`, `add_time`, `update_time`, `author`, `alarm_email`,
                           `schedule_type`, `schedule_conf`, `misfire_strategy`, `executor_route_strategy`,
                           `executor_handler`, `executor_param`, `executor_block_strategy`, `executor_timeout`,
                           `executor_fail_retry_count`, `glue_type`, `glue_source`, `glue_remark`, `glue_updatetime`,
                           `child_jobid`)
VALUES (1, 1, '测试任务1', '2018-11-03 22:21:31', '2018-11-03 22:21:31', 'XXL', '', 'CRON', '0 0 0 * * ? *',
        'DO_NOTHING',
        'FIRST', 'demoJobHandler', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2018-11-03 22:21:31',
        '');
INSERT INTO `xxl_job_user`(`id`, `username`, `password`, `role`, `permission`)
VALUES (1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', 1, NULL);
INSERT INTO `xxl_job_lock` (`lock_name`)
VALUES ('schedule_lock');

commit;

