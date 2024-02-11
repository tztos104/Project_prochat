package prochat.yj_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class FilterConfig {

   // @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder){
        return builder.routes()
                .route(r -> r.path("/user-service/**")
                        .filters(f-> f.addRequestHeader("user-request","user-request-header")
                                .addResponseHeader("user-response","user-response-header"))
                        .uri("http://localhost:8081"))
//                        .uri("lb://USER-SERVICE")

                .route(r -> r.path("/activity-service/**")
                        .filters(f-> f.addRequestHeader("activity-request","activity-request-header")
                                .addResponseHeader("activity-response","activity-response-header"))
                        .uri("http://localhost:8082"))
//                        .uri("lb://USER-SERVICE")
                .route(r -> r.path("/newsfeed-service/**")
                        .filters(f-> f.addRequestHeader("newsfeed-request","newsfeed-request-header")
                                .addResponseHeader("newsfeed-response","newsfeed-response-header"))
                        .uri("http://localhost:8081"))
//                        .uri("lb://USER-SERVICE")
                .build();

    }

}
