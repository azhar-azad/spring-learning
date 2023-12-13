package com.azad.springbootwebflux;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;

@Configuration
public class WebSiteRouter {

    @Bean
    public RouterFunction<ServerResponse> route(WebSiteHandler webSiteHandler) {
        RouterFunction<ServerResponse> webSiteRoute = RouterFunctions
                .route(RequestPredicates.GET("/websites")
                                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                        webSiteHandler::getAllWebSites)
                .andRoute(RequestPredicates.GET("/websites/{id}")
                                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                        webSiteHandler::getWebSite)
                .andRoute(RequestPredicates.POST("/websites")
                                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)
                                        .and(RequestPredicates.contentType(MediaType.APPLICATION_JSON))),
                        webSiteHandler::addWebSite)
                .andRoute(RequestPredicates.PUT("/websites")
                                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)
                                        .and(RequestPredicates.contentType(MediaType.APPLICATION_JSON))),
                        webSiteHandler::updateWebSite)
                .andRoute(RequestPredicates.DELETE("/websites/{id}"),
                        webSiteHandler::deleteWebSite);

        return webSiteRoute;
    }
}
