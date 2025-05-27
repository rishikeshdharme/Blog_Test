package com.example.Blog_test.controller;

import com.example.Blog_test.Security.CustomUserDetailsService;
import com.example.Blog_test.payload.*;
import com.example.Blog_test.service.JWTService;
import com.example.Blog_test.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final CustomUserDetailsService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final UserService userService1;

    public AuthController(CustomUserDetailsService userService, AuthenticationManager authenticationManager, JWTService jwtService, UserService userService1) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService1 = userService1;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthReponse> createToken(@RequestBody AuthRequest authRequest) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
            UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
            String token = jwtService.generateToken(userDetails.getUsername());

            AuthReponse authReponse = new AuthReponse();
            authReponse.setToken(token);
            return new ResponseEntity<>(authReponse, HttpStatus.OK);
        }catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AuthReponse("Invalid credentials"), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(new AuthReponse("Authentication failed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService1.registerUser(userRequest);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    public void authenticate(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
