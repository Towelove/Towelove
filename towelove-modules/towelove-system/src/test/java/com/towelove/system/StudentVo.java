package com.towelove.system;
 
import lombok.Builder;
import lombok.Data;
 
/**
 * Created with IntelliJ IDEA.
 *
 * @author： AI码师
 * @date： 2021/11/27
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Data
@Builder
public class StudentVo {
    private String userName;
    private String userId;
    private String address;
    private String school;
    private int age;
    private String emailAddress;
}
 
