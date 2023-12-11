package blossom.project.towelove.gateway.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterFunctionConfig {

    @Bean
    public RouterFunction routerFunction1()
    {
        //传入的请求通过RouterFunction直接返回数据
        return RouterFunctions.route(
                RequestPredicates.path("/code1")
        ,request -> ServerResponse.ok().body(
                        BodyInserters.fromValue("hello")
                ));
    }
}
