package com.chandan.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chandan.blog.models.Post;

public interface PostRepo extends JpaRepository<Post, Integer> {
	
	List<Post> findByUserId(Integer userId);
	List<Post> findByCategoryCategoryId(Integer categoryId);
	
	List<Post> findByTitleContaining(String title);

}
