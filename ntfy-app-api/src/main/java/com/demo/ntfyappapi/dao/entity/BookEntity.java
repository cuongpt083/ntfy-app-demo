package com.demo.ntfyappapi.dao.entity;

import com.demo.ntfyappapi.dto.BookStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DiscriminatorFormula;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

@Entity
@Data
@Table(name = "book", schema = "ntfy_app")
@DiscriminatorFormula("'Book'")
public class BookEntity implements Serializable,Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "isbn", nullable = false, unique = true)
    private String isbn;

    @Column(name="description", nullable = false)
    private String description;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private BookStatus status;

    @Column(name = "status_desc", nullable = false)
    private String statusDescription;

    @Column(name = "created_at",nullable = false)
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    public BookEntity(){
        this.createdAt = ZonedDateTime.now();
    }

    @PrePersist
    protected void onCreate(){
        this.createdAt = ZonedDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = ZonedDateTime.now();
    }

    @Override
    public BookEntity clone() {
        try {
            return (BookEntity) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
