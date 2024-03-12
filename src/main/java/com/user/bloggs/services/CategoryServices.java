package com.user.bloggs.services;

import java.util.List;

import com.user.bloggs.payloads.CategoryDto;

public interface CategoryServices {

	// create category
	CategoryDto createCategory(CategoryDto categoryDto);
	
	//update category
	CategoryDto updateCategory(CategoryDto categoryDto , Integer categoryId);
	
	//get single category
	
	CategoryDto getCategoryById(Integer categoryID);
	
	//get all category list
	List<CategoryDto> getAllCategory();
	
	// delete category
	void deleteCategory(Integer categoryId);
}
