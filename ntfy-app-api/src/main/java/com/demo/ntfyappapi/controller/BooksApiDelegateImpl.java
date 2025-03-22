package com.demo.ntfyappapi.controller;

import com.demo.ntfyappapi.dao.entity.BookEntity;
import com.demo.ntfyappapi.dao.repository.BookRepository;
import com.demo.ntfyappapi.dto.BookDTO;
import com.demo.ntfyappapi.dto.BookStatus;
import com.demo.ntfyappapi.dto.request.BooksIdApprovePatchRequest;
import com.demo.ntfyappapi.dto.request.BooksIdRejectPatchRequest;
import com.demo.ntfyappapi.dto.request.BooksIdRequestApprovalPatchRequest;
import com.demo.ntfyappapi.mapper.BookMapper;
import com.demo.ntfyappapi.service.BookService;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BooksApiDelegateImpl implements BooksApiDelegate {
    @Autowired
    private BookService bookService;

    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private BookRepository bookRepository;

    /*@Autowired
    private Validator validator;*/


    public Mono<ResponseEntity<BookDTO>> booksPost(BookDTO bookDTO) {
        return bookService.getBookById(bookDTO.getId())
                .flatMap(existingBook -> Mono.just(ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(existingBook)))
                .switchIfEmpty(bookService.createBook(bookDTO)
                        .map(savedBook -> ResponseEntity.status(HttpStatus.CREATED).body(savedBook))
                )
                .onErrorResume(err -> {
                    //log.error("Error creating book",err);
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
                });
    }

    /**
     * Fetch all books
     * */
    public Flux<ResponseEntity<BookDTO>> booksGetAll(int page, int size, String sort){
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                sort != null ? createSort(sort) : Sort.by(Sort.Direction.ASC, "title")
        );

        Page<BookEntity> bookEntityPage = bookRepository.findAll(pageRequest);
        List<BookDTO> bookDTOList = bookEntityPage.getContent().stream()
                .map(Mapper)
                .collect(Collectors.toList());
        return bookService.getAllBooks()
                .map(ResponseEntity::ok)
                .onErrorResume(err -> {
                    // log.error("Error message", err);
                    return Flux.empty() ;
                });
    }

    /**
     * Fetch books match status
     * */
    /*public ResponseEntity<List<BookDTO>> booksGet(@Valid BookStatus status){
        List<BookDTO> bookDTOList = bookService.getAllBooksByStatus(status).collectList().block(Duration.ofMinutes(1));
        return ResponseEntity.ok(bookDTOList);
    }*/
    public Flux<ResponseEntity<BookDTO>> booksGetByStatus(@Valid BookStatus status){
        return bookService.getAllBooksByStatus(status)
                .map(ResponseEntity::ok)
                .onErrorResume(err -> {
                    // log.error("Error message", err);
                    return Flux.empty() ;
                });
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
    public Mono<ResponseEntity<BookDTO>> booksIdApprovePatch(String id, BooksIdApprovePatchRequest request){
        //BookDTO approvedBook = bookService.approveBook(id,request.getStatusDescription()).block();
        return bookService.approveBook(id,request.getStatusDescription())
                .map(ResponseEntity::ok)
                .onErrorResume(err -> {
                    // log.error("Error message", err);
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
                });
    }

    /**
     * Request Approval book
     * */
    public Mono<ResponseEntity<BookDTO>> booksIdRequestApprovalPatch(String id, BooksIdRequestApprovalPatchRequest request){
        //BookDTO requestedApprovalBook = bookService.requestApproval(id,request.getStatusDescription()).block(Duration.ofSeconds(30));
        return bookService.requestApproval(id, request.getStatusDescription())
                .map(ResponseEntity::ok)
                .onErrorResume(err -> {
                    // log.error("Error message", err);
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
                });
        //return ResponseEntity.ok(requestedApprovalBook);
    }


    public Mono<ResponseEntity<BookDTO>> booksIdRejectPatch(String id, BooksIdRejectPatchRequest request) {
        //BookDTO rejectedBook = bookService.rejectBook(id,request.getStatusDescription()).block(Duration.ofSeconds(30));
        return bookService.rejectBook(id,request.getStatusDescription())
                .map(ResponseEntity::ok)
                .onErrorResume(err -> {
                    // log.error("Error message", err);
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
                });
        //return ResponseEntity.ok(rejectedBook);
    }

    public Mono<ResponseEntity<BookDTO>> booksIdPut(String id, BookDTO book) {
        //BookDTO updatedBook = bookService.updateBook(id, book).block(Duration.ofSeconds(30));
        //return ResponseEntity.ok(updatedBook);
        return bookService.updateBook(id, book)
                .map(ResponseEntity::ok)
                .onErrorResume(err -> {
                    // log.error("Error message", err);
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
                });
    }

    public Mono<ResponseEntity<BookDTO>> booksIdGet(String id){
        //BookDTO book = bookService.getBookById(id).block(Duration.ofSeconds(30));
        //return ResponseEntity.ok(book);
        return bookService.getBookById(id)
                .map(ResponseEntity::ok)
                .onErrorResume(err -> {
                    // log.error("Error message", err);
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
                });
    }

    public Mono<ResponseEntity<Void>> booksIdDelete(String id) {
        //bookService.deleteBook(id).;
        return bookService.deleteBook(id)
                .map(ResponseEntity::ok)
                .onErrorResume(err -> {
                    // log.error("Error message", err);
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
                });

    }
 }
