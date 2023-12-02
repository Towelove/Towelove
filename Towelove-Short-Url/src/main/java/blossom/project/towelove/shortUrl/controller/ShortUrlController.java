package blossom.project.towelove.shortUrl.controller;

import blossom.project.towelove.common.request.surl.CreateShortUrlRequest;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.framework.log.annotation.LoveLog;
import blossom.project.towelove.shortUrl.service.ShortUrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.shortUrl.controller
 * @className: 短链控制层
 * @author: Link Ji
 * @description: TODO
 * @date: 2023/12/2 15:36
 * @version: 1.0
 */
@Controller
@RequestMapping("/v1/surl")
@RequiredArgsConstructor
@LoveLog()
public class ShortUrlController {

    private final ShortUrlService shortUrlService;

    /**
     * 创建短链
     * @param request
     * @return
     */
    @PostMapping("")
    @ResponseBody
    public Result<String> create(@Validated @RequestBody CreateShortUrlRequest request){
        return shortUrlService.createShortUrl(request);
    }

    /**
     * 短链解析重定向
     * @param url
     * @return
     */
    @GetMapping("/{url}")
    public String mapping(@NotNull(message = "url could not be null") @PathVariable("url") String url){
        String sourceUrl =  shortUrlService.mappingToSourceUrl(url);
        return "redirect:" + sourceUrl;
    }




}