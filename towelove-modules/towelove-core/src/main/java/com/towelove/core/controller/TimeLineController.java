package com.towelove.core.controller;

import com.towelove.common.core.domain.R;
import com.towelove.core.domain.timeline.TimeLine;
import com.towelove.core.mapper.TimeLineMapper;
import com.towelove.core.service.TimeLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: 张锦标
 * @date: 2023/5/11 20:41
 * TimeLineController类
 */
@RequestMapping("/core/timeline")
@RestController
public class TimeLineController {
    @Autowired
    private TimeLineService timeLineService;

    @GetMapping("/list")
    public R list(){
        List<TimeLine> list = timeLineService.list();
        return R.ok(list);
    }
    @PostMapping("/add")
    public R addTimeLine(TimeLine timeLine){
        boolean insert = timeLineService.insert(timeLine);
        if (insert){
            return R.ok("插入成功");
        }else {
            return R.fail("插入失败");
        }
    }
    @DeleteMapping("/delete/{id}")
    public R removeTimeLine(@PathVariable("id") Long id){
        boolean delete = timeLineService.delete(id);
        if (delete){
            return R.ok("插入成功");
        }else {
            return R.fail("插入失败");
        }
    }
}
