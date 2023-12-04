package blossom.project.towelove.msg;

import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.HashUtil;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.shortUrl
 * @className: HashTest
 * @author: Link Ji
 * @description:
 * @date: 2023/12/2 15:55
 * @version: 1.0
 */
public class HashTest {
    @Test
    void hash(){
        String key = "https://wenku.baidu.com/view/ccf149235223482fb4daa58da0116c175f0e1ef9.html?_wkts_=1701499963315";
        int hash = Math.abs(HashUtil.murmur32(key.getBytes()));
        System.out.println(hash);
        int index = hash % 10;
        System.out.println(index);
        String url = Integer.toString(hash, 32);
        System.out.println(url);
        String convertHash = new BigInteger(url,32).toString(10);
        System.out.println(convertHash);
        System.out.println(Integer.parseInt(convertHash) % 10);

    }
    private String convert2Base62(long hash){
        StringBuilder sb = new StringBuilder();
        while (hash > 0){
            sb.append(hash % 62);
            hash /= 62;
        }
        return sb.toString();
    }

    @Test
    void netTest(){
        byte[] localHardwareAddress = NetUtil.getLocalHardwareAddress();

    }
}
