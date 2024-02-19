package blossom.project.towelove.common.request.loves.album;

import java.time.LocalDateTime;
import java.util.Date;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlbumsCreateRequest  {


    //情侣组id
    @NotNull(message = "coupleId can not be null!!!")
    private Long coupleId;


    //相册创建时间
    @NotNull(message = "create time can not be null!!!")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    //相册标题
    @NotNull(message = "title can not be null!!!")
    private String title;

    //相册封面url
    private String coverUrl;

    //照片集合，使用英文逗号分隔
    private String photoUrls;

}


