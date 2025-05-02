package com.example.Blog_test.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



public class UserRequest {

    private int id;
    @NotEmpty
    @Size(min = 2,message = "Username must be minimun of 2 character !!")
    private String name;
    @Email
    private String email;
    @NotNull
    @Size(min = 8 , message = "Password must be greater than 8 Characters !!")
    private String password;
    @NotEmpty
    private String about;



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
