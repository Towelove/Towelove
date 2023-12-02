package blossom.project.towelove.shortUrl.convert;

import blossom.project.towelove.common.request.msg.MsgTaskCreateRequest;
import blossom.project.towelove.common.request.msg.MsgTaskUpdateRequest;
import blossom.project.towelove.common.response.msg.MsgTaskResponse;
import blossom.project.towelove.shortUrl.entity.MsgTask;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author: ZhangBlossom
 * @date: 2023/11/21 21:27
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * MsgTaskConvert接口
 */
@Mapper
public interface MsgTaskConvert {
    MsgTaskConvert INSTANCE = Mappers.getMapper(MsgTaskConvert.class);

    MsgTask convert(MsgTaskCreateRequest request);


    MsgTask convert(MsgTaskUpdateRequest request);

    MsgTaskResponse convert(MsgTask bean);


}
