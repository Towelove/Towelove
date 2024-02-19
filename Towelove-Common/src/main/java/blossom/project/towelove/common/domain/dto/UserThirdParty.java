package blossom.project.towelove.common.domain.dto;

/**
 * @Author SIK
 * @Date 2023 12 05 11 20
 **/

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("user_third_party")
public class UserThirdParty implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("user_id")
    private Long userId;

    @TableField("third_party_id")
    private String thirdPartyId;

    @TableField("provider")
    private String provider;
}


