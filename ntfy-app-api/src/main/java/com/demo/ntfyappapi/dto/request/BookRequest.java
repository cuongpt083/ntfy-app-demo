package com.demo.ntfyappapi.dto.request;

//import org.openapitools.jackson.nullable.JsonNullable;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.Size;

import javax.validation.constraints.NotNull;

/**
 * BookRequest
 */
@lombok.Builder
@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-03-21T11:07:15.924603100+07:00[Asia/Bangkok]", comments = "Generator version: 7.7.0")
public class BookRequest {

  @Size(max = 200)
  @Schema(description = "Book title", example = "Java Programming")
  @NotNull
  private String title;

  @Size(max = 500)
  @Schema(description = "Book description", example = "Book description detail")
  @NotNull
  private String description;

}

