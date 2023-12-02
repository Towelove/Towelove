package blossom.project.towelove.msg.service.impl;

import blossom.project.towelove.common.exception.ServiceException;
import blossom.project.towelove.common.request.surl.CreateShortUrlRequest;
import blossom.project.towelove.common.response.Result;
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

    private static BitMapBloomFilter bitMap = BloomFilterUtil.createBitMap(500);


    //    private final Redisson redisson;
    @Override
    public Result<String> createShortUrl(CreateShortUrlRequest request) {
        String sourceUrl = request.getSourceUrl();
        //生成短链,获得短链对应分片hashKey Index,短链结果
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
        //TODO：使用lua脚本重构
        while (redisService.hasHashValue(urlMappingKey,shortUrl)){
            shortUrl = generateUrl(sourceUrl + System.currentTimeMillis()).getShortUrl();
        }
        redisService.setCacheMapValue(urlMappingKey, shortUrl,sourceUrl);
        if (request.getStatistics()){
            //TODO:短链统计业务逻辑
        }
        return Result.ok(ShortUrlCacheConstants.getUrl(shortUrl));
    }

    private ShortResponse generateUrl(String sourceUrl){
        //使用murmurHash算法
        int hash = HashUtil.murmur32(sourceUrl.getBytes());
        //转36进制
        String sourUrl = Integer.toString(hash, 36);
        if (StrUtil.isBlank(sourUrl)){
            throw new ServiceException("短链生成失败");
        }
        return new ShortResponse(hash % 10,sourUrl);
    }

    @Override
    public String mappingToSourceUrl(String url) {
        //转换回hash值找到分片索引
        String hash = new BigInteger(url, 36).toString(10);
        int kIndex = Integer.parseInt(hash) % 10;
        Object cacheMapValue = redisService.getCacheMapValue(getUrlMappingKey(kIndex), url);
        if (Objects.isNull(cacheMapValue)){
            throw new ServiceException("短链无效");
        }
        return String.valueOf(cacheMapValue);
    }

    private String getUrlMappingKey(int kIndex){
//        return String.format(ShortUrlCacheConstants.URL_MAPPING_FROM,from);
        return String.format(ShortUrlCacheConstants.URL_MAPPING_FROM,kIndex);
    }
}
