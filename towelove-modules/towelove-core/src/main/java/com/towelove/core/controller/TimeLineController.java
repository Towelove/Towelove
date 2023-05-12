package com.towelove.core.controller;

import com.towelove.common.core.domain.R;
import com.towelove.common.core.utils.JwtUtils;
import com.towelove.core.domain.timeline.TimeLine;
import com.towelove.core.mapper.TimeLineMapper;
import com.towelove.core.service.LoveAlbumService;
import com.towelove.core.service.TimeLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    private LoveAlbumService loveAlbumService;
    @GetMapping("/list")
    public R list(HttpServletRequest request){
        Long loveAlbumId = getLoveAlbumIdByHeader(request);
        List<TimeLine> list = timeLineService.listByLoveAlbumId(loveAlbumId);
        return R.ok(list);
    }
    @PostMapping("/add")
    public R addTimeLine(@RequestBody TimeLine timeLine,
                         HttpServletRequest request){
        Long loveAlbumId = getLoveAlbumIdByHeader(request);
        timeLine.setLoveAlbumId(loveAlbumId);
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

    private Long getLoveAlbumIdByHeader(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String userId = JwtUtils.getUserId(token);
        Long loveAlbumId = loveAlbumService.selectLoveAlbumIdByUserId(userId);
        return loveAlbumId;
    }

}
