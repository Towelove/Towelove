package blossom.project.towelove.framework.oss.config;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * Minio 配置信息
 *
 * @author 张锦标
 */
@Data
@AutoConfiguration
@ConfigurationProperties(prefix = "minio")
public class MinioConfig
{
    @Bean
    public MinioClient minioClient(){
        return MinioClient.builder()
                        .endpoint(url)
                        .credentials(accessKey, secretKey)
                        .build();
    }
    /**
     * 服务地址
     */
    private String url;

    /**
     * 用户名
     */
    private String accessKey;

    /**
     * 密码
     */
    private String secretKey;

    /**
     * 存储图片桶名称
     */
    private String bucketImages;
    /**
     * 存储文件txt桶名称
     */
    private String bucketFiles;
    /**
     * 存储video影片桶名称
     */
    private String bucketVideos;

}
