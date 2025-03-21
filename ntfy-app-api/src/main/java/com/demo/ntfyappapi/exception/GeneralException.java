package com.demo.ntfyappapi.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class GeneralException extends RuntimeException{
    public GeneralException(String message){
        super(message);
    }
}
