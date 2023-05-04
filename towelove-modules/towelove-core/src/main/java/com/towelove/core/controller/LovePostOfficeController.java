package com.towelove.core.controller;

import com.towelove.common.core.domain.PageResult;
import com.towelove.common.core.domain.R;
import com.towelove.core.domain.lovepostoffice.LovePostOffice;
import com.towelove.core.service.LovePostOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author: 张锦标
 * @date: 2023/5/4 14:57
 * LovePostOfficeController类
 * 爱情邮局也就是项目中的信箱了
 *
 */
@RestController
@RequestMapping("/core/love/post/office")
public class LovePostOfficeController {
    @Autowired
    private LovePostOfficeService officeService;

    @Autowired
    @Qualifier("logThreadPool")
    private ThreadPoolExecutor threadPoolExecutor;

    private ArrayList<Long> readList  = new ArrayList<>(1);
    @PostConstruct
    public void updateReadStyle(){
        threadPoolExecutor.execute(()->{
            while(true){
                if (readList.size()>=3){
                    officeService.readBatch(readList);
                }
                try {
                    Thread.sleep(1000 * 20);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    @GetMapping("/page")
    public R page(@RequestParam("pageNo")Integer pageNo,
                  @RequestParam("pageSize")Integer pageSize,
                  @RequestParam("userId")Long userId){
        PageResult<LovePostOffice> pageResult =
                officeService.selectPage(pageNo,pageSize,userId);
        return R.ok(pageResult,"查询数据成功");
    }
    /**
     * 需要考虑到一个地方在于，如果每一次看了文章就直接发一个请求
     * 效率特别低，因此我考虑的是写一个缓冲区
     */
    @PutMapping("/read/{id}")
    public R readMail(@PathVariable("id")Long id){
        readList.add(id);
        return R.ok();
    }

}
