package com.towelove.system;
 

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
 
@SpringBootTest
class SpringbootMapstructApplicationTests {
    @Autowired
    private MainMapper mainMapper;
 
    @Test
    void testSimpleMap() {
        StudentVo studentVo = StudentVo.builder()
                .school("清华大学")
                .userId("ams")
                .userName("AI码师")
                .age(27)
                .address("合肥")
                .build();
        StudentDto studentDto = mainMapper.studentVo2Dto(studentVo);
        System.out.println(studentDto);
    }
}
