package com.demo.ntfyappapi.dao.repository;

import com.demo.ntfyappapi.dao.entity.BookEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepo extends JpaRepository<BookEntity, Long> {
    List<BookEntity> findByIsPublished(Boolean isPublished);

    List<BookEntity> findByTitleContaining(String title, Pageable pageable);
}
