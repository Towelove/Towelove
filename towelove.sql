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
    business_type  int           default 0  null comment '业务类型（0其它 1新增 2修改 3删除）',
    method         varchar(100)  default '' null comment '方法名称',
    request_method varchar(10)   default '' null comment '请求方式',
    operator_type  int           default 0  null comment '操作类别（0其它 1后台用户 2手机端用户）',
    oper_name      varchar(50)   default '' null comment '操作人员',
    dept_name      varchar(50)   default '' null comment '部门名称',
    oper_url       varchar(255)  default '' null comment '请求URL',
    oper_ip        varchar(128)  default '' null comment '主机地址',
    oper_location  varchar(255)  default '' null comment '操作地点',
    oper_param     varchar(2000) default '' null comment '请求参数',
    json_result    varchar(2000) default '' null comment '返回参数',
    status         int           default 0  null comment '操作状态（0正常 1异常）',
    error_msg      varchar(2000) default '' null comment '错误消息',
    oper_time      datetime                 null comment '操作时间',
    -- 公共字段
    `create_by`    VARCHAR(255) COMMENT '创建者',
    `remark`       TEXT COMMENT '备注',
    `deleted`      TINYINT(1) COMMENT '是否删除'
)
    comment '操作日志记录' auto_increment = 99;

create table towelove.sys_user
(
    user_id     bigint auto_increment comment '主键,用户id'
        primary key,
    role_id     bigint                     not null comment '用户角色id',
    user_name   varchar(64) default 'NULL' not null comment '用户名',
    nick_name   varchar(64) default 'NULL' not null comment '昵称',
    password    varchar(64) default 'NULL' not null comment '密码',
    email       varchar(64)                null comment '邮箱',
    smtp_code   varchar(64)                null comment '邮箱SMTP编码',
    phonenumber varchar(32)                null comment '手机号',
    sex         char                       null comment '用户性别（0男，1女，2未知）',
    avatar      varchar(128)               null comment '头像',
    status      char        default '0'    null comment '账号状态（0正常 1停用）',
    user_type   char        default '1'    not null comment '用户类型（0管理员，1普通用户）',
    login_ip    varchar(64)                null comment '上次登录ip',
    create_by   varchar(64)                null comment '创建人的用户id',
    create_time datetime                   null comment '创建时间',
    update_by   varchar(64)                null comment '更新人',
    update_time datetime                   null comment '更新时间',
    deleted    tinyint(1)         default 0      null comment '删除标志（0代表未删除，1代表已删除）',
    remark      varchar(200)               null comment '备注'
)
    comment '系统用户表' auto_increment = 6;



create table towelove.sys_dict_type
(
    dict_id     bigint auto_increment comment '字典主键'
        primary key,
    dict_name   varchar(100) default ''  null comment '字典名称',
    dict_type   varchar(100) default ''  null comment '字典类型',
    status      char         default '0' null comment '状态（0正常 1停用）',
    create_by   varchar(64)  default ''  null comment '创建者',
    create_time datetime                 null comment '创建时间',
    update_by   varchar(64)  default ''  null comment '更新者',
    update_time datetime                 null comment '更新时间',
    remark      varchar(500)             null comment '备注',
    `deleted`   tinyint(1)   default 0 COMMENT '是否删除',
    constraint dict_type
        unique (dict_type)
)
    comment '字典类型表' auto_increment = 99;



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
    `ssl_enable`  TINYINT(1)   DEFAULT 1 COMMENT '是否开启 SSL',

    -- 公共字段
    `create_by`   VARCHAR(255) COMMENT '创建者',
    `create_time` DATETIME COMMENT '创建时间',
    `update_by`   VARCHAR(255) COMMENT '更新者',
    `update_time` DATETIME COMMENT '更新时间',
    `remark`      TEXT COMMENT '备注',
    `deleted`     TINYINT(1)   default 0 COMMENT '是否删除',
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
    status         char        default '0'           null comment '账号状态（0正常 1停用）',
    create_by      varchar(64)                       null comment '创建人的用户id',
    create_time    datetime                          null comment '创建时间',
    update_by      varchar(64)                       null comment '更新人',
    update_time    datetime                          null comment '更新时间',
    deleted        tinyint(1)  default 0             null comment '删除标志（0代表未删除，1代表已删除）',
    remark         varchar(200)                      null comment '备注'
)
    comment '系统用户表';

create table towelove.user_email
(
    user_email_id  bigint auto_increment comment '用户邮箱映射表id'
        primary key,
    user_id        bigint                 not null comment '用户id',
    email_id       bigint                 not null comment '对应的邮箱id类型',
    email          varchar(64)            not null comment '邮箱',
    email_password varchar(64)            not null comment 'smtp密码',
    status         char       default '0' null comment '账号状态（0正常 1停用）',
    create_by      varchar(64)            null comment '创建人的用户id',
    create_time    datetime               null comment '创建时间',
    update_by      varchar(64)            null comment '更新人',
    update_time    datetime               null comment '更新时间',
    deleted        tinyint(1) default 0   null comment '删除标志（0代表未删除，1代表已删除）',
    remark         varchar(200)           null comment '备注'
)
    comment '系统用户表' auto_increment = 2;

