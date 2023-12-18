package blossom.project.towelove.common.enums;

import com.alibaba.fastjson2.annotation.JSONType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.ToString;

/**
 * oss类型
 * @author wangLele
 * @Mail 1819220754@qq.com
 * @date 10:54 2023/12/18
 */

@Getter
@ToString
public enum OssType {

    /**
     * 0: minio
     */
    MINIO(0),
    /**
     * 1: aliyun 阿里云
     */
    ALIYUN(1),
    /**
     * 2: tencent 腾讯云
     */
    TENCENT(2),
    /**
     * 3: qiniu 七牛云
     */
    QINIU(3);

    private final Integer value;

    OssType(Integer value) {
        this.value = value;
    }

}
