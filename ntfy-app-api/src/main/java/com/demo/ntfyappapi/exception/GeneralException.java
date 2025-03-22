package com.demo.ntfyappapi.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class GeneralException extends RuntimeException{
    public GeneralException(String message){
        super(message);
    }
}
