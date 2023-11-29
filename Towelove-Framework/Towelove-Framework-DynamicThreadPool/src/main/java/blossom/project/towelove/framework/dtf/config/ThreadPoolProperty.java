package blossom.project.towelove.framework.dtf.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author: 张锦标
 * @date: 2023/6/15 11:25
 * ThreadPoolProperty类
 */
@Data
@RefreshScope
@AutoConfiguration
@ConfigurationProperties("dynamic.threadpool")
public class ThreadPoolProperty {
    private Integer ioCorePoolSize = 3;
    private Integer ioMaximumPoolSize = 10;
    private Integer ioQueueCapacity = 100;
    private Integer cpuCorePoolSize = 4;
    private Integer cpuMaximumPoolSize = 8;
    private Integer cpuQueueCapacity = 200;
}


