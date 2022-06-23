package com.chandan.blog.payloads;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	
	private int id;
	
	@NotEmpty
	@Size(min = 4, max = 10, message = "Username must be of minimum 4 chars size")
	private String name;
	
	@Email(message = "Email must be valid")
	private String email;
	
	@NotEmpty
	@Size(min = 4, message = "Password must be of minimum 4 characters")
	private String password;
	
	@NotEmpty
	private String about;
	
	private List<CommentDto> comments = new ArrayList<>();
	
	private Set<RoleDto> roles = new HashSet<>();

}
