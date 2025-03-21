package com.demo.ntfyappapi.dao.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DiscriminatorFormula;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Entity
@Data
@Table(name = "book")
@DiscriminatorFormula("'Book'")
public class BookEntity implements Serializable,Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name="desc", nullable = false)
    private String description;

    @Column(name = "is_published")
    private Boolean isPublished = false;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "created_at",nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Override
    public BookEntity clone() {
        try {
            return (BookEntity) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
