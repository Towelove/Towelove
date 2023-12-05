package blossom.project.towelove.common.utils;


import blossom.project.towelove.common.config.thirdParty.ThirdPartyLoginConfig;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

public class ThirdPartyLoginUtil {

    public static URI initiateLogin(ThirdPartyLoginConfig config, RestTemplate restTemplate, String type) {
        String url = "https://uniqueker.top/connect.php";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("act", "login")
                .queryParam("appid", config.getAppId())
                .queryParam("appkey", config.getAppKey())
                .queryParam("type", type)
                .queryParam("redirect_uri", config.getRedirectUrl());

        String redirectUrlString = restTemplate.getForObject(builder.toUriString(), String.class);
        Map<String, Object> jsonStringMap = JsonUtils.stringToMap(redirectUrlString, String.class, Object.class);

        return URI.create(jsonStringMap.get("url").toString());
    }

    public static <T> T getSocialUserInfo(ThirdPartyLoginConfig config, RestTemplate restTemplate, String type, String code, Class<T> responseType) {
        String url = "https://uniqueker.top/connect.php";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("act", "callback")
                .queryParam("appid", config.getAppId())
                .queryParam("appkey", config.getAppKey())
                .queryParam("type", type)
                .queryParam("code", code);

        String response = restTemplate.getForObject(builder.toUriString(), String.class);
        return JsonUtils.jsonToPojo(response, responseType);
    }
}
