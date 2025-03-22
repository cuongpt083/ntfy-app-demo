package com.demo.ntfyappapi.dao.repository;

import com.demo.ntfyappapi.dao.entity.BookEntity;
import com.demo.ntfyappapi.dto.BookStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
    List<BookEntity> findAllByIsPublished(Boolean isPublished);
    List<BookEntity> findAllByStatus(BookStatus status);
    List<BookEntity> findByTitleContaining(String title, Pageable pageable);

    List<BookEntity> findAllByIsbn(String isbn);

    void deleteById(Long id) ;
}
