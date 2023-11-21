package blossom.project.towelove.msg.req;

import java.time.LocalTime;
import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MsgTaskCreateRequest  {
   private Long id;
}

