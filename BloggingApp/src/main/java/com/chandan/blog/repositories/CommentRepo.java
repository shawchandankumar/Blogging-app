package com.chandan.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chandan.blog.models.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {
	
	List<Comment> findByUserIdAndPostPostId(Integer userId, Integer postId);

}
