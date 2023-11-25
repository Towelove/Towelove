//package blossom.project.towelove.framework.oss.config;
//
//import com.qcloud.cos.COSClient;
//import com.qcloud.cos.ClientConfig;
//import com.qcloud.cos.auth.BasicCOSCredentials;
//import com.qcloud.cos.auth.COSCredentials;
//import com.qcloud.cos.http.HttpProtocol;
//import com.qcloud.cos.region.Region;
//import lombok.Data;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.AutoConfiguration;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Data
//@AutoConfiguration
//@ConfigurationProperties(prefix = "oss.tencent")
//public class TencentOssProperties {
//    private String secretId;
//    private String secretKey;
//
//    public String bucketName;
//
//    public String region = "ap-nanjing";
//
//    @Bean
//    public COSClient cosClient(){
//        COSCredentials cosCredentials = new BasicCOSCredentials(secretId,secretKey);
//        Region region = new Region(getRegion());
//        ClientConfig clientConfig = new ClientConfig(region);
//        clientConfig.setHttpProtocol(HttpProtocol.https);
//        return new COSClient(cosCredentials,clientConfig);
//    }
//
//}
