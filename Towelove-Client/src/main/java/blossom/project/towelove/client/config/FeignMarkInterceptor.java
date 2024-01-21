package blossom.project.towelove.client.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.client.config
 * @className: RequestInterceptor
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/1/19 22:58
 * @version: 1.0
 */
public class FeignMarkInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("invoke","inner");
    }
}
