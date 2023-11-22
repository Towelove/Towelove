package blossom.project.towelove.common.request.msg;

import java.time.LocalTime;
import java.util.Date;

import java.io.Serializable;

import blossom.project.towelove.common.page.PageRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MsgTaskPageRequest extends PageRequest {

    //查询的msgtask对应的id
    @NotNull
    private Long userId;


}   

