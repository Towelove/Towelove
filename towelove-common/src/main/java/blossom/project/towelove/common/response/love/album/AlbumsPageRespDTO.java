package blossom.project.towelove.common.response.love.album;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlbumsPageRespDTO {

    private Long id;

    private Long coupleId;

    private String title;

    private String coverUrl;

    private LocalDateTime createTime;

}


