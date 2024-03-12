package com.user.bloggs.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.bloggs.payloads.ApiResponse;
import com.user.bloggs.payloads.UserDto;
import com.user.bloggs.services.UserServices;



@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = { "*" })
public class UserController {

	@Autowired
	private UserServices userServices ;
	
	
	// create user
	
	@PostMapping("/")
	public ResponseEntity<?> createUser( @RequestBody UserDto userDto){
		
		    UserDto createdUser =  this.userServices.createUser(userDto);
		
		ApiResponse apiResponse = new ApiResponse("User created Succesfully !", true);
		
//		Map<String , Object> response  = new HashMap<>();
//		
//		response.put("apiResponse", apiResponse);
//		response.put("createdUser", createdUser);
		
//		Queue<Object> response = new LinkedList<>();
//		
//		response.offer(apiResponse);
//		response.offer(createdUser);
		
		return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
	}
	
	
	// update user
	@PutMapping("/{userId}")
	ResponseEntity<UserDto> updateUserInfo( @RequestBody UserDto userDto ,@PathVariable("userId") Integer userId){
		
		UserDto updatedUserDto = this.userServices.updateUser(userDto, userId);
		
		return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
	}
	
	
	// get single user by id
	@GetMapping("/{userId}")
	ResponseEntity<UserDto> findUserById(@PathVariable("userId") Integer userId){
        
		return ResponseEntity.ok(this.userServices.getUserById(userId));
	}
	
	//get list of all uses
	
	@GetMapping("/")
	ResponseEntity<List<UserDto>> getAllUser(){
		
		return ResponseEntity.ok(this.userServices.getAllUsers());
	}
	
	@DeleteMapping("/{userId}")
	ResponseEntity<?> deleteUser(@PathVariable("userId") Integer userId){
		
		 this.userServices.deleteUserById(userId);
//		String message = "user deleted Succesfully";
		 
		 ApiResponse apiResponse = new ApiResponse("user deleted succesfully", true);
		
		return ResponseEntity.ok(apiResponse);
	}
}
