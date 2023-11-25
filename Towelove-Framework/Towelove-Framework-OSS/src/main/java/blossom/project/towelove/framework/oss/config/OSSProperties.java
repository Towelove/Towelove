package blossom.project.towelove.framework.oss.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@AutoConfiguration
@ConfigurationProperties(prefix = "oss")
public class OSSProperties {
    /**
     * oss服务类型
     * 0：minio  MinIO
     * 1：aliyun 阿里云
     * 2：tecent 腾讯云
     */
    private Integer type;
    /**
     * 路径前缀
     */

    private String filePathPrefix;
    /**
     * linux下文件存储位置
     */
    private String linuxFilePath = "/opt/towelove/data";
    /**
     * windows下文件存储位置
     */
    private String windowsFilePath = "D:\\towelove\\data";

}
