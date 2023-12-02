package blossom.project.towelove.msg.cache;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.net.NetUtil;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.shortUrl.cache
 * @className: ShortUrlCacheConstants
 * @author: Link Ji
 * @description: TODO
 * @date: 2023/12/2 16:34
 * @version: 1.0
 */
public class ShortUrlCacheConstants {
    /**
     * redis 分片 HashKey，由hkey的hash值与HashKey数量取模得到
     */
    public final static String URL_MAPPING_FROM = "url_mapping:%s";
    public final static String URL_PREFIX = "http://%s:9301/%s";

    /**
     * 获得短链url
     * 如果是本地环境则生成 127.0.0.1:9999/v1/surl/xxxx
     * 如果是线上环境则生成 公网ip:9999/v1/surl/xxxx
     * @param shortUrl
     * @return
     */
    public static String getUrl(String shortUrl){
        return FileUtil.isWindows() ?
                String.format(URL_PREFIX,NetUtil.LOCAL_IP,shortUrl)
                : String.format(URL_PREFIX,NetUtil.getLocalhost().getHostAddress(),shortUrl);
    }
}
