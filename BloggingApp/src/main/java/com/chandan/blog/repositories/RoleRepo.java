package com.chandan.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chandan.blog.models.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {

}
