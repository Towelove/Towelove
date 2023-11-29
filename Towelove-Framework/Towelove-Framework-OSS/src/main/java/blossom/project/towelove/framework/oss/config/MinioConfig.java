package blossom.project.towelove.framework.oss.config;

import io.minio.MinioAsyncClient;
import io.minio.MinioClient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
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
    public MinioClient minioClient(@Autowired MinioAsyncClient client){
        System.out.println(client);
        return MinioClient.builder()
                        .endpoint(url)
                        .credentials(accessKey, secretKey)
                        .build();
    }
    /**
     * 服务地址
     */
    private String url = "http://8.130.13.254:9000";

    /**
     * 用户名
     */
    private String accessKey = "towelove";

    /**
     * 密码
     */
    private String secretKey = "towelove";

    /**
     * 存储图片桶名称
     */
    private String bucketImages = "towelove-images";
    /**
     * 存储文件txt桶名称
     */
    private String bucketFiles = "towelove-files";
    /**
     * 存储video影片桶名称
     */
    private String bucketVideos = "towelove-videos";

}
