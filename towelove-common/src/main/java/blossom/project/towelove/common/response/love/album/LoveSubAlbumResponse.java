package blossom.project.towelove.common.response.love.album;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoveSubAlbumResponse {
    private Date createTime;
}

