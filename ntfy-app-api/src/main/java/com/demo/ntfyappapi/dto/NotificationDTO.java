package com.demo.ntfyappapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    String id;
    long time;
    long expires;
    String event;
    String topic;
    String message;
}
