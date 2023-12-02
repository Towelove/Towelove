package blossom.project.towelove.server.service;

import blossom.project.towelove.common.request.surl.CreateShortUrlRequest;
import blossom.project.towelove.common.response.Result;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.shortUrl.service
 * @className: ShortUrlService
 * @author: Link Ji
 * @description: TODO
 * @date: 2023/12/2 15:43
 * @version: 1.0
 */
public interface ShortUrlService {
    Result<String> createShortUrl(CreateShortUrlRequest request);

    String mappingToSourceUrl(String url);
}
