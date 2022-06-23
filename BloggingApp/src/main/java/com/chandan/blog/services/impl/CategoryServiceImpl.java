package com.chandan.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chandan.blog.exceptions.ResourceNotFoundException;
import com.chandan.blog.models.Category;
import com.chandan.blog.payloads.CategoryDto;
import com.chandan.blog.repositories.CategoryRepo;
import com.chandan.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		 Category toCreate = this.modelMapper.map(categoryDto, Category.class);
		 Category savedCategory = this.categoryRepo.save(toCreate);
		 return this.modelMapper.map(savedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category oldCategory = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", " Category Id", categoryId));
		
		oldCategory.setCategoryTitle(categoryDto.getCategoryTitle());
		oldCategory.setCategoryDescription(categoryDto.getCategoryDescription());
		
		Category updatedCategory = this.categoryRepo.save(oldCategory);
		
		return this.modelMapper.map(updatedCategory, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category delCategory = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", " Category Id", categoryId));
		
		this.categoryRepo.delete(delCategory);
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", " Category Id", categoryId));
		
		return this.modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		List<Category> allCategory = this.categoryRepo.findAll();
		
		List<CategoryDto> categoryDtos = allCategory.stream().map(category -> this.modelMapper.map(category, CategoryDto.class))
		.collect(Collectors.toList());
		
		return categoryDtos;
	}

}
