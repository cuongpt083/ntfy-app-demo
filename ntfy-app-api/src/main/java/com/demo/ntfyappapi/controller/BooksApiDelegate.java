package com.demo.ntfyappapi.controller;

import com.demo.ntfyappapi.api.ApiUtil;
import com.demo.ntfyappapi.api.BooksApi;
import com.demo.ntfyappapi.dao.entity.BookEntity;
import com.demo.ntfyappapi.dto.request.BookRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.List;
import java.util.Optional;
import jakarta.annotation.Generated;

/**
 * A delegate to be called by the {@link BooksApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-03-21T11:07:15.924603100+07:00[Asia/Bangkok]", comments = "Generator version: 7.7.0")
public interface BooksApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /books/{id}/approve : Approve a book for publishing
     *
     * @param id  (required)
     * @return Book approved and published (status code 200)
     *         or Book not found (status code 404)
     *         or Book already published (status code 409)
     * @see BooksApi#approveBook
     */
    default ResponseEntity<BookEntity> approveBook(Long id) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"isPublished\" : false, \"description\" : \"A comprehensive guide to Java programming\", \"id\" : 1, \"title\" : \"Java Programming\", \"updatedAt\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * POST /books : Create a new book
     *
     * @param bookRequest  (required)
     * @return Book created successfully (status code 201)
     *         or Invalid input (status code 400)
     * @see BooksApi#createBook
     */
    default ResponseEntity<BookEntity> createBook(BookRequest bookRequest) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"isPublished\" : false, \"description\" : \"A comprehensive guide to Java programming\", \"id\" : 1, \"title\" : \"Java Programming\", \"updatedAt\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * DELETE /books/{id} : Delete a book
     *
     * @param id  (required)
     * @return Book deleted (status code 204)
     *         or Book not found (status code 404)
     * @see BooksApi#deleteBook
     */
    default ResponseEntity<Void> deleteBook(Long id) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /books : Get all books
     *
     * @param isPublished Filter books by published status (optional)
     * @return List of books (status code 200)
     * @see BooksApi#getAllBooks
     */
    default ResponseEntity<List<BookEntity>> getAllBooks(Boolean isPublished) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"isPublished\" : false, \"description\" : \"A comprehensive guide to Java programming\", \"id\" : 1, \"title\" : \"Java Programming\", \"updatedAt\" : \"2000-01-23T04:56:07.000+00:00\" }, { \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"isPublished\" : false, \"description\" : \"A comprehensive guide to Java programming\", \"id\" : 1, \"title\" : \"Java Programming\", \"updatedAt\" : \"2000-01-23T04:56:07.000+00:00\" } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /books/{id} : Get a book by ID
     *
     * @param id  (required)
     * @return Book found (status code 200)
     *         or Book not found (status code 404)
     * @see BooksApi#getBookById
     */
    default ResponseEntity<BookEntity> getBookById(Long id) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"isPublished\" : false, \"description\" : \"A comprehensive guide to Java programming\", \"id\" : 1, \"title\" : \"Java Programming\", \"updatedAt\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * POST /books/{id}/request-approval : Request approval for a book
     *
     * @param id  (required)
     * @return Approval requested (status code 200)
     *         or Book not found (status code 404)
     *         or Book already published (status code 409)
     * @see BooksApi#requestApproval
     */
    default ResponseEntity<BookEntity> requestApproval(Long id) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"isPublished\" : false, \"description\" : \"A comprehensive guide to Java programming\", \"id\" : 1, \"title\" : \"Java Programming\", \"updatedAt\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * PUT /books/{id} : Update a book
     *
     * @param id  (required)
     * @param bookRequest  (required)
     * @return Book updated (status code 200)
     *         or Invalid input (status code 400)
     *         or Book not found (status code 404)
     * @see BooksApi#updateBook
     */
    default ResponseEntity<BookEntity> updateBook(Long id,
        BookRequest bookRequest) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"isPublished\" : false, \"description\" : \"A comprehensive guide to Java programming\", \"id\" : 1, \"title\" : \"Java Programming\", \"updatedAt\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
