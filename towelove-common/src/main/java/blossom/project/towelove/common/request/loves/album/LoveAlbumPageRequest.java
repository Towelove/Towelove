package blossom.project.towelove.common.request.loves.album;

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
public class LoveAlbumPageRequest extends PageRequest {

   @NotNull(message = "lovesId can not be null!")
   private Long lovesId;


}

