package com.demo.ntfyappapi.mapper;

import com.demo.ntfyappapi.dao.entity.BookEntity;
import com.demo.ntfyappapi.dto.BookDTO;
import com.demo.ntfyappapi.dto.request.BookPostRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.time.ZonedDateTime;

@Mapper(componentModel = "spring", nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    // Mapping from BookReq to BookEntity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "DRAFT")
    @Mapping(target = "createdAt", expression = "java(java.time.ZonedDateTime.now())")
    BookEntity requestToEntity(BookPostRequest bookReq);

    // Mapping from BookEntity to BookDTO
    BookDTO entityToDto(BookEntity entity);

    // Mapping from BookDTO to BookEntity
    BookEntity dtoToEntity(BookDTO dto);

    // Mapping from BookReq to BookDTO
    default BookDTO requestToDto(BookPostRequest bookReq) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle(bookReq.getTitle());
        bookDTO.setDescription(bookReq.getDescription());
        bookDTO.setIsbn(bookReq.getIsbn());
        bookDTO.setStatusDescription(bookReq.getStatusDescription());
        return bookDTO;
    }
}
