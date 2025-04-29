package com.example.project3.API;

import lombok.Data;

public class ApiException extends RuntimeException {
    public ApiException(String msg) {
        super(msg);
    }
}
