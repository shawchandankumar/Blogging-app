package com.chandan.blog.services;

import com.chandan.blog.payloads.UserDto;

import java.util.List;

public interface UserService {

	UserDto createUser(UserDto userDto);
	UserDto updateUser(UserDto userDto, Integer userId);
	UserDto getUserById(Integer id);
	List<UserDto> getAllUsers();
	void deleteUser(Integer userId);
	
}
