package com.chandan.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.chandan.blog.exceptions.ResourceNotFoundException;
import com.chandan.blog.models.*;
import com.chandan.blog.payloads.*;
import com.chandan.blog.repositories.*;
import com.chandan.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", " user Id", userId));

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", " category Id", categoryId));

		Post post = this.modelMapper.map(postDto, Post.class);
		post.setAddedDate(new Date());
		post.setImageName("default.png");
		post.setCategory(category);
		post.setUser(user);

		Post savedPost = postRepo.save(post);
		UserDto userDto = this.modelMapper.map(savedPost.getUser(), UserDto.class);
		CategoryDto categoryDto = this.modelMapper.map(savedPost.getCategory(), CategoryDto.class);
		
		postDto = this.modelMapper.map(savedPost, PostDto.class);
		postDto.setCategory(categoryDto);
		postDto.setUser(userDto);
		return postDto;
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", " post id", postId));

		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());

		Post updatedPost = this.postRepo.save(post);
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", " post id", postId));

		this.postRepo.delete(post);
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

		Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> postPage = this.postRepo.findAll(pageable);

		List<Post> allPosts = postPage.getContent();

		List<PostDto> allPostDtos = allPosts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		PostResponse postResponse = new PostResponse();

		postResponse.setContent(allPostDtos);
		postResponse.setPageNumber(postPage.getNumber());
		postResponse.setPageSize(postPage.getSize());
		postResponse.setTotalElements(postPage.getTotalElements());
		postResponse.setTotalPages(postPage.getTotalPages());
		postResponse.setLastPage(postPage.isLast());

		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", " post id", postId));

		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostsByCategoryId(Integer categoryId) {
		List<Post> allCategoryPost = this.postRepo.findByCategoryCategoryId(categoryId);

		List<PostDto> allCategoryPostDto = allCategoryPost.stream()
				.map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

		return allCategoryPostDto;
	}

	@Override
	public List<PostDto> getPostsByUserId(Integer userId) {
		List<Post> allUserPosts = this.postRepo.findByUserId(userId);

		List<PostDto> allUserPostDtos = allUserPosts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		return allUserPostDtos;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> posts = this.postRepo.findByTitleContaining(keyword);
		
		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
		.collect(Collectors.toList());
		
		return postDtos;
	}

}
