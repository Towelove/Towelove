package blossom.project.towelove.common.request.loves.album;

import java.time.LocalDateTime;
import java.util.Date;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlbumsUpdateRequest  {

    //相册id
    @NotNull(message = "id can not be null!!!")
    private Long id;


    @NotNull(message = "create time can not be null!!!")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @NotNull(message = "title can not be null!!!")
    private String title;

    private String photoUrls;

    private String coverUrl;

}


