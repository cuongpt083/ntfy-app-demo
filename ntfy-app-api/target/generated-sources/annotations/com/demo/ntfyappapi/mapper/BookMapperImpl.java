package com.demo.ntfyappapi.mapper;

import com.demo.ntfyappapi.dao.entity.BookEntity;
import com.demo.ntfyappapi.dto.BookDTO;
import com.demo.ntfyappapi.dto.BookStatus;
import com.demo.ntfyappapi.dto.request.BookPostRequest;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-02T10:04:09+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.4.1 (Oracle Corporation)"
)
@Component
public class BookMapperImpl implements BookMapper {

    @Override
    public BookEntity requestToEntity(BookPostRequest bookReq) {
        if ( bookReq == null ) {
            return null;
        }

        BookEntity bookEntity = new BookEntity();

        bookEntity.setTitle( bookReq.getTitle() );
        bookEntity.setIsbn( bookReq.getIsbn() );
        bookEntity.setDescription( bookReq.getDescription() );
        bookEntity.setStatusDescription( bookReq.getStatusDescription() );

        bookEntity.setStatus( BookStatus.DRAFT );
        bookEntity.setCreatedAt( java.time.ZonedDateTime.now() );

        return bookEntity;
    }

    @Override
    public BookDTO entityToDto(BookEntity entity) {
        if ( entity == null ) {
            return null;
        }

        BookDTO.BookDTOBuilder bookDTO = BookDTO.builder();

        if ( entity.getId() != null ) {
            bookDTO.id( String.valueOf( entity.getId() ) );
        }
        bookDTO.title( entity.getTitle() );
        bookDTO.description( entity.getDescription() );
        bookDTO.isbn( entity.getIsbn() );
        bookDTO.status( entity.getStatus() );
        bookDTO.statusDescription( entity.getStatusDescription() );
        bookDTO.createdAt( entity.getCreatedAt() );
        bookDTO.updatedAt( entity.getUpdatedAt() );

        return bookDTO.build();
    }

    @Override
    public BookEntity dtoToEntity(BookDTO dto) {
        if ( dto == null ) {
            return null;
        }

        BookEntity bookEntity = new BookEntity();

        if ( dto.getId() != null ) {
            bookEntity.setId( Long.parseLong( dto.getId() ) );
        }
        bookEntity.setTitle( dto.getTitle() );
        bookEntity.setIsbn( dto.getIsbn() );
        bookEntity.setDescription( dto.getDescription() );
        bookEntity.setStatus( dto.getStatus() );
        bookEntity.setStatusDescription( dto.getStatusDescription() );
        bookEntity.setCreatedAt( dto.getCreatedAt() );
        bookEntity.setUpdatedAt( dto.getUpdatedAt() );

        return bookEntity;
    }
}
