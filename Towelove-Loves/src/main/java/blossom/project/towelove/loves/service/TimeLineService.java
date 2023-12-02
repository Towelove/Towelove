package blossom.project.towelove.loves.service;

import blossom.project.towelove.loves.entity.TimeLine;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * TimeLine 事件服务接口
 */
public interface TimeLineService extends IService<TimeLine> {

    TimeLine createTimeLine(List<MultipartFile> images, TimeLine timeLine);

    TimeLine getTimeLineById(Long timeLineId);

    List<TimeLine> getAllTimeLines();

    TimeLine updateTimeLine(Long timeLineId, TimeLine timeLine);

    boolean deleteTimeLine(Long timeLineId);
}
