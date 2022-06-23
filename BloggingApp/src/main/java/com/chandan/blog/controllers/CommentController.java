package com.chandan.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chandan.blog.payloads.ApiResponse;
import com.chandan.blog.payloads.CommentDto;
import com.chandan.blog.services.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/user/{userId}/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Integer postId, @PathVariable Integer userId) {
		CommentDto createdComment = commentService.createComment(commentDto, postId, userId);
		return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
	}
	
	@GetMapping("/user/{userId}/post/{postId}/comments")
	public ResponseEntity<List<CommentDto>> getComment(@PathVariable Integer postId, @PathVariable Integer userId) {
		List<CommentDto> allComments = commentService.getCommentOfUserWithPostId(postId, userId);
		return new ResponseEntity<>(allComments, HttpStatus.OK);
	}
	
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId) {
		this.commentService.deleteComment(commentId);
		return new ResponseEntity<>(new ApiResponse("Comment deleted successfully !!", true), HttpStatus.OK);
	}

}



