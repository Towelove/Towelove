package com.towelove.file.domain.wx;

import lombok.Data;

@Data
public class ChatGptResponse {
    private String id;
    private String object;
    private Integer created;
    private String model;

    private Usage usage;

    private Choices[] choices;
}
