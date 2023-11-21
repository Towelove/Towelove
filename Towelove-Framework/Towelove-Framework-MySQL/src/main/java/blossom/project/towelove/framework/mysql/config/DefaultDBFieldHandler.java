package blossom.project.towelove.framework.mysql.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author: 张锦标
 * @date: 2023/3/29 15:05
 * MetaObjectFieldHandler类
 */
@AutoConfiguration
public class DefaultDBFieldHandler implements MetaObjectHandler {
    //TODO 后续这一块的信息可以从MDC里面拿出来
    //mp执行添加操作，这个方法执行
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime",LocalDateTime.now(),metaObject);
        this.setFieldValByName("updateTime",LocalDateTime.now(),metaObject);
        this.setFieldValByName("createBy","Love",metaObject);
        this.setFieldValByName("updateBy","You",metaObject);
        this.setFieldValByName("status",0,metaObject);
        this.setFieldValByName("remark","",metaObject);
    }

    //mp执行修改操作，这个方法执行
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime",LocalDateTime.now(),metaObject);
        this.setFieldValByName("updateBy","Love You",metaObject);
    }
}