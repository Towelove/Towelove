package blossom.project.towelove.msg.service.impl;

import blossom.project.towelove.common.exception.ServiceException;
import blossom.project.towelove.common.request.surl.CreateShortUrlRequest;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.common.utils.StringUtils;
import blossom.project.towelove.framework.redis.service.RedisService;
import blossom.project.towelove.msg.cache.ShortUrlCacheConstants;
import blossom.project.towelove.msg.dto.ShortResponse;
import blossom.project.towelove.msg.service.ShortUrlService;
import cn.hutool.bloomfilter.BitMapBloomFilter;
import cn.hutool.bloomfilter.BloomFilterUtil;
import cn.hutool.core.util.HashUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Objects;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.shortUrl.service.impl
 * @className: ShortUrlServiceImpl
 * @author: Link Ji
 * @description: TODO
 * @date: 2023/12/2 15:43
 * @version: 1.0
 */
@Service
@RequiredArgsConstructor
public class ShortUrlServiceImpl implements ShortUrlService {
    private final RedisService redisService;

    private static final BitMapBloomFilter bitMap = BloomFilterUtil.createBitMap(500);
    private static final int HASH_MODULUS = 10;
    private static final int RADIX_36 = 36;


    @Override
    public Result<String> createShortUrl(CreateShortUrlRequest request) {
        String sourceUrl = request.getSourceUrl();
        ShortResponse shortResponse = generateUrl(sourceUrl);
        //建立映射关系（暂时缓存到redis中）
//        RBloomFilter<Object> shortUrlBloomFilter = redisson.getBloomFilter("short_url_bloomFilter");
//        while (shortUrlBloomFilter.contains(shortUrl)) {
//            //重新构建hash值
//            shortUrl = generateUrl(sourceUrl + DateTime.now());
//        }
//        shortUrlBloomFilter.add(shortUrl);
        int index = shortResponse.getIndex();
        String shortUrl = shortResponse.getShortUrl();
        String urlMappingKey = getUrlMappingKey(index);

        shortUrl = resolveUrlCollision(urlMappingKey, shortUrl, sourceUrl);

        if (request.getStatistics()) {
            // 短链统计业务逻辑（待实现）
        }

        return Result.ok(ShortUrlCacheConstants.getUrl(shortUrl));
    }


    /**
     * 解决URL冲突
     * @param urlMappingKey
     * @param shortUrl
     * @param sourceUrl
     * @return
     */
    private String resolveUrlCollision(String urlMappingKey, String shortUrl, String sourceUrl) {
        //TODO：使用lua脚本重构
        while (redisService.hasHashValue(urlMappingKey, shortUrl)) {
            shortUrl = generateUrl(sourceUrl + System.currentTimeMillis()).getShortUrl();
        }
        redisService.setCacheMapValue(urlMappingKey, shortUrl, sourceUrl);
        return shortUrl;
    }

    private ShortResponse generateUrl(String sourceUrl) {
        int hash = HashUtil.murmur32(sourceUrl.getBytes());
        String encodedUrl = Integer.toString(hash, RADIX_36);
        if (StringUtils.isBlank(encodedUrl)) {
            throw new ServiceException("短链生成失败");
        }
        return new ShortResponse(hash % HASH_MODULUS, encodedUrl);
    }

    @Override
    public String mappingToSourceUrl(String url) {
        String hash = new BigInteger(url, RADIX_36).toString(10);
        int kIndex = Integer.parseInt(hash) % HASH_MODULUS;
        Object cacheMapValue = redisService.getCacheMapValue(getUrlMappingKey(kIndex), url);
        if (Objects.isNull(cacheMapValue)) {
            throw new ServiceException("短链无效");
        }
        return String.valueOf(cacheMapValue);
    }

    /**
     * 获得URL映射字符串key
     * @param kIndex
     * @return
     */
    private String getUrlMappingKey(int kIndex){
//        return String.format(ShortUrlCacheConstants.URL_MAPPING_FROM,from);
        return String.format(ShortUrlCacheConstants.URL_MAPPING_FROM,kIndex);
    }
}
