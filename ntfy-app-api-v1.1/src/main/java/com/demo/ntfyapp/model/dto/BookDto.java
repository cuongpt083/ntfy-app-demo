package com.demo.ntfyapp.model.dto;

import java.time.ZonedDateTime;
import java.time.ZonedDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import com.demo.ntfyapp.model.entity.Book.ApprovalStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class BookDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Request object for creating a new book")
    public static class CreateRequest {

        @NotBlank(message = "Title is required")
        @Size(max = 255, message = "Title must be less than 255 characters")
        @Schema(description = "Book title", example = "Spring Boot in Action", required = true)
        private String title;

        @NotBlank(message = "Author is required")
        @Size(max = 255, message = "Author must be less than 255 characters")
        @Schema(description = "Book author", example = "Craig Walls", required = true)
        private String author;

        @Size(max = 2000, message = "Description must be less than 2000 characters")
        @Schema(description = "Book description", example = "A comprehensive guide to Spring Boot")
        private String description;

        @NotBlank(message = "ISBN is required")
        @Pattern(regexp = "^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$",
                message = "Invalid ISBN format")
        @Schema(description = "Book ISBN", example = "978-1617292545", required = true)
        private String isbn;

        @Schema(description = "Year of publication", example = "2021")
        private Integer publishYear;

        @Schema(description = "Book publisher", example = "Manning Publications")
        private String publisher;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Request object for updating an existing book")
    public static class UpdateRequest {

        @Size(max = 255, message = "Title must be less than 255 characters")
        @Schema(description = "Book title", example = "Spring Boot in Action")
        private String title;

        @Size(max = 255, message = "Author must be less than 255 characters")
        @Schema(description = "Book author", example = "Craig Walls")
        private String author;

        @Size(max = 2000, message = "Description must be less than 2000 characters")
        @Schema(description = "Book description", example = "A comprehensive guide to Spring Boot")
        private String description;

        @Pattern(regexp = "^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$",
                message = "Invalid ISBN format")
        @Schema(description = "Book ISBN", example = "978-1617292545")
        private String isbn;

        @Schema(description = "Year of publication", example = "2021")
        private Integer publishYear;

        @Schema(description = "Book publisher", example = "Manning Publications")
        private String publisher;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Response object for book data")
    public static class Response {

        @Schema(description = "Book ID", example = "123e4567-e89b-12d3-a456-426614174000")
        private String id;

        @Schema(description = "Book title", example = "Spring Boot in Action")
        private String title;

        @Schema(description = "Book author", example = "Craig Walls")
        private String author;

        @Schema(description = "Book description", example = "A comprehensive guide to Spring Boot")
        private String description;

        @Schema(description = "Book ISBN", example = "978-1617292545")
        private String isbn;

        @Schema(description = "Year of publication", example = "2021")
        private Integer publishYear;

        @Schema(description = "Book publisher", example = "Manning Publications")
        private String publisher;

        @Schema(description = "Approval status", example = "APPROVED",
                allowableValues = {"DRAFT", "PENDING", "APPROVED", "REJECTED"})
        private ApprovalStatus approvalStatus;

        @Schema(description = "Creation timestamp")
        private ZonedDateTime createdAt;

        @Schema(description = "Last update timestamp")
        private ZonedDateTime updatedAt;

        @Schema(description = "When approval was requested")
        private ZonedDateTime approvalRequestedAt;

        @Schema(description = "When the book was approved")
        private ZonedDateTime approvedAt;

        @Schema(description = "Who approved the book", example = "john.doe@example.com")
        private String approvedBy;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Request object for approving a book")
    public static class ApprovalRequest {

        @jakarta.validation.constraints.NotBlank(message = "Approver identifier is required")
        @Schema(description = "Identifier of the person approving the book", example = "john.doe@example.com", required = true)
        private String approvedBy;

        @jakarta.validation.constraints.NotBlank(message = "Approval decision is required")
        @jakarta.validation.constraints.Pattern(regexp = "^(APPROVED|REJECTED)$", message = "Decision must be either APPROVED or REJECTED")
        @Schema(description = "Approval decision", example = "APPROVED", allowableValues = {"APPROVED", "REJECTED"}, required = true)
        private String decision;
    }
}
