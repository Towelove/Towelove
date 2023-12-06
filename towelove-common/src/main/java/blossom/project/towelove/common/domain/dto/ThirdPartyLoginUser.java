package blossom.project.towelove.common.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Author 苏佳
 * @Date 2023 11 08 12 16
 **/
@Data
public class ThirdPartyLoginUser {

    private int code;

    private String msg;

    private String type;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("social_uid")
    private String socialUid;

    @JsonProperty("faceimg")
    private String avatar;
    @JsonProperty("nickname")
    private String nickName;
    @JsonProperty("gender")
    private String sex;

    private String location;

    private String ip;
}
