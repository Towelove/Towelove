package blossom.project.towelove.common.domain;

import com.baomidou.mybatisplus.annotation.TableField;

import java.util.Date;

public class BaseEntity {
    @TableField("crete_by")
    private String createBy;
    @TableField("create_time")
    private Date createTime;
    @TableField("update_by")
    private String  updateBy;
    @TableField("update_time")
    private Date updateTime;
    @TableField("deleted")
    private int deleted;
    @TableField("remark")
    private String remark;
}
