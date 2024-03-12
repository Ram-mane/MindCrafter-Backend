package com.user.bloggs.services;

import java.util.List;

import com.user.bloggs.payloads.UserDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public interface UserServices {

	UserDto registerUser(UserDto userDto);
	
	UserDto createUser(UserDto userDto);
	
	UserDto updateUser(UserDto userDto , Integer userId);
	
	UserDto getUserById(Integer userId);
	
	List<UserDto> getAllUsers();
	
	void deleteUserById(Integer userId);
}
