package com.example.Blog_test.payload;

public class AuthReponse {
    private String token;

    public AuthReponse() {
    }

    public AuthReponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
