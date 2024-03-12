package com.user.bloggs.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.bloggs.entity.Category;

import com.user.bloggs.exceptions.ResourceNotFoundException;
import com.user.bloggs.payloads.CategoryDto;
import com.user.bloggs.repository.CategoryRepo;
import com.user.bloggs.services.CategoryServices;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor

@Service
public class CategoryServicesImpl implements CategoryServices {

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		
//		Category category = this.modelMapper.map(categoryDto,Category.class);
		
//		Category createdUser = this.categoryRepo.save(this.modelMapper.map(categoryDto,Category.class));
		
		
		return this.modelMapper.map(this.categoryRepo.save(this.modelMapper.map(categoryDto,Category.class)), CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {

		Category existingCategory = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category name", "category Id", categoryId));
		
		
		existingCategory.setCategoryName(categoryDto.getCategoryName());
		existingCategory.setCategoryDescription(categoryDto.getCategoryDescription());
		
		
		return this.modelMapper.map(this.categoryRepo.save(existingCategory), CategoryDto.class);
	}

	@Override
	public CategoryDto getCategoryById(Integer categoryID) {

		CategoryDto categoryDto = this.modelMapper.map(this.categoryRepo.findById(categoryID).orElseThrow(() -> new ResourceNotFoundException("category name", "category Id", categoryID)), CategoryDto.class);
		
		
		return categoryDto;
	}

	@Override
	public List<CategoryDto> getAllCategory() {

		List<Category> categories = this.categoryRepo.findAll();
		
		List<CategoryDto> categoryDtos = categories.stream()
	            .map(category -> modelMapper.map(category, CategoryDto.class))
	            .collect(Collectors.toList());
		return categoryDtos;
		
		
	}

	@Override
	public void deleteCategory(Integer categoryId) {

		Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category name", "category Id", categoryId));
		this.categoryRepo.delete(category);
	}

}
