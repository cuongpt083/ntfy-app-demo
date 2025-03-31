package com.demo.ntfyappapi.dao.entity;

import com.demo.ntfyappapi.dto.BookStatus;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.ZonedDateTime;


@Data
@Table(name = "book", schema = "ntfy_app")
public class BookEntity implements Serializable,Cloneable {
    @Id
    private Long id;

    @Column("title")
    private String title;

    @Column("isbn")
    private String isbn;

    @Column("description")
    private String description;

    @Column("status")
    private BookStatus status;

    @Column("status_desc")
    private String statusDescription;

    @Column("created_at")
    @CreatedDate
    private ZonedDateTime createdAt;

    @Column("updated_at")
    @LastModifiedDate
    private ZonedDateTime updatedAt;

    public BookEntity(){
        this.createdAt = ZonedDateTime.now();
    }

    /*@PrePersist
    protected void onCreate(){
        this.createdAt = ZonedDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = ZonedDateTime.now();
    }*/

    @Override
    public BookEntity clone() {
        try {
            return (BookEntity) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

}
