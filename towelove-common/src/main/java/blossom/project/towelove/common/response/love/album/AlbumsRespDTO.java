package blossom.project.towelove.common.response.love.album;
import java.time.LocalDateTime;
import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlbumsRespDTO  {

    private Long id;

    private Long coupleId;

    private String title;

    private String coverUrl;

    private String photoUrls;

    private LocalDateTime createTime;

}


