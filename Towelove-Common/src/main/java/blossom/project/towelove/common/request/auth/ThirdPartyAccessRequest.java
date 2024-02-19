package blossom.project.towelove.common.request.auth;

import lombok.Data;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.common.request.auth
 * @className: ThirdPartyAccessRequest
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/1/18 23:28
 * @version: 1.0
 */
@Data
public class ThirdPartyAccessRequest {

    private String thirdPartyCode;

    private String type;
}
