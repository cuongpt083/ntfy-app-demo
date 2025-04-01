package com.demo.ntfyappapi.dao.repository;

import com.demo.ntfyappapi.dao.entity.BookEntity;
import com.demo.ntfyappapi.dto.BookStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

import java.awt.print.Book;

@Repository
public interface BookRepository extends ReactiveCrudRepository<BookEntity, Long> {
    Flux<BookEntity> findAllByStatus(Pageable pageable, BookStatus status);
    Flux<BookEntity> findAllBy(Pageable pageable);
    Flux<BookEntity> findAllByIsbn(String isbn);

    Mono<Void> deleteById(@NonNull Long id) ;

    Mono<Long> countAllByStatusIs(BookStatus status);
}
