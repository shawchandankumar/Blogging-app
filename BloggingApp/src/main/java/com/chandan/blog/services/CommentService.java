package com.chandan.blog.services;

import java.util.List;

import com.chandan.blog.payloads.CommentDto;

public interface CommentService {
	
	CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId);
	void deleteComment(Integer commentId);
	List<CommentDto> getCommentOfUserWithPostId(Integer postId, Integer userId); 

}
