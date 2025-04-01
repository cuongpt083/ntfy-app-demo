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
import org.springframework.boot.actuate.startup.StartupEndpoint;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;
import java.util.List;

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

    //private StartupEndpoint startupEndpoint;


    /**
     * @param bookDTO
     * @return
     */
    @Override
    @Transactional(
            propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = {
                    GeneralException.class,
                    DataIntegrityViolationException.class
            }
    )
    public Mono<BookDTO> createBook(BookDTO bookDTO) {
        BookEntity bookEntity = bookMapper.dtoToEntity(bookDTO);
        bookEntity.setStatus(BookStatus.DRAFT);
        bookEntity.setCreatedAt(ZonedDateTime.now());

        return bookRepository.save(bookEntity).
                map(bookMapper::entityToDto)
                .onErrorResume(err -> Mono.error(new GeneralException("Cannot create a new book")))
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
    public Mono<Page<BookDTO>> getAllBooks(Pageable pageable) {
        return bookRepository.findAllBy(pageable)
                .map(bookMapper::entityToDto)
                .collectList()
                .zipWith(bookRepository.count())
                .map(tuple -> {
                    List<BookDTO> bookDTOS = tuple.getT1();
                    long total = tuple.getT2();
                    return new PageImpl<>(bookDTOS, pageable, total);
                });
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
    public Mono<Page<BookDTO>> getAllBooksByStatus(Pageable pageable, BookStatus status) {
        Mono<Long> totalCount = bookRepository.count();
        if(status == null) {
            return getAllBooks(pageable);
        }else{
            return bookRepository.findAllByStatus(pageable, status)
                    .map(bookMapper::entityToDto)
                    .collectList()
                    .zipWith(bookRepository.countAllByStatusIs(status))
                    .map(tuple -> {
                        List<BookDTO> bookDTOS = tuple.getT1();
                        long total = tuple.getT2();

                        return new PageImpl<>(bookDTOS,pageable,total);
                    });
        }
    }

    @Override
    @Transactional(
            propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = {
                    GeneralException.class,
                    IllegalStateException.class,
                    DataIntegrityViolationException.class
            })
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
    @Transactional(
            propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = {
                    GeneralException.class,
                    DataIntegrityViolationException.class
            })
    public Mono<Void> deleteBook(String id) {
        return bookRepository.deleteById(Long.valueOf(id))
                .onErrorMap(this::mapToException);
    }

    /**
     * @param id
     * @param bookDTO
     * @return
     */
    @Override
    @Transactional(
            propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = {
                    GeneralException.class,
                    DataIntegrityViolationException.class,
                    IllegalStateException.class
            }
    )
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
                .map(bookMapper::entityToDto)
                .onErrorMap(this::mapToException);
    }

    /**
     * @param id
     * @param bookDTO
     * @return
     */
    @Override
    @Transactional(
            propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = {
                    GeneralException.class,
                    DataIntegrityViolationException.class,
                    IllegalStateException.class
            }
    )
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
                    request.setTopic(MAKER_TOPIC);
                    request.setMessage("Đã phê duyệt nhé: book id: " + savedBook.getId());
                    request.setRcvrRoles(new String[] {"ho-maker,br-maker"});

                    notificationService.sendToChecker(request).subscribe();
                })
                .map(bookMapper::entityToDto)
                .onErrorMap(this::mapToException);
    }

    /// @param id
    /// @param bookDTO
    /// @return
    /// Todo: implement this method
    @Override
    @Transactional(
            propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = {
                    GeneralException.class,
                    DataIntegrityViolationException.class,
                    IllegalStateException.class
            }
    )
    public Mono<BookDTO> rejectBook(String id, BookDTO bookDTO) {
        return bookRepository.findById(Long.valueOf(id))
                .switchIfEmpty(Mono.error(new BookNotFoundException("Book not found with id: " + id)))
                .flatMap(bookEntity -> {
                    if(!BookStatus.PENDING_APPROVAL.equals(bookDTO.getStatus()))
                        return Mono.error(new IllegalStateException("Cannot reject request approval for book id " + id
                        + " due to illegal state."));
                    bookEntity.setStatus(BookStatus.REJECTED);
                    bookEntity.setUpdatedAt(ZonedDateTime.now());
                    bookEntity.setStatusDescription("Tôi từ chối nhé, kiểm tra lại thông tin book id " + id);
                    return bookRepository.save(bookEntity);
                })
                .doOnSuccess(savedBook -> {
                    NotificationRequest request = new NotificationRequest();
                    request.setTopic(MAKER_TOPIC);
                    request.setMessage("Yêu cầu bị từ chối, book id: " + savedBook.getId());
                    request.setRcvrRoles(new String[] {"ho-makers,br-makers"});
                    notificationService.sendToChecker(request).subscribe();
                })
                .map(bookMapper::entityToDto)
                .onErrorMap(this::mapToException);
    }

    /// Todo: implement this method
    @Override
    @Transactional(
            propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = {
                    GeneralException.class,
                    DataIntegrityViolationException.class,
                    IllegalStateException.class
            }
    )
    public Mono<BookDTO> requestApproval(String id, String statusDescription) {
        return bookRepository.findById(Long.valueOf(id))
                .switchIfEmpty(Mono.error(new BookNotFoundException("Book not found with id " + id)))
                .flatMap(book -> {
                    if(BookStatus.DRAFT.equals(book.getStatus())){
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
                .map(bookMapper::entityToDto)
                .onErrorMap(this::mapToException);
    }

    @Override
    @Transactional(
            propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = {
                    GeneralException.class,
                    DataIntegrityViolationException.class,
                    IllegalStateException.class
            }
    )
    public Mono<BookDTO> approveBook(String id, String statusDescription) {
        return bookRepository.findById(Long.valueOf(id))
                .switchIfEmpty(Mono.error(new BookNotFoundException("Book not found with id: " + id)))
                .flatMap(book -> {
                    if(BookStatus.PENDING_APPROVAL.equals(book.getStatus())){
                        book.setStatus(BookStatus.APPROVED);
                        book.setStatusDescription(String.valueOf(book.getStatusDescription()));
                        book.setUpdatedAt(ZonedDateTime.now());
                        return bookRepository.save(book);
                    }else {
                        return Mono.error(new IllegalStateException("Cannot approve the book with id " + id
                                + " due to illegal state"));
                    }
                })
                .doOnSuccess(savedBook -> {
                    NotificationRequest request = new NotificationRequest();
                    request.setTopic(MAKER_TOPIC);
                    request.setMessage("Đã phê duyệt nhé: book id: " + savedBook.getId());
                    request.setRcvrRoles(new String[] {"ho-makers,br-makers"});

                    notificationService.sendToChecker(request).subscribe();
                })
                .map(bookMapper::entityToDto)
                .onErrorMap(this::mapToException);

    }



    @Override
    @Transactional(
            propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = {
                    GeneralException.class,
                    DataIntegrityViolationException.class,
                    IllegalStateException.class
            }
    )
    public Mono<BookDTO> rejectBook(String id, String statusDescription) {
        return bookRepository.findById(Long.valueOf(id))
                .switchIfEmpty(Mono.error(new BookNotFoundException("Book not found with id: " + id)))
                .flatMap(bookEntity -> {
                    if(!BookStatus.PENDING_APPROVAL.equals(bookEntity.getStatus()))
                        return Mono.error(new IllegalStateException("Cannot reject request approval for book id " + id
                                + " due to illegal state."));
                    bookEntity.setStatus(BookStatus.REJECTED);
                    bookEntity.setUpdatedAt(ZonedDateTime.now());
                    bookEntity.setStatusDescription("Tôi từ chối nhé, kiểm tra lại thông tin book id " + id);
                    return bookRepository.save(bookEntity);
                })
                .doOnSuccess(savedBook -> {
                    NotificationRequest request = new NotificationRequest();
                    request.setTopic(MAKER_TOPIC);
                    request.setMessage("Yêu cầu bị từ chối, book id: " + savedBook.getId());
                    request.setRcvrRoles(new String[] {"ho-makers,br-makers"});
                    notificationService.sendToChecker(request).subscribe();
                })
                .map(bookMapper::entityToDto)
                .onErrorMap(this::mapToException);
    }


    /**
     * Map different types of exceptions to more specific service exceptions
     *
     * @param originalException The original exception thrown
     * @return A mapped exception for better error handling
     */
    private Throwable mapToException(Throwable originalException){
        // Map database-related exceptions
        if (originalException instanceof DataIntegrityViolationException) {
            return new GeneralException("Unable to reject book due to data integrity issues", originalException);
        }

        // Map repository-related exceptions
        if (originalException instanceof DataAccessException) {
            return new GeneralException("Database error occurred during book rejection", originalException);
        }

        return new GeneralException("Unexpected error during book rejection", originalException);
    }
}
