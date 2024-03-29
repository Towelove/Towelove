package blossom.project.towelove.common.response.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.common.response.user
 * @className: LoginUserResponse
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/3/29 15:10
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginUserResponse {
    private Long id;


    private String userName;


    private String nickName;



    public String email;


    private String smtpCode;


    public String phoneNumber;


    private String sex;


    private String avatar;

    private String loginIp;

    private List<String> userPermission;
}
