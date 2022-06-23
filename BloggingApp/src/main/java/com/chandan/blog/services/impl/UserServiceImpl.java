package com.chandan.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.chandan.blog.config.AppConstants;
import com.chandan.blog.exceptions.ResourceNotFoundException;
import com.chandan.blog.models.Role;
import com.chandan.blog.models.User;
import com.chandan.blog.payloads.UserDto;
import com.chandan.blog.repositories.RoleRepo;
import com.chandan.blog.repositories.UserRepo;
import com.chandan.blog.services.UserService;


@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private RoleRepo roleRepo;

	@Override
	public UserDto createUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		
		Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		user.getRoles().add(role);
		
		User savedUser = userRepo.save(user);
		return this.modelMapper.map(savedUser, UserDto.class);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User ", "Id ", userId));
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword());
		
		User updatedUser = userRepo.save(user);
		return this.modelMapper.map(updatedUser, UserDto.class);
	}

	@Override
	public UserDto getUserById(Integer id) {
		User user = userRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User", " Id ", id));
		
		return this.modelMapper.map(user, UserDto.class);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = userRepo.findAll();
		
		List<UserDto> userDtos = users.stream()
				.map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
		
		return userDtos;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@Override
	public void deleteUser(Integer userId) {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User ", "Id ", userId));
		
		userRepo.delete(user);
	}

}
