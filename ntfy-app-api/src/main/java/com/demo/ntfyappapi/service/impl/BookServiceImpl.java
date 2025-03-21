package com.demo.ntfyappapi.service.impl;

import com.demo.ntfyappapi.dto.request.BookRequest;
import com.demo.ntfyappapi.dto.response.BookResponse;
import com.demo.ntfyappapi.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.result.view.RequestContext;

@Service
@lombok.RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    @Override
    public BookResponse add(BookRequest bookRequest) {
        BookRequest bookRequest = (BookRequest) requestContext.
        return null;
    }

    @Override
    public BookResponse update(RequestContext requestContext) {
        return null;
    }
}
