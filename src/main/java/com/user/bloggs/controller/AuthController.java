package com.user.bloggs.controller;

import java.util.LinkedList;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.bloggs.exceptions.UserAlreadyExistsException;
import com.user.bloggs.payloads.ApiResponse;
import com.user.bloggs.payloads.JwtAuthRequest;
import com.user.bloggs.payloads.JwtAuthResponse;
import com.user.bloggs.payloads.Response;
import com.user.bloggs.payloads.UserDto;
import com.user.bloggs.repository.UserRepo;
import com.user.bloggs.security.JwtTokenHelper;
import com.user.bloggs.services.UserServices;


@RestController
@RequestMapping("/api/auth/")
@CrossOrigin(origins = { "*" })
public class AuthController {

	

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;


    @Autowired
    private JwtTokenHelper helper;
    
    @Autowired
    private UserServices userServices;
    
    
    
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        try {
            UserDto registerUser = this.userServices.registerUser(userDto);
            Response<UserDto> apiResponse = new Response<>("User Registered Successfully!", true, registerUser);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (UserAlreadyExistsException e) {
            Response<UserDto> apiResponse = new Response<>("User with this email already exists", false);
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
    }



    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) {

        this.authenticate(request.getUsername(), request.getPassword());


        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = this.helper.generateToken(userDetails);

        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(token);
        
        return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
    }
    
    
    private void authenticate(String username, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
           this.manager.authenticate(authentication);

    }

   
}




//try {
//    this.manager.authenticate(authentication);
//
//
// } catch (BadCredentialsException e) {
// 	System.out.println("Invalid details !");
//     throw new BadCredentialsException(" Invalid Username or Password  !!");
// }



