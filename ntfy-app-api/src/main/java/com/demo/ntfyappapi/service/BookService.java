package com.demo.ntfyappapi.service;

import com.demo.ntfyappapi.dto.BookStatus;
import com.demo.ntfyappapi.dto.BookDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookService {
    // Create
    Mono<BookDTO> createBook(BookDTO bookDTO);
    // Read

    Mono<BookDTO> getBookById(String id);

    Flux<BookDTO> getAllBooksByIsbn(String isbn);

    Flux<BookDTO> getAllBooks();
    Flux<BookDTO> getAllBooksByStatus(BookStatus status);

    // Update
    Mono<BookDTO> updateBook(String id, BookDTO bookDTO);
    // Delete
    Mono<Void> deleteBook(String id);
    // Approval workflow
    Mono<BookDTO> requestApproval(String id, BookDTO bookDTO);
    Mono<BookDTO> approveBook(String id, BookDTO bookDTO);
    Mono<BookDTO> rejectBook(String id, BookDTO bookDTO);


    Mono<BookDTO> requestApproval(String id, String statusDescription);

    Mono<BookDTO> approveBook(String id, String statusDescription);

    Mono<BookDTO> rejectBook(String id, String statusDescription);
}
