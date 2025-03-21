package com.demo.ntfyappapi.controller;


import com.demo.ntfyappapi.api.BooksApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;
import jakarta.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-03-21T10:35:18.771287+07:00[Asia/Bangkok]", comments = "Generator version: 7.7.0")
@Controller
@RequestMapping("${openapi.book.base-path:}")
public class BooksApiController implements BooksApi {

    private final BooksApiDelegate delegate;

    public BooksApiController(@Autowired(required = false) BooksApiDelegate delegate) {
        this.delegate = Optional.ofNullable(delegate).orElse(new BooksApiDelegate() {});
    }

    @Override
    public BooksApiDelegate getDelegate() {
        return delegate;
    }

}
