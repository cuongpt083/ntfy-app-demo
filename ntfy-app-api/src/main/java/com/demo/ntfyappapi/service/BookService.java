package com.demo.ntfyappapi.service;

import com.demo.ntfyappapi.dto.response.BookResponse;
import org.springframework.web.reactive.result.view.RequestContext;

public interface BookService {
    BookResponse add(RequestContext requestContext);
    BookResponse update(RequestContext requestContext);
}
