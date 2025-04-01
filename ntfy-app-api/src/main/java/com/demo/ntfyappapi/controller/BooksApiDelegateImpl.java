package com.demo.ntfyappapi.controller;

import com.demo.ntfyappapi.dao.repository.BookRepository;
import com.demo.ntfyappapi.dto.BookDTO;
import com.demo.ntfyappapi.dto.BookStatus;
import com.demo.ntfyappapi.dto.request.BooksIdApprovePatchRequest;
import com.demo.ntfyappapi.dto.request.BooksIdRejectPatchRequest;
import com.demo.ntfyappapi.dto.request.BooksIdRequestApprovalPatchRequest;
import com.demo.ntfyappapi.exception.BookNotFoundException;
import com.demo.ntfyappapi.exception.GeneralException;
import com.demo.ntfyappapi.service.BookService;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class BooksApiDelegateImpl implements BooksApiDelegate {
    private final BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    private Logger log;

    /*@Autowired
    private Validator validator;*/

    @Override
    public Mono<ResponseEntity<BookDTO>> booksPost(BookDTO bookDTO) {
        // Skip ID check if ID is null (new book with auto-generated ID)
        if(bookDTO.getId() == null) {
            return bookService.createBook(bookDTO)
                    .map(savedBook -> ResponseEntity.status(HttpStatus.CREATED).body(savedBook))
                    .onErrorResume(err -> {
                        log.error(err.getCause());
                        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
                    });
        }
        /* Check if book with the same ID already exists */
        return bookService.getBookById(bookDTO.getId())
                .flatMap(existingBook ->
                        // If exists, return Error
                        Mono.just(ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(existingBook)))
                .switchIfEmpty(
                        // If doesn't exist, create new book
                        bookService.createBook(bookDTO)
                        .map(savedBook -> ResponseEntity.status(HttpStatus.CREATED).body(savedBook))
                )
                .onErrorResume(err -> {
                    log.error("Error creating book",err);
                    //err.printStackTrace();
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
                });
    }




    /**
     * Fetch books match status
     * */
    /*public ResponseEntity<List<BookDTO>> booksGet(@Valid BookStatus status){
        List<BookDTO> bookDTOList = bookService.getAllBooksByStatus(status).collectList().block(Duration.ofMinutes(1));
        return ResponseEntity.ok(bookDTOList);
    }*/
    @Override
    public Mono<ResponseEntity<Page<BookDTO>>> booksStatusGet(int page, int size, BookStatus status){
        Pageable pageable = PageRequest.of(page,size);
        Mono<Long> count = bookRepository.count();
        if(status == null) {
            return bookService.getAllBooks(pageable)
                    .map(bookPage -> {
                        // If page is empty and not first page, return 404
                        if(bookPage.isEmpty() && bookPage.getNumber() > 0){
                            return ResponseEntity.notFound().build();
                        }
                        // Return successful response with page of BookDTOs
                        return ResponseEntity.ok(bookPage);
                    });
        }else {
            return bookService.getAllBooksByStatus(pageable,status)
                    .map(bookPage -> {
                        // If page is empty and not first page, return 404
                        if(bookPage.isEmpty() && bookPage.getNumber() > 0){
                            return ResponseEntity.notFound().build();
                        }
                        // Return successful response with page of BookDTOs
                        return ResponseEntity.ok(bookPage);
                    });
        }

    }


    // Demo if you want to maintain ResponseEntity inside Flux
    /*public Flux<ResponseEntity<BookDTO>> booksGet(@Valid BookStatus status){
        return bookService.getAllBooksByStatus(status)
                .map(book -> ResponseEntity.ok(book))
                .onErrorResume(err -> {
                    // log.error("Error message", err);
                    return Flux.empty() ;
                });
    }*/
    /**
     * Approve book
     * */
    @Override
    public Mono<ResponseEntity<BookDTO>> booksIdApprovePatch(String id, BooksIdApprovePatchRequest request){
        //BookDTO approvedBook = bookService.approveBook(id,request.getStatusDescription()).block();
        return bookService.approveBook(id,request.getStatusDescription())
                .map(ResponseEntity::ok)
                .onErrorResume(err -> {
                    log.error("Error message ", err.getCause());
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
                });
    }

    /**
     * Request Approval book
     * */
    @Override
    public Mono<ResponseEntity<BookDTO>> booksIdRequestApprovalPatch(String id, BooksIdRequestApprovalPatchRequest request){
        //BookDTO requestedApprovalBook = bookService.requestApproval(id,request.getStatusDescription()).block(Duration.ofSeconds(30));
        return bookService.requestApproval(id, request.getStatusDescription())
                .map(ResponseEntity::ok)
                .onErrorResume(err -> {
                    log.error("Error message ", err.getCause());
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
                });
        //return ResponseEntity.ok(requestedApprovalBook);
    }

    @Override
    public Mono<ResponseEntity<BookDTO>> booksIdRejectPatch(String id, BooksIdRejectPatchRequest request) {
        //BookDTO rejectedBook = bookService.rejectBook(id,request.getStatusDescription()).block(Duration.ofSeconds(30));
        return bookService.rejectBook(id,request.getStatusDescription())
                .map(ResponseEntity::ok)
                .onErrorResume(err -> {
                    log.error("Error message ", err.getCause());
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
                });
        //return ResponseEntity.ok(rejectedBook);
    }

    @Override
    public Mono<ResponseEntity<BookDTO>> booksIdPut(String id, BookDTO book) {
        //BookDTO updatedBook = bookService.updateBook(id, book).block(Duration.ofSeconds(30));
        //return ResponseEntity.ok(updatedBook);
        return bookService.updateBook(id, book)
                .map(ResponseEntity::ok)
                .onErrorResume(err -> {
                    log.error("Error message ", err.getCause());
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
                });
    }

    @Override
    public Mono<ResponseEntity<BookDTO>> booksIdGet(String id){
        //BookDTO book = bookService.getBookById(id).block(Duration.ofSeconds(30));
        //return ResponseEntity.ok(book);
        return bookService.getBookById(id)
                .map(ResponseEntity::ok)
                .onErrorResume(err -> {
                    log.error("Error message ", err.getCause());
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
                });
    }

    @Override
    public Mono<ResponseEntity<Map<String,String>>> booksIdDelete(String id) {
        //bookService.deleteBook(id).;
        return bookService.deleteBook(id)
                .then(
                        Mono.just(ResponseEntity.ok(Map.of("Congratulation", "Book with id " + id + " deleted successfully")))
                )
                .onErrorResume(err -> {
                    log.error("Error message", err);
                    if(err instanceof BookNotFoundException) {
                        return Mono.just(ResponseEntity.status(
                                HttpStatus.NOT_FOUND)
                                .body(Map.of("Sorry", err.getMessage())));
                    } else if( err instanceof GeneralException) {
                        return Mono.just(ResponseEntity.status(
                                HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(Map.of("Sorry", err.getMessage())));
                    } else {
                        return Mono.just(ResponseEntity.status(
                                        HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(Map.of("Sorry", err.getMessage())));
                    }

                });

    }
 }
