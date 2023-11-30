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

   private Long id;

   @NotNull(message = "loves_id can not be null!")
   private Long lovesId;

   //相册标题
   @NotBlank(message = "title can not be null!")
   private String title;

   //外人是否可见 0:不可见 1：可见
   private Integer canSee = 0;

   /**
    * 0: 表示当前请求上传的是封面
    * 1: 表示当前请求上传的是子照片组
    */
   @NotNull
   private Integer status;
}

