package blossom.project.towelove.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: ZhangBlossom
 * @date: 2024/1/14 16:52
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * @description:
 */
@RestController
@RequestMapping("/test")
public class TestController {


    @GetMapping("")
    public String test(){
        return "hello ,this is springboot3";
    }

}
