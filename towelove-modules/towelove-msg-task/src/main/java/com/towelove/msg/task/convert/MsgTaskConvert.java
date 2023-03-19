package com.towelove.msg.task.convert;

import cn.hutool.core.util.StrUtil;
import com.towelove.common.core.domain.PageResult;
import com.towelove.msg.task.domain.MsgTask;
import com.towelove.msg.task.domain.vo.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author: 张锦标
 * @date: 2023/3/11 17:41
 * MsgTaskConvert类
 * 将MsgTask与VO和DTO对象之间进行转换
 * 关于lombok和mapstruct的版本兼容问题多说几句，
 * maven插件要使用3.6.0版本以上、
 * lombok使用1.16.16版本以上，
 * 另外编译的lombok mapstruct的插件不要忘了加上。
 * 否则会出现下面的错误：No
 * property named "aaa" exists in source parameter(s).
 * Did you mean "null"?
 *
 * 这种异常就是lombok编译异常导致缺少get setter方法造成的
 * 还有就是缺少构造函数也会抛异常。
 */
@Mapper
//暂时不可以使用 有问题
public interface MsgTaskConvert {
    MsgTaskConvert INSTANCE = Mappers.getMapper(MsgTaskConvert.class);

    MsgTask convert(MsgTaskCreateReqVO bean);

    MsgTask convert(MsgTaskUpdateReqVO bean);

    MsgTaskRespVO convert(MsgTask bean);

    PageResult<MsgTaskBaseVO> convertPage(PageResult<MsgTask> pageResult);

    List<MsgTaskSimpleRespVO> convertList02(List<MsgTask> list);

    MsgTaskSimpleRespVO map(MsgTask value);

}
