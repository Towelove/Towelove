package blossom.project.towelove.framework.dtf.config;

import blossom.project.towelove.common.utils.StringUtils;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.yaml.snakeyaml.Yaml;

import java.util.Map;

/**
 * @author: 张锦标
 * @date: 2023/6/15 11:25
 * ThreadPoolProperty类
 */
@Slf4j
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
    private Integer virtualCorePoolSize = 5;
    private Integer virtualMaximumPoolSize = 10;
    private Integer virtualQueueCapacity = 100;

    public void updateConfig(String configInfo) {
        log.info("收到配置信息：{}", configInfo);
        if (StringUtils.isEmpty(configInfo)) {
            return;
        }
        Yaml yaml = new Yaml();
        Map<String, Object> load = yaml.load(configInfo);
        Map<String, Object> dynamic = JSONObject.parseObject(JSONObject.toJSONString(load.get("dynamic")), new TypeReference<>() {
        });
        ThreadPoolProperty threadpool = JSONObject.parseObject(JSONObject.toJSONString(dynamic.get("threadpool")), new TypeReference<>() {
        });
        this.ioCorePoolSize = threadpool.getIoCorePoolSize();
        this.ioMaximumPoolSize = threadpool.getIoMaximumPoolSize();
        this.ioQueueCapacity = threadpool.getIoQueueCapacity();
        this.cpuCorePoolSize = threadpool.getCpuCorePoolSize();
        this.cpuMaximumPoolSize = threadpool.getCpuMaximumPoolSize();
        this.cpuQueueCapacity = threadpool.getCpuQueueCapacity();
        this.virtualCorePoolSize = threadpool.getVirtualCorePoolSize();
        this.virtualMaximumPoolSize = threadpool.getVirtualMaximumPoolSize();
        this.virtualQueueCapacity = threadpool.getVirtualQueueCapacity();
        log.info("更新配置信息：{}", this);
    }

}


