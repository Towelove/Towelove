package com.towelove.core.domain.wx;

import lombok.Data;

@Data
public class WxToken {
    private String access_token;
    private Integer expires_in;
}
