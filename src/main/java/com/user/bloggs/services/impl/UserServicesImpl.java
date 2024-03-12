package com.user.bloggs.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.user.bloggs.entity.User;
import com.user.bloggs.entity.Role;

import com.user.bloggs.exceptions.ResourceNotFoundException;
import com.user.bloggs.exceptions.UserAlreadyExistsException;
import com.user.bloggs.payloads.UserDto;
import com.user.bloggs.repository.RoleRepo;
import com.user.bloggs.repository.UserRepo;
import com.user.bloggs.services.UserServices;
import com.user.bloggs.utils.AppConstant;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Service

public class UserServicesImpl implements UserServices {

	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	
	
	@Override
	public UserDto registerUser(UserDto userDto) {

		
		
		// Check if the user with the given email already exists
	    if (userRepo.existsByEmail(userDto.getEmail())) {
	        throw new UserAlreadyExistsException(userDto.getEmail());
	    }
	    
	    
		User user = this.dtoToUser(userDto);
		
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		
		// roles
		Role role = this.roleRepo.getById(AppConstant.NORAMAL_USER);
		user.getRoles().add(role);
		
		User newUser = this.userRepo.save(user);
		return this.userToDto(newUser);
	}
	
	@Override
	public UserDto createUser(UserDto userDto) {
		User user = this.dtoToUser(userDto);
		User savedUser = this.userRepo.save(user);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","Id", userId));
		
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		user.setUsername(userDto.getUsername());
		user.setPhone_no(userDto.getPhone_no());
		user.setGender(userDto.getGender());
		
		 User updatedUser = this.userRepo.save(user);
		
		return this.userToDto(updatedUser);	}

	@Override
	public UserDto getUserById(Integer userId) {

		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user","Id",userId));
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = this.userRepo.findAll();
		
		List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUserById(Integer userId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","Id",userId));
		
		this.userRepo.delete(user);

	}
	
	public User dtoToUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto,User.class);
		
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setPassword(userDto.getPassword());
//		user.setAbout(userDto.getAbout());
//		user.setUsername(userDto.getUsername());
//		user.setPhone_no(userDto.getPhone_no());
//		user.setGender(userDto.getGender());
		return user;
	}
	
    public UserDto userToDto(User user){
		
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setPassword(user.getPassword());
//		userDto.setAbout(user.getAbout());
//		userDto.setUsername(user.getUsername());
//		userDto.setPhone_no(user.getPhone_no());
//		userDto.setGender(user.getGender());
		
		return userDto;
	}

	
	
	

}
