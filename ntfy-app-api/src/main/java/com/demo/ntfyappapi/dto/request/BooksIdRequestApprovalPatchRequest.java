package com.demo.ntfyappapi.dto.request;

import com.fasterxml.jackson.annotation.JsonTypeName;


import jakarta.annotation.Generated;

/**
 * BooksIdRequestApprovalPatchRequest
 */
@lombok.Builder
@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor

@JsonTypeName("_books__id__request_approval_patch_request")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-03-21T17:06:44.498786400+07:00[Asia/Bangkok]", comments = "Generator version: 7.7.0")
public class BooksIdRequestApprovalPatchRequest {

  private String statusDescription;

}

