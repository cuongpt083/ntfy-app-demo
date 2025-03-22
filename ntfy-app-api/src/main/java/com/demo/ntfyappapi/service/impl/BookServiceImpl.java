package com.demo.ntfyappapi.service.impl;

import com.demo.ntfyappapi.dao.entity.BookEntity;
import com.demo.ntfyappapi.dao.repository.BookRepository;
import com.demo.ntfyappapi.dto.BookStatus;
import com.demo.ntfyappapi.dto.BookDTO;
import com.demo.ntfyappapi.exception.BookNotFoundException;
import com.demo.ntfyappapi.mapper.BookMapper;
import com.demo.ntfyappapi.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@lombok.RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    /**
     * @param bookDTO
     * @return
     */
    @Override
    public Mono<BookDTO> createBook(BookDTO bookDTO) {
        BookEntity bookEntity = bookMapper.dtoToEntity(bookDTO);
        bookEntity.setStatus(BookStatus.DRAFT);
        bookEntity.setCreatedAt(ZonedDateTime.now());

        return Mono.fromCallable(() -> bookRepository.save(bookEntity))
            .map(bookMapper::entityToDto)
                .onErrorResume(ex -> Mono.error(new RuntimeException("Failed to create book")));
    }

    @Override
    public Mono<BookDTO> getBookById(String id) {
        return Mono.fromCallable(() ->
                bookRepository.findById(Long.valueOf(id))
                        .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id))
        ).map(bookMapper::entityToDto);
    }

    /**
     * @param isbn
     * @return
     */
    @Override
    public Flux<BookDTO> getAllBooksByIsbn(String isbn) {
        return Flux.fromIterable(bookRepository.findAllByIsbn(isbn))
                .map(bookMapper::entityToDto);
    }

    public Flux<BookDTO> getAllBooks() {
        return Flux.fromIterable(bookRepository.findAll())
                .map(bookMapper::entityToDto);
    }

    /*@Override
    public Flux<BookDTO> getAllBooks(int page, int size, String sort) {
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                sort != null ? createSort(soft) : Sort.by(Sort.Direction.ASC, "title")
        );
        Page<BookEntity> bookEntityPage = bookRepository.findAll(pageRequest);
        List<BookDTO> bookDTOList
        return Flux.fromIterable(bookEntityPage.getContent().stream())
                .map(bookMapper::entityToDto);
    }*/

    /**
     * @param status
     * @return
     */
    @Override
    public Flux<BookDTO> getAllBooksByStatus(BookStatus status) {
        return Flux.fromIterable(bookRepository.findAllByStatus(status))
                .map(bookMapper::entityToDto);
    }

    @Override
    public Mono<BookDTO> updateBook(String id, BookDTO bookDTO) {
        return Mono.fromCallable(() -> {
            BookEntity existingBook = bookRepository.findById(Long.valueOf(id))
                    .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));

            // Update fields
            existingBook.setTitle(bookDTO.getTitle());
            existingBook.setDescription(bookDTO.getDescription());
            existingBook.setIsbn(bookDTO.getIsbn());
            existingBook.setUpdatedAt(ZonedDateTime.now());

            return bookRepository.save(existingBook);
        }).map(bookMapper::entityToDto);
    }

    @Override
    public Mono<Void> deleteBook(String id) {
        return Mono.fromRunnable(() -> {
            BookEntity book = bookRepository.findById(Long.valueOf(id))
                    .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));
            bookRepository.deleteById(Long.valueOf(id));
        });
    }

    /**
     * @param id
     * @param bookDTO
     * @return
     */
    @Override
    public Mono<BookDTO> requestApproval(String id, BookDTO bookDTO) {
        return Mono.fromCallable(() -> {
            BookEntity book = bookRepository.findById(Long.valueOf(id)).orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));
            book.setStatus(BookStatus.PENDING_APPROVAL);
            book.setStatusDescription(String.valueOf(bookDTO.getStatusDescription()));
            book.setUpdatedAt(ZonedDateTime.now());
            return bookRepository.save(book);
        }).map(bookMapper::entityToDto);
    }

    /**
     * @param id
     * @param bookDTO
     * @return
     */
    @Override
    public Mono<BookDTO> approveBook(String id, BookDTO bookDTO) {
        return Mono.fromCallable(() -> {
            BookEntity book = bookRepository.findById(Long.valueOf(id))
                    .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));
            book.setStatus(BookStatus.APPROVED);
            book.setStatusDescription(String.valueOf(bookDTO.getStatusDescription()));
            book.setUpdatedAt(ZonedDateTime.now());
            return bookRepository.save(book);
        }).map(bookMapper::entityToDto);
    }

    /**
     * @param id
     * @param bookDTO
     * @return
     */
    @Override
    public Mono<BookDTO> rejectBook(String id, BookDTO bookDTO) {
        return null;
    }

    @Override
    public Mono<BookDTO> requestApproval(String id, String statusDescription) {
        return Mono.fromCallable(() -> {
            BookEntity book = bookRepository.findById(Long.valueOf(id))
                    .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));

            book.setStatus(BookStatus.PENDING_APPROVAL);
            book.setStatusDescription(statusDescription);
            book.setUpdatedAt(ZonedDateTime.now());

            return bookRepository.save(book);
        }).map(bookMapper::entityToDto);
    }

    @Override
    public Mono<BookDTO> approveBook(String id, String statusDescription) {
        return Mono.fromCallable(() -> {
            BookEntity book = bookRepository.findById(Long.valueOf(id))
                    .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));

            book.setStatus(BookStatus.APPROVED);
            book.setStatusDescription(book.getDescription() + " -> " + statusDescription);
            book.setUpdatedAt(ZonedDateTime.now());

            return bookRepository.save(book);
        }).map(bookMapper::entityToDto);
    }

    @Override
    public Mono<BookDTO> rejectBook(String id, String statusDescription) {
        return Mono.fromCallable(() -> {
            BookEntity book = bookRepository.findById(Long.valueOf(id))
                    .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));

            book.setStatus(BookStatus.REJECTED);
            book.setStatusDescription(book.getStatusDescription() + " -> " +statusDescription);
            book.setUpdatedAt(ZonedDateTime.now());

            return bookRepository.save(book);
        }).map(bookMapper::entityToDto);
    }

}
