package blossom.project.towelove.common.response.msg;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MsgTaskResponse {
    private Date createTime;
}

