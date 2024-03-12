package com.user.bloggs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.bloggs.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
