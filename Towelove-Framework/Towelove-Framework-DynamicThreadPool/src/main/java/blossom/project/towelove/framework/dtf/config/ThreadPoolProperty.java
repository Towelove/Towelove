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
@RefreshScope
@AutoConfiguration
@ConfigurationProperties("dynamic.threadpool")
@Data
public class ThreadPoolProperty {
    private Integer ioCorePoolSize;
    private Integer ioMaximumPoolSize;
    private Integer ioQueueCapacity;
    private Integer cpuCorePoolSize;
    private Integer cpuMaximumPoolSize;
    private Integer cpuQueueCapacity;
}


