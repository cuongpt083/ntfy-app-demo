package com.demo.ntfyapp.service;

import java.time.ZonedDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.demo.ntfyapp.mapper.BookMapper;
import com.demo.ntfyapp.repo.BookRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.demo.ntfyapp.exception.BookNotFoundException;
import com.demo.ntfyapp.model.dto.BookDto;
import com.demo.ntfyapp.exception.BookAlreadyExistsException;
import com.demo.ntfyapp.exception.BookNotFoundException;
import com.demo.ntfyapp.exception.InvalidOperationException;
import com.demo.ntfyapp.mapper.BookMapper;
import com.demo.ntfyapp.model.entity.Book;
import com.demo.ntfyapp.model.entity.Book.ApprovalStatus;
import com.demo.ntfyapp.repo.BookRepository;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    /**
     * Get all books in the system
     * @return List of all books
     */
    @Transactional(readOnly = true)
    public List<BookDto.Response> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get a book by its ID
     * @param id Book ID
     * @return Book details
     * @throws com.demo.ntfyapp.exception.BookNotFoundException if book not found
     */
    @Transactional(readOnly = true)
    public BookDto.Response getBookById(String id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with ID: " + id));
        return bookMapper.toResponse(book);
    }

    /**
     * Create a new book in the system
     * @param request Book creation details
     * @return Created book
     * @throws com.demo.ntfyapp.exception.BookAlreadyExistsException if ISBN already exists
     */
    @Transactional
    public BookDto.Response createBook(BookDto.CreateRequest request) {
        // Check if ISBN already exists
        Book existingBook = bookRepository.findByIsbn(request.getIsbn());
        if (existingBook != null) {
            throw new BookAlreadyExistsException("Book with ISBN " + request.getIsbn() + " already exists");
        }

        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setDescription(request.getDescription());
        book.setIsbn(request.getIsbn());
        book.setPublishYear(request.getPublishYear());
        book.setPublisher(request.getPublisher());
        book.setApprovalStatus(ApprovalStatus.DRAFT);

        Book savedBook = bookRepository.save(book);
        return bookMapper.toResponse(savedBook);
    }

    /**
     * Update an existing book
     * @param id Book ID
     * @param request Book update details
     * @return Updated book
     * @throws BookNotFoundException if book not found
     * @throws com.demo.ntfyapp.exception.InvalidOperationException if book is not in DRAFT state
     */
    @Transactional
    public BookDto.Response updateBook(String id, BookDto.UpdateRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with ID: " + id));

        // Only allow updates to books in DRAFT status
        if (book.getApprovalStatus() != ApprovalStatus.DRAFT) {
            throw new InvalidOperationException("Cannot update book that is not in DRAFT status");
        }

        // Update only non-null fields
        if (request.getTitle() != null) {
            book.setTitle(request.getTitle());
        }
        if (request.getAuthor() != null) {
            book.setAuthor(request.getAuthor());
        }
        if (request.getDescription() != null) {
            book.setDescription(request.getDescription());
        }
        if (request.getIsbn() != null) {
            // Check that new ISBN doesn't conflict with existing book
            Book existingBook = bookRepository.findByIsbn(request.getIsbn());
            if (existingBook != null && !existingBook.getId().equals(book.getId())) {
                throw new BookAlreadyExistsException("Book with ISBN " + request.getIsbn() + " already exists");
            }
            book.setIsbn(request.getIsbn());
        }
        if (request.getPublishYear() != null) {
            book.setPublishYear(request.getPublishYear());
        }
        if (request.getPublisher() != null) {
            book.setPublisher(request.getPublisher());
        }

        book.setUpdatedAt(ZonedDateTime.now());
        Book updatedBook = bookRepository.save(book);
        return bookMapper.toResponse(updatedBook);
    }

    /**
     * Delete a book by its ID
     * @param id Book ID
     * @throws BookNotFoundException if book not found
     * @throws com.demo.ntfyapp.exception.InvalidOperationException if book is not in DRAFT state
     */
    @Transactional
    public void deleteBook(String id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with ID: " + id));

        // Only allow deletion of books in DRAFT status
        if (book.getApprovalStatus() != ApprovalStatus.DRAFT) {
            throw new InvalidOperationException("Cannot delete book that is not in DRAFT status");
        }

        bookRepository.delete(book);
    }

    /**
     * Request approval for a book
     * @param id Book ID
     * @return Updated book with PENDING status
     * @throws BookNotFoundException if book not found
     * @throws com.demo.ntfyapp.exception.InvalidOperationException if book is not in DRAFT state
     */
    @Transactional
    public BookDto.Response requestApproval(String id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with ID: " + id));

        // Only books in DRAFT status can request approval
        if (book.getApprovalStatus() != ApprovalStatus.DRAFT) {
            throw new InvalidOperationException("Only books in DRAFT status can request approval");
        }

        book.setApprovalStatus(ApprovalStatus.PENDING);
        book.setApprovalRequestedAt(ZonedDateTime.now());
        book.setUpdatedAt(ZonedDateTime.now());

        Book updatedBook = bookRepository.save(book);
        return bookMapper.toResponse(updatedBook);
    }

    /**
     * Process approval decision for a book
     * @param id Book ID
     * @param request Approval details including approver and decision
     * @return Updated book with APPROVED or REJECTED status
     * @throws BookNotFoundException if book not found
     * @throws com.demo.ntfyapp.exception.InvalidOperationException if book is not in PENDING state
     */
    @Transactional
    public BookDto.Response processApproval(String id, BookDto.ApprovalRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with ID: " + id));

        // Only books in PENDING status can be approved/rejected
        if (book.getApprovalStatus() != ApprovalStatus.PENDING) {
            throw new InvalidOperationException("Only books in PENDING status can be approved or rejected");
        }

        // Process approval decision
        ApprovalStatus decision = ApprovalStatus.valueOf(request.getDecision());
        book.setApprovalStatus(decision);
        book.setUpdatedAt(ZonedDateTime.now());

        if (decision == ApprovalStatus.APPROVED) {
            book.setApprovedAt(ZonedDateTime.now());
            book.setApprovedBy(request.getApprovedBy());
        }

        Book updatedBook = bookRepository.save(book);
        return bookMapper.toResponse(updatedBook);
    }

    /**
     * Get books by approval status
     * @param status Approval status
     * @return List of books with the given status
     */
    @Transactional(readOnly = true)
    public List<BookDto.Response> getBooksByStatus(ApprovalStatus status) {
        return bookRepository.findByApprovalStatus(status).stream()
                .map(bookMapper::toResponse)
                .collect(Collectors.toList());
    }
}