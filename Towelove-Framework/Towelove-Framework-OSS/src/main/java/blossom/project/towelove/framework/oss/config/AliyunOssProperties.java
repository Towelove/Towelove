package blossom.project.towelove.framework.oss.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.Data;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@AutoConfiguration
@ConfigurationProperties(prefix = "oss.aliyun")
public class AliyunOssProperties {
    private String accessKey;
    private String accessSecret;
    private String bucketName;

    private String endpoint = "https://oss-cn-beijing.aliyuncs.com";
    @Bean
    public OSS ossClient(){
        return new OSSClientBuilder().build(endpoint,accessKey, accessSecret);
    }
}
