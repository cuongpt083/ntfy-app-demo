/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (7.7.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package com.demo.ntfyappapi.api;

import com.demo.ntfyappapi.controller.BooksApiDelegate;
import com.demo.ntfyappapi.dao.entity.BookEntity;
import com.demo.ntfyappapi.dto.request.BookRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

import jakarta.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-03-21T11:07:15.924603100+07:00[Asia/Bangkok]", comments = "Generator version: 7.7.0")
@Validated
@Tag(name = "books", description = "the books API")
public interface BooksApi {

    default BooksApiDelegate getDelegate() {
        return new BooksApiDelegate() {};
    }

    /**
     * POST /books/{id}/approve : Approve a book for publishing
     *
     * @param id  (required)
     * @return Book approved and published (status code 200)
     *         or Book not found (status code 404)
     *         or Book already published (status code 409)
     */
    @Operation(
        operationId = "approveBook",
        summary = "Approve a book for publishing",
        responses = {
            @ApiResponse(responseCode = "200", description = "Book approved and published", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = BookEntity.class))
            }),
            @ApiResponse(responseCode = "404", description = "Book not found"),
            @ApiResponse(responseCode = "409", description = "Book already published")
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/books/{id}/approve",
        produces = { "application/json" }
    )
    
    default ResponseEntity<BookEntity> approveBook(
        @Parameter(name = "id", description = "", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id
    ) {
        return getDelegate().approveBook(id);
    }


    /**
     * POST /books : Create a new book
     *
     * @param bookRequest  (required)
     * @return Book created successfully (status code 201)
     *         or Invalid input (status code 400)
     */
    @Operation(
        operationId = "createBook",
        summary = "Create a new book",
        responses = {
            @ApiResponse(responseCode = "201", description = "Book created successfully", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = BookEntity.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid input")
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/books",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    
    default ResponseEntity<BookEntity> createBook(
        @Parameter(name = "BookRequest", description = "", required = true) @Valid @RequestBody BookRequest bookRequest
    ) {
        return getDelegate().createBook(bookRequest);
    }


    /**
     * DELETE /books/{id} : Delete a book
     *
     * @param id  (required)
     * @return Book deleted (status code 204)
     *         or Book not found (status code 404)
     */
    @Operation(
        operationId = "deleteBook",
        summary = "Delete a book",
        responses = {
            @ApiResponse(responseCode = "204", description = "Book deleted"),
            @ApiResponse(responseCode = "404", description = "Book not found")
        }
    )
    @RequestMapping(
        method = RequestMethod.DELETE,
        value = "/books/{id}"
    )
    
    default ResponseEntity<Void> deleteBook(
        @Parameter(name = "id", description = "", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id
    ) {
        return getDelegate().deleteBook(id);
    }


    /**
     * GET /books : Get all books
     *
     * @param isPublished Filter books by published status (optional)
     * @return List of books (status code 200)
     */
    @Operation(
        operationId = "getAllBooks",
        summary = "Get all books",
        responses = {
            @ApiResponse(responseCode = "200", description = "List of books", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = BookEntity.class)))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/books",
        produces = { "application/json" }
    )
    
    default ResponseEntity<List<BookEntity>> getAllBooks(
        @Parameter(name = "isPublished", description = "Filter books by published status", in = ParameterIn.QUERY) @Valid @RequestParam(value = "isPublished", required = false) Boolean isPublished
    ) {
        return getDelegate().getAllBooks(isPublished);
    }


    /**
     * GET /books/{id} : Get a book by ID
     *
     * @param id  (required)
     * @return Book found (status code 200)
     *         or Book not found (status code 404)
     */
    @Operation(
        operationId = "getBookById",
        summary = "Get a book by ID",
        responses = {
            @ApiResponse(responseCode = "200", description = "Book found", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = BookEntity.class))
            }),
            @ApiResponse(responseCode = "404", description = "Book not found")
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/books/{id}",
        produces = { "application/json" }
    )
    
    default ResponseEntity<BookEntity> getBookById(
        @Parameter(name = "id", description = "", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id
    ) {
        return getDelegate().getBookById(id);
    }


    /**
     * POST /books/{id}/request-approval : Request approval for a book
     *
     * @param id  (required)
     * @return Approval requested (status code 200)
     *         or Book not found (status code 404)
     *         or Book already published (status code 409)
     */
    @Operation(
        operationId = "requestApproval",
        summary = "Request approval for a book",
        responses = {
            @ApiResponse(responseCode = "200", description = "Approval requested", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = BookEntity.class))
            }),
            @ApiResponse(responseCode = "404", description = "Book not found"),
            @ApiResponse(responseCode = "409", description = "Book already published")
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/books/{id}/request-approval",
        produces = { "application/json" }
    )
    
    default ResponseEntity<BookEntity> requestApproval(
        @Parameter(name = "id", description = "", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id
    ) {
        return getDelegate().requestApproval(id);
    }


    /**
     * PUT /books/{id} : Update a book
     *
     * @param id  (required)
     * @param bookRequest  (required)
     * @return Book updated (status code 200)
     *         or Invalid input (status code 400)
     *         or Book not found (status code 404)
     */
    @Operation(
        operationId = "updateBook",
        summary = "Update a book",
        responses = {
            @ApiResponse(responseCode = "200", description = "Book updated", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = BookEntity.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Book not found")
        }
    )
    @RequestMapping(
        method = RequestMethod.PUT,
        value = "/books/{id}",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    
    default ResponseEntity<BookEntity> updateBook(
        @Parameter(name = "id", description = "", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id,
        @Parameter(name = "BookRequest", description = "", required = true) @Valid @RequestBody BookRequest bookRequest
    ) {
        return getDelegate().updateBook(id, bookRequest);
    }

}
