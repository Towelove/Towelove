package com.towelove.core;

import com.towelove.core.mapper.LovePostOfficeMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author: 张锦标
 * @date: 2023/5/4 17:56
 * BatchTest类
 */
@SpringBootTest(classes = ToweloveCoreApplication.class)
public class BatchTest {
    @Autowired
    LovePostOfficeMapper mapper;
    @Test
    public void test(){
        ArrayList<Long> list = new ArrayList<>();
        list.add(1L);
        list.add(2L);
        list.add(3L);
        mapper.readBatch(list);
    }
}
