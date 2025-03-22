package com.demo.ntfyappapi.controller;

import com.demo.ntfyappapi.api.BooksApi;
import com.demo.ntfyappapi.dto.BookDTO;
import com.demo.ntfyappapi.dto.BookStatus;
import com.demo.ntfyappapi.dto.request.BooksIdApprovePatchRequest;
import com.demo.ntfyappapi.dto.request.BooksIdRejectPatchRequest;
import com.demo.ntfyappapi.dto.request.BooksIdRequestApprovalPatchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class BookController implements BooksApi {
    @Autowired
    private BooksApiDelegate delegate;

    @PostMapping(value = "/api/books")
    public Mono<ResponseEntity<BookDTO>> createBook(@RequestBody BookDTO bookDTO){
        return delegate.booksPost(bookDTO);
    }

    @GetMapping(value = "/api/books")
    public Flux<ResponseEntity<BookDTO>> getAllBooks(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "5") int size,
                                                     @RequestParam(required = false) String sort ){
        return delegate.booksGetAll(page, size, sort);
    }

    @GetMapping(value = "/api/books/{status}")
    public Flux<ResponseEntity<BookDTO>> getAllBooksByStatus(@PathVariable String status) {
        return delegate.booksGetByStatus(BookStatus.valueOf(status));
    }

    @PutMapping(value = "/api/books/{id}")
    public Mono<ResponseEntity<BookDTO>> updateBook(@PathVariable String id, @RequestBody BookDTO bookDTO){
        return delegate.booksIdPut(id, bookDTO);
    }

    @DeleteMapping(value = "/api/books/{id}")
    public Mono<ResponseEntity<Void>> deleteBook(@PathVariable String id) {
        return delegate.booksIdDelete(id);
    }

    @GetMapping(value = "/api/books/{id}")
    public Mono<ResponseEntity<BookDTO>> getBookById(@PathVariable String id) {
        return delegate.booksIdGet(id);
    }

    @PatchMapping(value = "/api/books/{id}/request-approval")
    public Mono<ResponseEntity<BookDTO>> requestApproval(@PathVariable String id, @RequestBody BooksIdRequestApprovalPatchRequest request) {
        return delegate.booksIdRequestApprovalPatch(id,request);
    }

    @PatchMapping(value = "/api/books/{id}/approve")
    public Mono<ResponseEntity<BookDTO>> approve(@PathVariable String id, @RequestBody BooksIdApprovePatchRequest request) {
        return delegate.booksIdApprovePatch(id, request);
    }

    @PatchMapping(value = "/api/books/{id}/reject")
    public Mono<ResponseEntity<BookDTO>> reject (@PathVariable String id, @RequestBody BooksIdRejectPatchRequest request) {
        return delegate.booksIdRejectPatch(id, request);
    }
}
