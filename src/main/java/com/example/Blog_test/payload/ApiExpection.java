package com.example.Blog_test.payload;

public class ApiExpection extends RuntimeException{

    private String message;

    public ApiExpection(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
