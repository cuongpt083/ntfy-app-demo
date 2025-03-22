package com.demo.ntfyappapi.controller;

import com.demo.ntfyappapi.api.ApiUtil;
import com.demo.ntfyappapi.api.BooksApi;
import com.demo.ntfyappapi.api.BooksApiController;
import com.demo.ntfyappapi.dto.BookDTO;
import com.demo.ntfyappapi.dto.BookStatus;
import com.demo.ntfyappapi.dto.request.BooksIdApprovePatchRequest;
import com.demo.ntfyappapi.dto.request.BooksIdRejectPatchRequest;
import com.demo.ntfyappapi.dto.request.BooksIdRequestApprovalPatchRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;
import jakarta.annotation.Generated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * A delegate to be called by the {@link BooksApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-03-21T17:06:44.498786400+07:00[Asia/Bangkok]", comments = "Generator version: 7.7.0")
public interface BooksApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * GET /books : Retrieve all books
     *
     * @param status Filter books by status (optional)
     * @return Successfully retrieved books (status code 200)
     * @see BooksApi#booksGet
     */
    default Flux<ResponseEntity<BookDTO>> booksGetByStatus(BookStatus status) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"statusDescription\" : \"statusDescription\", \"isbn\" : \"isbn\", \"description\" : \"description\", \"id\" : \"id\", \"title\" : \"title\", \"status\" : \"DRAFT\", \"updatedAt\" : \"2000-01-23T04:56:07.000+00:00\" }, { \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"statusDescription\" : \"statusDescription\", \"isbn\" : \"isbn\", \"description\" : \"description\", \"id\" : \"id\", \"title\" : \"title\", \"status\" : \"DRAFT\", \"updatedAt\" : \"2000-01-23T04:56:07.000+00:00\" } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return Flux.just(ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null));

    }

    default Flux<ResponseEntity<BookDTO>> booksGetAll(int page, int size, String sort) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"statusDescription\" : \"statusDescription\", \"isbn\" : \"isbn\", \"description\" : \"description\", \"id\" : \"id\", \"title\" : \"title\", \"status\" : \"DRAFT\", \"updatedAt\" : \"2000-01-23T04:56:07.000+00:00\" }, { \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"statusDescription\" : \"statusDescription\", \"isbn\" : \"isbn\", \"description\" : \"description\", \"id\" : \"id\", \"title\" : \"title\", \"status\" : \"DRAFT\", \"updatedAt\" : \"2000-01-23T04:56:07.000+00:00\" } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return Flux.just(ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null));

    }

    /**
     * PATCH /books/{id}/approve : Approve a book
     *
     * @param id  (required)
     * @param booksIdApprovePatchRequest  (optional)
     * @return Book approved successfully (status code 200)
     *         or Book not found (status code 404)
     * @see BooksApi#booksIdApprovePatch
     */
    default Mono<ResponseEntity<BookDTO>> booksIdApprovePatch(String id,
        BooksIdApprovePatchRequest booksIdApprovePatchRequest) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"statusDescription\" : \"statusDescription\", \"isbn\" : \"isbn\", \"description\" : \"description\", \"id\" : \"id\", \"title\" : \"title\", \"status\" : \"DRAFT\", \"updatedAt\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null));

    }

    /**
     * DELETE /books/{id} : Delete a book
     *
     * @param id  (required)
     * @return Book deleted successfully (status code 204)
     *         or Book not found (status code 404)
     * @see BooksApi#booksIdDelete
     */
    default Mono<ResponseEntity<Void>> booksIdDelete(String id) {
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null));

    }

    /**
     * GET /books/{id} : Retrieve a specific book by ID
     *
     * @param id  (required)
     * @return Successfully retrieved book (status code 200)
     *         or Book not found (status code 404)
     * @see BooksApi#booksIdGet
     */
    default Mono<ResponseEntity<BookDTO>> booksIdGet(String id) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"statusDescription\" : \"statusDescription\", \"isbn\" : \"isbn\", \"description\" : \"description\", \"id\" : \"id\", \"title\" : \"title\", \"status\" : \"DRAFT\", \"updatedAt\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null));

    }

    /**
     * PUT /books/{id} : Update an existing book
     *
     * @param id  (required)
     * @param bookDTO  (required)
     * @return Book updated successfully (status code 200)
     *         or Invalid input (status code 400)
     *         or Book not found (status code 404)
     * @see BooksApi#booksIdPut
     */
    /*default ResponseEntity<BookDTO> booksIdPut(String id,
        BookDTO bookDTO) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"statusDescription\" : \"statusDescription\", \"isbn\" : \"isbn\", \"description\" : \"description\", \"id\" : \"id\", \"title\" : \"title\", \"status\" : \"DRAFT\", \"updatedAt\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }*/
    default Mono<ResponseEntity<BookDTO>> booksIdPut(String id,
                                               BookDTO bookDTO) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"statusDescription\" : \"statusDescription\", \"isbn\" : \"isbn\", \"description\" : \"description\", \"id\" : \"id\", \"title\" : \"title\", \"status\" : \"DRAFT\", \"updatedAt\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null));

    }

    /**
     * PATCH /books/{id}/reject : Reject a book
     *
     * @param id  (required)
     * @param booksIdRejectPatchRequest  (optional)
     * @return Book rejected successfully (status code 200)
     *         or Book not found (status code 404)
     * @see BooksApi#booksIdRejectPatch
     */
    default Mono<ResponseEntity<BookDTO>> booksIdRejectPatch(String id,
        BooksIdRejectPatchRequest booksIdRejectPatchRequest) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"statusDescription\" : \"statusDescription\", \"isbn\" : \"isbn\", \"description\" : \"description\", \"id\" : \"id\", \"title\" : \"title\", \"status\" : \"DRAFT\", \"updatedAt\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null));

    }

    /**
     * PATCH /books/{id}/request-approval : Request approval for a book
     *
     * @param id  (required)
     * @param booksIdRequestApprovalPatchRequest  (optional)
     * @return Approval requested successfully (status code 200)
     *         or Book not found (status code 404)
     * @see BooksApi#booksIdRequestApprovalPatch
     */
    default Mono<ResponseEntity<BookDTO>> booksIdRequestApprovalPatch(String id,
        BooksIdRequestApprovalPatchRequest booksIdRequestApprovalPatchRequest) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"statusDescription\" : \"statusDescription\", \"isbn\" : \"isbn\", \"description\" : \"description\", \"id\" : \"id\", \"title\" : \"title\", \"status\" : \"DRAFT\", \"updatedAt\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null));

    }

    /**
     * POST /books : Create a new book
     *
     * @param bookDTO  (required)
     * @return Book created successfully (status code 201)
     *         or Invalid input (status code 400)
     * @see BooksApi#booksPost
     */
    default Mono<ResponseEntity<BookDTO>> booksPost(BookDTO bookDTO) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\", \"statusDescription\" : \"statusDescription\", \"isbn\" : \"isbn\", \"description\" : \"description\", \"id\" : \"id\", \"title\" : \"title\", \"status\" : \"DRAFT\", \"updatedAt\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null));
    }

}
