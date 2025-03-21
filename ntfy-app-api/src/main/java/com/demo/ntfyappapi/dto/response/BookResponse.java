package com.demo.ntfyappapi.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@lombok.Builder
@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class BookResponse {
    @Schema(description = "Book id")
    private long id;

}
