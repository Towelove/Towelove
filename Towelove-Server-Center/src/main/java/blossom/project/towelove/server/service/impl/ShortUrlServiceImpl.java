package blossom.project.towelove.server.service.impl;

import blossom.project.towelove.common.exception.ServiceException;
import blossom.project.towelove.common.request.surl.CreateShortUrlRequest;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.common.utils.StringUtils;
import blossom.project.towelove.framework.redis.service.RedisService;
import blossom.project.towelove.server.cache.ShortUrlCacheConstants;
import blossom.project.towelove.server.dto.ShortResponse;
import blossom.project.towelove.server.service.ShortUrlService;
import cn.hutool.bloomfilter.BitMapBloomFilter;
import cn.hutool.bloomfilter.BloomFilterUtil;
import cn.hutool.core.util.HashUtil;
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
    private static final int RADIX_10 = 10;
    private static final int HASH_MODULUS = 10;
    private static final int RADIX_36 = 36;


    @Override
    public Result<String> createShortUrl(CreateShortUrlRequest request) {
        String sourceUrl = request.getSourceUrl();
        //建立映射关系（暂时缓存到redis中）
//        RBloomFilter<Object> shortUrlBloomFilter = redisson.getBloomFilter("short_url_bloomFilter");
//        while (shortUrlBloomFilter.contains(shortUrl)) {
//            //重新构建hash值
//            shortUrl = generateUrl(sourceUrl + DateTime.now());
//        }
//        shortUrlBloomFilter.add(shortUrl);
        ShortResponse shortResponse = resolveUrlCollision(generateUrl(sourceUrl), sourceUrl);
        String shortUrl = shortResponse.getShortUrl();
        if (request.getStatistics()) {
            // TODO:短链统计业务逻辑（待实现）
            //需要持久化到数据库存储

        }

        return Result.ok(ShortUrlCacheConstants.getUrl(shortUrl));
    }

    @Override
    public String mappingToSourceUrl(String url) {
        String hash = new BigInteger(url, RADIX_36).toString(RADIX_10);
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

    /**
     * 创建短链URL
     * @param sourceUrl
     * @return
     */
    private ShortResponse generateUrl(String sourceUrl) {
        int hash = Math.abs(HashUtil.murmur32(sourceUrl.getBytes()));
        String encodedUrl = Integer.toString(hash, RADIX_36);
        if (StringUtils.isBlank(encodedUrl)) {
            throw new ServiceException("短链生成失败");
        }
        return new ShortResponse(hash % HASH_MODULUS, encodedUrl);
    }


    /**
     * 解决URL冲突（index 与 新生成的url都需要修改）
     * @param
     * @param shortResponse,source
     * @param sourceUrl
     * @return
     */
    private ShortResponse resolveUrlCollision(ShortResponse shortResponse,String sourceUrl) {
        while (redisService.hasHashValue(getUrlMappingKey(shortResponse.getIndex()), shortResponse.getShortUrl())) {
            shortResponse = generateUrl(sourceUrl + System.currentTimeMillis());
        }
        String urlMappingKey = getUrlMappingKey(shortResponse.getIndex());
        String shortUrl = shortResponse.getShortUrl();
        redisService.setCacheMapValue(urlMappingKey, shortUrl, sourceUrl);
        return shortResponse;
    }
}
