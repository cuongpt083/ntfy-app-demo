package com.demo.ntfyappapi.dto;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.format.annotation.DateTimeFormat;


import jakarta.annotation.Generated;

/**
 * BookDTO
 */
@lombok.Builder
@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-03-21T17:06:44.498786400+07:00[Asia/Bangkok]", comments = "Generator version: 7.7.0")
public class BookDTO {

  private String id;

  private String title;

  private String description;

  private String isbn;

  private BookStatus status;

  private String statusDescription = "";

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private ZonedDateTime createdAt;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private ZonedDateTime updatedAt;

}

