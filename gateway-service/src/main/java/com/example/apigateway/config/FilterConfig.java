//package com.example.apigateway.config;
//
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
////@Configuration
//public class FilterConfig {
//
////    @Bean
//    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route(r -> r.path("/api/user/**")
//                        .filters(f -> f.addRequestHeader("first-request", "first-request-header")
//                                        .addResponseHeader("first-response", "first-response-header"))
//                        .uri("http://localhost:8080"))
//                .route(r -> r.path("/api/newsfeed/**")
//                        .filters(f -> f.addRequestHeader("first-request", "first-request-header")
//                                .addResponseHeader("first-response", "first-response-header"))
//                        .uri("http://localhost:8081"))
//                .route(r -> r.path("/api/activity/**")
//                        .filters(f -> f.addRequestHeader("first-request", "first-request-header")
//                                .addResponseHeader("first-response", "first-response-header"))
//                        .uri("http://localhost:8082"))
//                .build();
//
////        return builder.routes()
////                .route(r -> r.path("/api/auth/**")
////                        .uri("http://localhost:8080"))
////                .route(r -> r.path("/api/user/**")
////                        .uri("http://localhost:8080"))
////                .route(r -> r.path("/api/follow/**")
////                        .uri("http://localhost:8080"))
////                .route(r -> r.path("/api/mail/**")
////                        .uri("http://localhost:8080"))
////
////                .route(r -> r.path("/api/notification/**")
////                        .uri("http://localhost:8081"))
////
////                .route(r -> r.path("/api/activity/**")
////                        .uri("http://localhost:8082"))
////                .route(r -> r.path("/api/comment/**")
////                        .uri("http://localhost:8082"))
////                .route(r -> r.path("/api/like/**")
////                        .uri("http://localhost:8082"))
////                .route(r -> r.path("/api/post/**")
////                        .uri("http://localhost:8082"))
////                .build();
//    }
//}
