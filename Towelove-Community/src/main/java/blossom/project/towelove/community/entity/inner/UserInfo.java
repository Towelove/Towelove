package blossom.project.towelove.community.entity.inner;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfo {

    //用户id
    private Long userId;

    //用户昵称
    private String nickname;

    //用户头像
    private String avatar;
}

