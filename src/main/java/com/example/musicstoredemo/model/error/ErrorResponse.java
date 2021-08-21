package com.example.musicstoredemo.model.error;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {

    private long errorCode;
    private String errorMessage;
    private String path;

}
