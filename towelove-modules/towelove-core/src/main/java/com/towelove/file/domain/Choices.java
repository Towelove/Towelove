package com.towelove.file.domain;

import lombok.Data;

@Data
public class Choices {
    private Messages message;
    private String finish_reason;
    private Integer index;
}
