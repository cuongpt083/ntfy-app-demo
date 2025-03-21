package com.demo.ntfyappapi.controller;

import com.demo.ntfyappapi.api.BooksApi;
import com.demo.ntfyappapi.api.BooksApiDelegate;
import com.demo.ntfyappapi.dto.BookDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class BookController implements BooksApi {
    @Autowired
    private BooksApiDelegate delegate;

    @PostMapping(value = "/api/books")
    public Mono<ResponseEntity<BookDTO>> createBook(BookDTO bookDTO){
        return delegate.booksPost(bookDTO);
    }

    /*@GetMapping(value = "/api/books/{status}")
    public Flux<ResponseEntity<BookDTO>> getAllBooks() {
        return delegate.
    }*/
}
