package com.chandan.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chandan.blog.models.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
