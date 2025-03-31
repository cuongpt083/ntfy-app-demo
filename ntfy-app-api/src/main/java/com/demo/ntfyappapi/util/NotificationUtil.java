package com.demo.ntfyappapi.util;

import com.demo.ntfyappapi.dto.request.NotificationRequest;
import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.print.attribute.standard.Media;

public final class NotificationUtil {

    @Value(value = "${app.notification.maker-topic}")
    private String makerTopicName;

    @Value(value = "${app.notification.checker-topic}")
    private String checkerTopicName;

    private NotificationUtil(){
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static <T,R> Mono<R> doPost(String baseUrl, String endpoint, T requestBody, Class<R> responseType){
        WebClient webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, String.valueOf(MediaType.APPLICATION_JSON))
                .build();
        return webClient.post()
                .uri(endpoint)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(responseType)
                .onErrorResume(
                        err -> Mono.error(new RuntimeException("POST request failed", err))
                );
    }

    public static <T,R> Mono<R> doPostWithHeaders(String baseUrl, String endpoint, T requestBody, Class<R> responseType,
                                                  HttpHeaders customHeaders){
        WebClient webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeaders(headers -> headers.addAll(customHeaders))
                .build();

        return webClient.post()
                .uri(endpoint)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(responseType)
                .onErrorResume(
                        err -> Mono.error(new RuntimeException("POST request failed", err))
                );
    }
}
