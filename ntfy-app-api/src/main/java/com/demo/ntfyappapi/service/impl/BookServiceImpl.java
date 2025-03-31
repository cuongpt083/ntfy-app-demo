package com.demo.ntfyappapi.service.impl;

import com.demo.ntfyappapi.dao.entity.BookEntity;
import com.demo.ntfyappapi.dao.repository.BookRepository;
import com.demo.ntfyappapi.dto.BookStatus;
import com.demo.ntfyappapi.dto.BookDTO;
import com.demo.ntfyappapi.dto.request.NotificationRequest;
import com.demo.ntfyappapi.exception.BookNotFoundException;
import com.demo.ntfyappapi.exception.GeneralException;
import com.demo.ntfyappapi.mapper.BookMapper;
import com.demo.ntfyappapi.service.BookService;
import com.demo.ntfyappapi.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private static final Logger log = LogManager.getLogger(BookServiceImpl.class);

    @Value(value = "${app.notification.maker-topic}")
    private String MAKER_TOPIC;

    @Value(value = "${app.notification.checker-topic}")
    private String CHECKER_TOPIC;

    @Value(value = "${app.approval.notification.baseUrl}")
    private String NOTI_BASE_URL;

    @Autowired
    private NotificationService notificationService;


    /**
     * @param bookDTO
     * @return
     */
    @Override
    public Mono<BookDTO> createBook(BookDTO bookDTO) {
        BookEntity bookEntity = bookMapper.dtoToEntity(bookDTO);
        bookEntity.setStatus(BookStatus.DRAFT);
        bookEntity.setCreatedAt(ZonedDateTime.now());

        return bookRepository.save(bookEntity).
                map(bookMapper::entityToDto)
                .onErrorResume(err -> {
                    return Mono.error(new GeneralException("Cannot create a new book"));
                })
                ;

    }

    @Override
    public Mono<BookDTO> getBookById(String id) {
        return bookRepository.findById(Long.valueOf(id))
                .map(bookMapper::entityToDto)
                .switchIfEmpty(Mono.error(new BookNotFoundException("Book not found with id: " + id)));
    }

    /**
     * @param isbn
     * @return
     */
    @Override
    public Flux<BookDTO> getAllBooksByIsbn(String isbn) {
        return bookRepository.findAllByIsbn(isbn)
                .map(bookMapper::entityToDto)
                .switchIfEmpty(Flux.error(new BookNotFoundException("Book not found with isbn " + isbn)));
    }

    /**
     * @return
     */
    @Override
    public Flux<BookDTO> getAllBooks() {
        return bookRepository.findAll()
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
    public Flux<BookDTO> getAllBooksByStatus(int page, int size, BookStatus status) {
        if(status == null) {
            return bookRepository.findAll(PageRequest.of(page, size))
                    .map(bookMapper::entityToDto);
        }
        return bookRepository.findAllByStatus(PageRequest.of(page,size), status)
                .map(bookMapper::entityToDto);
    }

    @Override
    public Mono<BookDTO> updateBook(String id, BookDTO bookDTO) {
        return bookRepository.findById(Long.valueOf(id))
                .flatMap(existingBook -> {
                    if(BookStatus.APPROVED.equals(existingBook.getStatus()))
                        return Mono.error(new IllegalStateException("Cannot update an approved book."));
                    //BeanUtils.copyProperties(bookDTO, );
                    // Update fields
                    existingBook.setTitle(bookDTO.getTitle());
                    existingBook.setDescription(bookDTO.getDescription());
                    existingBook.setIsbn(bookDTO.getIsbn());
                    existingBook.setUpdatedAt(ZonedDateTime.now());

                    return bookRepository.save(existingBook);
                }).map(bookMapper::entityToDto)
                .switchIfEmpty(Mono.error(new BookNotFoundException("Cannot find a book with id " + id)));
    }

    @Override
    public Mono<Void> deleteBook(String id) {
        return bookRepository.deleteById(Long.valueOf(id))
                .onErrorResume(BookNotFoundException.class, e -> Mono.error(
                        new BookNotFoundException("Cannot find a book with id " + id)))
                .onErrorResume(GeneralException.class, e -> Mono.error(new GeneralException("Undefined error.")));
    }

    /**
     * @param id
     * @param bookDTO
     * @return
     */
    @Override
    public Mono<BookDTO> requestApproval(String id, BookDTO bookDTO) {
        return bookRepository.findById(Long.valueOf(id))
                .switchIfEmpty(Mono.error(new BookNotFoundException("Book not found with id " + id)))
                .flatMap(book -> {
                    if(BookStatus.DRAFT.equals(bookDTO.getStatus())){
                        book.setStatus(BookStatus.PENDING_APPROVAL);
                        book.setUpdatedAt(ZonedDateTime.now());
                        return bookRepository.save(book);
                    }else {
                        return Mono.error(new IllegalStateException("The book cannot send to approve due to illegal state."));
                    }
                })
                .doOnSuccess(savedBook -> {
                    NotificationRequest request = new NotificationRequest();
                    request.setTopic(CHECKER_TOPIC);
                    request.setMessage("Phê duyệt giúp tôi nhé: book id: " + savedBook.getId());
                    request.setRcvrRoles(new String[] {"ho-checkers,br-checkers"});

                    notificationService.sendToChecker(request).subscribe();
                })
                .map(bookMapper::entityToDto);
    }

    /**
     * @param id
     * @param bookDTO
     * @return
     */
    @Override
    public Mono<BookDTO> approveBook(String id, BookDTO bookDTO) {
        return bookRepository.findById(Long.valueOf(id))
                .switchIfEmpty(Mono.error(new BookNotFoundException("Book not found with id: " + id)))
                .flatMap(book -> {
                    if(BookStatus.PENDING_APPROVAL.equals(book.getStatus())){
                        book.setStatus(BookStatus.APPROVED);
                        book.setStatusDescription(String.valueOf(bookDTO.getStatusDescription()));
                        book.setUpdatedAt(ZonedDateTime.now());
                        return bookRepository.save(book);
                    }else {
                        return Mono.error(new IllegalStateException("Cannot approve the book with id " + id
                                + " due to illegal state"));
                    }
                })
                .doOnSuccess(savedBook -> {
                    NotificationRequest request = new NotificationRequest();
                    request.setTopic(CHECKER_TOPIC);
                    request.setMessage("Phê duyệt giúp tôi nhé: book id: " + savedBook.getId());
                    request.setRcvrRoles(new String[] {"ho-checkers,br-checkers"});

                    notificationService.sendToChecker(request).subscribe();
                })
                .map(bookMapper::entityToDto);
    }

    /// @param id
    /// @param bookDTO
    /// @return
    /// Todo: implement this method
    @Override
    public Mono<BookDTO> rejectBook(String id, BookDTO bookDTO) {
        return null;
    }

    /// Todo: implement this method
    @Override
    public Mono<BookDTO> requestApproval(String id, String statusDescription) {
        return Mono.fromCallable(() -> {
            BookEntity book = bookRepository.findById(Long.valueOf(id))
                    .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));

            book.setStatus(BookStatus.PENDING_APPROVAL);
            book.setStatusDescription(statusDescription);
            book.setUpdatedAt(ZonedDateTime.now());
            return bookRepository.save(book);
        }).doOnSuccess(savedEntity -> {
            // After processing business logic, send notification
            NotificationRequest request = new NotificationRequest();
            request.setTopic(CHECKER_TOPIC);
            request.setMessage("Phê duyệt giúp tôi nhé: book id: " + savedEntity.getId());
            request.setRcvrRoles(new String[] {"ho-checkers,br-checkers"});

            notificationService.sendToChecker(request).subscribe();
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
            BookEntity approvedBook = bookRepository.save(book);

            NotificationRequest sendToMaker = new NotificationRequest();
            sendToMaker.setTopic(CHECKER_TOPIC);
            sendToMaker.setMessage("Đã phê duyệt nhé: book id: " + approvedBook.getId());
            sendToMaker.setRcvrRoles(new String[] {"all"});

            return approvedBook;
        }).map(bookMapper::entityToDto)
                .flatMap(bookDTO -> {
                    // After processing business logic, send notification
                    NotificationRequest request = new NotificationRequest();
                    request.setTopic(MAKER_TOPIC);
                    request.setMessage("Đã Phê duyệt nhé: book id: " + bookDTO.getId());
                    request.setRcvrRoles(new String[] {"ho-makers,br-makers"});
                    return notificationService.sendToChecker(request).thenReturn(bookDTO);
                });
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
