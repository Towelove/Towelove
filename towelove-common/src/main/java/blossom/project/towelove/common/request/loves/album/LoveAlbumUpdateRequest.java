package blossom.project.towelove.common.request.loves.album;

import java.time.LocalDateTime;
import java.util.Date;

import java.io.Serializable;
import java.util.TreeMap;

import com.baomidou.mybatisplus.annotation.TableField;
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
public class LoveAlbumUpdateRequest  {
   @NotNull(message = "id can not be null!!")
   private Long id;


   //相册标题
   private String title;

   //相册封面，如果为空，默认使用上传的第一张照片
   private String albumCoverUrl;

   //存储map，其中key为photo的index，value为url
   private TreeMap<Integer, String> photoDesc;

   private String photoUrls;

   //外人是否可见 0:不可见 1：可见
   private Integer canSee;

   //创建时间
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private LocalDateTime createTime;

}

