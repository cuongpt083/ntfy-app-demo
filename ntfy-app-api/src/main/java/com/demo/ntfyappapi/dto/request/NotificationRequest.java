package com.demo.ntfyappapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@lombok.RequiredArgsConstructor
@lombok.AllArgsConstructor
public class NotificationRequest {
    @NotBlank(message = "Topic name must not be null.")
    private String topic;

    @NotBlank
    private String[] rcvrRoles;

    @NotBlank
    @Size(max = 200)
    private String message;
}
