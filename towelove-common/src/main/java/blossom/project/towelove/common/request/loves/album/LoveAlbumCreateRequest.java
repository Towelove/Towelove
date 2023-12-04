package blossom.project.towelove.common.request.loves.album;

import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoveAlbumCreateRequest  {

   @NotNull(message = "loves_id can not be null!")
   private Long lovesId;

   //相册标题
   @NotBlank(message = "title can not be null!")
   private String title;

   //相册封面
   private String albumCoverUrl;

   //外人是否可见 0:不可见 1：可见
   private Integer canSee = 0;
}

