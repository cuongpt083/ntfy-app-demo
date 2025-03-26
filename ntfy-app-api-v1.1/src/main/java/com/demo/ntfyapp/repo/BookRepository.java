package com.demo.ntfyapp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.ntfyapp.model.entity.Book;
import com.demo.ntfyapp.model.entity.Book.ApprovalStatus;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {

    // Find books by approval status
    List<Book> findByApprovalStatus(ApprovalStatus status);

    // Find books by author
    List<Book> findByAuthorContainingIgnoreCase(String author);

    // Find books by title
    List<Book> findByTitleContainingIgnoreCase(String title);

    // Find books by ISBN
    Book findByIsbn(String isbn);

    // Find books by publisher
    List<Book> findByPublisherContainingIgnoreCase(String publisher);
}
