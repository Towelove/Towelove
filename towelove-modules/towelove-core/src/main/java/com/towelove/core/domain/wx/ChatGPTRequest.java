package com.towelove.core.domain.wx;

import lombok.Data;

@Data
public class ChatGPTRequest {
    private String model;
    private String prompt;
    private Integer max_tokens;
    private float temperature;
    private Integer top_p;
    private Integer n;
    private Boolean stream = false;
    private Boolean logprobs;
    private String stop;

    private Messages[] messages;
}
