package com.chandan.blog.payloads;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {

	private Integer postId;
	private String title;
	private String content;
	private String imageName;
	private UserDto user;
	private CategoryDto category;
	private List<CommentDto> comments = new ArrayList<>();

}
