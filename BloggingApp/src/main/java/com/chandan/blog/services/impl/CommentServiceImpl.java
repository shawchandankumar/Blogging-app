package com.chandan.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chandan.blog.exceptions.ResourceNotFoundException;
import com.chandan.blog.models.Comment;
import com.chandan.blog.models.Post;
import com.chandan.blog.models.User;
import com.chandan.blog.payloads.CommentDto;
import com.chandan.blog.repositories.CommentRepo;
import com.chandan.blog.repositories.PostRepo;
import com.chandan.blog.repositories.UserRepo;
import com.chandan.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId) {
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", " post id", postId));
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", " user id", userId));
		
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		comment.setUser(user);
		Comment createdComment = commentRepo.save(comment);
		
		return this.modelMapper.map(createdComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", " comment id", commentId));
		commentRepo.delete(comment);
	}

	@Override
	public List<CommentDto> getCommentOfUserWithPostId(Integer postId, Integer userId) {
		List<Comment> commentByUserIdWithPostId = this.commentRepo.findByUserIdAndPostPostId(userId, postId);
		List<CommentDto> commentDtos = commentByUserIdWithPostId.stream()
				.map((comment) -> this.modelMapper.map(comment, CommentDto.class)).collect(Collectors.toList());
		
		return commentDtos;
	}

}
