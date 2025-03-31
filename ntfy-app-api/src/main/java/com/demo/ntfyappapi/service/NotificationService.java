package com.demo.ntfyappapi.service;

import com.demo.ntfyappapi.dto.NotificationDTO;
import com.demo.ntfyappapi.dto.request.NotificationRequest;
import com.demo.ntfyappapi.exception.GeneralException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@lombok.RequiredArgsConstructor
@Service
public class NotificationService {
    private final WebClient webClient;
    @Value(value = "${app.approval.notification.baseUrl}")
    private String baseUrl;

    @Value(value = "${app.notification.maker-topic}")
    private String MAKER_TOPIC;

    @Value(value = "${app.notification.checker-topic}")
    private String CHECKER_TOPIC;

    public Mono<Void> sendToChecker(NotificationRequest request){
        return webClient.post()
                .uri(baseUrl + "/" + CHECKER_TOPIC)
                .bodyValue(request.getMessage())
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> Mono.error(new GeneralException("Runtime exception when posting notification.")))
                .bodyToMono(Void.class);
    }

    public Mono<Void> sendToMaker(NotificationRequest request){
        return webClient.post()
                .uri(baseUrl + "/" + MAKER_TOPIC)
                .bodyValue(request.getMessage())
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> Mono.error(new GeneralException("Runtime exception when posting notification.")))
                .bodyToMono(Void.class);
    }
}
