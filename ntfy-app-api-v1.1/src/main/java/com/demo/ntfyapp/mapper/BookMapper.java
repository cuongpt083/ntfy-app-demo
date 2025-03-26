package com.demo.ntfyapp.mapper;

import org.springframework.stereotype.Component;

import com.demo.ntfyapp.model.dto.BookDto;
import com.demo.ntfyapp.model.entity.Book;

@Component
public class BookMapper {

    /**
     * Convert a Book entity to a Response DTO
     *
     * @param book The book entity
     * @return BookDto.Response
     */
    public BookDto.Response toResponse(Book book) {
        if (book == null) {
            return null;
        }

        return new BookDto.Response(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getDescription(),
                book.getIsbn(),
                book.getPublishYear(),
                book.getPublisher(),
                book.getApprovalStatus(),
                book.getCreatedAt(),
                book.getUpdatedAt(),
                book.getApprovalRequestedAt(),
                book.getApprovedAt(),
                book.getApprovedBy()
        );
    }
}