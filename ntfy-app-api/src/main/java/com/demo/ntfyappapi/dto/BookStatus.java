package com.demo.ntfyappapi.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonValue;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Gets or Sets BookStatus
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-03-21T17:06:44.498786400+07:00[Asia/Bangkok]", comments = "Generator version: 7.7.0")
public enum BookStatus {
  
  DRAFT("DRAFT"),
  
  PENDING_APPROVAL("PENDING_APPROVAL"),
  
  APPROVED("APPROVED"),
  
  REJECTED("REJECTED");

  private String value;

  BookStatus(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static BookStatus fromValue(String value) {
    for (BookStatus b : BookStatus.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}

