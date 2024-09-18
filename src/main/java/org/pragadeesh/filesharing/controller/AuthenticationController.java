package org.pragadeesh.filesharing.controller;

import org.apache.coyote.Response;
import org.pragadeesh.filesharing.dto.UserDto;
import org.pragadeesh.filesharing.model.JwtRequest;
import org.pragadeesh.filesharing.model.JwtResponse;
import org.pragadeesh.filesharing.model.User;
import org.pragadeesh.filesharing.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDto user) {
        return new ResponseEntity<>(authenticationService.register(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest user) {
        return new ResponseEntity<>(authenticationService.login(user), HttpStatus.OK);
    }
}
