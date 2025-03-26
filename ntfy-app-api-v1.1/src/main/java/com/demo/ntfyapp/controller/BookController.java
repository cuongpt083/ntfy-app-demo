package com.demo.ntfyapp.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.ntfyapp.model.dto.BookDto;
import com.demo.ntfyapp.model.entity.Book.ApprovalStatus;
import com.demo.ntfyapp.service.BookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/books")
@Tag(name = "Book API", description = "API for managing books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Operation(summary = "Get all books", description = "Retrieves a list of all books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Books retrieved successfully",
                    content = @Content(schema = @Schema(implementation = BookDto.Response.class)))
    })
    @GetMapping
    public ResponseEntity<List<BookDto.Response>> getAllBooks(
            @Parameter(description = "Filter by approval status")
            @RequestParam(required = false) ApprovalStatus status) {

        List<BookDto.Response> books;
        if (status != null) {
            books = bookService.getBooksByStatus(status);
        } else {
            books = bookService.getAllBooks();
        }

        return ResponseEntity.ok(books);
    }

    @Operation(summary = "Get book by ID", description = "Retrieves a specific book by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book found",
                    content = @Content(schema = @Schema(implementation = BookDto.Response.class))),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<BookDto.Response> getBookById(
            @Parameter(description = "Book ID", required = true)
            @PathVariable String id) {

        BookDto.Response book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    @Operation(summary = "Create a new book", description = "Creates a new book with draft status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Book created successfully",
                    content = @Content(schema = @Schema(implementation = BookDto.Response.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input or book with same ISBN already exists",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<BookDto.Response> createBook(
            @Parameter(description = "Book creation details", required = true)
            @Valid @RequestBody BookDto.CreateRequest request) {

        BookDto.Response createdBook = bookService.createBook(request);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a book", description = "Updates an existing book in draft status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book updated successfully",
                    content = @Content(schema = @Schema(implementation = BookDto.Response.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input or book cannot be updated",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<BookDto.Response> updateBook(
            @Parameter(description = "Book ID", required = true)
            @PathVariable String id,
            @Parameter(description = "Book update details", required = true)
            @Valid @RequestBody BookDto.UpdateRequest request) {

        BookDto.Response updatedBook = bookService.updateBook(id, request);
        return ResponseEntity.ok(updatedBook);
    }

    @Operation(summary = "Delete a book", description = "Deletes a book in draft status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Book deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Book cannot be deleted",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(
            @Parameter(description = "Book ID", required = true)
            @PathVariable String id) {

        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Request approval for a book",
            description = "Changes book status from DRAFT to PENDING approval")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Approval requested successfully",
                    content = @Content(schema = @Schema(implementation = BookDto.Response.class))),
            @ApiResponse(responseCode = "400", description = "Book is not in appropriate state for approval request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content)
    })
    @PostMapping("/{id}/request-approval")
    public ResponseEntity<BookDto.Response> requestApproval(
            @Parameter(description = "Book ID", required = true)
            @PathVariable String id) {

        BookDto.Response book = bookService.requestApproval(id);
        return ResponseEntity.ok(book);
    }

    @Operation(summary = "Process book approval",
            description = "Approves or rejects a book that is pending approval")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Approval processed successfully",
                    content = @Content(schema = @Schema(implementation = BookDto.Response.class))),
            @ApiResponse(responseCode = "400", description = "Book is not in appropriate state for approval processing",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content)
    })
    @PostMapping("/{id}/process-approval")
    public ResponseEntity<BookDto.Response> processApproval(
            @Parameter(description = "Book ID", required = true)
            @PathVariable String id,
            @Parameter(description = "Approval details", required = true)
            @Valid @RequestBody BookDto.ApprovalRequest request) {

        BookDto.Response book = bookService.processApproval(id, request);
        return ResponseEntity.ok(book);
    }
}
